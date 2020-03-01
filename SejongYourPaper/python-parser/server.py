import traceback
import threading
import time
import json

import pika
import parser_exceptions

import parser_constants
from parser_interface   import ParserInterface
from parser_logger      import Logger
from parser_mailman     import Mailman

# 메세지 서버 주소
RABBITMQ_SERVER_URL = parser_constants.RABBITMQ_SERVER_URL

# 메세지 콜백 정의
def cons_callback(ch, method, properties, body):
    logger      = Logger()
    '''
        바디 메세지 형식 : "type, uid, targetUrl, extra"

        - type      : 페이지 타입 (DETAIL_LINK, CITING_LINK)
        - UID       : WOS 레코드 uid
        - targetUrl : 목적 파싱 주소
        - extra     : 기타 인자
    '''
    ch.basic_ack(delivery_tag=method.delivery_tag)
    
    # 로직 시작 #
    try:
        ## 파라미터 추출 ##
        str_body: str   = str(body.decode('utf-8'))
        args: dict      = json.loads(str_body)

        targetType  = args['sourceType']
        uid         = args['UID']
        targetURL   = args['targetUrl']
        # extra       = args['extra']
        
        mailman = Mailman()
        parser = ParserInterface(mailman)


        ## 파라미터 추출 끝 ##
        logger.log('info', 'Message received "%s" ' % (', '.join(args)[:50]))
        logger.log('info', 'URL: (%s)'%(targetURL))
        
        ## 파싱 쓰레드 준비
        x = threading.Thread(target=parser.run, args=(targetType, uid, targetURL,))

        ## 과잉 쓰레딩 방어 (threading.enumerate()은 다른 스레드도 포함함을 주의)
        logger.log('info', len(threading.enumerate()))
        while len(threading.enumerate()) > 5:
            logger.log('info', 'Waiting for other threads, 10sec.')
            time.sleep(10)

        ## 쓰레드 시작
        logger.log('info', 'Parsing thread will start for "%s". ' % (uid))
        x.start()

        ## 서버 부담을 덜기 위한 기다림
        logger.log('info', 'Waiting... 5sec')
        time.sleep(5)

    ## 전역 에러 처리 ##
    except parser_exceptions.LoginRequired as lre:
        print(lre)
        traceback.print_exc()
    except parser_exceptions.NoPaperDataError as npde:
        print(npde)
        traceback.print_exc()
    except Exception as e:
        print(e)
        traceback.print_exc()
    else:
        logger.log('info', 'Parsing %s ends'%(uid))

    ## 전역 에러 끝 ##
    # 로직 끝 #
# 메세지 콜백 정의 끝


# 서버 메인
if __name__ == "__main__":
    # RabbitMQ 커넥션 설정 #
    connection = pika.BlockingConnection(pika.URLParameters(RABBITMQ_SERVER_URL))
    channel = connection.channel()

    channel.basic_consume('targetUrl-queue', cons_callback)

    # 메세지 소비 시작 #
    Logger().log('info', 'Waiting for targetURLs. To exit press CTRL+C')
    channel.start_consuming()
# 서버 메인 끝
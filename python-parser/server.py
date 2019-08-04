import traceback

import pika
import parser_exceptions
from parser_interface import ParserInterface
from parser_logger import Logger

# 메세지 서버 주소
RABBITMQ_SERVER_URL = 'amqp://sejong:sejong1234@localhost:5672/'


# 메세지 콜백 정의
def cons_callback(ch, method, properties, body):
    logger = Logger("SERVER_ENTR")

    '''
        바디 메세지 형식 : "type$,uid$,SID$,targetURL$,extra"

        - type      : 페이지 타입 (DETAIL, CITE_CNT)
        - uid       : WOS 레코드 uid
        - SID       : WOS 접속 세션 아이디
        - targetURL : 목적 파싱 주소
        - extra     : 기타 인자
    '''
    ch.basic_ack(delivery_tag=method.delivery_tag)
    
    # 로직 시작 #
    try:
        ## 파라미터 추출 ##
        args = str(body.decode('utf-8')).split('$,')

        if (len(args) != 5):
            '''인자는 반드시 지정된 갯수로 제공되어야 한다.'''
            raise Exception("Invalid message body: %s", body)

        parser = ParserInterface()

        targetType  = args[0]
        uid         = args[1]
        SID         = args[2]
        targetURL   = args[3]
        extra       = args[3]

        ## 파라미터 추출 끝 ##
        logger.log('info', 'Parsing %s started'%(uid))
        logger.log('info', 'URL: (%s)'%(targetURL))
        
        ## 파싱 시작
        parser.run(targetType, uid, SID, targetURL)

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

    channel.basic_consume('targetURLs', cons_callback)

    # 메세지 소비 시작 #
    Logger('SERVER_MAIN').log('info', 'Waiting for targetURLs. To exit press CTRL+C')
    channel.start_consuming()
# 서버 메인 끝
import traceback

import pika
import parser_exceptions
from parser_logger import Logger

# 메세지 서버 주소
RABBITMQ_SERVER_URL = 'amqp://sejong:sejong1234@localhost:5672/'

# 
def send(sender: str, targetType: str, uid: str, targetURL: str, extra: str):
    '''
        바디 메세지 형식 : "type$,uid$,targetURL$,extra"

        - type      : 페이지 타입 (DETAIL, CITE_CNT)
        - uid       : WOS 레코드 uid
        - targetURL : 목적 파싱 주소
        - extra     : 기타 인자
    '''
    logger = Logger("MAILMAN FOR %s" % sender)
    connection = pika.BlockingConnection(pika.URLParameters(RABBITMQ_SERVER_URL))
    channel = connection.channel()

    msg = '$,'.join([targetType, uid, targetURL, extra])
    channel.basic_publish(
        exchange='create', routing_key='target.create.recursion',
        body=msg
    )

    logger.log('info', 'MSG sent.')
    logger.log('info', 'MSG >> %s' % msg)

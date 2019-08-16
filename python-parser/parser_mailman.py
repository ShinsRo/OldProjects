import os
import traceback
from datetime import timedelta
from datetime import datetime

import json
import pika
import parser_exceptions
from parser_logger import Logger

# 메세지 서버 주소
RABBITMQ_SERVER_URL = 'amqp://sejong:sejong1234@127.0.0.1:5672/'

class Mailman():
    def __init__(self, parser_id = None):
        self.parser_id  = parser_id if parser_id else os.getpid()
        self.es_time    = 0
        self.logger     = Logger()
        self.connection = None
        self.channel    = None

    def connect(self):
        now     = datetime.now()

        self.es_time    = now
        self.connection = pika.BlockingConnection(pika.URLParameters(RABBITMQ_SERVER_URL))
        self.channel    = self.connection.channel()

    def send(self, sender: str, targetType: str, uid: str, targetURL: str, extra: str):
        '''
            바디 메세지 형식 : "type, uid, targetURL, extra"

            - type      : 페이지 타입 (DETAIL, CITE_CNT)
            - uid       : WOS 레코드 uid
            - targetURL : 목적 파싱 주소
            - extra     : 기타 인자
        '''
        if not self.connection or self.connection.is_closed:
            self.connect()

        logger      = self.logger
        channel     = self.channel

        obj = {
            'sourceType'    : targetType,
            'UID'           : uid,
            'targetURL'     : targetURL,
            'extra'         : extra
        }

        msg = json.dumps(obj)
        channel.basic_publish(
            exchange='create',  routing_key='target.create.recursion',
            body=msg
        )

        self.send_flow('broker', uid, 'IN_PROGRESS')

        logger.log('info', 'MSG sent.')
        logger.log('info', 'MSG >> %s' % msg)

    def send_flow(self, to, uid, state):
        if not self.connection or self.connection.is_closed:
            self.connect()

        channel     = self.channel

        msg = json.dumps({
            'from'  : self.parser_id,
            'to'    : to,
            'UID'   : uid,
            'state' : state
        })

        channel.basic_publish(
            exchange='any',  routing_key='flow.parser',
            body=msg
        )
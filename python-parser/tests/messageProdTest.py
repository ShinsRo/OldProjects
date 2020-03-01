

import pika

RABBITMQ_SERVER_URL = 'amqp://remote-dev:compact@www.siotman.com:15673/'

if __name__ == "__main__":
    connection = pika.BlockingConnection(pika.URLParameters(RABBITMQ_SERVER_URL))
    channel = connection.channel()
    for idx in range(0,10):
        msg = "detail$,uid$,SSSIIIDDD$,http://testest%d.com$,상태"%idx
        channel.basic_publish(exchange='test-ex',
                      routing_key='test.log',
                      body=msg)
        print(msg)

 
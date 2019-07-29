

import pika

RABBITMQ_SERVER_URL = 'amqp://sejong:sejong1234@127.0.0.1:5672/'

if __name__ == "__main__":
    connection = pika.BlockingConnection(pika.URLParameters(RABBITMQ_SERVER_URL))
    channel = connection.channel()
    for idx in range(0,10):
        msg = "detail$,uid$,SSSIIIDDD$,http://testest%d.com$,상태"%idx
        channel.basic_publish(exchange='create',
                      routing_key='target.create.test',
                      body=msg)
        print(msg)




import pika
RABBITMQ_SERVER_URL = 'amqp://sejong:sejong1234@localhost:5672/'

if __name__ == "__main__":
    connection = pika.BlockingConnection(pika.URLParameters(RABBITMQ_SERVER_URL))
    channel = connection.channel()
    msgs = 0
    def cons_callback(ch, method, properties, body):
        global msgs

        msgs += 1
        print(" [%d] Received %r" % (msgs, body))
        ch.basic_ack(delivery_tag=method.delivery_tag)

    channel.basic_consume('targetURLs', cons_callback)

    print(' [*] Waiting for messages. To exit press CTRL+C')
    channel.start_consuming()

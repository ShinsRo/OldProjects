import logging
import threading

class Logger():
    def __init__(self, name = None):
        self.name = name if name else threading.current_thread()

        format = "%(asctime)s %(message)s"
        logging.basicConfig(
            format=format, level=logging.INFO,
            datefmt="%H:%M:%S"
        )
    
    def log(self, level, msg):
        msg = '%s %s' % (self.name, msg)
        if      level == 'info':
            logging.info(msg)
        elif    level == 'error':
            logging.error(msg)
        elif    level == 'debug':
            logging.debug(msg)
        else:
            logging.info(msg)
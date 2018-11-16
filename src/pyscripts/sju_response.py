import logging
import sys
import json

class SJUresponse():
    def __init__(self, name):
        # Logging configuration
        spath = sys.path[0]
        self.name = name
        handler = logging.FileHandler(filename=spath + "/name.log", mode="a", encoding="utf-8")
        # log_fh = open(spath + "/.log", "w", encoding="utf-8")
        streamHandler = logging.StreamHandler()
        logging.basicConfig(handlers=[handler, streamHandler],
                            level=logging.INFO,
                            format='time:%(asctime)s@lineout:%(message)s&')

        self.stdout = logging.getLogger()

    def print(self, command, msg):
        res = {'name':self.name, 'command':command, 'msg': msg}
        self.stdout.info(json.dumps(res))
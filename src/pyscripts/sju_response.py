import logging
import os
import sys
import json
import urllib
class SJUresponse():
    def __init__(self, name):
        self.name = name
        # Logging configuration
        # self.spath = sys.path[0]
        # handler = logging.FileHandler(filename=self.spath + "/_temp.log", mode="w+", encoding="utf-8")
        # log_fh = open(spath + "/.log", "w", encoding="utf-8")
        streamHandler = logging.StreamHandler()
        logging.basicConfig(handlers=[streamHandler],
                            level=logging.INFO,
                            format='time:%(asctime)s#@lineout:%(message)s#&')

        self.stdout = logging.getLogger(name)

    def print(self, command, msg=None, target=None, res=None):
        if self.name == 'MultiCitationSearch':
            pass
        if command == 'res' or command == 'cres':
            returnRes = {'name':self.name, 'command':command, 'target':target, 'res': res}
        elif command == 'log' or command == 'err' or command == 'sysErr':
            # returnRes = {'name':self.name, 'command':command, 'msg': msg}
            returnRes = {'name':self.name, 'command':command, 'msg': urllib.parse.quote(msg)}
        elif command == 'errObj':
            returnRes = {'name':self.name, 'command':command, 'msg':msg}
        
        try:
            returnJSON = json.dumps(returnRes, allow_nan=False)
            self.stdout.info(returnJSON)
        except Exception as e:
            msg = '통신 JSON 제작에 실패했습니다.'
            returnJSON = json.dumps({'command':'sysErr', 'msg':urllib.parse.quote(msg)})
            self.stdout.info(returnJSON)
            returnJSON = json.dumps({'command':'sysErr', 'msg':str(e)})
            self.stdout.info(returnJSON)

        # print(returnRes)
import logging
import os
import sys
import json
import urllib
import traceback

class SJUresponse():
    def __init__(self, name):
        self.name = name
        streamHandler = logging.StreamHandler()
        logging.basicConfig(handlers=[streamHandler],
                            level=logging.INFO,
                            format='time:%(asctime)s#@lineout:%(message)s#&')

        self.stdout = logging.getLogger(name)

    def print(self, command, msg=None, target=None, res=None):
        retrunRes = ''
        # if self.name == 'MultiCitationSearch':
        #     pass
        if command == 'res' or command == 'cres' or command == 'mres' or command == 'ares':
            returnRes = {'name':self.name, 'command':command, 'target':target, 'res': res}
        elif command == 'log' or command == 'err' or command == 'sysErr':
            # returnRes = {'name':self.name, 'command':command, 'msg': msg}
            returnRes = {'name':self.name, 'command':command, 'msg': urllib.parse.quote(msg)}
        elif command == 'errObj':
            returnRes = {'name':self.name, 'command':command, 'msg':str(msg)}
            traceback.print_tb(msg.__traceback__) 
        try:
            returnJSON = json.dumps(returnRes, allow_nan=False)
            self.stdout.info(returnJSON)
        except Exception as e:
            msg = '통신 JSON 제작에 실패했습니다.'
            returnJSON = json.dumps({'command':'sysErr', 'msg':urllib.parse.quote(msg)})
            self.stdout.info(returnJSON)

        # print(returnRes)
import logging
import os
import sys
import json
import urllib
class SJUresponse():
    def __init__(self, name):
        # Logging configuration
        self.spath = sys.path[0]
        self.name = name
        handler = logging.FileHandler(filename=self.spath + "/name.log", mode="a", encoding="utf-8")
        # log_fh = open(spath + "/.log", "w", encoding="utf-8")
        streamHandler = logging.StreamHandler()
        logging.basicConfig(handlers=[handler, streamHandler],
                            level=logging.INFO,
                            format='time:%(asctime)s#@lineout:%(message)s#&')

        self.stdout = logging.getLogger(name)

    def print(self, command, msg=None, target=None, res=None):
        spath = self.spath

        if command == 'res':
            returnRes = {'name':self.name, 'command':command, 'target':target, 'res': res}
        elif command == 'log' or command == 'err':
            returnRes = {'name':self.name, 'command':command, 'msg': urllib.parse.quote(msg)}
            # res = {'name':self.name, 'command':command, 'msg': msg}
        
        returnJSON = json.dumps(returnRes, allow_nan=False)
        # if len(returnJSON) > 8000 :
        #     del returnRes['res']
        #     returnRes['command'] = 'tooLong'

        #     cnt = 0
        #     tempFileName = 'temp%d.log'%(cnt)
        #     returnRes['path'] = spath + '/' +tempFileName
        #     while(os.path.exists(returnRes['path'])) :
        #         cnt += 1
        #         tempFileName = 'temp%d.log'%(cnt)
        #         returnRes['path'] = spath + '/' +tempFileName
            
        #     returnJSON = json.dumps(returnJSON)             
        #     with open(returnRes['path'], 'w') as returnFile:
        #         returnFile.write(returnJSON)
        #         returnFile.close()

        self.stdout.info(returnJSON)
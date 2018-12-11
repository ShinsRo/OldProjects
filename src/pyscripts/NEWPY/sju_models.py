

import os
import sys
import json
import urllib
import logging
import traceback

class UI_Stream():
    def __init__(self, name, thread, res_name):
        self.name = name
        self.thread = thread
        self.res_name = res_name
        streamHandler = logging.StreamHandler()
        formatter = logging.Formatter(
            'time:%(asctime)s#@lineout:%(message)s#&',
            '%m-%d %H:%M:%S'    
        )
        logging.basicConfig(
            handlers=[streamHandler],
            level=logging.INFO,
        )
        streamHandler.formatter = formatter
        streamHandler.setLevel(logging.NOTSET)
        
        self.stdout = logging.getLogger(name)

    def push(self, command, msg=None, target=None, res=None):
        return_res = { 'name':self.name, 'thread':self.thread, 'command':command }
        thread = self.thread

        if command == 'res':
            return_res.update({ 'command':self.res_name, 'target':target, 'res': res })
        elif command == 'log' or command == 'err' or command == 'sysErr':
            msg = '[%s thread] %s'%(thread, msg)
            
            return_res.update({ 'msg': msg })
            print(return_res)

            return_res.update({ 'msg': urllib.parse.quote(msg) })
        elif command == 'errObj':
            return_res.update({ 'msg':str(msg) })
            traceback.print_tb(msg.__traceback__)
        try:
            return_JSON = json.dumps(return_res, allow_nan=False)
            # print(return_res)
            self.stdout.info(return_JSON)
        except Exception as e:
            msg = '통신 JSON 제작에 실패했습니다.'
            return_JSON = json.dumps({'command':'sysErr', 'msg':urllib.parse.quote(msg)})
            self.stdout.info(return_JSON)

class SearchLock():
    def __init__(self):
        self.lock = threading.Lock()
        self.cnt = 0
        self.limit = 180

    def count_before(self, sres, browser):
        with self.lock:
            sres.print(command="log", msg="총 서비스 검색 횟수 %d회"%self.cnt)
            sres.print(command="log", msg="히스토리 삭제로부터 %d회"%(self.cnt%self.limit))
            self.cnt += 1
            if self.cnt % self.limit == 0:
                sres.print(command="log", msg="검색 제한으로 인해 히스토리를 삭제합니다.")
                history = browser.select('a.snowplow-search-history')
                browser.follow_link(history[0])

                WOS_CombineSearches_input_form = browser.get_form(action='/WOS_CombineSearches.do')
                tag = browser.find(action='/WOS_CombineSearches.do')
                dSetList = tag.find_all('input', attrs={"name":"dSet"})
                for dSet in dSetList:
                    dSet['checked'] = 'true'
                    WOS_CombineSearches_input_form.add_field(Input(dSet))

                if len(dSetList) > 0:
                    browser.submit_form(WOS_CombineSearches_input_form)
                sres.print(command="log", msg="히스토리를 모두 삭제했습니다.")
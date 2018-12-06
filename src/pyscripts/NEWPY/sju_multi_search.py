import sju_utiles
import sju_models
import sju_CONSTANTS
import sju_exceptions

import re
import requests
import threading
import concurrent.futures

from bs4 import BeautifulSoup

class SessionContainer():
    '''
    '''
    def __init__(self, thread_id):
        '''
        '''
        self.qid = 0
        self.container_lock = threading.Lock()
        self.session = False
        self.res_name = 'mres'
        self.base_url = "http://apps.webofknowledge.com"
        self.ui_stream = sju_models.UI_Stream('multi_search', 'multi sub%d'%thread_id, self.res_name)

        self.set_session()

    def set_session(self):
        '''
        '''
        ui_stream = self.ui_stream

        # 세션 갱신
        ui_stream.push(command='log', msg=sju_CONSTANTS.STATE_MSG[1001])
        session = requests.Session()

        # 세션 SID, JSESSIONID 요청
        ui_stream.push(command='log', msg=sju_CONSTANTS.STATE_MSG[1101])
        res = session.get("http://www.webofknowledge.com")

        # SID요청 에러 판별
        if res.status_code == requests.codes.ok:
            self.SID = session.cookies['SID'].replace("\"", "")
            self.jsessionid = session.cookies['JSESSIONID']
            ui_stream.push(command='log', msg='SID : %s'%self.SID)
            ui_stream.push(command='log', msg='JSESSIONID : %s'%self.jsessionid)

            # 요청 성공
            ui_stream.push(command='log', msg=sju_CONSTANTS.STATE_MSG[1201])
        else:
            # 요청 실패
            ui_stream.push(command='err', msg=sju_CONSTANTS.STATE_MSG[1301][0])
            raise sju_exceptions.InitSessionError()

        if self.session: self.session.close()
        
        self.session = session

    def run(self, query, start_year, end_year, gubun):
        print(query, start_year, end_year, gubun)
        self.container_lock.release()
    
class MultiSearch():
    '''
    '''
    def __init__(self):
        '''
        '''
        self.main_lock = threading.Lock()
        self.res_name = 'mres'
        self.threading_amount = 2
        self.ui_stream = sju_models.UI_Stream('multi_search', 'multi main', self.res_name)

        containers = {}
        ui_stream = self.ui_stream
        threading_amount = self.threading_amount

        ui_stream.push(command='log', msg=sju_CONSTANTS.STATE_MSG[2101])
        with concurrent.futures.ThreadPoolExecutor(max_workers=threading_amount) as executor:
            future_containers = {
                executor.submit(SessionContainer, thread_id,
                ): thread_id for thread_id in range(threading_amount) 
            }

            for future in concurrent.futures.as_completed(future_containers):
                thread_id = future_containers[future]
                try:
                    containers[thread_id] = future.result()
                    ui_stream.push(command='log', msg='multi sub%d의 %s'%(thread_id, sju_CONSTANTS.STATE_MSG[2201]))
                except Exception as e:
                    ui_stream.push(command='err', msg='multi sub%d의 %s'%(thread_id, sju_CONSTANTS.STATE_MSG[2301][0]))
        
        self.containers = containers
        ui_stream.push(command='log', msg='%s'%(sju_CONSTANTS.STATE_MSG[2201]))

    def get_idle_container(self):
        self.main_lock.acquire()
        idle_container = ''

        for thread_id, container in self.containers.items():
            if not container.container_lock.locked():
                idle_container = container
                container.container_lock.acquire()
                break
        
        self.main_lock.release()
        return idle_container

    def start(self, start_year, end_year, gubun, path):
        '''
        '''
        containers = self.containers
        threading_amount = self.threading_amount

        query_list = []
        query_list = sju_utiles.get_query_list_from_file(path)

        with concurrent.futures.ThreadPoolExecutor(max_workers=threading_amount) as executor:
            idle_container = self.get_idle_container()
            future_run = {
                executor.submit(
                    idle_container.run, query, start_year, end_year, gubun
                ): query for query in query_list
            }
            
            for future in concurrent.futures.as_completed(future_run):
                query = future_run[future]
                try:
                    future.result()
                except Exception as e:
                    ui_stream.push(command='err', msg='%s 검색 중 에러발생'%query)

    
if __name__ == "__main__":
    ms = MultiSearch()
    ms.start('2010','2018','TI', 
    'C:\\Users\\F\\Desktop\\testData\\test2.xlsx')
        
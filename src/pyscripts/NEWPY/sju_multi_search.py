import sju_utiles
import sju_models
import sju_CONSTANTS
import sju_exceptions
import sju_single_search

import re
import math
import threading
import concurrent.futures

from bs4 import BeautifulSoup

class SingleSearchContainer():
    '''
    '''
    def __init__(self, thread_id):
        '''
        '''
        self.qid = 0
        self.thread_id = thread_id
        self.query_package = []
        self.container_lock = threading.Lock()
        self.session = False
        self.res_name = 'mres'
        self.base_url = "http://apps.webofknowledge.com"
        self.ui_stream = sju_models.UI_Stream('multi_search', 'multi sub%d'%thread_id, self.res_name)

        self.single_search = sju_single_search.SingleSearch(thread_id = thread_id)

    def set_query_package_and_portion(self, query_package, portion):
        self.query_package = query_package
        self.portion = portion

    def run(self, start_year, end_year, gubun):

        with self.container_lock:
            for query in self.query_package:
                self.single_search.start(query, start_year, end_year, gubun)
        
class MultiSearch():
    '''
    '''
    def __init__(self):
        '''
        '''
        self.res_name = 'mres'
        self.threading_amount = 32
        self.ui_stream = sju_models.UI_Stream('multi_search', 'multi main', self.res_name)

        containers = {}
        ui_stream = self.ui_stream
        threading_amount = self.threading_amount

        ui_stream.push(command='log', msg=sju_CONSTANTS.STATE_MSG[2101])
        with concurrent.futures.ThreadPoolExecutor(max_workers=threading_amount) as executor:
            future_containers = {
                executor.submit(SingleSearchContainer, thread_id,
                ): thread_id for thread_id in range(threading_amount) 
            }

            for future in concurrent.futures.as_completed(future_containers):
                thread_id = future_containers[future]
                try:
                    containers[thread_id] = future.result()
                    ui_stream.push(command='log', msg='multi sub%d의 %s'%(thread_id, sju_CONSTANTS.STATE_MSG[2201]))
                except sju_exceptions.InitSessionError as ise:
                    ui_stream.push(command='err', msg='multi sub%d %s'%(thread_id, '접근 거부 User-Agent 삭제'))
                    # del containers[thread_id]
                    self.threading_amount -= 1
                except Exception as e:
                    ui_stream.push(command='err', msg='multi sub%d의 %s'%(thread_id, sju_CONSTANTS.STATE_MSG[2301][0]))
                    raise e
        
        ui_stream.push(command='log', msg='가용 스레드 수 = %d'%self.threading_amount)
        if self.threading_amount < 2:
            ui_stream.push(command='err', msg='서버가 거부한 초기화가 너무 많아 검색을 수행할 수 없습니다.')
            raise sju_exceptions.InitMultiSessionErr()
            

        self.containers = containers
        ui_stream.push(command='log', msg='%s'%(sju_CONSTANTS.STATE_MSG[2201]))


    def distribute(self, target_id, query_package, portion):
        containers = self.containers
        containers[target_id].set_query_package_and_portion(query_package, portion)


    def start(self, start_year, end_year, gubun, path):
        '''
        '''
        ui_stream = self.ui_stream
        containers = self.containers
        threading_amount = self.threading_amount

        query_list = []
        query_list = sju_utiles.get_query_list_from_file(path)
        portion = math.ceil(len(query_list) / threading_amount) 

        target_ids = containers.keys()
        for thread_id in target_ids:
            start_idx = thread_id * portion
            self.distribute(thread_id, query_list[start_idx : start_idx + portion], portion)

        with concurrent.futures.ThreadPoolExecutor(max_workers=threading_amount) as executor:
            future_run = {
                executor.submit(
                    container.run, start_year, end_year, gubun
                ): (thread_id, container) for thread_id, container in containers.items() 
            }
            
            for future in concurrent.futures.as_completed(future_run):
                thread_id, container = future_run[future]
                try:
                    future.result()
                except Exception as e:
                    ui_stream.push(command='err', msg='%d 검색 중 에러발생'%thread_id)
                    raise e

if __name__ == "__main__":
    ms = MultiSearch()
    ms.start('2010','2018','TI', 
    'C:\\Users\\F\\Desktop\\testData\\test2.xlsx')
        
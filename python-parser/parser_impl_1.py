import re
import random
import requests
import datetime

from bs4 import BeautifulSoup

import parser_mailman
import parser_utiles
import parser_logger

from func_parse_detail import parse_detail

class WosParser():
    def __init__(self):
        self.logger = parser_logger.Logger('WOS_PARSER')
        self.base_url = "http://apps.webofknowledge.com"
        self.server_url = ""

    # run 메서드
    def run(self, targetType: str, uid: str, SID: str, targetURL: str):
        # HTTP 요청 및 Bytes -> BS4 #
        session = requests.Session()
        session.cookies['SID'] = SID

        headers = session.headers
        headers.update({'User-Agent': str(random.getrandbits(16))})

        http_res = session.get(targetURL)
        soup = BeautifulSoup(http_res.content, features="lxml")
        # HTTP 요청 끝 #

        # 상세 페이지 링크
        if targetType == 'DETAIL_LINK':
            ## 상세 페이지 처리 ##
            # [0100] PHASE 시작
            self.logger.log('info', '[0100] %s PHASE started.' % targetType)

            # [0110] CITE_CNT_LINK와 논문 정보 파싱
            self.logger.log('info', '[0101] Getting "CITE_CNT_LINK" & Parsing the detail page.')

            link, paper_data = parse_detail(soup, uid)

            # [0120] 파싱한 정보 DB 저장 요청
            self.logger.log('info', '[0102] Requesting server to save detail data.')

            # requests.post('', data={})

            # [0130] 파싱한 링크 메세징, CITE_CNT_LINK 타입 파싱 요청
            self.logger.log('info', '[0103] Messaging CITE_CNT_LINK.')

            # parser_mailman.send('DETAIL_LINK', 'CITE_CNT_LINK', uid, SID, link, 'NONE')

            ## 상세 페이지 처리 끝 ##

        # 대상을 인용 중인 논문 리스트 페이지 링크
        elif targetType == 'CITE_CNT_LINK':

            pass
        
        # 년도별 인용 횟수 정보가 있는 페이지 링크
        elif targetType == 'TIMES_CITED_BY_YEAR_LINK':
            pass
        else:
            pass
        
        return False
    # run 끝
    
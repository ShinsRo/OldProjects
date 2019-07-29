import re
import random
import requests
import datetime

from bs4 import BeautifulSoup

import parser_utiles
import parser_logger

class WosParser():
    def __init__(self):
        self.logger = parser_logger.Logger('WosParser')
        self.base_url = "http://apps.webofknowledge.com"

    # run 메서드
    def run(self, SID: str, targetType: str, targetURL: str):
        session = requests.Session()
        session.cookies['SID'] = SID

        headers = session.headers
        headers.update({'User-Agent': str(random.getrandbits(16))})

        http_res = session.get("http://www.naver.com")
        soup = BeautifulSoup(http_res.content, features="lxml")

        if targetType == 'detail':
            link, paper_data = self.parse_detail(session, soup)
            print(link, paper_data)
        elif targetType == 'cite_cnt':
            pass
        
        return True
    # run 끝

    # parse_detail 메서드
    def parse_detail(self, session: requests.Session, soup: BeautifulSoup):
        
        return ("link", {})
    # parse_detail 끝
    
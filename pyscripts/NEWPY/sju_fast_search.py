import sju_utiles
import sju_models
import sju_CONSTANTS
import sju_exceptions

import re
import random
import requests

from sju_utiles import os
from sju_utiles import UserAgent
from sju_utiles import BeautifulSoup

class FastSearch():
    '''
		FastSearch Class는 빠른검색 기능을 제공
		이 Class는 start() 함수를 사용하여 검색한 논문의 정보의 제목,각 논문의 정보를 보낸다.
        (저자 목록,출판일,발행처,권,호,페이지)
    '''
    def __init__(self, thread_id = None, cookies = None):
        '''
			FastSearch constructor
            :param thread_id: 쓰레드 네임
            :Param cookies: 쿠키 값 변수
			:param qid: session 재호출을 위한 카운트변수
			:param session: 세션 변수
			:param base_url: 메인 도메인 URL
			:param res_name: push command 변수
			:param ui_stream: logging을 위한 함수 호출 후 저장
			:return:
        '''
        self.qid = 0
        self.session = False
        self.base_url = "http://apps.webofknowledge.com"
        self.res_name = 'fres'
        self.thread_id = 'fast main'
        ui_stream_name = 'fast_search'
        self.ui_stream = sju_models.UI_Stream(ui_stream_name, self.thread_id, self.res_name)

        self.set_session(cookies=cookies)


    def set_session(self, cookies = None):
        '''
			세션 갱신 함수
			:param cookies: 쿠키 값 저장 변수
			:return:
        '''
        MAX_TRIES = 5
        self.qid = 0
        # self.ua = UserAgent()
        # self.userAgent = self.ua.random
        # self.headers = {'User-Agent': self.userAgent}
        # self.headers = {}

        ui_stream = self.ui_stream

        tries = 0
        session = requests.Session()

        # SID와 JSESSIONID가 주어질 경우
        if cookies:
            session = sju_utiles.set_user_agent(session)
            session.cookies.update(cookies)
            self.SID = session.cookies['SID'].replace("\"", "")
            self.jsessionid = session.cookies['JSESSIONID']
            ui_stream.push(command='log', msg='SID : %s'%self.SID)
            ui_stream.push(command='log', msg='JSESSIONID : %s'%self.jsessionid)
            
        while tries < MAX_TRIES and not cookies:
            # 세션 갱신
            ui_stream.push(command='log', msg=sju_CONSTANTS.STATE_MSG[1001])
            session = sju_utiles.set_user_agent(session)
            # 세션 SID, JSESSIONID 요청
            ui_stream.push(command='log', msg=sju_CONSTANTS.STATE_MSG[1101])
            res = session.get('http://apps.webofknowledge.com', allow_redirects=False)

            for redirect in session.resolve_redirects(res, res.request):
                if 'SID' in session.cookies.keys(): break

            # SID요청 에러 판별
            if res.status_code == requests.codes.FOUND or res.status_code == requests.codes.OK:
                if res.url.find('login') > -1:
                    raise sju_exceptions.LoginRequired()
                
                self.SID = session.cookies['SID'].replace("\"", "")
                self.jsessionid = session.cookies['JSESSIONID']
                
                ui_stream.push(command='log', msg='SID : %s'%self.SID)
                ui_stream.push(command='log', msg='JSESSIONID : %s'%self.jsessionid)

                # 요청 성공
                ui_stream.push(command='log', msg=sju_CONSTANTS.STATE_MSG[1201])
                break
            elif res.status_code == 403:
                # ui_stream.push(command='log', msg='스레드가 403 상태 메세지를 받았습니다.')
                tries += 1
                ui_stream.push(command='log', msg='서버에서 거부하여 2초 뒤 재시도합니다. [%d/%d]'%(tries, MAX_TRIES))
                continue
            else:
                # 요청 실패
                ui_stream.push(command='err', msg=sju_CONSTANTS.STATE_MSG[1301][0])
                raise sju_exceptions.InitSessionError()

        if tries >= MAX_TRIES:
            raise sju_exceptions.InitSessionError()

        if self.session: self.session.close()
        self.session = session


    def start(self, query, start_year, end_year, gubun):
        '''
			논문 빠른 검색 및 상세정보 제공함수
			:param query: keyword, p_authors, organization 각각 문자열 
			:param start_year: 시작년도
			:param end_year: 끝년도
			:param gubun: 검색 구분 카테고리
			:return:
        '''
        # Sejong Univ 로 고정
        #####################
        query = (query[0], query[1], 'Sejong Univ')
        
        session = self.session
        base_url = self.base_url
        ui_stream = self.ui_stream

        keyword = query[0]
        p_authors = query[1]
        organization = query[2]

        # 검색속도 향상을 위한 헤더 랜더마이즈
        # orginal_headers = session.headers
        # session.headers.update({'User-Agent': str(random.getrandbits(32))})
        # [단계 1/3] 최초 검색
        #########################################################################
        ui_stream.push(command='log', msg=sju_CONSTANTS.STATE_MSG[1002])
        ui_stream.push(command='log', msg='검색어 : %s'%keyword)

        if keyword.find('=') != -1:
            ui_stream.push(command='err', msg=sju_CONSTANTS.STATE_MSG[1300][0])
            ui_stream.push(
                command='res', target='errQuery', 
                res={'query': query, 'msg': sju_CONSTANTS.STATE_MSG[1300][0]}
            )
            return

        action_url = '/WOS_GeneralSearch.do'
        form_data = {
            'action': 'search',
            'product': 'WOS',
            'search_mode': 'GeneralSearch',
            'sa_params': 'WOS||%s|http://apps.webofknowledge.com|\''%self.SID,
            'SID': self.SID,
            'value(input1)': keyword,
            'value(select1)': gubun,
            'startYear': start_year,
            'endYear': end_year,
        }
        if organization != '':
            form_data.update({
                'limitStatus': 'expanded',
                'value(bool_1_2)': 'AND',
                'value(input2)': organization,
                'value(select2)': 'AD',
                'fieldCount': '2',
            })
        
        # 검색 요청
        ui_stream.push(command='log', msg=sju_CONSTANTS.STATE_MSG[1102])

        url = base_url + action_url
        # SEJONG WIFI 접속 시 변수명에 특정 문자를 바르게 인코딩하지 못하는 현상
        # 어떤 문자인 지 찾아서 수정하는 작업이 필요.
        # form_data = sju_utiles.get_form_data(action_url, form_data)
        
        self.qid += 1
        http_res = session.post(url, form_data)

        # # 검색 성공
        # if http_res.status_code == requests.codes.ok:
        #     location = http_res.history[0].headers['Location']
        #     reffer = base_url + '/' + location
        
        # # 검색 실패
        # else:
        #     ui_stream.push(command='err', msg=sju_CONSTANTS.STATE_MSG[1302][2])
        #     raise sju_exceptions.RequestsError

        # Access Denied
        if http_res.status_code == 403:
            ui_stream.push(
                command='res', target='errQuery', 
                res={'query': query, 'msg': '검색을 요청했으나 서버가 접근 권한 없음을 반환했습니다.'}
            )
            return

        # http_res = session.get(reffer)
        
        # # Access Denied
        # if http_res.status_code == 403:
        #     ui_stream.push(
        #         command='res', target='errQuery', 
        #         res={'query': query, 'msg': '결과 리스트 페이지를 요청했으나 서버가 접근 권한 없음을 반환했습니다.'}
        #     )
        #     return

        target_content = http_res.content
        soup = BeautifulSoup(target_content, 'html.parser')
        atag = soup.select_one('a.snowplow-full-record')
        try: 
            if not atag:
                raise sju_exceptions.NoPaperDataError()
        # 검색 결과가 없을 경우
        except sju_exceptions.NoPaperDataError:
            ui_stream.push(command='err', msg=sju_CONSTANTS.STATE_MSG[1302][0])
            
            ui_stream.push(
                command='res', target='errQuery', 
                res={'query': query, 'msg': sju_CONSTANTS.STATE_MSG[1302][0]}
            )
            return
        except Exception as e:
            ui_stream.push(command='log', msg=sju_CONSTANTS.STATE_MSG[1303][0])
            raise Exception(e)

        ui_stream.push(command='log', msg=sju_CONSTANTS.STATE_MSG[1202])
        # [단계 3/3] 전체 Fast 데이터 다운로드
        #########################################################################

        # Fast 5000 요청 및 다운로드
        ui_stream.push(command='log', msg=sju_CONSTANTS.STATE_MSG[1204])

        qid = soup.select('input#qid')[0].attrs['value']
        rurl = soup.select('input#rurl')[0].attrs['value']
        self.qid = int(qid)

        action_url = '/OutboundService.do?action=go&&'
        form_data = {
            'qid': str(self.qid),
            'SID': self.SID,
            'mark_to': '5000',
            'markTo': '5000',
        }
        form_data = sju_utiles.get_form_data(action_url, form_data)

        url = base_url + action_url
        http_res = session.post(url, form_data)
        self.qid += 1

        # Access Denied
        if http_res.status_code == 403:
            ui_stream.push(
                command='res', target='errQuery', 
                res={'query': query, 'msg': '인용 논문 자료 다운로드를 요청했으나 서버가 접근 권한 없음을 반환했습니다.'}
            )
            return

        # Fast 5000 데이터 처리
        ui_stream.push(command='log', msg=sju_CONSTANTS.STATE_MSG[1404])

        fast_5000 = http_res.content.decode('utf-8').replace('\r', '')
        fast_5000_list = fast_5000.split('\n')
        keys = fast_5000_list[0].split('\t')
        fast_5000_list = fast_5000_list[1:]
        if fast_5000_list[-1] == '': fast_5000_list.pop()

        article = {}
        articles = []
        for row in fast_5000_list:
            row_list = row.split('\t')
            for idx, key in enumerate(keys):
                article[key] = row_list[idx]
            article['id'] = str(random.getrandbits(8))
            articles.append(article)
            article = {}

        if self.qid > 180:
            self.set_session()

        ui_stream.push(command='res', target='fast_5000', res=articles)
        return

if __name__ == "__main__":
    ss = FastSearch()
    while(True):
        print('입력바람')
        ss.start(
            (input(), input(), input())
            ,'1945',
            '2018',
            'TI'
        )
        

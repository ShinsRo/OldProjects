import sju_utiles
import sju_models
import sju_CONSTANTS
import sju_exceptions

import requests
import random

import math
import threading
import concurrent.futures
from datetime import datetime

from sju_utiles import re
from sju_utiles import BeautifulSoup

class MultiSearchContainer():
    '''
        MultiSearchContainer Class는 중복 검색 함수의 개별검색을 제공
		이 Class는 start() 함수를 사용하여 검색된 한 논문의 관한 상세 정보를 보낸다.
        (IMPACT FACTOR,JCR 순위,연구기관 주소)
    '''
    def __init__(self, ui_stream, cookies = None):
        '''
            MultiSearchContainer constructor
            :param ui_stream: UI를 이어 받기위해 받는 변수
            :param cookies: 쿠키 값을 받는 변수
            :return: 
        '''
        self.session = False
        self.res_name = 'res'
        self.base_url = "http://apps.webofknowledge.com"
        self.ui_stream = ui_stream

    def get_tc_data(self, report_link, paper_data_id, tc_data):
        '''
			인용보고서에서 인용년도를 조회하는 함수 (start() 함수에서 사용)
			:param report_link: 인용보고서 링크
            :param paper_data_id: 
            :param tc_data: 인용년도 연도별 데이터 저장
			:return:
        '''
        self.ui_stream.push(command='log', msg='인용년도 정보를 가져옵니다.')
        session = self.session
        current_year = datetime.today().year

        # [단계 2/3] 인용년도 조회 (병렬 구성)
        #########################################################################

        http_res = session.get(self.base_url + "/" + report_link)
        target_content = http_res.content
        soup = BeautifulSoup(target_content, 'html.parser')

        raw_tc_data = soup.select_one('tr#PublicationYear_raMore_tr')

        tc_tuple_list = re.findall(r'20.. \(.+\)', raw_tc_data.text)
        tmp = ""
        for i in range(len(tc_tuple_list)):
            tmp += tc_tuple_list[i]

        tmp = tmp.replace(' ','')
        tmp = tmp.split(')')
        tmp.sort()
        
        tmp_tc_dict = {}
        for i in range(len(tmp)):
            if tmp[i] == "":
                continue
            a1 = tmp[i][:tmp[i].find('(')]
            a2 = tmp[i][tmp[i].find('(')+1:]
            tmp_tc_dict.update({a1: a2})
            
        print("tmp tc dict : ",tmp_tc_dict)

        tc_dict = {}
        # 빈 연도 0으로 채우기
        for y_now in range(current_year-9,current_year+1):
            try:
                if tmp_tc_dict[str(y_now)] != 0:
                    #print("already have data")
                    tc_dict.update({str(y_now): tmp_tc_dict[str(y_now)]})
            except:
                tc_dict.update({str(y_now): '0'})

        tc_data['tc_dict'] = tc_dict

    def get_one_tc_data(self, report_year, tc_data):
        self.ui_stream.push(command='log', msg='인용년도 정보를 가져옵니다.')
        current_year = datetime.today().year

        if report_year == 'zero':
            a1 = '0'
            a2 = '0'
        else:
            a1 = report_year[:report_year.find('(')]
            a2 = report_year[report_year.find('(')+1: report_year.find(')')]
        
        
        tc_dict = {}
        # 빈 연도 0으로 채우기
        for y_now in range(current_year-9,current_year+1):
            if str(y_now) == a1:
                tc_dict.update({a1: a2})
            else:
                tc_dict.update({str(y_now): '0'})
            
        tc_data['tc_dict'] = tc_dict

    def start(self, query, query_string_url, session, p_authors):
        '''
			각 논문 정보를 보여줄 상세 정보 제공함수
			:param query_string_url: 상세정보를 받을 url 변수
            :param session: session 변수
            :param p_authors:
			:return:
        '''
        session = session
        self.session = session
        ui_stream = self.ui_stream
        base_url = self.base_url
        qid =  query_string_url[query_string_url.find('qid')+4:query_string_url.find('SID')-1]
        local_qid = qid
        SID = query_string_url[query_string_url.find('SID')+4:query_string_url.find('page')-1]
        paper_data_id = str(random.getrandbits(32))

        
        # 임시로 페이지 확인! 로그!!
        #ui_stream.push(command='log', msg='[url]: %s'%query_string_url[query_string_url.find('page'):])
        
        # 상세 정보 페이지 요청
        http_res = session.get(query_string_url)

        # Access Denied
        if http_res.status_code == 403:
            ui_stream.push(
                command='res', target='errQuery', 
                res={'query': query_string_url[query_string_url.find('page'):], 'msg': '해당 논문의 상세 정보를 요청했으나 서버가 접근 권한 없음을 반환했습니다.'}
            )
            return

        # 논문의 정보 및 인용정보 fetch
        #########################################################################
        target_content = http_res.content
        try:
            paper_data, cnt_link = sju_utiles.parse_paper_data(target_content, paper_data_id, "dupl", SID)
        except Exception as e:
            ui_stream.push(command='err', msg=sju_CONSTANTS.STATE_MSG[4302][2])
            ui_stream.push(
                command='res', target='errQuery', 
                res={'query': query_string_url[query_string_url.find('page'):], 'msg': sju_CONSTANTS.STATE_MSG[4302][2]}
            )
            # raise sju_exceptions.FailedToParseError(e, query)
            return
        # 요청 성공
        else:
            ui_stream.push(command='res', target='paperData', res=paper_data)
            ui_stream.push(command='log', msg=sju_CONSTANTS.STATE_MSG[4203])

            try:
                query = [paper_data['title'], p_authors, paper_data['researchAreas']]
            except:
                query = [paper_data['title'], '', '']
        # # 요청 실패

        # 인용 논문 정보
        ui_stream.push(command='log', msg=sju_CONSTANTS.STATE_MSG[4004])
        
        # 인용 횟수에 따른 분기
        # 인용 논문이 없을 때
        test_check = 0
        if not cnt_link:
        #    ui_stream.push(command='log', msg=sju_CONSTANTS.STATE_MSG[4304][0])
            #ui_stream.push(command='log', msg='[url]: %s'%query_string_url)
            #local_qid += 1
            #self.qid += 1
            test_check = 1
        # 인용 논문이 5000개 이상일 때
        elif int(paper_data['timesCited']) > 4999:
            ui_stream.push(command='err', msg=sju_CONSTANTS.STATE_MSG[4304][1])
            ui_stream.push(
                command='res', target='errQuery', 
                res={'query': query_string_url[query_string_url.find('page'):], 'msg': sju_CONSTANTS.STATE_MSG[4304][1]}
            )
            ui_stream.push(command='log', msg='[url]: %s'%query_string_url)
            #local_qid += 1
            #self.qid += 1
            return

        # 인용 리포트 요청
        tc_data = {'tc_dict': []}
        if test_check == 0:
            ui_stream.push(command='log', msg=sju_CONSTANTS.STATE_MSG[4104])

            url = base_url + cnt_link['href']
            http_res = session.get(url)

            # Access Denied
            if http_res.status_code == 403:
                ui_stream.push(
                    command='res', target='errQuery', 
                    res={'query': query, 'msg': '인용 리포트를 요청했으나 서버가 접근 권한 없음을 반환했습니다.'}
                )
                return

            target_content = http_res.content

            soup = BeautifulSoup(target_content, 'html.parser')

            # 인용문 링크는 존재하나, 클릭할 경우 검색 결과가 없다는 메세지가 뜰 때
            if  soup.text.find('Your search found no records') != -1 or soup.text.find('None of the Citing Articles are in your subscription') != -1:
                self.get_one_tc_data('zero', tc_data)
                tc_dict = tc_data['tc_dict']
                ui_stream.push(command='res', target='tc_data', res={'id': paper_data_id, 'tc_data': tc_dict})
                """
                ui_stream.push(command='log', msg=sju_CONSTANTS.STATE_MSG[4304][3])
                ui_stream.push(
                    command='res', target='errQuery', 
                    res={'query': query, 'msg': sju_CONSTANTS.STATE_MSG[4304][3]}
                )
                """
                return

        # 연도별 인용 횟수 가져오기 (NEW PART)
        if cnt_link:
            try:
                year_url = soup.select('a#PublicationYear')[0].attrs['href']
                self.get_tc_data(year_url, paper_data_id, tc_data)
                #print("======================================================")
                #print("tc_data[1] : ",tc_data)
                #print("======================================================")
            except:
                # 만약 인용 횟수가 한 연도만 존재할 때
                year_url = soup.select('div#PublicationYear_tr')[0]
                year_url = year_url.select_one('label.ra-summary-text').text
                year_url = year_url.replace(' ','')
                self.get_one_tc_data(year_url, tc_data)
                #print("======================================================")
                #print("tc_data[2] : ",tc_data)
                #print("======================================================")
        # 인용횟수가 없을 경우 > 인용 정보를 0으로 초기화
        else:
            ui_stream.push(command='log', msg=sju_CONSTANTS.STATE_MSG[4304][0])
            self.get_one_tc_data('zero', tc_data)
            #print("======================================================")
            #print("tc_data[3] : ",tc_data)
            #print("======================================================")
        
        tc_dict = tc_data['tc_dict']
        # 인용 년도 조회 성공 시 출력
        ui_stream.push(command='res', target='tc_data', res={'id': paper_data_id, 'tc_data': tc_dict})
        
        if test_check == 1:
            return

        local_qid = soup.select('input#qid')[0].attrs['value']
        rurl = soup.select('input#rurl')[0].attrs['value']
        times_cited = paper_data['timesCited']
        #self.qid = int(qid)
        local_qid = int(local_qid)

        # Fast 5000 요청 및 다운로드
        ui_stream.push(command='log', msg=sju_CONSTANTS.STATE_MSG[4204])

        action_url = '/OutboundService.do?action=go&&'
        form_data = {
            'qid': str(local_qid),
            'SID': SID,
            'mark_to': times_cited,
            'markTo': times_cited,
        }
        form_data = sju_utiles.get_form_data(action_url, form_data)

        url = base_url + action_url
        http_res = session.post(url, form_data)
        local_qid += 1
        #self.qid += 1 

        # Access Denied
        if http_res.status_code == 403:
            ui_stream.push(
                command='res', target='errQuery', 
                res={'query': query, 'msg': '인용 논문 자료 다운로드를 요청했으나 서버가 접근 권한 없음을 반환했습니다.'}
            )
            return

        # Fast 5000 데이터 처리
        ui_stream.push(command='log', msg=sju_CONSTANTS.STATE_MSG[4404])

        fast_5000 = http_res.content.decode('utf-8').replace('\r', '')
        fast_5000_list = fast_5000.split('\n')
        keys = fast_5000_list[0].split('\t')
        fast_5000_list = fast_5000_list[1:]
        if fast_5000_list[-1] == '': fast_5000_list.pop()

        article = {}
        citing_articles = []
        for row in fast_5000_list:
            row_list = row.split('\t')
            for idx, key in enumerate(keys):
                article[key] = row_list[idx]
            citing_articles.append(article)
            article = {}

        # UI 응답 형식에 맞게 변환
        ##############################################################################
        citingArticles = {'id': paper_data['id'], 'selfCitation': 0, 'othersCitation': 0, 'titles': [], 'authors': [], 'isSelf': []}

        # 기준 저자 검증
        if p_authors != '':
            ui_stream.push(command='log', msg=sju_CONSTANTS.STATE_MSG[1504])
            p_authors = list(map(lambda x: x.replace(' ', '').replace(',', ''), p_authors.split(';')))

        for article in citing_articles:
            citingArticles['titles'] += [article['TI']]
            citingArticles['authors'] += [article['AU']]
            au_temp = article['AU'].replace(' ', '').replace(',', '')
            if p_authors != '':
                found = False
                for pa in p_authors:
                    if re.search(pa, au_temp, flags=re.IGNORECASE):
                        found = True
                        citingArticles['selfCitation'] += 1
                        citingArticles['isSelf'] += ['Self']
                        break

                if not found:
                    citingArticles['othersCitation'] += 1
                    citingArticles['isSelf'] += ['Others\''] 
            else:
                citingArticles['isSelf'] += ['-']

        ui_stream.push(command='log', msg=sju_CONSTANTS.STATE_MSG[4404])
        ui_stream.push(command='res', target='citingArticles', res=citingArticles)
        #ui_stream.push(command='log', msg=sju_CONSTANTS.STATE_MSG[4200][0])
        return
    

class DuplSearch():
    '''
        DuplSearch Class는 논문의 중복 상세검색 기능을 제공 (*연도별 인용횟수는 미적용)
        이 Class는 start() 함수를 사용하여 검색된 2개이상 논문의 관한 상세 정보를 보낸다.
        (IMPACT FACTOR,JCR 순위,연구기관 주소)
    '''
    def __init__(self, cookies = None):
        '''
            DuplSearch constructor
            :param cookies: 쿠키 값 저장변수
            :return:
        '''
        self.qid = 0
        self.session = False
        self.base_url = "http://apps.webofknowledge.com"
        
        self.res_name = 'res'
        self.threading_amount = 10
        self.ui_stream = sju_models.UI_Stream('dupl_search','dupl main', self.res_name)
        self.set_session(cookies)

    def set_session(self, cookies = None):
        '''
			세션 갱신 함수
			:param cookies: 쿠키 값 저장 변수
			:return:
        '''
        MAX_TRIES = 5
        self.qid = 0

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
			둘 이상의 논문에 관한 상세 정보 제공함수
            :param query: 입력 값 (논문 제목,저자)
            :param start_year: 시작년도
            :param end_year: 끝년도
            :param gubun: 검색 구분 카테고리
            :return:
        '''
        # Sejong Univ 로 고정
        query = (query[0], query[1], 'Sejong Univ')

        session = self.session
        base_url = self.base_url
        
        ui_stream = self.ui_stream
        threading_amount = self.threading_amount

        keyword = query[0]
        p_authors = query[1]
        organization = query[2]

        # [단계 1/3] 최초 검색
        #########################################################################
        ui_stream.push(command='log', msg=sju_CONSTANTS.STATE_MSG[4002])
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
        ui_stream.push(command='log', msg=sju_CONSTANTS.STATE_MSG[4102])

        url = base_url + action_url
        
        self.qid += 1
        http_res = session.post(url, form_data)

        # Access Denied
        if http_res.status_code == 403:
            ui_stream.push(
                command='res', target='errQuery', 
                res={'query': query, 'msg': '검색을 요청했으나 서버가 접근 권한 없음을 반환했습니다.'}
            )
            return

        target_content = http_res.content
        soup = BeautifulSoup(target_content, 'html.parser')
        atag_list = soup.select('a.snowplow-full-record')

        #report_link = soup.select('a.citation-report-summary-link')

        # 1페이지 검색 결과가 없을 경우
        try:
            if soup.find(id="footer_formatted_count") == None:
                raise sju_exceptions.NoPaperDataError()
            else:
                total_count = soup.find(id="footer_formatted_count").text
                total_count = int(total_count.replace(",",""))        

            if len(atag_list) == 0:
                raise sju_exceptions.NoPaperDataError()

        # 검색 결과가 없을 경우
        except sju_exceptions.NoPaperDataError:
            ui_stream.push(command='err', msg=sju_CONSTANTS.STATE_MSG[4302][0])
            
            ui_stream.push(
                command='res', target='errQuery', 
                res={'query': query, 'msg': sju_CONSTANTS.STATE_MSG[4302][0]}
            )
            return
        
        except Exception as e:
            ui_stream.push(command='log', msg=sju_CONSTANTS.STATE_MSG[4303][0])
            raise Exception(e)

        ui_stream.push(command='log', msg=sju_CONSTANTS.STATE_MSG[4202])


        # [단계 2/3] 상세 정보 페이지 fetch
        #########################################################################
        ui_stream.push(command='log', msg=sju_CONSTANTS.STATE_MSG[4003])
        query_string = atag_list[0]['href']
        action_url = query_string[0:query_string.find('page')]       

        # 검색할 모든 페이지 url 저장
        page_count = 1
        query_string_list = []

        # 50건의 url만 저장
        if total_count > 50:
            doc_count_range = 51
        else:
            doc_count_range = total_count + 1

        for doc_count in range(1, doc_count_range):
            url = base_url + action_url + "page=" + str(page_count) + "&doc=" + str(doc_count)
            query_string_list.append(url)
            if doc_count % 10 == 0:
                page_count += 1

        # 각 페이지 상세 정보 요청 시작
        ui_stream.push(command='log', msg=sju_CONSTANTS.STATE_MSG[4103])

        MSC = MultiSearchContainer(ui_stream)

        with concurrent.futures.ThreadPoolExecutor(max_workers=threading_amount) as exe:
            future_run = {
                exe.submit(
                    MSC.start , query, q_url, session, p_authors
                ): q_url for q_url in query_string_list
            }
            for future in concurrent.futures.as_completed(future_run):
                q_url = future_run[future]
                try:
                    future.result()
                except Exception as e:
                    ui_stream.push(command='err', msg='[다중검색] 검색 중 에러발생')
                    raise e
        
        if self.qid > 180:
            self.set_session()

        ui_stream.push(command='log', msg=sju_CONSTANTS.STATE_MSG[4200][0])
        return


if __name__ == "__main__":
    ds = DuplSearch()
    print('입력바람1')
    
    ds.start(
            #(input(), input(), input()),
        #('world', '', 'sejong univ'),
        ("Assessing the economic values of World Heritage Sites and the effects of perceived authenticity on their values", '', 'sejong univ'),
        '1945',
        '2018',
        'TI'
    )
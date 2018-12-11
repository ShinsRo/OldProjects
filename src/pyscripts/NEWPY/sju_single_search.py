import sju_utiles
import sju_models
import sju_CONSTANTS
import sju_exceptions

import re
import requests

from bs4 import BeautifulSoup

class SingleSearch():
    '''
    '''
    def __init__(self, thread_id = None):
        '''
        '''
        self.qid = 0
        self.session = False
        self.base_url = "http://apps.webofknowledge.com"
        self.res_name = 'res' if thread_id == None else 'mres'
        self.thread_id = 'single main' if thread_id == None else thread_id
        ui_stream_name = 'single_search' if thread_id == None else 'multi sub'
        self.ui_stream = sju_models.UI_Stream(ui_stream_name, self.thread_id, self.res_name)

        self.set_session()


    def set_session(self):
        '''
        '''
        self.qid = 0
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
            # 요청 self.query_package실패
            ui_stream.push(command='err', msg=sju_CONSTANTS.STATE_MSG[1301][0])
            raise sju_exceptions.InitSessionError()

        if self.session: self.session.close()
        self.session = session
        

    def start(self, query, start_year, end_year, gubun):
        '''
        '''
        session = self.session
        base_url = self.base_url
        ui_stream = self.ui_stream

        keyword = query[0]
        p_authors = query[1]
        organization = query[2]


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
                'value(select2)': 'AD',
                'fieldCount': '2',
            })
        
        # 검색 요청
        ui_stream.push(command='log', msg=sju_CONSTANTS.STATE_MSG[1102])

        url = base_url + action_url
        form_data = sju_utiles.get_form_data(action_url, form_data)
        
        self.qid += 1
        http_res = session.post(url, form_data)

        # 검색 성공
        if http_res.status_code == requests.codes.ok:
            location = http_res.history[0].headers['Location']
            reffer = base_url + '/' + location
        # 검색 실패
        else:
            ui_stream.push(command='err', msg=sju_CONSTANTS.STATE_MSG[1302][2])
            raise sju_exceptions.RequestsError

        # 결과 리스트 페이지가 필요하면 사용
        http_res = session.get(reffer)
        target_content = http_res.content
        soup = BeautifulSoup(target_content, 'html.parser')
        
        atag_list = soup.select('a.snowplow-full-record')
        try: 
            if len(atag_list) == 0:
                raise sju_exceptions.NoPaperDataError()
            elif len(atag_list) > 1:
                raise sju_exceptions.MultiplePaperDataError()
        # 검색 결과가 없을 경우
        except sju_exceptions.NoPaperDataError:
            ui_stream.push(command='err', msg=sju_CONSTANTS.STATE_MSG[1302][0])
            ui_stream.push(
                command='res', target='errQuery', 
                res={'query': query, 'msg': sju_CONSTANTS.STATE_MSG[1302][0]}
            )
            return
        # 검색 결과가 2개 이상일 경우
        except sju_exceptions.MultiplePaperDataError:
            ui_stream.push(command='err', msg=sju_CONSTANTS.STATE_MSG[1302][1])
            ui_stream.push(
                command='res', target='errQuery', 
                res={'query': query, 'msg': sju_CONSTANTS.STATE_MSG[1302][1]}
            )
            return
        except Exception as e:
            ui_stream.push(command='log', msg=sju_CONSTANTS.STATE_MSG[1303][0])
            raise Exception(e)

        ui_stream.push(command='log', msg=sju_CONSTANTS.STATE_MSG[1202])

        # [단계 2/3] 상세 정보 페이지 fetch
        #########################################################################
        ui_stream.push(command='log', msg=sju_CONSTANTS.STATE_MSG[1003])
        
        # 결과 리스트 페이지를 들렀다 오는 경우
        query_string = atag_list[0]['href']

        # 상세 보기 바로 진입
        # qid가 랜덤한 경우가 존재... 사용하기 위해선
        # 이슈가 해결되야함.
        # action_url = '/full_record.do'
        # query_data = {
        #     'page': '1',
        #     'qid': str(self.qid),
        #     'SID': self.SID,
        #     'doc': '1',
        # }
        # query_string = sju_utiles.get_query_string(action_url, query_data)

        # 상세 정보 요청
        ui_stream.push(command='log', msg=sju_CONSTANTS.STATE_MSG[1103])
        
        session.headers['Reffer'] = reffer
        http_res = session.get(base_url + query_string)
        target_content = http_res.content

        # 상세 정보 파싱
        try:
            paper_data, cnt_link = sju_utiles.parse_paper_data(target_content)
            # paper_data['subsidy'] = sju_utiles.get_subsidy01(paper_data, p_authors)

        # 검색 결과가 없을 경우
        except sju_exceptions.NoPaperDataError:
            ui_stream.push(command='err', msg=sju_CONSTANTS.STATE_MSG[1302][0])
            ui_stream.push(
                command='res', target='errQuery', 
                res={'query': query, 'msg': sju_CONSTANTS.STATE_MSG[1302][0]}
            )
            return
        # 검색 결과가 2개 이상일 경우
        except sju_exceptions.MultiplePaperDataError:
            ui_stream.push(command='err', msg=sju_CONSTANTS.STATE_MSG[1302][1])
            ui_stream.push(
                command='res', target='errQuery', 
                res={'query': query, 'msg': sju_CONSTANTS.STATE_MSG[1302][1]}
            )
            return
        except Exception as e:
            ui_stream.push(command='err', msg=sju_CONSTANTS.STATE_MSG[1302][2])
            ui_stream.push(
                command='res', target='errQuery', 
                res={'query': query, 'msg': sju_CONSTANTS.STATE_MSG[1302][2]}
            )
            # raise sju_exceptions.FailedToParseError(e, query)
            return
        # 요청 성공
        else:
            ui_stream.push(command='res', target='paperData', res=paper_data)
            ui_stream.push(command='log', msg=sju_CONSTANTS.STATE_MSG[1203])
        # # 요청 실패
        

        # [단계 3/3] 인용 논문 정보
        #########################################################################
        ui_stream.push(command='log', msg=sju_CONSTANTS.STATE_MSG[1004])

        # 인용 횟수에 따른 분기
        if not cnt_link:
            ui_stream.push(command='log', msg=sju_CONSTANTS.STATE_MSG[1304][0])
            self.qid += 1
            return
        elif int(paper_data['timesCited']) > 500:
            ui_stream.push(command='err', msg=sju_CONSTANTS.STATE_MSG[1304][1])
            ui_stream.push(
                command='res', target='errQuery', 
                res={'query': query, 'msg': sju_CONSTANTS.STATE_MSG[1304][1]}
            )
            self.qid += 1
            return

        # 인용 리포트 요청
        ui_stream.push(command='log', msg=sju_CONSTANTS.STATE_MSG[1104])

        url = base_url + cnt_link['href']
        http_res = session.get(url)

        soup = BeautifulSoup(http_res.content, 'html.parser')
        # 인용문 링크는 존재하나, 클릭할 경우 검색 결과가 없다는 메세지가 뜰 때
        if  soup.text.find('Your search found no records') != -1 or soup.text.find('None of the Citing Articles are in your subscription') != -1:
            ui_stream.push(command='log', msg=sju_CONSTANTS.STATE_MSG[1304][3])
            ui_stream.push(
                command='res', target='errQuery', 
                res={'query': query, 'msg': sju_CONSTANTS.STATE_MSG[1304][3]}
            )
            return
        
        qid = soup.select('input#qid')[0].attrs['value']
        rurl = soup.select('input#rurl')[0].attrs['value']
        times_cited = paper_data['timesCited']
        self.qid = int(qid)

        # Fast 5000 요청 및 다운로드
        ui_stream.push(command='log', msg=sju_CONSTANTS.STATE_MSG[1204])

        action_url = '/OutboundService.do?action=go&&'
        form_data = {
            'qid': str(self.qid),
            'SID': self.SID,
            'mark_to': times_cited,
            'markTo': times_cited,
        }
        form_data = sju_utiles.get_form_data(action_url, form_data)

        url = base_url + action_url
        http_res = session.post(url, form_data)
        self.qid += 1

        # Fast 5000 데이터 처리
        ui_stream.push(command='log', msg=sju_CONSTANTS.STATE_MSG[1404])

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
        citingArticles = {'id': paper_data['id'], 'selfCitation': 0, 'othersCitation': 0, 'titles': [], 'authors': [], 'isSelf': []}

        # 기준 저자 검증
        if p_authors != '':
            ui_stream.push(command='log', msg=sju_CONSTANTS.STATE_MSG[1504])
            p_authors = list(map(lambda x: x.replace(' ', ''), p_authors.split(';')))

        for article in citing_articles:
            citingArticles['titles'] += [article['TI']]
            citingArticles['authors'] += [article['AU']]
            
            if p_authors != '':
                found = False
                for pa in p_authors:
                    if re.search(pa, article['AU'], flags=re.IGNORECASE):
                        found = True
                        citingArticles['selfCitation'] += 1
                        citingArticles['isSelf'] += ['Self']
                        break

                if not found:
                    citingArticles['othersCitation'] += 1
                    citingArticles['isSelf'] += ['Others\''] 
            else:
                citingArticles['isSelf'] += ['-']

        # history 제한 방지
        if self.qid > 180:
            self.set_session()

        ui_stream.push(command='res', target='citingArticles', res=citingArticles)
        ui_stream.push(command='log', msg=sju_CONSTANTS.STATE_MSG[1200][0])
        return
        # [단계 종료] 단일 상세 검색
        #########################################################################
        # # 필요할 경우 사용
        # # Fast 5000 다운로드
        # self.qid += 1
        # action_url = '/ETS/ets.do'
        # query_data = {
        #     'mark_from': '1',               # required
        #     'parentQid': self.qid,          # required
        #     'rurl': rurl,                   # required
        #     'mark_to': '',                  # required
        #     'rid': 'U-9523-2018',           # required_optional
        #     'qid': qid,                     # required
        #     'SID': self.SID,                # required
        #     'totalMarked': times_cited,     # required
        #     'action': 'crsaveToFile',       # required
        #     'fileOpt': 'xls',               # required
        #     'UserIDForSaveToRID': '10957580', # required_optional
        # }

        # query_string = sju_utiles.get_query_string(action_url, query_data)

        # url = base_url + query_string
        # http_res = session.get(url)

        # ui_stream.push(command='log', msg=sju_CONSTANTS.STATE_MSG[1204])


# if __name__ == "__main__":
#     ss = SingleSearch()
#     ss.start(
#         ('없는데이터', '', '')
#         ,'2000','2018','TI'
#     )

# if __name__ == "__main__":
#     ss = SingleSearch()
#     ss.start(
#         ('asd', '', '')
#         ,'2000','2018','TI'
#     )

# if __name__ == "__main__":
#     ss = SingleSearch()
#     ss.start(
#         ('Mathematical Modeling of Nitric Oxide Diffusion in Small Arterioles', '', '')
#         ,'2000','2018','TI'
#     )

if __name__ == "__main__":
    ss = SingleSearch()
    print('입력바람')
    while(True):
        ss.start(
            (input(), input(), input())
            ,'1945',
            '2018',
            'TI'
        )
        

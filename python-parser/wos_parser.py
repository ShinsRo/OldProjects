
import re
import datetime
import random
import requests
from bs4 import BeautifulSoup
import sju_models
# import sju_utiles
import parser_utiles
import sju_exceptions
import sju_CONSTANTS

class ParserInterface():
    def __init__(self, SID):
        self.base_url = "http://apps.webofknowledge.com"

        session = requests.Session();
        session.headers.update({'User-Agent': str(random.getrandbits(16))});
        session.cookies['SID'] = SID;

        self.SID = SID;
        self.session = session;

    def start(self, url):
        paper_data, cnt_link = self.extract_detail(url);
        tc_data = self.extract_tc_data(paper_data, cnt_link);

        paper_data["tc_data"] = tc_data;

        return paper_data;

    def extract_detail(self, url):
        session = self.session;

        http_res = session.get(url);
        target_content = http_res.content;

        # 상세 정보 파싱
        try:
            paper_data, cnt_link = parser_utiles.parse_paper_data(target_content, "1", "single", self.SID)

        # 검색 결과가 없을 경우
        except sju_exceptions.NoPaperDataError:
            print("NoPaperDataError");
            return
        # 검색 결과가 2개 이상일 경우
        except sju_exceptions.MultiplePaperDataError:
            print("MultiplePaperDataError");
            return
        except Exception as e:
            raise e;
        # 요청 성공
        else:
            pass;

        return paper_data, cnt_link;

    def extract_tc_data(self, paper_data, cnt_link):
        # [단계 3/3] 년도별 인용 횟수 파싱
        #########################################################################
        session = self.session
        base_url = self.base_url
        
        # 인용 횟수에 따른 분기
        test_check = 0
        if not cnt_link:
            test_check = 1
        elif int(paper_data['timesCited']) > 4999:
            return

        # 인용 리포트 요청
        tc_data = {'tc_dict': []}
        if test_check == 0:
            url = base_url + cnt_link['href']
            http_res = session.get(url);

            # Access Denied
            if http_res.status_code == 403:
                return

            target_content = http_res.content

            soup = BeautifulSoup(target_content, 'html.parser')

            # 인용문 링크는 존재하나, 클릭할 경우 검색 결과가 없다는 메세지가 뜰 때
            if  soup.text.find('Your search found no records') != -1 or soup.text.find('None of the Citing Articles are in your subscription') != -1:
                self.get_one_tc_data('zero', tc_data)
                tc_dict = tc_data['tc_dict']
                return
        
        # 연도별 인용 횟수 가져오기 (NEW PART)
        if cnt_link:
            try:
                year_url = soup.select('a#PublicationYear')[0].attrs['href']
                self.get_all_tc_data(year_url, tc_data)
            except:
                # 만약 인용 횟수가 한 연도만 존재할 때
                year_url = soup.select('div#PublicationYear_tr')[0]
                year_url = year_url.select_one('label.ra-summary-text').text
                year_url = year_url.replace(' ','')
                self.get_one_tc_data(year_url, tc_data)
        # 인용횟수가 없을 경우 > 인용 정보를 0으로 초기화
        else:
            self.get_one_tc_data('zero', tc_data)
        
        tc_dict = tc_data['tc_dict']

        return tc_dict;
        
    def get_one_tc_data(self, report_year, tc_data):
        current_year = datetime.date.today().year

        if report_year == 'zero':
            a1 = '0'
            a2 = '0'
        else:
            a1 = report_year[:report_year.find('(')]
            a2 = report_year[report_year.find('(')+1: report_year.find(')')]
        
        
        tc_dict = {}
        # 빈 연도 0으로 채우기
        for y_now in range(1945, current_year+1):
            if str(y_now) == a1:
                tc_dict.update({a1: a2})
            else:
                tc_dict.update({str(y_now): '0'})
            
        tc_data['tc_dict'] = tc_dict

    def get_all_tc_data(self, year_url, tc_data):
        current_year = datetime.date.today().year
        '''
			인용보고서에서 인용년도를 조회하는 함수
        '''
        http_res = self.session.get(self.base_url + "/" + year_url);
        soup = BeautifulSoup(http_res.content, 'html.parser')

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

        tc_dict = {}
        # # 빈 연도 0으로 채우기
        for y_now in range(1945,current_year+1):
            try:
                if tmp_tc_dict[str(y_now)] != 0:
                    tc_dict.update({str(y_now): tmp_tc_dict[str(y_now)]})
            except:
                tc_dict.update({str(y_now): '0'})

        tc_data['tc_dict'] = tc_dict
        return tc_dict
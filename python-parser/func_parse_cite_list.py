import re

from bs4 import BeautifulSoup

def parse_cite_list(soup: BeautifulSoup, uid: str):
    link            = ''            # 추가 연도별 tc data가 있는 페이지 링크
    tc_data         = {}            # 연도별 tc data
    # tc_paper_data   = []

    # TAG 정의 #

    TAG = {
        'PUB_YEAR_LINK' : 'a#PublicationYear',
        'PUB_YEARS'     : 'div#PublicationYear_tr'
    }

    # TAG 정의 끝 #

    # FAST5000 데이터 다운로드 #
    # 현재는 생략한다.
    # FAST5000 데이터 다운로드 끝#

    # 더 많은 연도를 조회할 필요가 있는 지 확인 #
    pub_year_link = soup.select_one(TAG['PUB_YEAR_LINK'])

    if pub_year_link:
        link = pub_year_link['href']
        return link, tc_data
    # 더 많은 연도를 조회할 필요가 있는 지 확인 끝 #

    pub_year_div = soup.select_one(TAG['PUB_YEARS'])

    regex_cnt_by_year = r'([1-9][0-9]{3}) \(([0-9]+)\)'
    ms = re.findall(regex_cnt_by_year, pub_year_div.text)

    for tub in ms:
        tc_data[tub[0]] = int(tub[1])
    
    return link, tc_data
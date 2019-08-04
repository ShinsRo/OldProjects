import re

from bs4 import BeautifulSoup

def parse_tc_data(soup: BeautifulSoup, uid: str):
    tc_data         = {}            # 연도별 tc data

    # TAG 정의 #

    TAG = {
        'TC_DATA_TR' : 'tr#PublicationYear_raMore_tr',
    }

    # TAG 정의 끝 #

    # TC DATA 파싱 #
    raw_tc_data = soup.select_one(TAG['TC_DATA_TR'])

    regex_cnt_by_year = r'([1-9][0-9]{3}) \(([0-9]+)\)'
    ms = re.findall(regex_cnt_by_year, raw_tc_data.text)

    for tub in ms:
        tc_data[tub[0]] = int(tub[1])
    
    return tc_data
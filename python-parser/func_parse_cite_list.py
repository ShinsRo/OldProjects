import re
import requests

from bs4 import BeautifulSoup
from parser_logger import Logger

import parser_constants
import parser_exceptions as exceptions

def parse_cite_list(soup: BeautifulSoup, session: requests.Session, uid: str):
    logger          = Logger()      # 로기
    link            = ''            # 추가 연도별 tc data가 있는 페이지 링크
    tc_data         = {}            # 연도별 tc data
                
    # tc_paper_data   = []

    # TAG 정의 #

    TAG = {
        'PUB_YEAR_LINK' : 'a#PublicationYear',
        'PUB_YEARS'     : 'div#PublicationYear_tr'
    }

    # TAG 정의 끝 #

    # 예외 페이지 처리 #
    if (re.search(r'None of the Citing Articles are in your subscription', soup.text, re.I)):
        raise exceptions.CiteListNoSubsError()
    # 예외 페이지 처리 끝 #

    # FAST5000 데이터 다운로드 #
    SID = soup.select('input#SID')[0].attrs['value']
    qid = soup.select('input#qid')[0].attrs['value']
    rurl = soup.select('input#rurl')[0].attrs['value']

    action_url = '/OutboundService.do?action=go&&'
    form_data = {
        'qid': str(qid),
        'SID': SID,
        'mark_to': '5000',
        'markTo': '5000',
    }
    form_data = parser_constants.get_form_data(action_url, form_data)

    url = parser_constants.WOS_BASE_URL + action_url
    http_res = session.post(url, form_data)
    # FAST5000 데이터 다운로드 끝 #

    # FAST5000 데이터 정제 #
    fast_5000 = http_res.content.decode('utf-8').replace('\r', '')
    fast_5000_list = fast_5000.split('\n')
    keys = fast_5000_list[0].split('\t')
    fast_5000_list = fast_5000_list[1:]
    if fast_5000_list[-1] == '': fast_5000_list.pop()

    articles = []
    for row in fast_5000_list:
        article = {}

        row_list = row.split('\t')
        for idx, key in enumerate(keys):
            article[key.lower()] = row_list[idx]

        published_year = article['py'].strip() if article['py'] else 'unknown'
        published_date = article['pd'].strip() if article['pd'] else published_year
        published_month = published_date.split()[0]

        if published_year in tc_data:
            tc_data[published_year]['total'] += 1
        else:
            tc_data[published_year] = { 'total' : 1 }
        
        if published_month in tc_data[published_year]:
            tc_data[published_year][published_month] += 1
        else :
            tc_data[published_year][published_month] = 1

        articles.append(article)
    
    # FAST5000 데이터 정제 끝 #

    # # 더 많은 연도를 조회할 필요가 있는 지 확인 #
    # logger.log('info', 'CITE_LIST//[%s] 더 많은 연도를 조회할 필요가 있는 지 확인' % uid)
    # pub_year_link = soup.select_one(TAG['PUB_YEAR_LINK'])

    # if pub_year_link:
    #     link = pub_year_link['href']
    #     return link, tc_data
    # # 더 많은 연도를 조회할 필요가 있는 지 확인 끝 #

    # pub_year_div = soup.select_one(TAG['PUB_YEARS'])

    # regex_cnt_by_year = r'([1-9][0-9]{3}) \(([0-9]+)\)'
    # ms = re.findall(regex_cnt_by_year, pub_year_div.text)

    # for tub in ms:
    #     tc_data[tub[0]] = int(tub[1])
    
    # logger.log('info', 'CITE_LIST//[%s] DONE' % uid)
    return link, tc_data, articles
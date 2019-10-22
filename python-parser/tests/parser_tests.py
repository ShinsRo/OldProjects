import os
import sys

sys.path.append(
    os.path.dirname(
        os.path.abspath(
            os.path.dirname(os.path.abspath(__file__))
        )
    )
)

import requests
import random

from bs4 import BeautifulSoup

import parser_constants
import parser_interface

from func_parse_detail      import parse_detail
from func_parse_cite_list   import parse_cite_list
from func_parse_tc_data     import parse_tc_data

def get_soup(path):
    content = ''
    
    with open(path, 'r') as sample:
        
        line = sample.readline()
        while line:
            content += line
            line = sample.readline()

    assert content

    soup = BeautifulSoup(content, features="lxml")

    return soup

def detail_page_proc_test():
    
    soup = get_soup('./tests/resource/detail_sample_WOS:000307154400005.html')
    link, paper_data = parse_detail(soup, 'test')

    soup = get_soup('./tests/resource/WOS:A1997WH59900007.html')
    link, paper_data = parse_detail(soup, 'test')

    soup = get_soup('./tests/resource/WOS:000085778500012.html')
    link, paper_data = parse_detail(soup, 'test')

    soup = get_soup('./tests/resource/detail_sample_WOS:000079302700020.html')
    link, paper_data = parse_detail(soup, 'test')

    soup = get_soup('./tests/resource/detail_sample_WOS:A1997XX11500012.html')
    link, paper_data = parse_detail(soup, 'test')

    soup = get_soup('./tests/resource/detail_sample_WOS:A1996UC95200020.html')
    link, paper_data = parse_detail(soup, 'test')

    soup = get_soup('./tests/resource/detail_sample_1.html')
    link, paper_data = parse_detail(soup, 'test')

    assert link                                 is ''
    assert len(paper_data['parsedAuthorList'])  is 3
    assert paper_data['reprint']['name']        == 'Lee, SK'
    assert paper_data['grades'][0]['caped']     == 'SSCI'

    soup = get_soup('./tests/resource/detail_sample_2.html')

    link, paper_data = parse_detail(soup, 'test')

    assert link                                 is not ''
    assert len(paper_data['parsedAuthorList'])  is 34
    assert paper_data['timesCited']             == 1162
    assert paper_data['reprint']['name']        == 'Ahn, JK'
    # assert paper_data['grades'][0]['caped'] == 'SSCI'       

    soup = get_soup('./tests/resource/detail_sample_WOS:000475354700041.html')

    link, paper_data = parse_detail(soup, 'test')


def cite_cnt_page_proc_test():
    session = requests.Session()

    headers = session.headers
    
    ua          = parser_constants.USER_AGENT_LIST
    rand_val    = random.random() * 10000
    user_agent  = ua[int(rand_val) % len(ua)]
    headers.update({'User-Agent': user_agent})

    http_res = session.get(
        'http://gateway.webofknowledge.com/gateway/Gateway.cgi?GWVersion=2&SrcApp=PARTNER_APP&SrcAuth=LinksAMR&KeyUT=WOS:000457071500001&DestLinkType=RelatedRecords&DestApp=ALL_WOS&UsrCustomerID=f6bb6a9d62a1652eee5cbcc7e544ea0c'
    )
    soup = BeautifulSoup(http_res.content, features="lxml")
    link, tc_data, articles = parse_cite_list(soup, session, 'WOS:000457071500001')



    print(link, tc_data, articles)

def times_cited_page_proc_test():
    soup = get_soup('./tests/resource/times_cited_sample_1.html')

    tc_data = parse_tc_data(soup, 'test')

    print(tc_data)
    
if __name__ == "__main__":
    # 디테일 페이지 처리 테스트
    # detail_page_proc_test()

    # 인용 목록 페이지 처리 테스트
    cite_cnt_page_proc_test()

    # 연도별 인용 횟수 페이지 처리 테스트
    times_cited_page_proc_test()
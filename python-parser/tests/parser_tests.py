import os
import sys

sys.path.append(
    os.path.dirname(
        os.path.abspath(
            os.path.dirname(os.path.abspath(__file__))
        )
    )
)

import parser_interface
from bs4 import BeautifulSoup

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

    assert link                             is ''
    assert len(paper_data['authors'])       is 3
    assert paper_data['reprint']['name']    == 'Lee, SK'
    assert paper_data['grades'][0]['caped'] == 'SSCI'

    soup = get_soup('./tests/resource/detail_sample_2.html')

    link, paper_data = parse_detail(soup, 'test')

    assert link                             is not ''
    assert paper_data['timesCited']         == 1162
    assert len(paper_data['authors'])       is 34
    assert paper_data['reprint']['name']    == 'Ahn, JK'
    # assert paper_data['grades'][0]['caped'] == 'SSCI'       

    soup = get_soup('./tests/resource/detail_sample_WOS:000475354700041.html')

    link, paper_data = parse_detail(soup, 'test')


def cite_cnt_page_proc_test():
    soup = get_soup('./tests/resource/cite_list_sample_1.html')
    
    link, tc_data = parse_cite_list(soup, 'test')

    print(link, tc_data)

def times_cited_page_proc_test():
    soup = get_soup('./tests/resource/tc_data_sample_1.html')

    tc_data = parse_tc_data(soup, 'test')

    print(tc_data)
    
if __name__ == "__main__":
    # 디테일 페이지 처리 테스트
    detail_page_proc_test()

    # 인용 목록 페이지 처리 테스트
    cite_cnt_page_proc_test()

    # 연도별 인용 횟수 페이지 처리 테스트
    times_cited_page_proc_test()
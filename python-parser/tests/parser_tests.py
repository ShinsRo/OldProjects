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

from func_parse_detail import parse_detail

def detail_page_proc_test():
    content = ''
    
    with open('./tests/resources/detail_sample_1.html', 'r') as sample:
        
        line = sample.readline()
        while line:
            content += line
            line = sample.readline()

    assert content

    soup = BeautifulSoup(content, features="lxml")
    link, paper_data = parse_detail(soup, 'test')

    assert link                             is ''
    assert len(paper_data['authors'])       is 3
    assert paper_data['reprint']['name']    == 'Lee, SK'
    assert paper_data['grades'][0]['caped'] == 'SSCI'

def cite_cnt_page_proc_test():
    pass

def times_cited_page_proc_test():
    pass

if __name__ == "__main__":
    # 디테일 페이지 처리 테스트
    detail_page_proc_test()

    # 인용 목록 페이지 처리 테스트
    cite_cnt_page_proc_test()

    # 연도별 인용 횟수 페이지 처리 테스트
    times_cited_page_proc_test()
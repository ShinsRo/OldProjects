
# paper_data  :dict   = {         # 논문 정보 
#     'uid'           : uid,      # 논문 아이디
#     'timesCited'    : 0,        # 인용 횟수
#     'authors'       : [],       # 저자 리스트
#     'firstAuthor'   : {},       # 제 1 저자
#     'reprint'       : {},       # 교신 저자
#     'jcr'           : {},       # 저널 랭크
#     'grades'        : [],       # 논문 등급
# }
# # authors 원소 형태
# '''
#     author = {
#         'name'      : ''
#         'full_name' : '',
#         'addresses' : ['', '']
#     }
# '''
# # grades 원소 형태
# '''
# {
#     'full_grade': '', 
#     'caped': ''
# }

import json
import requests

SERVER_URL = 'http://127.0.0.1:9400'

def get_test():
    res = requests.get('%s/?test=테스트'%SERVER_URL)
    print(res.content)

def post_test():
    uid = 'TESTUID'
    paper_data  :dict   = {         # 논문 정보 
        'uid'           : uid,      # 논문 아이디
        'timesCited'    : '0',        # 인용 횟수
        'authors'       : [],       # 저자 리스트
        'firstAuthor'   : {},       # 제 1 저자
        'reprint'       : {},       # 교신 저자
        'jcr'           : {},       # 저널 랭크
        'grades'        : [],       # 논문 등급
        'recordState'   : 'valid'   # 레코드 상태
    }

    paper_data['authors'] += [{'name': 'SSK', 'full_name': 'SSKIM', 'addresses': ['ADDRESS1', 'ADDRESS2']}]
    paper_data['authors'] += [{'name': 'SDK', 'full_name': 'SDKIM', 'addresses': ['ADDRESS3', 'ADDRESS4']}]
    paper_data['authors'] += [{'name': 'SYK', 'full_name': '', 'addresses': ['ADDRESS1']}]
    paper_data['reprint'] = paper_data['authors'][0]
    paper_data['grades']  += [{'full_grade': 'FG', 'caped': 'fg'}]

    session = requests.session()
    res = session.post('%s/save'%SERVER_URL, json=paper_data)

    print(res.content)

if __name__ == '__main__':
    get_test()
    post_test()
    
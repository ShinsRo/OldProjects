

import re

from bs4 import BeautifulSoup

import parser_exceptions as exceptions

def parse_detail(soup: BeautifulSoup, uid: str):
    ret_link    :str    = ''        # 대상을 인용 중인 논문 목록 링크
    paper_data  :dict   = {         # 논문 정보 
        'uid'           : uid,      # 논문 아이디
        'timesCited'    : 0,        # 인용 횟수
        'authors'       : [],       # 저자 리스트
        'firstAuthor'   : {},       # 제 1 저자
        'reprint'       : {},       # 교신 저자
        'jcr'           : {},       # 저널 랭크
        'grades'        : [],       # 논문 등급
        'recordState'   : ''        # 레코드 상태
    }
    # authors 원소 형태
    '''
        author = {
            'name'      : ''
            'fullName' : '',
            'addresses' : ['', '']
        }
    '''
    # grades 원소 형태
    '''
    {
        'fullGrade': '', 
        'caped': ''
    }
    '''

    # Tags 정의 #
    TAG = {
        'PAGING_BTN'    : 'a.paginationNext',
        'CITE_CNT_LINK' : 'a.snowplow-citation-network-times-cited-count-link',
        'RECORD_INFOS'  : 'div.block-record-info',
        'JCR_TABLE'     : 'table.JCR_Category_table',
        'GRADES'        : 'div.flex-justify-start > ul > span.box-label',
    }
    # Tags 정의 끝 #


    # 예외 페이지 처리 #
    pbtn = soup.select(TAG['PAGING_BTN'])

    if not pbtn and re.search('Record not available', soup.text, re.I):
        raise exceptions.RecordNotAvailableError()

    if not pbtn and re.search('Access denied'       , soup.text, re.I):
        raise exceptions.AcessDeniedError()

    if not pbtn:
        raise exceptions.NoPaperDataError()
    # 예외 페이지 처리 끝 #


    # 인용 횟수 및 링크 #
    cnt_link = soup.select_one(TAG['CITE_CNT_LINK'])

    if not cnt_link:
        ret_link = ''
        paper_data['timesCited'] = 0
    else:
        ret_link = cnt_link['href']
        paper_data['timesCited'] = int(cnt_link.span.text)
    # 인용 횟수 및 링크 끝 #

    # 저자 및 주소 #
    authors         = []
    reprint_name    = ''
    address_map     = {}

    raw_author_by       = ''
    raw_author_infos    = ''

    record_infos = soup.select(TAG['RECORD_INFOS'])

    target_flag = 0
    for info in record_infos:
        if target_flag is 2:
            break

        # 저자
        if info.text.find('By:') != -1:
            target_flag += 1
            raw_author_by = info.text.split(':')[1]
        
        # 주소
        if info.text.find('Author Information') != -1:
            target_flag += 1
            raw_author_infos = info.text

    regex_sp = r'\n|(&nbsp)|(  )|(\\xa0)|(\t)'
    raw_author_by       = re.sub(regex_sp, '', raw_author_by, flags=re.M)
    raw_author_infos    = re.sub(regex_sp, '', raw_author_infos, flags=re.M)
    
    ## 원본 주소 데이터 정제 ##
    ptn_reprint = re.compile(r'.+Reprint Address:+(?P<name>[A-Z,a-z.\- ]+)')
    ptn_address = re.compile(r'\[ (?P<address_key>\d+) \] (?P<address>[A-Za-z,.\- ]+)')

    m = ptn_reprint.match(raw_author_infos)
    reprint_name = m.group('name').strip()

    for address_tup in ptn_address.findall(raw_author_infos):
        address_map[address_tup[0].strip()] = address_tup[1].strip()

    ## 원본 주소 데이터 정제 끝 ##

    ## 원본 저자 데이터 정제 ##
    '''By:Zhou, B (Zhou, Bo)[1 ]; Xu, YP (Xu, Yanping)[2 ]; Lee, SK (Lee, Seul Ki)[3,4 ]'''
    ptn_author_line   = re.compile(r'(?P<name>[A-Z,a-z.\- ]+) (\((?P<full_name>[A-Z,a-z.\- ]+)\))?(\[(?P<address_keys>[0-9, ]+)\])?')
    
    for raw_author in raw_author_by.split(';'):
        m = ptn_author_line.match(raw_author.strip())
        author = {
            'name'      : m.group('name').strip(),
            'fullName' : m.group('full_name').strip() if m.group('full_name') else '',
            'addresses' : [],
            'reprint'   : False
        }

        address_keys = m.group('address_keys')

        if not address_keys:
            address_keys = []

        for key in address_keys.split(','):
            key = key.strip()
            author['addresses'].append(address_map[key])

        if reprint_name == author['name']:
            author['reprint'] = True
            paper_data['reprint'] = author

        authors.append(author)

    paper_data['firstAuthor']   = authors[0]
    paper_data['authors']       = authors
    ## 원본 저자 데이터 정제 끝 ##
    # 저자 및 주소 끝 #


    # JCR #
    # 일단 생략한다.
    # JCR 끝 # 


    # 등급 #
    grades          = []

    labels = soup.select(TAG['GRADES'])

    for label in labels:
        full_grade  = label.text.replace('- ', '').strip()
        caped       = re.sub(r'[ a-z]+', r'', full_grade)

        grades += [{'fullGrade': full_grade, 'caped': caped}]

    paper_data['grades'] = grades
    # 등급 끝 #

    return (ret_link, paper_data)
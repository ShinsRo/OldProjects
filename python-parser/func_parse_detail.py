

import re

from bs4 import BeautifulSoup

import parser_exceptions as exceptions
from parser_logger import Logger

def parse_detail(soup: BeautifulSoup, uid: str):
    logger              = Logger()  # 로거
    ret_link    :str    = ''        # 대상을 인용 중인 논문 목록 링크
    paper_data  :dict   = {         # 논문 정보 
        'uid'               : uid,      # 논문 아이디
        'timesCited'        : 0,        # 인용 횟수
        'parsedAuthorList'  : [],       # 저자 리스트
        'firstAuthor'       : {},       # 제 1 저자
        'reprint'           : {},       # 교신 저자
        'journalImpact'     : {},       # 저널 랭크
        'grades'            : [],       # 논문 등급
        'recordState'       : '',       # 레코드 상태
    }
    # parsedAuthorList 원소 형태
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
        'SOURCE_TITLE'  : 'source_title_txt_label',
        'JCR_BLOCK'     : 'div.overlayJCRblock',
        'IF_TABLE'      : 'table.Impact_Factor_table',
        'JCR_TABLE'     : 'table.JCR_Category_table',
        'JCR_TEXT'      : 'p.overlayJCRtext',
        'GRADES'        : 'div.flex-justify-start > ul > span.box-label',
    }
    # Tags 정의 끝 #


    # 예외 페이지 처리 #
    logger.log('info', 'DETAIL//[%s] 예외 페이지 처리' % uid)
    pbtn = soup.select(TAG['PAGING_BTN'])

    if not pbtn and re.search('Record not available', soup.text, re.I):
        raise exceptions.RecordNotAvailableError()

    if not pbtn and re.search('server error', soup.text, re.I):
        raise exceptions.RecordNotAvailableError()

    if not pbtn and re.search('Access denied', soup.text, re.I):
        raise exceptions.AcessDeniedError()

    if not pbtn:
        raise exceptions.NoPaperDataError()
    # 예외 페이지 처리 끝 #


    # 인용 횟수 및 링크 #
    logger.log('info', 'DETAIL//[%s] 인용 횟수 및 링크' % uid)
    cnt_link = soup.select_one(TAG['CITE_CNT_LINK'])

    if not cnt_link:
        ret_link = ''
        paper_data['timesCited'] = 0
    else:
        ret_link = cnt_link['href']
        paper_data['timesCited'] = int(cnt_link.span.text)
    # 인용 횟수 및 링크 끝 #

    # 저자 및 주소 #
    logger.log('info', 'DETAIL//[%s] 저자 및 주소' % uid)
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
    logger.log('info', 'DETAIL//[%s] 원본 주소 데이터 정제' % uid)
    ptn_reprint = re.compile(r'.+Reprint Address:+(?P<name>[A-Z,a-z.\'\- ]+)\(reprint author\)(?P<address>[A-Z,a-z.\'\- 0-9&]+)?')
    ptn_address = re.compile(r'\[ (?P<address_key>\d+) \] (?P<address>[A-Za-z,.\'\- 0-9&]+)')

    m = ptn_reprint.match(raw_author_infos)
    
    if m:
        reprint_name    = m.group('name').strip()       if m.group('name') else None
        reprint_address = m.group('address').strip()    if m.group('address') else None
    else:
        reprint_name    = None
        reprint_address = None

    for address_tup in ptn_address.findall(raw_author_infos):
        address_map[address_tup[0].strip()] = address_tup[1].strip()

    ## 원본 주소 데이터 정제 끝 ##

    ## 원본 저자 데이터 정제 ##
    logger.log('info', 'DETAIL//[%s] 원본 저자 데이터 정제' % uid)
    '''By:Zhou, B (Zhou, Bo)[1 ]; Xu, YP (Xu, Yanping)[2 ]; Lee, SK (Lee, Seul Ki)[3,4 ]'''
    ptn_author_line   = re.compile(r'(?P<name>[A-Z,a-z.\'\- ]+) (\((?P<full_name>[A-Z,a-z.\'\- ]+)\))?(\[(?P<address_keys>[0-9,\' ]+)\])?')
    
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
            address_keys = ['1'] if address_map else []
        else:
            address_keys = address_keys.split(',')

        for key in address_keys:
            key = key.strip()
            author['addresses'].append(address_map[key])

        # 교신 저자 정보가 없을 경우, 제 1저자가 교신 저자이다.
        if reprint_name and reprint_name == author['name']:
            if not author['addresses'] and reprint_address: 
                author['addresses'] += [reprint_address]

            author['reprint'] = True
            paper_data['reprint'] = author

        authors.append(author)

    if not reprint_name and authors:
        authors[0]['reprint'] = True
        paper_data['reprint'] = authors[0]

    paper_data['reprint']           = paper_data['reprint']['name']
    paper_data['firstAuthor']       = authors[0]
    paper_data['parsedAuthorList']  = authors
    ## 원본 저자 데이터 정제 끝 ##
    # 저자 및 주소 끝 #


    # JCR #
    sourceTitle = soup.select(TAG['SOURCE_TITLE'])
    jcr_block   = soup.select(TAG['JCR_BLOCK'])
    journalImpact = {
        'sourceTitle': sourceTitle[0].text.strip() if sourceTitle else None,
        'impactFactorByYear': {},
        'jcrDataByYear': {}
    }

    if_table    = None
    jcr_tables  = None
    jcr_texts   = None
    if jcr_block:
        if_table    = soup.select(TAG['IF_TABLE'])
        jcr_tables  = soup.select(TAG['JCR_TABLE'])
        jcr_texts   = soup.select(TAG['JCR_TEXT'])

    if if_table:
        if_table = if_table[0]

        tds = if_table.find_all('td')
        ths = if_table.find_all('th')
        for idx in range(len(ths)):
            td = tds[idx]
            th = ths[idx]
            journalImpact['impactFactorByYear'][th.text.strip()] = td.text.strip()
    
    if jcr_tables:
        for idx, jcr_table in enumerate(jcr_tables):
            year = re.search(r'\d+', jcr_texts[idx].text)
            year = year[0] if year else 'unknown'

            journalImpact['jcrDataByYear'][year] = {
                'category': [],
                'rankInCategory': [],
                'quartileInCategory': []
            }

            trs = jcr_table = jcr_table.find_all('tr')
            for tr in trs[1:]:
                tds = tr.find_all('td')
                if tds[0]:
                    journalImpact['jcrDataByYear'][year]['category'].append(tds[0].text.strip())
                if tds[1]:
                    journalImpact['jcrDataByYear'][year]['rankInCategory'].append(tds[1].text.strip())
                if tds[2]:
                    journalImpact['jcrDataByYear'][year]['quartileInCategory'].append(tds[2].text.strip())

    paper_data['journalImpact'] = journalImpact
    # JCR 끝 # 


    # 등급 #
    logger.log('info', 'DETAIL//[%s] 등급' % uid)
    grades          = []

    labels = soup.select(TAG['GRADES'])

    for label in labels:
        full_grade  = label.text.replace('- ', '').strip()
        caped       = re.sub(r'[ a-z]+', r'', full_grade)

        # grades += [{'fullGrade': full_grade, 'caped': caped}]
        grades += [caped]

    paper_data['grades'] = grades
    # 등급 끝 #

    logger.log('info', 'DETAIL//[%s] DONE' % uid)
    return (ret_link, paper_data)
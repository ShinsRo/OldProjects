import sju_exceptions
import sju_CONSTANTS

import os
import re
import random
import datetime
import requests
from time import sleep
from itertools import cycle
from lxml.html import fromstring
from fake_useragent import UserAgent

import numpy as np
import pandas as pd
from bs4 import BeautifulSoup

def set_user_agent(session):
    ua = UserAgent()
    new_user_agent = {'User-Agent': ua.random}
    # new_user_agent = {'User-Agent': str(random.getrandbits(16))}
    # sleep(2)
    session.close()
    session = requests.Session()
    
    session.headers.update(new_user_agent)
    return session

def get_subsidy01(
        paper_data,
        p_authors, 
    ):
    '''
        대상 저자에 대한 본 논문관련 장려금 산정
        실패 시
         -1 : 기준 저자 없음
         -2 : 인정 아티클이 아님
         -3 : 분류(등급)이 없음
         -4 : 기준 저자의 자격을 판단할 수 없음
        성공 시
        금액 반환
    '''
    # 기준 저자 체크
    if p_authors == '':
        return -1

    p_authors = list(map(lambda x: x.strip(), p_authors.split(';')))

    # docType 체크
    if not re.search(r'(Article)|(Letter)|(Review)', paper_data['docType'], flags=re.I):
        return -2
    
    # 주저자인 지 판단
    # 1. 제 1저자인가.
    is_first = False
    for pa in p_authors:
        is_first = paper_data['authors'][0].find(pa) != -1
        if is_first: break
    
    # 2. 교신저자인가.
    is_reprint = False
    for pa in p_authors:
        is_reprint = paper_data['reprint'].find(pa) != -1
        if is_reprint: break

    # 3. 제 1저자, 교신저자가 본교 소속인가.
    is_reprint_sju = re.search('Sejong Univ', paper_data['reprint'], flags=re.I)
    is_first_sju = re.search('Sejong Univ', ' '.join(paper_data['addresses'][paper_data['authors'][0]]), flags=re.I)

    # 4. 공동저자가 존재하는가.
    # 풀네임 또한 저자로 포함 되므로, 존재할 때마다 2씩 증가한다.
    has_co_author = False
    count = 0
    for au in paper_data['authors']:
        if re.search('Sejong Univ', ' '.join(paper_data['addresses'][au]), flags=re.I):
            count += 1
            has_co_author = count >= 3

    # 5. 저자 상태 판단
    '''
        0: 알 수 없음
        - 주저자 인정
        1: 교신 저자 및 제 1저자 모두인 경우               100%
        2: 교신 저자의 소속이 뵨교인 제 1저자인 경우        100%   
        3: 교신 저자의 소속이 타기관인 제 1저자인 경우      100%
        4: 제 1저자의 소속이 본교인 교신저자인 경우         100%
        5: 제 1저자의 소속이 타기관인 교신저자인 경우       50%
        - 주저자 미인정
        6: 주저자가 본교인 공동저자의 경우                  0%
        7: 주저자가 타기관인 공동저자의 경우                50 / 공동저자 수 %

    '''
    author_is = 0
    discount = 100
    if is_first             and is_reprint: author_is = 1; discount = 100
    elif is_reprint_sju     and is_first: author_is = 2; discount = 100
    elif not is_reprint_sju and is_first: author_is = 3; discount = 100
    elif is_first_sju       and is_reprint: author_is = 4; discount = 100
    elif not is_first_sju   and is_reprint: author_is = 5; discount = 50
    elif has_co_author      and (is_first_sju or is_reprint_sju): author_is = 6; discount = 0
    elif has_co_author      and not (is_first_sju or is_reprint_sju): author_is = 7; discount = 50/(count/2)
    else: author_is = 0

    if author_is == 0:
        return -4

    # 6. 구분 기준이 무엇인가.
    '''
        0. 알 수 없음 or ESCI
        1. Nature, Science, Cell 
        2. SCI/SCIE
        3. SSCI
        4. A&HCI
    '''
    p_rank = 0
    grades = ' '.join(paper_data['capedGrades'])

    if re.search(r'(SCI)|(SCIE)', grades, flags=re.I):
        p_rank = 2
    elif grades.find('SSCI') > 0:
        p_rank = 3
    elif grades.find('A&HCI') > 0:
        p_rank = 4
    else:
        p_rank = 0

    if p_rank == 0:
        return -3

    # if 상위 백분율에 따른 기본 지급금
    top_percent = paper_data['goodRank']
    subsidy = 0

    # 전년도 IF가 없는 경우
    if paper_data['prevYearIF'] == 'None':
        top_percent = 99.0
    
    if p_rank == 2:
        if top_percent <= 10:   subsidy = 400
        elif top_percent <= 20: subsidy = 250
        elif top_percent <= 50: subsidy = 150
        else:                   subsidy = 0
    elif p_rank == 3:
        if top_percent <= 10:   subsidy = 600
        elif top_percent <= 20: subsidy = 550
        elif top_percent <= 50: subsidy = 500
        else:                   subsidy = 300
    elif p_rank == 4:
        subsidy = 500
    else:
        pass

    return subsidy*discount/100

def get_query_string(action, data):
    query_string = '%s?'%action
    
    if action == '/WOS_GeneralSearch_input.do':
        query_data = {
            'product': 'WOS',
        }
    elif action == '/Search.do':
        query_data = {
            'product': 'WOS',
            'search_mode': 'GeneralSearch',
            'SID': '',
            'prID': '',
        }
    elif action == '/full_record.do':
        query_data = {
            'product': 'WOS',
            'search_mode': 'GeneralSearch',
            'qid': '',
            'SID': '',
            'page': '',
            'doc': '',
        }
    elif action == '/OutboundService.do':
        query_data = {
            'action': 'go',
            'save_options': 'xls',
        }
    elif action == '/ETS/ets.do':
        query_data = {
            'mark_from': '1',               # required
            'product': 'UA',
            'colName': 'WOS',
            'displayUsageInfo': 'true',
            'parentQid': '',                # required
            'rurl': '',                     # required
            'start_year': '',                
            'mark_to': '',                  # required
            'filters': 'null',
            'rid': 'U-9523-2018',           # required_optional
            'qid': '',                      # required
            'end_year': '',
            'SID': '',                      # required
            'totalMarked': '',              # required
            'action': 'crsaveToFile',       # required
            'timeSpan': '',
            'sortBy': 'PY.D;LD.D;SO.A;VL.D;PG.A;AU.A',
            'displayTimesCited': 'true',
            'displayCitedRefs': 'true',
            'fileOpt': 'xls',                 # required
            'UserIDForSaveToRID': '10957580', # required_optional
        }
    elif action == '/CitationReport.do':
        query_data = {
            'product': 'WOS',
            'search_mode': 'CitationReport',
            'SID': '',
            'page': '',
            'cr_pqid': '',
            'viewType': '',
            'colName': 'WOS',
        }
    else:
        pass

    query_data.update(data)
    query_string += '&'.join(['%s=%s'%(k, v) for k, v in query_data.items()])

    return query_string

def get_form_data(action, data):
    form_data = {}

    if action == '/WOS_GeneralSearch.do':
        form_data = {
            'fieldCount': '1',              # required_optional
            'action': 'search',
            'product': 'WOS',
            'search_mode': 'GeneralSearch',
            'SID': '',                      # required
            'max_field_count': '25',
            'sa_params': '',                # required
            'formUpdated': 'true', 
            'value(input1)': '',            # required
            'value(select1)': '',           # required
            'value(hidInput1)': '', 
            'limitStatus': 'collapsed',     # required_optional
            'ss_lemmatization': 'On', 
            'ss_spellchecking': 'Suggest', 
            'SinceLastVisit_UTC': '', 
            'SinceLastVisit_DATE': '', 
            'period': 'Year Range',         
            'range': 'CUSTOM',
            'start_year': '',                # required
            'end_year': '',                  # required
            'editions': ['SCI', 'SSCI', 'AHCI', 'ESCI'], 
            'update_back2search_link_param': 'yes', 
            'ssStatus': 'display:none',
            'ss_showsuggestions': 'ON',
            'ss_numDefaultGeneralSearchFields': '1',
            'ss_query_language': '', 
            'rs_sort_by': 'PY.D;LD.D;SO.A;VL.D;PG.A;AU.A',
        }
    elif action == '/OutboundService.do':
        form_data = {
            'selectedIds': '',
            'displayCitedRefs':'',
            'displayTimesCited':'',
            'displayUsageInfo':'true',
            'viewType':'summary',
            'product':'WOS',
            'rurl': '',
            'mark_id':'WOS',
            'colName':'WOS',
            'search_mode':'CitationReport',
            'view_name':'WOS-CitationReport-summary',
            'sortBy': '',
            'mode':'OpenOutputService',
            'qid': '',
            'SID': '',
            'format': 'crsaveToFile',
            'mark_to': '',
            'mark_from': '',
            'queryNatural': '',
            'count_new_items_marked': '0',
            'use_two_ets': 'false',
            'IncitesEntitled': 'no',
            'value(record_select_type)': 'range',
            'markFrom': '',
            'markTo': '',
            'action':'recalulate',
            'start_year_val':'1900',
            'end_year_val':'2019',
            'viewAbstractUrl':'',
            'LinksAreAllowedRightClick': 'full_record.do',
            'filters': '',
            'timeSpan':  '',
            'db_editions': '',
            'additional_qoutput_params': 'cr_qid=',
            'print_opt':'Html',
            'include_mark_from_in_url':'true',
            'end_year': '',
            'start_year': '',
            'piChart': '',
            'toChart': '',
            'fields':'DUMMY_VALUE'
        }
    elif action == '/OutboundService.do?action=go&&':
        form_data = {
            'selectedIds': '',
            'displayCitedRefs': 'true',
            'displayTimesCited': 'true',
            'displayUsageInfo': 'true',
            'viewType': 'summary',
            'product': 'WOS',
            'mark_id': 'WOS',
            'colName': 'WOS',
            'search_mode': 'CitingArticles',
            'locale': 'en_US',
            'research_id': 'U-9523-2018',
            'view_name': 'WOS-CitingArticles-summary',
            'sortBy': 'PY.D;LD.D;SO.A;VL.D;PG.A;AU.A',
            'mode': 'OpenOutputService',
            'qid': '',                      # required
            'SID': '',                      # required
            'format': 'fastSave',
            'mark_to': '',                  # required
            'mark_from': '1',
            'queryNatural': '',             # 의심 
            'count_new_items_marked': '0',
            'use_two_ets': 'false',
            'IncitesEntitled': 'no',
            'value(record_select_type)': 'range',
            'markFrom': '1',
            'markTo': '',                    # required
            'save_options': 'tabWinUnicode'
        }
    else:
        pass

    form_data.update(data)
    return form_data

def parse_paper_data(target_content, paper_data_id):
    soup = BeautifulSoup(target_content, 'html.parser')

    # 검색 결과 수
    pagination_btn = soup.select('a.paginationNext')
    
    # 결과 수가 없을 경우 즉시 종료
    if not pagination_btn or len(pagination_btn) == 0:
        raise sju_exceptions.NoPaperDataError()

    pagination_btn_alt = soup.select('a.paginationNext')[0].attrs['alt']
    # 결과 수가 1개가 아닐 경우 즉시 종료
    # and pagination_btn_alt.find('비활성') == -1
    if pagination_btn_alt.find('Inactive') == -1:
        raise sju_exceptions.MultiplePaperDataError()

    # 논문 제목
    title = soup.select('div.title')[0].text.replace('\n', '')

    # ISSN
    ISSN = soup.select('p.sameLine')
    if ISSN:
        ISSN = ISSN[0].value.contents[0]
    else: ISSN = ''

    # 등급
    grades = []
    caped_grades = []
    box_label = soup.select('span.box-label')
    for label in box_label:
        if label.text.find('- ') != -1:
            temp = label.text.replace('- ', '')
            grades += [temp]
            caped_grades += [re.sub(r'[ a-z]+', r'', temp)]

    # 임팩트 팩토
    Impact_Factor_table = soup.select('table.Impact_Factor_table')
    impact_factor = {}
    if len(Impact_Factor_table) > 0:
        trs = Impact_Factor_table[0].find_all('tr')
        tds = trs[0].find_all('td')
        ths = trs[1].find_all('th')

        for idx, th in enumerate(ths):
            impact_factor[th.text.strip()] = tds[idx].text.strip()
        
    else:
        impact_factor = {}
    
    # JCR 랭크
    JCR_Category_table = soup.select('table.JCR_Category_table')
    jcr_headers = []
    jcr = []
    ranks = []
    good_rank = ''
    trs = []
    if len(JCR_Category_table) > 0: 
        JCR_Category_table = JCR_Category_table[0]
        trs = JCR_Category_table.find_all('tr')
        if trs:
            jcr.append([x.text.strip() for x in trs[0].find_all('th')])
            for tr in trs[1:]:
                temp = [x.text.strip() for x in tr.find_all('td')]
                jcr.append(temp)
                jrank, jall = map(int, temp[1].split(' of '))
                temp.append(round(jrank/jall  * 100, 2))
                ranks.append(temp)
    
        good_rank = max(ranks, key=lambda x: -x[-1])[-1]

    # 인용 횟수 및 링크
    cnt_link = soup.select('a.snowplow-citation-network-times-cited-count-link')
    if not cnt_link:
        times_cited = '0'
    else:
        cnt_link = cnt_link[0]
        times_cited = cnt_link.span.text

    # 기타 필드
    correction_form = soup.find(action='http://ips.clarivate.com/cgi-bin/forms/wok_datachange/wok-proc.pl')
    if not correction_form:
        correction_form = soup.find(action='https://support.clarivate.com/ScientificandAcademicResearch/s/datachanges')
    correction_form_inputs_by_name = {}
    for inputTag in correction_form.find_all('input'):
        inputDict = inputTag.attrs
        correction_form_inputs_by_name[inputDict['name']] = inputDict['value']
    
    doc_type = ''
    published_month = ''
    research_areas = ''
    publisher = ''
    language = ''
    reprint = ''
    authors = []
    fr_authors = []
    fr_addresses = []
    for fr_field in soup.select('p.FR_field'):
        if fr_field.text.find('Document Type:') != -1:
            doc_type = fr_field.text.split(':')[1]
        
        if fr_field.text.find('Published:') != -1:
            published_month = fr_field.text.split(':')[1]

        if fr_field.text.find('Research Areas:') != -1:
            research_areas = fr_field.text.split(':')[1]

        if fr_field.text.find('Publisher ') != -1:
            publisher = ' '.join(fr_field.text.split(' ')[1:])
            publisher = publisher.split(',')

        if fr_field.text.find('Language:') != -1:
            language = fr_field.text.split(':')[1]
            
        if fr_field.text.find('Reprint Address:') != -1:
            reprint = fr_field.text.split(':')[1].replace('\n', '').strip()

        if fr_field.text.find('By:') != -1:
            fr_authors = fr_field

        # if fr_field.text.find('Addresses:') != -1:
        #     if fr_field.text.find('E-mail') != -1:
        #         continue
        #     fr_addresses = fr_field.nextSibling
            
    addresses = {}

    #저자, 연구기관
    fconts = fr_authors.select('a')
    fr_authors_text = fr_authors.text.replace('\n', '')
    fr_authors_text = fr_authors_text.split(':')[1].split(';')

    # 풀 네임
    full_name = {}
    for fa in fr_authors_text:
        p_count = fa.count('(')
        if p_count > 1: fa_match = re.search(r'(.+) \((.+)\(.+\)\)', fa)
        elif p_count == 1: fa_match = re.search(r'(.+) \((.+)\)', fa)
        if fa_match:
            full_name[fa_match.group(1).strip()] = fa_match.group(2).replace(r'\(|\)', '').strip()
    
    target_author = ''
    tauthor_address = []
    for con in fconts:
        isSub = con.get('href').find('javascript') != -1
        if not isSub:
            if target_author != '':
                addresses[target_author] = tauthor_address
                if target_author in full_name.keys(): addresses[full_name[target_author]] = tauthor_address
            tauthor_address = []
            target_author =  con.text.strip()
            authors += [target_author]
        else:
            addressId = re.sub(r'.+\'(.+)\'.+', r'\1', con.get('href'))
            temp = soup.find('a', id=addressId)
            if temp != None:
                # tauthor_address += [temp.contents[0]]
                tauthor_address += [temp.text]

    if target_author != '':
                addresses[target_author] = tauthor_address
                addresses[full_name[target_author]] = tauthor_address
    if reprint == '':
        reprint = 'None'

    paperData = {
        'id' : paper_data_id,
        # 'authors' : correction_form_inputs_by_name['00N70000002C0wa'].split(';'),
        'authors' : authors,
        'full_name' : full_name,
        'fr_authors_text' : fr_authors_text,
        'firstAuthor': authors[0],
        'addresses' : addresses,
        'authorsCnt' : str(len(correction_form_inputs_by_name['00N70000002C0wa'].split(';')) - 1),
        'doi' : correction_form_inputs_by_name['00N70000002n88A'],
        'volume' : correction_form_inputs_by_name['00N70000002Bdnt'],
        'issue' : correction_form_inputs_by_name['00N700000029W18'],
        'pages' : correction_form_inputs_by_name['00N70000002C0vh'],
        'published' : correction_form_inputs_by_name['00N70000002BdnY'],
        'publishedMonth' : published_month,
        'publisher' : publisher,
        # 'title' : correction_form_inputs_by_name['00N70000002BdnX'],
        'title' : title,
        'impact_factor' : impact_factor,
        'prevYearIF' : 'None',
        'goodRank' : good_rank,
        'timesCited' : times_cited,
        'grades' : grades,
        'capedGrades' : caped_grades,
        'docType' : doc_type,
        'researchAreas' : research_areas,
        'language' : language,
        'reprint' : reprint,
        'jcr' : jcr,
        'citingArticles' : [],
        'issn' : ISSN,
    }
    paperData['ivp'] = ['%s/%s'%(paperData['issue'], paperData['volume']), paperData['pages']]
    
    # 전년도 임팩트 팩토
    now = datetime.datetime.now()
    prev_year = str(now.year - 1)
    # prev_year = str(int(paperData['published']) - 1)
    if prev_year in impact_factor.keys():
        paperData['prevYearIF'] = impact_factor[prev_year]

    return paperData, cnt_link

def get_query_list_from_file(path):
    fname, ext = os.path.splitext(path)
    encodings = ['utf-8', 'cp949', 'euc-kr']
    for d_codec in encodings:
        try:
            if ext == '.csv':
                df = pd.read_csv(path, header=0, encoding=d_codec)
                query_list = df.values[:].tolist()
            elif ext == '.xls' or ext == '.xlsx':
                df = pd.read_excel(path, header=0, encoding=d_codec)
                query_list = df.values[:].tolist()
            else:
                raise Exception()
        except UnicodeDecodeError as e:
            pass
            traceback.print_tb(e.__traceback__) 
        else:
            break

    return_query_list = []
    for idx, qry in enumerate(query_list):
        if type(qry[0]) == type(np.nan) or not qry[0]:
            continue
        else :
            if (len(qry[0]) < 3) or qry[0].strip() == '':
                continue

            if len(qry) == 1:
                query_list[idx] = (qry[0], '', '')
            elif len(qry) == 2:
                query_list[idx] = (qry[0], '' if type(qry[1]) == type(np.nan) else qry[1], '')
            else:
                query_list[idx] = (
                    qry[0], 
                    '' if type(qry[1]) == type(np.nan) else qry[1], 
                    '' if type(qry[2]) == type(np.nan) else qry[2])
            return_query_list += [query_list[idx]]

    return return_query_list

def input_validation(service_name):
    now = datetime.datetime.now()
    returnDict = {}
    InputValidationError = sju_exceptions.InputValidationError
    
    # 단일 상세 검색 및 빠른 검색
    if service_name == 'singleSearch' or service_name == 'fastSearch':
        query = input().strip()
        start_year = input().strip()
        end_year = input().strip()
        p_authors = input().strip()
        organization = input().strip()
        gubun = input().strip()
        
        # Sejong Univ 로 고정
        #####################
        organization = 'Sejong Univ'

        if not len(query) > 2: raise InputValidationError('쿼리의 길이가 너무 짧습니다.')
        if not 1900 <= int(start_year) <= now.year: raise InputValidationError('시작년도가 올바르지 않습니다.')
        if not 1900 <= int(end_year) <= now.year: raise InputValidationError('끝 년도가 올바르지 않습니다.')
        if not int(start_year) <= int(end_year): raise InputValidationError('검색 기간이 올바르지 않습니다.')
        # if not p_authors: raise InputValidationError('---')
        returnDict['query'] = query 
        returnDict['start_year'] = start_year 
        returnDict['end_year'] = end_year 
        returnDict['p_authors'] = p_authors
        returnDict['organization'] = organization
        returnDict['gubun'] = gubun

    # 다중 상세 검색
    elif service_name == 'multiSearch':
        start_year = input().strip()
        end_year = input().strip()
        gubun = input().strip()
        path = input().strip()
    
        if not 1900 <= int(start_year) <= now.year: raise InputValidationError('시작년도가 올바르지 않습니다.')
        if not 1900 <= int(end_year) <= now.year: raise InputValidationError('끝 년도가 올바르지 않습니다.')
        if not int(start_year) <= int(end_year): raise InputValidationError('검색 기간이 올바르지 않습니다.')
        if not (gubun == 'TI' or gubun == 'DO'): raise InputValidationError('구분이 올바르지 않습니다.')
        if not (
            path.endswith('.csv') 
            or path.endswith('.xls')
            or path.endswith('.xlsx')): raise InputValidationError('파일 확장자가 올바르지 않습니다.')
        returnDict['start_year'] = start_year 
        returnDict['end_year'] = end_year 
        returnDict['gubun'] = gubun 
        returnDict['path'] = path 

    # 다중 일반 검색
    elif service_name == 'multiCommonSearch':
        start_year = input().strip()
        end_year = input().strip()
        gubun = input().strip()
        path = input().strip()
        defaultQueryPackSize = input().strip()

        if not 1900 <= int(start_year) <= now.year: raise InputValidationError('시작년도가 올바르지 않습니다.')
        if not 1900 <= int(end_year) <= now.year: raise InputValidationError('끝 년도가 올바르지 않습니다.')
        if not int(start_year) <= int(end_year): raise InputValidationError('검색 기간이 올바르지 않습니다.')
        if not (gubun == 'TI' or gubun == 'DO'): raise InputValidationError('구분이 올바르지 않습니다.')
        path = path
        if not (
            path.endswith('.csv') 
            or path.endswith('.xls')
            or path.endswith('.xlsx')): raise InputValidationError('파일 확장자가 올바르지 않습니다.')
        returnDict['start_year'] = start_year 
        returnDict['end_year'] = end_year 
        returnDict['gubun'] = gubun 
        returnDict['path'] = path 
        returnDict['defaultQueryPackSize'] = defaultQueryPackSize 
    else:
        raise InputValidationError('등록되지 않은 서비스 이름')
    return returnDict

if __name__ == "__main__":
    authors = ['Kim SS', 'Kim WH', 'Lee SS', 'Park MH', 'Hong, JT']
    good_rank = ['SCIE', 'SCI']
    paper_data = {
        'authors' : authors,
        'fr_authors_text' : '',
        'firstAuthor': authors[0],
        'addresses' : {
            'Kim SS': 'Sejong Univer',
            'Kim WH': 'efg',
            'Lee SS': 'abcd',
            'Park MH': 'other',
            'Hong, JT': 'Hanyang',
        },
        'authorsCnt' : '5',
        'doi' : '',
        'prevYearIF' : '123',
        'goodRank' : 15,
        'timesCited' : '3',
        'grades' : [],
        'capedGrades' : good_rank,
        'docType' : 'Article',
        'reprint' : 'Kim WH',
    }
    print(get_subsidy01(
        paper_data,
        'Kim SS; Kim SeungShin',
    ))
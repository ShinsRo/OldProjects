import sju_exceptions

import os
import re
import random
import datetime

import numpy as np
import pandas as pd
from bs4 import BeautifulSoup

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
            'startYear': '',                
            'mark_to': '',                  # required
            'filters': 'null',
            'rid': 'U-9523-2018',           # required_optional
            'qid': '',                      # required
            'endYear': '',
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
            'startYear': '',                # required
            'endYear': '',                  # required
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
            'endYear': '',
            'startYear': '',
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
            'locale': 'ko_KR',
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

def parse_paper_data(target_content):
    soup = BeautifulSoup(target_content, 'html.parser')

    # 검색 결과 수
    pagination_btn = soup.select('a.paginationNext')
    
    # 결과 수가 없을 경우 즉시 종료
    if not pagination_btn or len(pagination_btn) == 0:
        raise sju_exceptions.NoPaperDataError()

    pagination_btn_alt = soup.select('a.paginationNext')[0].attrs['alt']
    # 결과 수가 1개가 아닐 경우 즉시 종료
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
    target_author = ''
    tauthor_address = []
    for con in fconts:
        isSub = con.get('href').find('javascript') != -1
        if not isSub:
            if target_author != '':
                addresses[target_author] = tauthor_address
            tauthor_address = []
            target_author =  con.text
            authors += [target_author]
        else:
            addressId = re.sub(r'.+\'(.+)\'.+', r'\1', con.get('href'))
            temp = soup.find('a', id=addressId)
            if temp != None:
                # tauthor_address += [temp.contents[0]]
                tauthor_address += [temp.text]

    if target_author != '':
                addresses[target_author] = tauthor_address
    if reprint == '':
        reprint = 'None'

    paperData = {
        'id' : random.getrandbits(32),
        # 'authors' : correction_form_inputs_by_name['00N70000002C0wa'].split(';'),
        'authors' : authors,
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
    prev_year = str(int(paperData['published']) - 1)
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
    
    # 단일 상세 검색 및 저자명 검색
    if service_name == 'singleCitationSearch' or service_name == 'citationSearchByAuthor':
        query = input().strip()
        startYear = input().strip()
        endYear = input().strip()
        pAuthors = input().strip()
        organization = input().strip()
        
        if not len(query) > 2: raise InputValidationError('쿼리의 길이가 너무 짧습니다.')
        if not 1900 <= int(startYear) <= now.year: raise InputValidationError('시작년도가 올바르지 않습니다.')
        if not 1900 <= int(endYear) <= now.year: raise InputValidationError('끝 년도가 올바르지 않습니다.')
        if not int(startYear) <= int(endYear): raise InputValidationError('검색 기간이 올바르지 않습니다.')
        # if not pAuthors: raise InputValidationError('---')
        returnDict['query'] = query 
        returnDict['startYear'] = startYear 
        returnDict['endYear'] = endYear 
        returnDict['pAuthors'] = pAuthors
        returnDict['organization'] = organization

    # 다중 상세 검색
    elif service_name == 'multiCitationSearch':
        startYear = input().strip()
        endYear = input().strip()
        gubun = input().strip()
        path = input().strip()
    
        if not 1900 <= int(startYear) <= now.year: raise InputValidationError('시작년도가 올바르지 않습니다.')
        if not 1900 <= int(endYear) <= now.year: raise InputValidationError('끝 년도가 올바르지 않습니다.')
        if not int(startYear) <= int(endYear): raise InputValidationError('검색 기간이 올바르지 않습니다.')
        if not (gubun == 'TI' or gubun == 'DO'): raise InputValidationError('구분이 올바르지 않습니다.')
        if not (
            path.endswith('.csv') 
            or path.endswith('.xls')
            or path.endswith('.xlsx')): raise InputValidationError('파일 확장자가 올바르지 않습니다.')
        returnDict['startYear'] = startYear 
        returnDict['endYear'] = endYear 
        returnDict['gubun'] = gubun 
        returnDict['path'] = path 

    # 다중 일반 검색
    elif service_name == 'multiCommonSearch':
        startYear = input().strip()
        endYear = input().strip()
        gubun = input().strip()
        inputFilePath = input().strip()
        defaultQueryPackSize = input().strip()

        if not 1900 <= int(startYear) <= now.year: raise InputValidationError('시작년도가 올바르지 않습니다.')
        if not 1900 <= int(endYear) <= now.year: raise InputValidationError('끝 년도가 올바르지 않습니다.')
        if not int(startYear) <= int(endYear): raise InputValidationError('검색 기간이 올바르지 않습니다.')
        if not (gubun == 'TI' or gubun == 'DO'): raise InputValidationError('구분이 올바르지 않습니다.')
        path = inputFilePath
        if not (
            path.endswith('.csv') 
            or path.endswith('.xls')
            or path.endswith('.xlsx')): raise InputValidationError('파일 확장자가 올바르지 않습니다.')
        returnDict['startYear'] = startYear 
        returnDict['endYear'] = endYear 
        returnDict['gubun'] = gubun 
        returnDict['inputFilePath'] = path 
        returnDict['defaultQueryPackSize'] = defaultQueryPackSize 
    else:
        raise InputValidationError('등록되지 않은 서비스 이름')
    return returnDict

if __name__ == "__main__":
    input_validation('singleCitationSearch')
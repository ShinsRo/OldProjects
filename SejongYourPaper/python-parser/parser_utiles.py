import os
import re
import random
import datetime
import requests
from time import sleep
from itertools import cycle
from lxml.html import fromstring
from fake_useragent import UserAgent
from urllib.parse import unquote
from urllib.parse import quote
from urllib3.exceptions import NewConnectionError
import ast

import numpy as np
import pandas as pd
from bs4 import BeautifulSoup

import parser_exceptions

def parse_paper_data(target_content, paper_data_id, search_type, SID_name):
    """
        페이지 정보를 입력받아 정리한 내용을 리스트로 반환하는 함수
        :param target_content: 페이지 내용
        :param paper_data_id: 랜덤 값 ID (10자리)
        :type: single, dupl search인지 판단
        :return: 페이지 정보, 인용 수 반환
    """
    soup = BeautifulSoup(target_content, 'html.parser')

    if search_type == "reprint":
        # 인용 횟수 및 링크
        cnt_link = soup.select('a.snowplow-citation-network-times-cited-count-link')
        if not cnt_link:
            # 임시
            raise Exception("인용횟수없음");

            times_cited = '0'
        else:
            cnt_link = cnt_link[0]
            times_cited = cnt_link.span.text

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
                if not re.search(r"Sejong Univ", reprint, re.IGNORECASE):
                    raise Exception("NOT SEJONG REPRINT")

            if fr_field.text.find('By:') != -1:
                fr_authors = fr_field

            return {'reprint': reprint, 'authors':[reprint.split(" (reprint author) ")[0]]}, cnt_link
    ###########################

    if search_type == "single":
        # 검색 결과 수
        pagination_btn = soup.select('a.paginationNext')
    
        # 결과 수가 없을 경우 즉시 종료
        if not pagination_btn or len(pagination_btn) == 0:
            if re.search("Record not available", soup.text):
                raise parser_exceptions.RecordNotAvailableError()
            raise parser_exceptions.NoPaperDataError()

        pagination_btn_alt = soup.select('a.paginationNext')[0].attrs['alt']
        # 결과 수가 1개가 아닐 경우 즉시 종료
        # and pagination_btn_alt.find('비활성') == -1
        if pagination_btn_alt.find('Inactive') == -1:
            raise parser_exceptions.MultiplePaperDataError()

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

    # 인용 횟수 및 링크
    cnt_link = soup.select('a.snowplow-citation-network-times-cited-count-link')
    if not cnt_link:
        # 임시
        raise Exception("인용횟수없음");

        times_cited = '0'
    else:
        cnt_link = cnt_link[0]
        times_cited = cnt_link.span.text

    #저널 명
    journal_name = soup.select('span.sourceTitle')
    journal_name = journal_name[0].text.replace('\n','')

    #print("[1type]journal_name : ", journal_name)
    #print("[2type]journal_name : ",type(journal_name))

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

    #impact_factor
    impact_factor = {};
    
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
    if len(fconts) > 99:
        raise Exception("저자가 너무 많음")
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
        'journal_name' : journal_name,
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
    

    return paperData, cnt_link
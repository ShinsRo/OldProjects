import random
import os
import sys
import time
import sju_response
import pandas as pd
import numpy as np
import requests
from robobrowser import RoboBrowser
from robobrowser.forms.fields import Input
from urllib3 import request
import xlrd
import json
import re
import bs4
import concurrent.futures
import threading

class SingleSearch():
    """
        단일 상세 검색 객체
    """
    def __init__(self, sres):
        """
            단일 상세 검색 객체 초기화 및 브라우저 세션 초기화
            sres : UI 통신, 로깅 객체
        """
        self.searchCnt = 0
        self.sres = sres
        self.browser = RoboBrowser(history=True, parser='lxml')
        self.baseUrl = "http://apps.webofknowledge.com"
        self.browser.open(self.baseUrl)

        self.SID = self.browser.session.cookies['SID'].replace("\"", "")
        self.jsessionid = self.browser.session.cookies['JSESSIONID']

        param = '?product=WOS'
        param += '&search_mode=GeneralSearch'
        param += '&preferencesSaved='
        param += '&SID=' + self.SID
        self.browser.open(self.baseUrl + '/WOS_GeneralSearch_input.do' + param)

    def backToGeneralSearch(self):
        """
            세션 파괴 및 재초기화
        """
        sres = self.sres

        if self.searchCnt % 200 == 0:
            self.browser = RoboBrowser(history=True, parser='lxml')
            self.baseUrl = "http://apps.webofknowledge.com"
            self.browser.open(self.baseUrl)

            self.SID = self.browser.session.cookies['SID'].replace("\"", "")
            self.jsessionid = self.browser.session.cookies['JSESSIONID']

            sres.print(command='log', msg='SID : %s'%(self.SID))
            sres.print(command='log', msg='jsessionid : %s'%(self.jsessionid))
            sres.print(command='log', msg='WOS GeneralSearch를 엽니다.')

        sres.print(command='log', msg='브라우저를 초기화합니다.')
        param = '?product=WOS'
        param += '&search_mode=GeneralSearch'
        param += '&preferencesSaved='
        param += '&SID=' + self.SID
        self.browser.open(self.baseUrl + '/WOS_GeneralSearch_input.do' + param)
        
        sres.print(command='log', msg='초기화가 완료되었습니다.')

    def getCitingArticleDetail(self, url, query):
        browser = RoboBrowser(history=True, parser='lxml')
        browser.open(url)

        title = browser.select('div.title')[0].text

        reprint = ''
        authors = []
        fr_authors = []
        fr_addresses = []
        for fr_field in browser.select('p.FR_field'):
            if fr_field.text.find('By:') != -1:
                fr_authors = fr_field
            if fr_field.text.find('Addresses:') != -1:
                if fr_field.text.find('E-mail') != -1:
                    continue
                fr_addresses = fr_field.nextSibling
                
        addresses = {}
        #저자, 연구기관
        fconts = fr_authors.select('a')
        fr_authors_text = fr_authors.text.replace('\n', '')
        targetAuthor = ''
        tauthorAddress = []
        for con in fconts:
            isSub = con.get('href').find('javascript') != -1
            if not isSub:
                if targetAuthor != '':
                    addresses[targetAuthor] = tauthorAddress
                tauthorAddress = []
                targetAuthor = con.text
                authors += [targetAuthor]
            else:
                addressId = re.sub(r'.+\'(.+)\'.+', r'\1', con.get('href'))
                temp = fr_addresses.find('a', id=addressId)
                if temp != None:
                    tauthorAddress += [temp.text]

        if targetAuthor != '':
                    addresses[targetAuthor] = tauthorAddress

        single_citing_data = {
            'title': title,
            'authors': " ".join(authors),
            'tauthorAddress': tauthorAddress,
            'fr_authors_text': fr_authors_text,
        }
        return single_citing_data

    def generalSearch(self, query, startYear, endYear, gubun, resName):
        sres = self.sres
        browser = self.browser
        baseUrl = self.baseUrl
        SID = self.SID
        jsessionid = self.jsessionid
        pAuthors = query[1]

        sres.print('log', msg='단일 조회를 시작합니다.')
        WOS_GeneralSearch_input_form = ''

        # 검색 페이지가 제대로 열리지 않았을 경우 새롭게 초기화
        try:
            WOS_GeneralSearch_input_form = browser.get_form('WOS_GeneralSearch_input_form')
        except Exception as e:
            sres.print(command='err', msg='검색 중 브라우저가 폼 객체를 찾지 못해 브라우저를 갱신합니다.')
            self.searchCnt = 1
            self.browser = RoboBrowser(history=True, parser='lxml')
            self.browser.open(self.baseUrl)

            self.SID = self.browser.session.cookies['SID'].replace("\"", "")
            self.jsessionid = self.browser.session.cookies['JSESSIONID']

            param = '?product=WOS'
            param += '&search_mode=GeneralSearch'
            param += '&preferencesSaved='
            param += '&SID=' + self.SID
            self.browser.open(self.baseUrl + '/WOS_GeneralSearch_input.do' + param)
            
            WOS_GeneralSearch_input_form = browser.get_form('WOS_GeneralSearch_input_form')

        WOS_GeneralSearch_input_form['value(input1)'] = query[0]
        WOS_GeneralSearch_input_form['value(select1)'] = gubun
        WOS_GeneralSearch_input_form['startYear'] = startYear
        WOS_GeneralSearch_input_form['endYear'] = endYear
        WOS_GeneralSearch_input_form['range'] = 'CUSTOM'
        WOS_GeneralSearch_input_form['period'].options.append('Year Range')
        WOS_GeneralSearch_input_form['period'] = 'Year Range'

        sres.print('log', msg='검색어 : %s'%(query[0]))
        browser.submit_form(WOS_GeneralSearch_input_form)
        self.searchCnt += 1

        aTagArr_record = browser.select('a.snowplow-full-record')

        stepFlag = True
        if not aTagArr_record:
            sres.print(command='err', msg='%s의 결과가 없습니다.'%query[0])
            sres.print(command=resName, target='errQuery', res={'query': query, 'msg': '검색결과가 없습니다.'})
            stepFlag = False
        elif len(aTagArr_record) > 1:
            sres.print(command='err', msg='검색 결과가 하나 이상입니다.')
            sres.print(command=resName, target='errQuery', res={'query': query, 'msg': '검색 결과가 하나 이상입니다. 위 결과는 가장 첫번째 결과입니다.'})
            # for aTag in aTagArr_record:
            #     sres.print(command='err', msg='%s'%(aTag.text.replace('\n', '')))
            sres.print(command='err', msg='가장 비슷한 검색 결과로 정보를 가져옵니다.')

        if not stepFlag:
            self.backToGeneralSearch()
            return

        sres.print(command='log', msg='논문 상세 정보 창에 접근합니다.')
        browser.follow_link(aTagArr_record[0])

        # 논문 제목
        title = aTagArr_record[0].text.replace('\n', '')

        # ISSN

        ISSN = browser.select('p.sameLine')
        if ISSN:
            ISSN = ISSN[0].value.contents[0]
        else: ISSN = ''

        # 등급
        grades = []
        capedGrades = []
        box_label = browser.select('span.box-label')
        for label in box_label:
            if label.text.find('- ') != -1:
                temp = label.text.replace('- ', '')
                grades += [temp]
                capedGrades += [re.sub(r'[ a-z]+', r'', temp)]

        # 임팩트 팩토
        Impact_Factor_table = browser.select('table.Impact_Factor_table')
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
        JCR_Category_table = browser.select('table.JCR_Category_table')
        jcr_headers = []
        jcr = []
        ranks = []
        goodRank = ''
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
        
            goodRank = max(ranks, key=lambda x: -x[-1])[-1]

        # 인용 횟수 및 링크
        cnt_link = browser.select('a.snowplow-citation-network-times-cited-count-link')
        if not cnt_link:
            timesCited = '0'
        else:
            cnt_link = cnt_link[0]
            timesCited = cnt_link.span.text

        # 기타 필드
        correction_form = browser.find(action='http://ips.clarivate.com/cgi-bin/forms/wok_datachange/wok-proc.pl')
        correction_form_inputs_by_name = {}
        for inputTag in correction_form.find_all('input'):
            inputDict = inputTag.attrs
            correction_form_inputs_by_name[inputDict['name']] = inputDict['value']
        
        docType = ''
        publishedMonth = ''
        researchAreas = ''
        publisher = ''
        language = ''
        reprint = ''
        authors = []
        fr_authors = []
        fr_addresses = []
        for fr_field in browser.select('p.FR_field'):
            if fr_field.text.find('Document Type:') != -1:
                docType = fr_field.text.split(':')[1]
            
            if fr_field.text.find('Published:') != -1:
                publishedMonth = fr_field.text.split(':')[1]

            if fr_field.text.find('Research Areas:') != -1:
                researchAreas = fr_field.text.split(':')[1]

            if fr_field.text.find('Publisher ') != -1:
                publisher = ' '.join(fr_field.text.split(' ')[1:])
                publisher = publisher.split(',')

            if fr_field.text.find('Language:') != -1:
                language = fr_field.text.split(':')[1]
                
            if fr_field.text.find('Reprint Address:') != -1:
                reprint = fr_field.text.split(':')[1].replace('\n', '').strip()

            if fr_field.text.find('By:') != -1:
                fr_authors = fr_field
            if fr_field.text.find('Addresses:') != -1:
                if fr_field.text.find('E-mail') != -1:
                    continue
                fr_addresses = fr_field.nextSibling
                
        addresses = {}
        #저자, 연구기관
        fconts = fr_authors.select('a')
        fr_authors_text = fr_authors.text.replace('\n', '')
        targetAuthor = ''
        tauthorAddress = []
        for con in fconts:
            isSub = con.get('href').find('javascript') != -1
            if not isSub:
                if targetAuthor != '':
                    addresses[targetAuthor] = tauthorAddress
                tauthorAddress = []
                targetAuthor =  con.text
                authors += [targetAuthor]
            else:
                addressId = re.sub(r'.+\'(.+)\'.+', r'\1', con.get('href'))
                temp = fr_addresses.find('a', id=addressId)
                if temp != None:
                    # tauthorAddress += [temp.contents[0]]
                    tauthorAddress += [temp.text]

        if targetAuthor != '':
                    addresses[targetAuthor] = tauthorAddress
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
            'publishedMonth' : publishedMonth,
            'publisher' : publisher,
            # 'title' : correction_form_inputs_by_name['00N70000002BdnX'],
            'title' : title,
            'impact_factor' : impact_factor,
            'prevYearIF' : 'None',
            'goodRank' : goodRank,
            'timesCited' : timesCited,
            'grades' : grades,
            'capedGrades' : capedGrades,
            'docType' : docType,
            'researchAreas' : researchAreas,
            'language' : language,
            'reprint' : reprint,
            'jcr' : jcr,
            'citingArticles' : [],
            'issn' : ISSN,
        }
        paperData['ivp'] = ['%s/%s'%(paperData['issue'], paperData['volume']), paperData['pages']]
        citingArticles = {'id': paperData['id'], 'selfCitation': 0, 'othersCitation': 0, 'titles': [], 'authors': [], 'isSelf': []}

        # 전년도 임팩트 팩토
        prevYear = str(int(paperData['published']) - 1)
        if prevYear in impact_factor.keys():
            paperData['prevYearIF'] = impact_factor[prevYear]

        sres.print(command='log', msg='해당 논문의 정보 검색을 완료했습니다.')
        sres.print(command=resName, target='paperData', res=paperData)
        
        stepFlag = True
        if not cnt_link:
            sres.print(command='log', msg='논문을 인용한 논문이 없으므로 검색을 종료합니다.')
            # sres.print(command=resName, target='errQuery', res={'query': query, 'msg': '논문을 인용한 논문이 없습니다.'})
            stepFlag = False
        elif int(timesCited) > 500 :
            sres.print(command='err', msg='해당 논문을 인용한 논문이 500개를 초과하여 검색을 종료합니다.')
            sres.print(command=resName, target='errQuery', res={'query': query, 'msg': '해당 논문을 인용한 논문이 500개를 초과하였습니다.'})
            stepFlag = False

        if not stepFlag:
            sres.print(command='log', msg='브라우저를 초기화합니다.')
            param = '?product=WOS'
            param += '&search_mode=GeneralSearch'
            param += '&preferencesSaved='
            param += '&SID=' + self.SID
            self.browser.open(self.baseUrl + '/WOS_GeneralSearch_input.do' + param)
            return paperData

        citingArticles = {'id': paperData['id'], 'selfCitation': 0, 'othersCitation': 0, 'titles': [], 'fr_authors_text': [], 'authors': [], 'isSelf': []}
        sres.print(command='log', msg='인용 중인 논문 정보 페이지를 가져옵니다.')

        # 사이팅 리포트 페이지 접속
        browser.follow_link(cnt_link)

        # 인용하는 논문이 적을 경우 인용 보고서를 제작하지 않고 바로 파싱한다.
        # [1] 인용 논문 스레딩 조회 
        if int(timesCited) < 11:
        # if int(timesCited) > 0:
            refine_form = browser.get_form(id='refine_form')
            qid = refine_form['parentQid'].value

            citing_record_url = 'http://apps.webofknowledge.com/full_record.do'
            citing_param = '?product=WOS'
            citing_param += '&search_mode=CitingArticles'
            citing_param += '&qid=' + qid
            citing_param += '&SID=' + self.browser.session.cookies['SID'].replace("\"", "")
            citing_param += '&page='
            citing_param += '&doc='

            # 인용 중인 논문별 퓨쳐 객체 생성 및 실행
            with concurrent.futures.ThreadPoolExecutor(max_workers=10) as exceutor:
                future_citing = {
                    exceutor.submit(
                        self.getCitingArticleDetail,
                        citing_record_url + citing_param + str(citing_doc),
                        query,
                    ): citing_doc for citing_doc in range(1, int(timesCited) + 1)
                }
                for future in concurrent.futures.as_completed(future_citing):
                    citing_doc = future_citing[future]
                    try:
                        single_citing_data = future.result()
                    except Exception as e:
                        sres.print(command=resName, target='errQuery', res={'query': query, 'msg':'인용한 논문 중 %d번 레코드 검색 중 에러가 발생.'%(citing_doc)})
                        sres.print(command='errObj', msg=e)
                    else:
                        citingArticles['titles'] += [single_citing_data['title']]
                        citingArticles['authors'] += [single_citing_data['authors']]
                        citingArticles['fr_authors_text'] += [single_citing_data['fr_authors_text']]

            # 모든 인용 중 논문 조회 완료, 자기인용, 타인인용 검색
            if pAuthors != '':
                pAuthors = list(map(lambda x: x.replace(' ', ''), pAuthors.split(';')))

                for idx, testString in enumerate(citingArticles['fr_authors_text']):
                    found = False
                    for pa in pAuthors:
                        if re.search(pa, testString, flags=re.IGNORECASE):
                            found = True
                            citingArticles['selfCitation'] += 1
                            citingArticles['isSelf'] += ['Self']
                    if not found:
                        citingArticles['othersCitation'] += 1
                        citingArticles['isSelf'] += ['Others\'']        

            sres.print(command='log', msg='인용 중인 논문 정보를 가져왔습니다.')
            sres.print(command=resName, target='citingArticles', res=citingArticles)

            paperData['citingArticles'] = citingArticles

            self.backToGeneralSearch()
            return paperData

        # [2] 인용 논문 엑셀 다운로딩 조회
        reportLink = browser.select("a.citation-report-summary-link")[0]
        browser.follow_link(reportLink)
        
        summary_records_form = browser.get_form(id='summary_records_form')
        qid = summary_records_form['qid'].value
        filters = summary_records_form['filters'].value
        sortBy = summary_records_form['sortBy'].value
        timeSpan = summary_records_form['timeSpan'].value
        endYear = summary_records_form['endYear'].value
        startYear = summary_records_form['startYear'].value
        rurl = summary_records_form['rurl'].value

        mark_from = '1'
        mark_to = timesCited

        piChart = summary_records_form['piChart'].value
        toChart = summary_records_form['piChart'].value

        makeExcelURL = "http://apps.webofknowledge.com/OutboundService.do?"
        makeExcelParam = ""
        makeExcelParam += "action=go"
        makeExcelParam += "&save_options=xls"

        makeExcelURL += makeExcelParam

        sres.print(command='log', msg='%s부터 %s레코드까지 데이터 제작을 요청합니다.'%(mark_from, mark_to))
        browser.session.post(makeExcelURL, data={
            "selectedIds": "",
            "displayCitedRefs":"",
            "displayTimesCited":"",
            "displayUsageInfo":"true",
            "viewType":"summary",
            "product":"WOS",
            "rurl":rurl,
            "mark_id":"WOS",
            "colName":"WOS",
            "search_mode":"CitationReport",
            "view_name":"WOS-CitationReport-summary",
            "sortBy": sortBy,
            "mode":"OpenOutputService",
            "qid":qid,
            "SID":self.SID,
            "format":"crsaveToFile",
            "mark_to":mark_to,
            "mark_from":mark_from,
            "queryNatural":"",
            "count_new_items_marked":"0",
            "use_two_ets":"false",
            "IncitesEntitled":"no",
            "value(record_select_type)":"range",
            "markFrom":mark_from,
            "markTo":mark_to,
            "action":"recalulate",
            "start_year_val":"1900",
            "end_year_val":"2019",
            "viewAbstractUrl":"",
            "LinksAreAllowedRightClick": "full_record.do",
            "filters":filters,
            "timeSpan": timeSpan,
            "db_editions": "",
            "additional_qoutput_params": "cr_qid="+qid,
            "print_opt":"Html",
            "include_mark_from_in_url":"true",
            "endYear":endYear,
            "startYear":startYear,
            "piChart":piChart,
            "toChart":toChart,
            "fields":"DUMMY_VALUE"
        })

        sres.print(command='log', msg='%s부터 %s레코드까지 엑셀을 다운로드 받습니다.'%(mark_from, mark_to))

        ExcelActionURL = "https://ets.webofknowledge.com"
        ExcelAction = "/ETS/ets.do?"
        
        ExcelParam = "mark_from=" + mark_from
        ExcelParam += "&product=UA"
        ExcelParam += "&colName=WOS"
        ExcelParam += "&displayUsageInfo=true"
        ExcelParam += "&parentQid=" + qid
        ExcelParam += "&rurl=" + requests.utils.quote(rurl)
        ExcelParam += "&startYear=" + startYear
        ExcelParam += "&mark_to=" + mark_to
        ExcelParam += "&filters=" + requests.utils.quote(filters)
        ExcelParam += "&qid=" + str(int(qid) + 1)
        ExcelParam += "&endYear=" + endYear
        ExcelParam += "&SID=" + self.SID
        ExcelParam += "&totalMarked=" + mark_to
        ExcelParam += "&action=crsaveToFile"
        ExcelParam += "&timeSpan=" + requests.utils.quote(timeSpan)
        ExcelParam += "&sortBy=" + sortBy
        ExcelParam += "&displayTimesCited=false"
        ExcelParam += "&displayCitedRefs=true"
        ExcelParam += "&fileOpt=xls"
        ExcelParam += "&UserIDForSaveToRID=null"

        ExcelActionURL += ExcelAction
        ExcelActionURL += ExcelParam

        res = requests.get(ExcelActionURL)
        if res.text.find("<html>") > 0 or res.text.find("Error report</title>") > 0:
            sres.print(command='err', msg='WOS 서버에서 에러를 보내왔습니다.')
            sres.print(command=resName, target='errQuery', res={'query': query, 'msg': '인용중 논문 검색 중 WOS 서버에서 에러를 보내왔습니다.'})
            self.backToGeneralSearch()
            return paperData

        directory = os.path.join(os.environ["HOMEPATH"], "Desktop/인용중인논문들")
        ofileName = "인용중인논문_ID{0}.xls".format(paperData['id'])
        outputLocationPath = directory

        if not os.path.exists(directory):
            os.makedirs(directory)

        while(os.path.exists(outputLocationPath + "/" + ofileName)):
            ofileName = "_" + ofileName

        with open(outputLocationPath + "/" + ofileName, "wb") as rsFile:
            rsFile.write(res.content)
            rsFile.close()
        resPD = pd.read_excel(outputLocationPath + "/" + ofileName, header=26)
        citingTitle = resPD['Title'].values.tolist()
        citingAuthors = resPD['Authors'].values.tolist()
        
        if pAuthors != '':
            pAuthors = list(map(lambda x: x.replace(' ', ''), pAuthors.split(';')))
        
        citingArticles = {'id': paperData['id'], 'selfCitation': 0, 'othersCitation': 0, 'titles': [], 'authors': [], 'isSelf': []}
        for idx, title in enumerate(citingTitle):
            if citingTitle[idx] == None or citingTitle[idx] == 'null': continue
            citingArticles['titles'] += [citingTitle[idx]]
            citingArticles['authors'] += [citingAuthors[idx]]
            if pAuthors != '':
                found = False
                temp = citingAuthors[idx].replace(' ', '')
                # temp = fr_authors_text.replace(' ', '')
                for pa in pAuthors:
                    if re.search(pa, temp, flags=re.IGNORECASE):
                        found = True
                        citingArticles['selfCitation'] += 1
                        citingArticles['isSelf'] += ['Self']
                if not found:
                    citingArticles['othersCitation'] += 1
                    citingArticles['isSelf'] += ['Others\'']

            else:
                citingArticles['isSelf'] += ['-']
        
        sres.print(command='log', msg='인용 중인 논문 정보를 가져왔습니다.')
        sres.print(command=resName, target='citingArticles', res=citingArticles)

        self.backToGeneralSearch()

class MultiSearch():
    def __init__(self, sres):
        WosPool = []
        threadAmount = 16
        self.lock = threading.Lock()
        self.threadAmount = threadAmount
        self.sres = sres
        with concurrent.futures.ThreadPoolExecutor(max_workers=threadAmount) as executor:
            sres = sju_response.SJUresponse('MultiCitationSearch')
            future_wos = {executor.submit(SingleSearch, sres): no for no in range(threadAmount)}
            for future in concurrent.futures.as_completed(future_wos):
                no = future_wos[future]
                try:
                    wos = future.result()
                    WosPool.append({'wos':wos, 'no':no, 'lock': threading.Lock()})
                except Exception as e:
                    print(e)
                else:
                    sres.print(command='log', msg='Wos Pool %d번 생성완료'%(no))

        self.WosPool = WosPool

    def getIdleWosAndRun(self, qry, startYear, endYear, gubun):
        worker = ''
        self.lock.acquire()
        for wosContainer in self.WosPool:
            if not wosContainer['lock'].locked():
                with wosContainer['lock']:
                    try:
                        self.sres.print(command='log', msg='Wos Pool에서 %d번 객체를 받았습니다.'%wosContainer['no'])
                        worker = wosContainer
                    except Exception as e:
                        self.sres.print(command='err', msg='Wos Pool %d번을 받는 과정에서 에러.'%wosContainer['no'])
                        self.lock.release()
                    else:
                        self.lock.release()
                        sres = worker['wos'].sres
                        try:
                            sres.print(command='log', msg='[%d번 객체] 검색을 시작합니다.'%wosContainer['no'])
                            res = worker['wos'].generalSearch(qry, startYear, endYear, gubun, 'mres')
                        except Exception as e:
                            sres.print(command='sysErr', msg='[%d번 객체] 검색 도중 오류를 일으켰습니다.'%wosContainer['no'])
                            sres.print(command='errObj', msg=e)
                        else:
                            sres.print(command='log', msg='[%d번 객체] 검색을 완료했습니다.'%wosContainer['no'])
                            return True
                    finally:
                        break
        
        if worker == '':
            self.sres.print(command='log', msg='모든 Wos Pool이 바쁩니다.')
            sres = sju_response.SJUresponse('MultiCitationSearch')
            wos = SingleSearch(sres)
            no = len(self.WosPool)
            self.WosPool.append({'wos':wos, 'no':no, 'lock': threading.Lock()})
            return False
        
        return

    def getQueryListFromFile(self, path):
        fname, ext = os.path.splitext(path)
        encodings = ['utf-8', 'cp949', 'euc-kr']
        for decodeCodec in encodings:
            try:
                if ext == ".csv":
                    df = pd.read_csv(path, header=0, encoding=decodeCodec)
                    queryList = df.values[:].tolist()
                elif ext == ".xls" or ext == ".xlsx":
                    df = pd.read_excel(path, header=0, encoding=decodeCodec)
                    queryList = df.values[:].tolist()
                else:
                    raise Exception()
            except UnicodeDecodeError as e:
                pass
                # traceback.print_tb(e.__traceback__) 
            else:
                break

        returnQueryList = []
        for idx, qry in enumerate(queryList):
            if type(qry[0]) == type(np.nan) or not qry[0]:
                continue
            else :
                if (len(qry[0]) < 3) or qry[0].strip() == '':
                    continue

                if len(qry) == 1:
                    queryList[idx] = (qry[0], '')
                else:
                    queryList[idx] = (qry[0], '' if type(qry[1]) == type(np.nan) else qry[1])
                returnQueryList += [queryList[idx]]

        return returnQueryList
            
    def generalSearch(self, startYear, endYear, gubun, path):
        threadAmount = self.threadAmount
        WosPool = self.WosPool
        sres = self.sres
        sres.print('log', msg='상세 엑셀 검색을 시작합니다.')
        sres.print('log', msg='%s에서 인풋을 읽습니다.'%path)

        queryList = []
        try:
            queryList = self.getQueryListFromFile(path)
        except Exception as e:
            sres.print(command='err', msg='인풋을 읽는 중 오류가 발생했습니다.')
            sres.print(command='errObj', msg=e)
            return
        
        with concurrent.futures.ThreadPoolExecutor(max_workers=threadAmount) as executor:
            future_wos = {
                executor.submit(
                    self.getIdleWosAndRun,
                    qry, 
                    startYear, 
                    endYear, 
                    gubun): qry for qry in queryList}
            for future in concurrent.futures.as_completed(future_wos):
                qry = future_wos[future]
                try:
                    paperData = future.result()
                except Exception as e:
                    sres.print(command='errObj', msg=e)
                    sres.print(command='err', msg='상세 엑셀 검색 중 치명적인 오류가 발생했습니다.')
                # else:
                #     sres.print(command='mres', target='mPaperData', msg=paperData)

class OneByOneSearch():
    def __init__(self, sres):
        self.searchCnt = 0
        self.sres = sres
        self.browser = RoboBrowser(history=True, parser='lxml')
        self.baseUrl = "http://apps.webofknowledge.com"
        self.browser.open(self.baseUrl)

        self.SID = self.browser.session.cookies['SID'].replace("\"", "")
        self.jsessionid = self.browser.session.cookies['JSESSIONID']

        param = '?product=WOS'
        param += '&search_mode=GeneralSearch'
        param += '&preferencesSaved='
        param += '&SID=' + self.SID
        self.browser.open(self.baseUrl + '/WOS_GeneralSearch_input.do' + param)

    def backToGeneralSearch(self):
        sres = self.sres

        if self.searchCnt % 200 == 0:
            self.browser = RoboBrowser(history=True, parser='lxml')
            self.baseUrl = "http://apps.webofknowledge.com"
            self.browser.open(self.baseUrl)

            self.SID = self.browser.session.cookies['SID'].replace("\"", "")
            self.jsessionid = self.browser.session.cookies['JSESSIONID']

            sres.print(command='log', msg='SID : %s'%(self.SID))
            sres.print(command='log', msg='jsessionid : %s'%(self.jsessionid))
            sres.print(command='log', msg='WOS GeneralSearch를 엽니다.')

        sres.print(command='log', msg='브라우저를 초기화합니다.')
        param = '?product=WOS'
        param += '&search_mode=GeneralSearch'
        param += '&preferencesSaved='
        param += '&SID=' + self.SID
        self.browser.open(self.baseUrl + '/WOS_GeneralSearch_input.do' + param)
        
        sres.print(command='log', msg='초기화가 완료되었습니다.')

    def getCitingArticleDetail(self, url, query):
        browser = RoboBrowser(history=True, parser='lxml')
        browser.open(url)

        title = browser.select('div.title')[0].text

        reprint = ''
        authors = []
        fr_authors = []
        fr_addresses = []
        for fr_field in browser.select('p.FR_field'):
            if fr_field.text.find('By:') != -1:
                fr_authors = fr_field
            if fr_field.text.find('Addresses:') != -1:
                if fr_field.text.find('E-mail') != -1:
                    continue
                fr_addresses = fr_field.nextSibling
                
        addresses = {}
        #저자, 연구기관
        fconts = fr_authors.select('a')
        fr_authors_text = fr_authors.text.replace('\n', '')
        targetAuthor = ''
        tauthorAddress = []
        for con in fconts:
            isSub = con.get('href').find('javascript') != -1
            if not isSub:
                if targetAuthor != '':
                    addresses[targetAuthor] = tauthorAddress
                tauthorAddress = []
                targetAuthor = con.text
                authors += [targetAuthor]
            else:
                addressId = re.sub(r'.+\'(.+)\'.+', r'\1', con.get('href'))
                temp = fr_addresses.find('a', id=addressId)
                if temp != None:
                    tauthorAddress += [temp.text]

        if targetAuthor != '':
                    addresses[targetAuthor] = tauthorAddress

        single_citing_data = {
            'title': title,
            'authors': " ".join(authors),
            'tauthorAddress': tauthorAddress,
            'fr_authors_text': fr_authors_text,
        }
        return single_citing_data

    def followDetailLink(self, aTagArr_record, query, resName, docNum):
        sres = self.sres
        pAuthors = query[1]
        browser = RoboBrowser(history=True, parser='lxml')
        browser.open(self.baseUrl + aTagArr_record['href'])

        # 논문 제목
        # title = aTagArr_record.text.replace('\n', '')
        title = browser.select('div.title')[0].text

        # ISSN
        ISSN = browser.select('p.sameLine')
        if ISSN:
            ISSN = ISSN[0].value.contents[0]
        else: ISSN = ''

        # 등급
        grades = []
        capedGrades = []
        box_label = browser.select('span.box-label')
        for label in box_label:
            if label.text.find('- ') != -1:
                temp = label.text.replace('- ', '')
                grades += [temp]
                capedGrades += [re.sub(r'[ a-z]+', r'', temp)]

        # 임팩트 팩토
        Impact_Factor_table = browser.select('table.Impact_Factor_table')
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
        JCR_Category_table = browser.select('table.JCR_Category_table')
        jcr_headers = []
        jcr = []
        ranks = []
        goodRank = ''
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
        
            goodRank = max(ranks, key=lambda x: -x[-1])[-1]

        # 인용 횟수 및 링크
        cnt_link = browser.select('a.snowplow-citation-network-times-cited-count-link')
        if not cnt_link:
            timesCited = '0'
        else:
            cnt_link = cnt_link[0]
            timesCited = cnt_link.span.text

        # 기타 필드
        correction_form = browser.find(action='http://ips.clarivate.com/cgi-bin/forms/wok_datachange/wok-proc.pl')
        correction_form_inputs_by_name = {}
        for inputTag in correction_form.find_all('input'):
            inputDict = inputTag.attrs
            correction_form_inputs_by_name[inputDict['name']] = inputDict['value']
        
        docType = ''
        publishedMonth = ''
        researchAreas = ''
        publisher = ''
        language = ''
        reprint = ''
        authors = []
        fr_authors = []
        fr_addresses = []
        for fr_field in browser.select('p.FR_field'):
            if fr_field.text.find('Document Type:') != -1:
                docType = fr_field.text.split(':')[1]
            
            if fr_field.text.find('Published:') != -1:
                publishedMonth = fr_field.text.split(':')[1]

            if fr_field.text.find('Research Areas:') != -1:
                researchAreas = fr_field.text.split(':')[1]

            if fr_field.text.find('Publisher ') != -1:
                publisher = ' '.join(fr_field.text.split(' ')[1:])
                publisher = publisher.split(',')

            if fr_field.text.find('Language:') != -1:
                language = fr_field.text.split(':')[1]
                
            if fr_field.text.find('Reprint Address:') != -1:
                reprint = fr_field.text.split(':')[1].replace('\n', '').strip()

            if fr_field.text.find('By:') != -1:
                fr_authors = fr_field
            if fr_field.text.find('Addresses:') != -1:
                if fr_field.text.find('E-mail') != -1:
                    continue
                fr_addresses = fr_field.nextSibling
                
        addresses = {}
        #저자, 연구기관
        fconts = fr_authors.select('a')
        fr_authors_text = fr_authors.text.replace('\n', '')
        targetAuthor = ''
        tauthorAddress = []
        for con in fconts:
            isSub = con.get('href').find('javascript') != -1
            if not isSub:
                if targetAuthor != '':
                    addresses[targetAuthor] = tauthorAddress
                tauthorAddress = []
                targetAuthor = con.text
                authors += [targetAuthor]
            else:
                addressId = re.sub(r'.+\'(.+)\'.+', r'\1', con.get('href'))
                temp = fr_addresses.find('a', id=addressId)
                if temp != None:
                    tauthorAddress += [temp.text]

        if targetAuthor != '':
                    addresses[targetAuthor] = tauthorAddress
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
            'publishedMonth' : publishedMonth,
            'publisher' : publisher,
            # 'title' : correction_form_inputs_by_name['00N70000002BdnX'],
            'title' : title,
            'impact_factor' : impact_factor,
            'prevYearIF' : 'None',
            'goodRank' : goodRank,
            'timesCited' : timesCited,
            'grades' : grades,
            'capedGrades' : capedGrades,
            'docType' : docType,
            'researchAreas' : researchAreas,
            'language' : language,
            'reprint' : reprint,
            'jcr' : jcr,
            'citingArticles' : [],
            'issn' : ISSN,
        }
        paperData['ivp'] = ['%s/%s'%(paperData['issue'], paperData['volume']), paperData['pages']]

        # 전년도 임팩트 팩토
        prevYear = str(int(paperData['published']) - 1)
        if prevYear in impact_factor.keys():
            paperData['prevYearIF'] = impact_factor[prevYear]

        sres.print(command='log', msg='%d번 해당 논문의 정보 검색을 완료했습니다.'%docNum)
        sres.print(command=resName, target='paperData', res=paperData)
        
        stepFlag = True
        if not cnt_link:
            sres.print(command='log', msg='%d번 논문을 인용한 논문이 없으므로 검색을 종료합니다.'%docNum)
            # sres.print(command=resName, target='errQuery', res={'query': query, 'msg': '논문을 인용한 논문이 없습니다.'})
            stepFlag = False
        elif int(timesCited) > 500 :
            sres.print(command='err', msg='%d번 해당 논문을 인용한 논문이 500개를 초과하여 검색을 종료합니다.'%docNum)
            sres.print(command=resName, target='errQuery', res={'query': query, 'msg': '%d번 해당 논문을 인용한 논문이 500개를 초과하였습니다.'%docNum})
            stepFlag = False

        if not stepFlag:
            return paperData

        citingArticles = {'id': paperData['id'], 'selfCitation': 0, 'othersCitation': 0, 'titles': [], 'fr_authors_text': [], 'authors': [], 'isSelf': []}
        sres.print(command='log', msg='%d번 논문을 인용 중인 논문 정보 페이지를 가져옵니다.'%docNum)

        # 사이팅 리포트 페이지 접속
        browser.follow_link(cnt_link)

        # 인용하는 논문이 적을 경우 인용 보고서를 제작하지 않고 바로 파싱한다.
        # [1] 인용 논문 스레딩 조회 
        if int(timesCited) < 11:
        # if int(timesCited) > 0:
            refine_form = browser.get_form(id='refine_form')
            qid = refine_form['parentQid'].value

            citing_record_url = 'http://apps.webofknowledge.com/full_record.do'
            citing_param = '?product=WOS'
            citing_param += '&search_mode=CitingArticles'
            citing_param += '&search_mode=CitingArticles'
            citing_param += '&qid=' + qid
            citing_param += '&SID=' + self.browser.session.cookies['SID'].replace("\"", "")
            citing_param += '&page='
            citing_param += '&doc='

            # 인용 중인 논문별 퓨쳐 객체 생성 및 실행 [저자 기준]
            with concurrent.futures.ThreadPoolExecutor(max_workers=10) as exceutor:
                future_citing = {
                    exceutor.submit(
                        self.getCitingArticleDetail,
                        citing_record_url + citing_param + str(citing_doc),
                        query,
                    ): citing_doc for citing_doc in range(1, int(timesCited) + 1)
                }
                for future in concurrent.futures.as_completed(future_citing):
                    citing_doc = future_citing[future]

                    try:
                        single_citing_data = future.result()
                        if not single_citing_data:
                            print('citing_doc %d 연결 실패'%citing_doc)
                            continue
                        
                        citingArticles['titles'] += [single_citing_data['title']]
                        citingArticles['authors'] += [single_citing_data['authors']]
                        citingArticles['fr_authors_text'] += [single_citing_data['fr_authors_text']]
                    except Exception as e:
                        sres.print(command=resName, target='errQuery', res={'query': query, 'msg':'인용한 논문 중 %d번 레코드 검색 중 에러가 발생.'%(citing_doc)})
                        sres.print(command='errObj', msg=e)

            # 모든 인용 중 논문 조회 완료, 자기인용, 타인인용 검색
            if pAuthors != '':
                pAuthors = list(map(lambda x: x.replace(' ', ''), pAuthors.split(';')))

                for idx, testString in enumerate(citingArticles['fr_authors_text']):
                    found = False
                    for pa in pAuthors:
                        if re.search(pa, testString, flags=re.IGNORECASE):
                            found = True
                            citingArticles['selfCitation'] += 1
                            citingArticles['isSelf'] += ['Self']
                    if not found:
                        citingArticles['othersCitation'] += 1
                        citingArticles['isSelf'] += ['Others\'']        

            sres.print(command='log', msg='%d 번을 인용 중인 논문 정보를 가져왔습니다.'%docNum)
            sres.print(command=resName, target='citingArticles', res=citingArticles)
            paperData['citingArticles'] = citingArticles
            return paperData

        # [2] 인용 논문 엑셀 다운로딩 조회
        reportLink = browser.select("a.citation-report-summary-link")[0]
        browser.follow_link(reportLink)
        
        summary_records_form = browser.get_form(id='summary_records_form')
        qid = summary_records_form['qid'].value
        filters = summary_records_form['filters'].value
        sortBy = summary_records_form['sortBy'].value
        timeSpan = summary_records_form['timeSpan'].value
        endYear = summary_records_form['endYear'].value
        startYear = summary_records_form['startYear'].value
        rurl = summary_records_form['rurl'].value

        mark_from = '1'
        mark_to = timesCited

        piChart = summary_records_form['piChart'].value
        toChart = summary_records_form['piChart'].value

        makeExcelURL = "http://apps.webofknowledge.com/OutboundService.do?"
        makeExcelParam = ""
        makeExcelParam += "action=go"
        makeExcelParam += "&save_options=xls"

        makeExcelURL += makeExcelParam

        # 엑셀 데이터 제작 요청
        sres.print(command='log', msg='%d번 논문을 인용한 %s부터 %s레코드까지 데이터 제작을 요청합니다.'%(docNum, mark_from, mark_to))
        browser.session.post(makeExcelURL, data={
            "selectedIds": "",
            "displayCitedRefs":"",
            "displayTimesCited":"",
            "displayUsageInfo":"true",
            "viewType":"summary",
            "product":"WOS",
            "rurl":rurl,
            "mark_id":"WOS",
            "colName":"WOS",
            "search_mode":"CitationReport",
            "view_name":"WOS-CitationReport-summary",
            "sortBy": sortBy,
            "mode":"OpenOutputService",
            "qid":qid,
            "SID":self.SID,
            "format":"crsaveToFile",
            "mark_to":mark_to,
            "mark_from":mark_from,
            "queryNatural":"",
            "count_new_items_marked":"0",
            "use_two_ets":"false",
            "IncitesEntitled":"no",
            "value(record_select_type)":"range",
            "markFrom":mark_from,
            "markTo":mark_to,
            "action":"recalulate",
            "start_year_val":"1900",
            "end_year_val":"2019",
            "viewAbstractUrl":"",
            "LinksAreAllowedRightClick": "full_record.do",
            "filters":filters,
            "timeSpan": timeSpan,
            "db_editions": "",
            "additional_qoutput_params": "cr_qid="+qid,
            "print_opt":"Html",
            "include_mark_from_in_url":"true",
            "endYear":endYear,
            "startYear":startYear,
            "piChart":piChart,
            "toChart":toChart,
            "fields":"DUMMY_VALUE"
        })

        # 엑셀 데이터 다운로드
        sres.print(command='log', msg='%d번 논문을 인용한 %s부터 %s레코드까지 엑셀을 다운로드 받습니다.'%(docNum, mark_from, mark_to))

        ExcelActionURL = "https://ets.webofknowledge.com"
        ExcelAction = "/ETS/ets.do?"
        
        ExcelParam = "mark_from=" + mark_from
        ExcelParam += "&product=UA"
        ExcelParam += "&colName=WOS"
        ExcelParam += "&displayUsageInfo=true"
        ExcelParam += "&parentQid=" + qid
        ExcelParam += "&rurl=" + requests.utils.quote(rurl)
        ExcelParam += "&startYear=" + startYear
        ExcelParam += "&mark_to=" + mark_to
        ExcelParam += "&filters=" + requests.utils.quote(filters)
        ExcelParam += "&qid=" + str(int(qid) + 1)
        ExcelParam += "&endYear=" + endYear
        ExcelParam += "&SID=" + self.SID
        ExcelParam += "&totalMarked=" + mark_to
        ExcelParam += "&action=crsaveToFile"
        ExcelParam += "&timeSpan=" + requests.utils.quote(timeSpan)
        ExcelParam += "&sortBy=" + sortBy
        ExcelParam += "&displayTimesCited=false"
        ExcelParam += "&displayCitedRefs=true"
        ExcelParam += "&fileOpt=xls"
        ExcelParam += "&UserIDForSaveToRID=null"

        ExcelActionURL += ExcelAction
        ExcelActionURL += ExcelParam

        res = requests.get(ExcelActionURL)
        if res.text.find("<html>") > 0 or res.text.find("Error report</title>") > 0:
            sres.print(command='err', msg='WOS 서버에서 에러를 보내왔습니다.')
            sres.print(command=resName, target='errQuery', res={'query': query, 'msg': '%d번을 인용한 논문 검색 중 WOS 서버에서 에러를 보내왔습니다.'%docNum})
            self.backToGeneralSearch()
            return paperData

        directory = os.path.join(os.environ["HOMEPATH"], "Desktop/인용중인논문들")
        ofileName = "인용중인논문_ID{0}.xls".format(paperData['id'])
        outputLocationPath = directory

        # 엑셀 데이터 로컬 저장
        if not os.path.exists(directory):
            os.makedirs(directory)

        while(os.path.exists(outputLocationPath + "/" + ofileName)):
            ofileName = "_" + ofileName

        with open(outputLocationPath + "/" + ofileName, "wb") as rsFile:
            rsFile.write(res.content)
            rsFile.close()
        resPD = pd.read_excel(outputLocationPath + "/" + ofileName, header=26)
        citingTitle = resPD['Title'].values.tolist()
        citingAuthors = resPD['Authors'].values.tolist()
        
        # 엑셀 데이터로부터 자기인용, 타인인용 판별
        if pAuthors != '':
            pAuthors = list(map(lambda x: x.replace(' ', ''), pAuthors.split(';')))
        
        citingArticles = {'id': paperData['id'], 'selfCitation': 0, 'othersCitation': 0, 'titles': [], 'authors': [], 'isSelf': []}
        for idx, title in enumerate(citingTitle):
            if citingTitle[idx] == None or citingTitle[idx] == 'null': continue
            citingArticles['titles'] += [citingTitle[idx]]
            citingArticles['authors'] += [citingAuthors[idx]]
            if pAuthors != '':
                found = False
                temp = citingAuthors[idx].replace(' ', '')
                # temp = fr_authors_text.replace(' ', '')
                for pa in pAuthors:
                    if re.search(pa, temp, flags=re.IGNORECASE):
                        found = True
                        citingArticles['selfCitation'] += 1
                        citingArticles['isSelf'] += ['Self']
                if not found:
                    citingArticles['othersCitation'] += 1
                    citingArticles['isSelf'] += ['Others\'']

            else:
                citingArticles['isSelf'] += ['-']
        
        sres.print(command='log', msg='%d번 논문을 인용 중인 논문 정보를 가져왔습니다.'%docNum)
        sres.print(command=resName, target='citingArticles', res=citingArticles)
        paperData['citingArticles'] = citingArticles
        return paperData

    def generalSearch(self, query, startYear, endYear, gubun, resName):
        sres = self.sres
        browser = self.browser
        baseUrl = self.baseUrl
        SID = self.SID
        jsessionid = self.jsessionid
        pAuthors = query[1]

        # 저자명 검색 로직 시작
        sres.print('log', msg='저자명 검색을 시작합니다.')

        WOS_GeneralSearch_input_form = browser.get_form('WOS_GeneralSearch_input_form')
        WOS_GeneralSearch_input_form['value(input1)'] = query[0]
        WOS_GeneralSearch_input_form['value(select1)'] = gubun
        WOS_GeneralSearch_input_form['startYear'] = startYear
        WOS_GeneralSearch_input_form['endYear'] = endYear
        WOS_GeneralSearch_input_form['range'] = 'CUSTOM'
        WOS_GeneralSearch_input_form['period'].options.append('Year Range')
        WOS_GeneralSearch_input_form['period'] = 'Year Range'

        # 저자명 검색에서 같이 검색할 연구기관이 있는 경우
        if query[2] != '':
            WOS_GeneralSearch_input_form['formUpdated'] = 'true'
            WOS_GeneralSearch_input_form['limitStatus'] = 'expanded'
            f1 = Input('<input name="value(bool_1_2)" value="AND" />')
            f2 = Input('<input name="value(input2)" value="%s" />'%(query[2]))
            f3 = Input('<input name="value(select2)" value="AD" />')
            f4 = Input('<input name="fieldCount" value="2" />')
            WOS_GeneralSearch_input_form.add_field(f1)
            WOS_GeneralSearch_input_form.add_field(f2)
            WOS_GeneralSearch_input_form.add_field(f3)
            WOS_GeneralSearch_input_form.add_field(f4)
            WOS_GeneralSearch_input_form['value(bool_1_2)'] = 'AND'
            WOS_GeneralSearch_input_form['value(input2)'] = query[2]
            WOS_GeneralSearch_input_form['value(select2)'] = 'AD'
            WOS_GeneralSearch_input_form['fieldCount'] = '2'

        sres.print('log', msg='검색어 : %s'%(query[0]))
        browser.submit_form(WOS_GeneralSearch_input_form)
        self.searchCnt += 1

        # 검색 결과 페이지 응답 받음, full_record조회 준비
        aTagArr_record = browser.select('a.snowplow-full-record')
        hitCount = browser.find('span', id='trueFinalResultCount')

        stepFlag = True
        if not aTagArr_record:
            sres.print(command='err', msg='%s의 결과가 없습니다.'%query[0])
            sres.print(command=resName, target='errQuery', res={'query': query, 'msg': '검색결과가 없습니다.'})
            stepFlag = False

        if not stepFlag:
            self.backToGeneralSearch()
            return

        qid = browser.get_form(id='refine_form')['parentQid'].value
        hitCount = int(hitCount.contents[0].replace(',', ''), base=10)

        full_record_actionUrl = '/full_record.do'
        param = '?product=WOS'
        param += '&search_mode=GeneralSearch'
        param += '&qid=' + qid
        param += '&SID=' + self.SID
        param += '&page='
        param += '&doc='        
        docParam = 1

        # 각 레코드에 대한 future객체 생성 및 실행
        with concurrent.futures.ThreadPoolExecutor(max_workers=16) as exceutor:
            
            future_detail = {
                exceutor.submit(
                    self.followDetailLink, 
                    bs4.Tag(name='a', attrs={'href': '%s%s%d'%(full_record_actionUrl, param, docParam)}), 
                    query, 
                    resName,
                    docParam,
                ): docParam for docParam in range(1, hitCount)
            }
            for future in concurrent.futures.as_completed(future_detail):
                docParam = future_detail[future]

                try:
                    singlePaperData = future.result()
                except Exception as e:
                    sres.print(command=resName, target='errQuery', res={'query': query, 'msg':'%d번 논문 검색 중 에러가 발생.'%(docParam)})
                    sres.print(command='errObj', msg=e)
                else:
                    sres.print(command='log', msg='%d번 논문 상세 검색 완료.'%(docParam))
        
        sres.print(command='log', msg='%d까지 검색이 완료되었습니다.'%(docParam))
        self.backToGeneralSearch()

# if __name__ == "__main__":
#     sres = sju_response.SJUresponse('singleTest')
#     sl = OneByOneSearch(sres)
#     sl.generalSearch(
#         ('Back SW', 'Lee', ''),
#         '2010',
#         '2018',
#         'AU',
#         'ares'
#     )

if __name__ == "__main__":
    sres = sju_response.SJUresponse('test')
    ml = MultiSearch(sres)
    ml.generalSearch('2010', '2018', 'TI', 
    'C:\\Users\\F\\Desktop\\testData\\test1_short.xlsx')

# if __name__ == "__main__":
#     sres = sju_response.SJUresponse('singleTest')
#     sl = SingleSearch(sres)
#     sl.generalSearch(
#         ('Compassionate attitude towards others suffering activates the mesolimbic neural system', 'Kim, Ji-Woong; Kim, JW'),
#         '1945',
#         '2018',
#         'TI',
#         'res'
#     )

# if __name__ == "__main__":
#     path = 'C:/Users/F/Downloads/input.csv'
#     fname, ext = os.path.splitext(path)
#     if ext == ".csv":
#         df = pd.read_csv(path, header=0)
#         queryList = df.values[:, 0]
#         pAuthorList = df.values[:, 1]
#     elif ext == ".xls" or ext == ".xlsx":
#         df = pd.read_excel(path, header=0)
#         queryList = df.values[:, 0]
#         pAuthorList = df.values[:, 1]
#     else:
#         raise Exception()

#     for idx, qry in enumerate(queryList):
#         queryList[idx] = (qry.strip(), pAuthorList[idx])

#     print(queryList)
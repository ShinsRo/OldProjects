import random
import os
import sys
import time
import sju_response
import pandas as pd
import requests
from robobrowser import RoboBrowser
import xlrd
import json
import re
import bs4
import concurrent.futures
import threading

class SingleSearch():
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
        sres.print(command='res', target='loading', res=False)

    def generalSearch(self, query, startYear, endYear, gubun, resName):
        sres = self.sres
        browser = self.browser
        baseUrl = self.baseUrl
        SID = self.SID
        jsessionid = self.jsessionid
        sres.print(command='res', target='loading', res=True)

        sres.print('log', msg='단일 조회를 시작합니다.')
        WOS_GeneralSearch_input_form = browser.get_form('WOS_GeneralSearch_input_form')
        WOS_GeneralSearch_input_form['value(input1)'] = query
        WOS_GeneralSearch_input_form['value(select1)'] = gubun
        WOS_GeneralSearch_input_form['startYear'] = startYear
        WOS_GeneralSearch_input_form['endYear'] = endYear
        WOS_GeneralSearch_input_form['range'] = 'CUSTOM'

        sres.print('log', msg='검색어 : %s'%(query))
        browser.submit_form(WOS_GeneralSearch_input_form)
        self.searchCnt += 1

        aTagArr_record = browser.select('a.snowplow-full-record')

        stepFlag = True
        if not aTagArr_record:
            sres.print(command='err', msg='%s의 결과가 없습니다.'%query)
            stepFlag = False
        elif len(aTagArr_record) > 1:
            sres.print(command='err', msg='검색 결과가 하나 이상입니다.')
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
        if not ISSN:
            ISSN = ISSN.find_all('value')[0].text
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
        trs = []
        if len(JCR_Category_table) > 0: 
            JCR_Category_table = JCR_Category_table[0]
            trs = JCR_Category_table.find_all('tr')
            if trs:
                jcr.append([x.text.strip() for x in trs[0].find_all('th')])
                for tr in trs[1:]:
                    jcr.append([x.text.strip() for x in tr.find_all('td')])

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
        targetAuthor = ''
        tauthorAddress = []
        for con in fconts:
            isSub = con.get('href').find('javascript') != -1
            if not isSub:
                if targetAuthor != '':
                    addresses[targetAuthor] = tauthorAddress
                tauthorAddress = []
                targetAuthor =  con.contents[0]
                authors += [targetAuthor]
            else:
                addressId = re.sub(r'.+\'(.+)\'.+', r'\1', con.get('href'))
                temp = fr_addresses.find('a', id=addressId)
                if temp != None:
                    tauthorAddress += [temp.contents[0]]

        if reprint == '':
            reprint = 'None'

        paperData = {
            'id' : random.getrandbits(32),
            # 'authors' : correction_form_inputs_by_name['00N70000002C0wa'].split(';'),
            'authors' : authors,
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
        paperData['citingArticles'] = {'id': paperData['id'], 'titles': [], 'authors': []}

        # 전년도 임팩트 팩토
        prevYear = str(int(paperData['published']) - 1)
        if prevYear in impact_factor.keys():
            paperData['prevYearIF'] = impact_factor[prevYear]

        sres.print(command='log', msg='해당 논문의 정보 검색을 완료했습니다.')
        sres.print(command=resName, target='paperData', res=paperData)
        
        stepFlag = True
        if not cnt_link:
            sres.print(command='err', msg='논문을 인용한 논문이 없으므로 검색을 종료합니다.')
            stepFlag = False
        elif int(timesCited) > 500 :
            sres.print(command='err', msg='해당 논물을 인용한 논문이 500개를 초과하여 검색을 종료합니다.')
            stepFlag = False

        if not stepFlag:
            sres.print(command='log', msg='브라우저를 초기화합니다.')
            param = '?product=WOS'
            param += '&search_mode=GeneralSearch'
            param += '&preferencesSaved='
            param += '&SID=' + self.SID
            self.browser.open(self.baseUrl + '/WOS_GeneralSearch_input.do' + param)
            return paperData

        sres.print(command='log', msg='인용 중인 논문 정보 페이지를 가져옵니다.')
        browser.follow_link(cnt_link)
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

        citingArticles = {'id': paperData['id'], 'titles': [], 'authors': []}
        for idx, title in enumerate(citingTitle):
            if citingTitle[idx] == None or citingTitle[idx] == 'null': continue
            citingArticles['titles'] += [citingTitle[idx]]
            citingArticles['authors'] += [citingAuthors[idx]]
        
        sres.print(command='log', msg='인용 중인 논문 정보를 가져왔습니다.')
        sres.print(command=resName, target='citingArticles', res=citingArticles)

        self.backToGeneralSearch()

class MultiSearch():
    def __init__(self, sres):
        WosPool = []
        threadAmount = 20
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
                            sres.print(command='err', msg='[%d번 객체] 검색 도중 오류를 일으켰습니다.'%wosContainer['no'])
                        else:
                            sres.print(command='log', msg='[%d번 객체] 검색을 완료했습니다.'%wosContainer['no'])
                    finally:
                        break
        
        if worker == '':
            self.sres.print(command='log', msg='모든 Wos Pool이 바쁩니다.')
            sres = sju_response.SJUresponse('MultiCitationSearch')
            wos = SingleSearch(sres)
            no = len(self.WosPool)
            self.WosPool.append({'wos':wos, 'no':no, 'lock': threading.Lock()})
            return False
        
        return res

    def getQueryListFromFile(self, path):
        fname, ext = os.path.splitext(path)
        if ext == ".csv":
            df = pd.read_csv(path, header=0)
            queryList = df.values[:, 0]
        elif ext == ".xls" or ext == ".xlsx":
            df = pd.read_excel(path, header=0)
            queryList = df.values[:, 0]
        else:
            raise Exception()

        for idx, qry in enumerate(queryList):
            queryList[idx] = qry.strip()

        return queryList
            
    def generalSearch(self, startYear, endYear, gubun, path):
        threadAmount = self.threadAmount
        WosPool = self.WosPool
        sres = self.sres
        sres.print(command='res', target='loading', res=True)
        sres.print('log', msg='상세 엑셀 검색을 시작합니다.')
        sres.print('log', msg='%s에서 인풋을 읽습니다.'%path)

        queryList = []
        try:
            queryList = self.getQueryListFromFile(path)
        except Exception as e:
            pass

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

if __name__ == "__main__":
    sres = sju_response.SJUresponse('test')
    ml = MultiSearch(sres)
    ml.generalSearch('2010', '2018', 'TI', 'C:\\input.csv')
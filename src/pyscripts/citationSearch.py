import logging
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

class SingleSearch():
    def __init__(self, sres):
        sres.print(command='log', msg='단일 인용정보 조회 서비스를 초기화합니다. 2~3분 정도의 시간이 소요됩니다.')
        self.sres = sres
        self.browser = RoboBrowser(history=True, parser='lxml')
        self.baseUrl = "http://apps.webofknowledge.com"
        self.browser.open(self.baseUrl)

        self.SID = self.browser.session.cookies['SID'].replace("\"", "")
        self.jsessionid = self.browser.session.cookies['JSESSIONID']

        sres.print(command='log', msg='SID : %s'%(self.SID))
        sres.print(command='log', msg='jsessionid : %s'%(self.jsessionid))
        sres.print(command='log', msg='WOS GeneralSearch를 엽니다.')

        param = '?product=WOS'
        param += '&search_mode=GeneralSearch'
        param += '&preferencesSaved='
        param += '&SID=' + self.SID
        self.browser.open(self.baseUrl + '/WOS_GeneralSearch_input.do' + param)
        
        sres.print(command='log', msg='초기화가 완료되었습니다.')
        sres.print(command='res', target='loading', res=False)

    def generalSearch(self, query, startYear, endYear):
        sres = self.sres
        browser = self.browser
        baseUrl = self.baseUrl
        SID = self.SID
        jsessionid = self.jsessionid
        sres.print(command='res', target='loading', res=True)

        sres.print('log', msg='시작합니다.')
        WOS_GeneralSearch_input_form = browser.get_form('WOS_GeneralSearch_input_form')
        WOS_GeneralSearch_input_form['value(input1)'] = query
        WOS_GeneralSearch_input_form['value(select1)'] = 'TI'
        WOS_GeneralSearch_input_form['startYear'] = startYear
        WOS_GeneralSearch_input_form['endYear'] = endYear
        WOS_GeneralSearch_input_form['range'] = 'CUSTOM'

        sres.print('log', msg='검색어 : %s'%(query))
        browser.submit_form(WOS_GeneralSearch_input_form)

        aTagArr_record = browser.select('a.snowplow-full-record')


        stepFlag = True
        if not aTagArr_record:
            sres.print(command='err', msg='결과가 없습니다.')
            stepFlag = False
        elif len(aTagArr_record) > 1:
            sres.print(command='err', msg='검색 결과가 하나 이상입니다.')
            for aTag in aTagArr_record:
                sres.print(command='err', msg='%s'%(aTag.text.replace('\n', '')))
            sres.print(command='err', msg='가장 비슷한 검색 결과로 정보를 가져옵니다.')

        if not stepFlag:
            sres.print(command='log', msg='브라우저를 초기화합니다.')
            param = '?product=WOS'
            param += '&search_mode=GeneralSearch'
            param += '&preferencesSaved='
            param += '&SID=' + self.SID
            self.browser.open(self.baseUrl + '/WOS_GeneralSearch_input.do' + param)
            return

        sres.print(command='log', msg='논문 상세 정보 창에 접근합니다.')
        browser.follow_link(aTagArr_record[0])

        # 논문 제목
        title = aTagArr_record[0].text.replace('\n', '')
        # ISSN
        ISSN = browser.select('p.sameLine')[0]
        ISSN = ISSN.find_all('value')[0].text

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
        Impact_Factor_table = browser.select('table.Impact_Factor_table')[0]
        impact_factor = {}
        if Impact_Factor_table:
            trs = Impact_Factor_table.find_all('tr')
            tds = trs[0].find_all('td')
            ths = trs[1].find_all('th')

            for idx, th in enumerate(ths):
                impact_factor[th.text.strip()] = tds[idx].text.strip()
        else:
            impact_factor = {}
        
        # JCR 랭크
        JCR_Category_table = browser.select('table.JCR_Category_table')[0]
        jcr_headers = []
        jcr = []
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
                temp_field = fr_field.text.replace('\n', '')
                for au in temp_field.split(';'):
                    temp = re.sub(r'.*\((.+)\).*', r'\g<1>', au)
                    if temp and len(temp) > 4: authors += [temp]

        if reprint == '':
            reprint = 'none'

        paperData = {
            'id' : random.getrandbits(32),
            # 'authors' : correction_form_inputs_by_name['00N70000002C0wa'].split(';'),
            'authors' : authors,
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
            'timesCited' : timesCited,
            'grades' : grades,
            'capedGrades' : capedGrades,
            'docType' : docType,
            'researchAreas' : researchAreas,
            'language' : language,
            'reprint' : reprint,
            'jcr' : jcr,
            'citingArticles' : [],
        }
        paperData['ivp'] = [paperData['issue'], paperData['volume'], paperData['pages']]      
        sres.print(command='log', msg='해당 논문의 정보 검색을 완료했습니다.')
        sres.print(command='res', target='paperData', res=paperData)
        
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
            return

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

        ofileName = "{0}__인용논문검색결과.xls".format(paperData['title'])

        outputLocationPath = os.path.join(os.environ["HOMEPATH"], "Desktop")
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
            citingArticles['titles'] += [citingTitle[idx]]
            citingArticles['authors'] += [citingAuthors[idx]]
        
        sres.print(command='log', msg='인용 중인 논문 정보를 가져왔습니다.')
        sres.print(command='res', target='citingArticles', res=citingArticles)

        sres.print(command='log', msg='브라우저를 초기화합니다.')
        param = '?product=WOS'
        param += '&search_mode=GeneralSearch'
        param += '&preferencesSaved='
        param += '&SID=' + self.SID
        self.browser.open(self.baseUrl + '/WOS_GeneralSearch_input.do' + param)

if __name__ == "__main__":
    cnt = 0
    sres = sju_response.SJUresponse('citationSearch')

    while(True):
        sres.print(command='res', target='loading', res=True)
        if cnt == 0:
            try:
                singleSearch = SingleSearch(sres)
            except Exception as e:
                sres.print(command='sysErr', msg='연결에 실패했습니다.')
                sres.print(command='sysErr', msg='인터넷 연결이나 접속한 장소가 유효한 지 확인해주세요.')
                sres.print(command='sysErr', msg='혹은 일시적 현상일 수 있으니 잠시 후 다시 접속해주세요.')
        cnt = (cnt+1)%200
        
        sres.print(command='log', msg='다음 지시를 기다립니다.')
        sres.print(command='res', target='loading', res=False)
        query = input().strip()
        # print(query)
        startYear = input().strip()
        endYear = input().strip()

        errMSG = ''
        try:
            if len(startYear) != 4 or len(endYear) != 4:
                errMSG = "입력 형식이 올바르지 않습니다."
                raise Exception()

            if not 1900 <= int(startYear) <= 2018:
                errMSG = "년도는 1900과 금년 사이여야 합니다."
                raise Exception()

            if not 1900 <= int(endYear) <= 2018:
                errMSG = "년도는 1900과 금년 사이여야 합니다."
                raise Exception()

            if not int(startYear) <= int(endYear):
                errMSG = "검색 기간이 올바르지 않습니다."
                raise Exception()

        except Exception as e:
            sres.print(command='log', msg=errMSG)
            continue
        try:
            singleSearch.generalSearch(query=query, startYear=startYear, endYear=endYear)
        except Exception as e:
            sres.print(command='sysErr', msg='심각한 오류')
            sres.print(command='sysErr', msg=e)
            cnt = 0
        sres.print(command='log', msg='한 쿼리가 완료되었습니다. cnt = %d'%cnt)
        sres.print(command='res', target='loading', res=True)
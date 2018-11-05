from robobrowser import RoboBrowser
from bs4 import BeautifulSoup
from multiprocessing import Process
import urllib
import json
import re 
import requests
import time
import sys
import pandas as pd

class Scraper():
    def __init__(self):
        self.paperList = []
        self.baseUrl = "http://apps.webofknowledge.com"
        self.state = "waiting"
        self.logState(state="0000", stateMSG="SID와 jsessionid 값을 얻습니다.")


        self.browser = RoboBrowser(history=True, parser="lxml")
        # 테스트용
        self.SID = "E2V7RPJe7OYXtfigmbk"
        self.jsessionid = "506EB868497C83AB26B65AA714B04878"

        self.browser.open(self.baseUrl)

        self.SID = self.browser.session.cookies['SID'].replace("\"", "")
        self.jsessionid = self.browser.session.cookies['JSESSIONID']

        self.logState(state="0001", stateMSG="SID : %s"%self.SID)
        self.logState(state="0002", stateMSG="jsessionid : %s"%self.jsessionid)
        self.logState(state="0003", stateMSG="작업을 위해 WOS 페이지에 접근 중입니다.")


        actionURL = self.baseUrl
        action = "/WOS_AdvancedSearch_input.do"
        param = ""
        param += "?SID=" + self.SID
        param += "&product=WOS"
        param += "&search_mode=AdvancedSearch"
        
        actionURL += action
        actionURL += param

        self.browser.open(actionURL)
        self.WOS_AdvancedSearch_input_form = self.browser.get_form(id="WOS_AdvancedSearch_input_form")

        self.logState(state="0200", stateMSG="질의를 시작할 수 있습니다.")


    def makeQueryFromFile(self, path, packSize, gubun):
        # if csv
        df = pd.read_csv(path, header=0)
        words = df.values[:, 0]

        wordsLen = len(words)
        if packSize <= 0 or packSize > wordsLen: packSize = wordsLen

        queryList = [words[chunkIdx: chunkIdx + packSize] for chunkIdx in range(0, wordsLen, packSize)]

        for idx, query in enumerate(queryList):
            queryList[idx] = "%s=(\"%s\")"%(gubun, "\" OR \"".join(query))

        return (queryList, words)

    # def queryProcess(self, browser, words):
    #     search_form = browser.get_form(id="WOS_AdvancedSearch_input_form")
    #     search_form['value(input1)'] = query

    #     browser.submit_form(search_form)
    #     historyResults = browser.select('div.historyResults')
        
    #     for idx, his in enumerate(historyResults):
    #         rsCnt = his.a.text
    #         rsCnt = rsCnt.replace(",","")
    #         rsCnt = int(rsCnt)

    #         if rsCnt >= 10000:
    #             self.logState(state="1150", stateMSG="결과가 10000개를 넘을 수 없으므로, 쿼리를 분할합니다.")
    #             wordsLen = len(words)
    #             lHisRs = self.queryProcess(browser, words[:wordsLen//2 + 1])
    #             rHisRs = self.queryProcess(browser, words[wordsLen//2 + 1:])
                
    #             historyResults = lHisRs + rHisRs

    #     return historyResults

    def logState(self, state, stateMSG):
        self.state = state
        self.stateMSG = stateMSG
        print('[state %s] %s'%(self.state, self.stateMSG))

    def getWOSExcel(self, idx, his, mark_from, mark_to, totalMarked):
        browser = RoboBrowser(history=True, parser="lxml")
        browser.open(self.baseUrl + his.a["href"])
        reportLink = browser.select("a.citation-report-summary-link")[0]

        browser.follow_link(reportLink)
        
        summary_records_form = browser.get_form(id='summary_records_form')
        CRTitle = browser.select("div.CRnewpageTitle")[0]
        summary_records_form_data = {
            "qid" : summary_records_form['qid'].value,
            "filters" : summary_records_form['filters'].value,
            "sortBy" : summary_records_form['sortBy'].value,
            "timeSpan" : summary_records_form['timeSpan'].value,
            "endYear" : summary_records_form['endYear'].value,
            "startYear" : summary_records_form['startYear'].value,
            "rurl" : summary_records_form['rurl'].value,
            # "totalMarked" : CRTitle.span.string.replace(",", ""),
            "totalMarked" : totalMarked,
            "piChart" : summary_records_form['piChart'].value,
            "toChart" : summary_records_form['piChart'].value
        }
        formData = {
            "SID" : self.SID,
            "additional_qoutput_params": "cr_qid="+summary_records_form_data['qid'],
            "selectedIds": "",
            "displayCitedRefs":"",
            "displayTimesCited":"",
            "displayUsageInfo":"true",
            "viewType":"summary",
            "product":"WOS",
            "mark_id":"WOS",
            "colName":"WOS",
            "search_mode":"CitationReport",
            "view_name":"WOS-CitationReport-summary",
            "mode":"OpenOutputService",
            "format":"crsaveToFile",
            "queryNatural":"",
            "count_new_items_marked":"0",
            "use_two_ets":"false",
            "IncitesEntitled":"no",
            "value(record_select_type)":"range",
            "action":"recalulate",
            "start_year_val":"1900",
            "end_year_val":"2019",
            "viewAbstractUrl":"",
            "LinksAreAllowedRightClick": "full_record.do",
            "db_editions": "",
            "print_opt":"Html",
            "include_mark_from_in_url":"true",
            "fields":"DUMMY_VALUE"
        }
        formData.update(summary_records_form_data)

        makeExcelURL = "http://apps.webofknowledge.com/OutboundService.do?"
        makeExcelParam = ""
        makeExcelParam += "action=go"
        makeExcelParam += "&save_options=xls"

        makeExcelURL += makeExcelParam

        browser.session.post(
            makeExcelURL, 
            data=formData
        )

        ExcelActionURL = "https://ets.webofknowledge.com/ETS/ets.do?"

        ExcelParam = "product=UA"
        ExcelParam += "&colName=WOS"
        ExcelParam += "&displayUsageInfo=true"
        ExcelParam += "&parentQid=" + formData['qid']
        ExcelParam += "&rurl=" + requests.utils.quote(formData['rurl'])
        ExcelParam += "&startYear=" + formData['startYear']
        ExcelParam += "&filters=" + requests.utils.quote(formData['filters'])
        ExcelParam += "&qid=" + str(int(formData['qid'])+1)
        ExcelParam += "&endYear=" + formData['endYear']
        ExcelParam += "&SID=" + self.SID
        ExcelParam += "&totalMarked=" + formData['totalMarked']
        ExcelParam += "&action=crsaveToFile"
        ExcelParam += "&timeSpan=" + requests.utils.quote(formData['timeSpan'])
        ExcelParam += "&sortBy=" + formData['sortBy']
        ExcelParam += "&displayTimesCited=false"
        ExcelParam += "&displayCitedRefs=true"
        ExcelParam += "&fileOpt=xls"
        ExcelParam += "&UserIDForSaveToRID=null"
        ExcelParam = "&mark_from=" + mark_from
        ExcelParam += "&mark_to=" + mark_to

        ExcelActionURL += ExcelParam

        res = requests.get(ExcelActionURL)
        resStr = res.text

        with open(str(idx) + "_excel_rs.xls", "wb") as rsFile:
            rsFile.write(res.content)

        return resStr

    # Run method
    def run(self, startDate, endDate, gubun, inputFilePath, outputLocationPath, defaultQueryPackSize):
        self.logState(
            state="1001", 
            stateMSG="WOS AdvancedSearch를 시작합니다.")
        browser = self.browser

        self.logState(
            state="1002", 
            stateMSG="쿼리를 만듭니다.")
        # queryList, words = self.makeQueryFromFile(inputFilePath, defaultQueryPackSize, gubun)
        queryList, words = self.makeQueryFromFile(inputFilePath, 0, gubun)

        queryListLen = len(queryList)
        for idx, query in enumerate(queryList):
            self.logState(
                state="1100", 
                stateMSG="쿼리를 시작합니다. %d/%d"%(idx+1, queryListLen))
            self.WOS_AdvancedSearch_input_form['value(input1)'] = query
            browser.submit_form(self.WOS_AdvancedSearch_input_form)

        historyResults = browser.select('div.historyResults')
        historyResultsLen = len(historyResults)  
        
        resStr = ""
        notFound = list(map(lambda x: [x, False], words))

        for idx, his in enumerate(historyResults):
            self.logState(state="1200", stateMSG="쿼리 팩키지를 실행합니다. %d/%d"%(idx+1, historyResultsLen))
            totalMarked = his.a.text.replace(",", "")

            for mark in range(1, int(totalMarked), 500):
                self.logState(
                    state="0000", 
                    stateMSG="%s개의 레코드 중 %d부터 레코드를 가져옵니다."%(totalMarked, mark))
                resStr += self.getWOSExcel(idx, his, str(mark), str(mark + 500 - 1), totalMarked)
        
        # words 에 따라 검색해서 없는 결과를 추출해야함.
        for idx, word in enumerate(notFound):
            notFound[idx][1] =( -1 != resStr.find(word[0]) )
        

        # 세션을 파괴
        self.browser = RoboBrowser(history=True, parser="lxml")
        self.browser.open(self.baseUrl)

        return
    ####################################

class PaperEntity:
    def __init__(self, doi, title, cited, issn, journal, author, date):
        self.doi = doi
        self.title = title
        self.cited = cited
        self.issn = issn
        self.journal = journal
        self.author = author
        self.date = date
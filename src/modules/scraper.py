from robobrowser import RoboBrowser
from bs4 import BeautifulSoup
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
        self.browser = RoboBrowser(history=True, parser="lxml")

        self.logState(state="0000", stateMSG="getting SID")
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

        self.logState(state="0200", stateMSG="질의를 시작할 수 있습니다.")

        # # 테스트용
        # self.SID = "E6iQIWCw3PkqehegemT"
        # self.jsessionid = "F1712DE4685F64BD8AC425E3B91FF108"

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

    def queryProcess(self, browser, words):
        search_form = browser.get_form(id="WOS_AdvancedSearch_input_form")
        search_form['value(input1)'] = query

        browser.submit_form(search_form)
        historyResults = browser.select('div.historyResults')
        
        for idx, his in enumerate(historyResults):
            rsCnt = his.a.text
            rsCnt = rsCnt.replace(",","")
            rsCnt = int(rsCnt)

            if rsCnt >= 10000:
                self.logState(state="1150", stateMSG="결과가 10000개를 넘을 수 없으므로, 쿼리를 분할합니다.")
                wordsLen = len(words)
                lHisRs = queryProcess(browser, words[:wordsLen//2 + 1])
                rHisRs = queryProcess(browser, words[wordsLen//2 + 1:])
                
                historyResults = lHisRs + rHisRs

        return historyResults

    def exportExcel(self, data, processId, outputLocationPath):
        with open(processId + "_excel_rs.xls", "wb") as rsFile:
                rsFile.write(data)

    def logState(self, state, stateMSG):
        self.state = state
        self.stateMSG = stateMSG
        print('[state %s] %s'%(self.state, self.stateMSG))

    # Run method
    ####################################
    def run(self, startDate, endDate, gubun, inputFilePath, outputLocationPath, defaultQueryPackSize):
        # 테스트 데이터
        # startDate = "201001"
        # endDate = "201001"
        # gubun = "TI" 
        # inputFilePath = "C:\\Users\\F\\Desktop\\papers\\sju-paper-scraper-app\\src\\modules\\"
        # outputLocationPath="C:\\Users\\F\\Desktop\\papers\\sju-paper-scraper-app\\src\\modules\\"

        # 단계 1/6 :
        ##############################################
        self.logState(state="1001", stateMSG="WOS AdvancedSearch를 시작합니다.")
        browser = self.browser

        # actionURL = self.baseUrl
        # action = "/WOS_AdvancedSearch_input.do"
        # param = ""
        # param += "?SID=" + self.SID
        # param += "&product=WOS"
        # param += "&search_mode=AdvancedSearch"
        
        # actionURL += action
        # actionURL += param

        # browser.open(actionURL)

        self.logState(state="1002", stateMSG="쿼리를 만듭니다.")
        queryList, words = self.makeQueryFromFile(inputFilePath, defaultQueryPackSize, gubun)

        queryListLen = len(queryList)
        for idx, query in enumerate(queryList):
            # self.logState(state="1010", stateMSG="Query : %s"%query)
            self.logState(state="1100", stateMSG="쿼리를 시작합니다. %d/%d"%(idx+1, queryListLen))
            
            search_form = browser.get_form(id="WOS_AdvancedSearch_input_form")
            search_form['value(input1)'] = query

            browser.submit_form(search_form)

        historyResults = browser.select('div.historyResults')
        
        historyResultsLen = len(historyResults)        
        for idx,his in enumerate(historyResults):
            self.logState(state="1200", stateMSG="쿼리 팩키지를 실행합니다. %d/%d"%(idx+1, historyResultsLen))

            browser.follow_link(his.a)

            reportLink = browser.select("a.citation-report-summary-link")
            browser.follow_link(reportLink[0])

            summary_records_form = browser.get_form(id='summary_records_form')
            qid = summary_records_form['qid'].value
            filters = summary_records_form['filters'].value
            sortBy = summary_records_form['sortBy'].value
            timeSpan = summary_records_form['timeSpan'].value
            endYear = summary_records_form['endYear'].value
            startYear = summary_records_form['startYear'].value
            rurl = summary_records_form['rurl'].value

            CRTitle = browser.select("div.CRnewpageTitle")[0]
            totalMarked = CRTitle.span.string.replace(",", "")

            int(totalMarked) / 500

            mark_to = totalMarked
            mark_from = "1"

            piChart = summary_records_form['piChart'].value
            toChart = summary_records_form['piChart'].value

            makeExcelURL = "http://apps.webofknowledge.com/OutboundService.do?"
            makeExcelParam = ""
            makeExcelParam += "action=go"
            makeExcelParam += "&save_options=xls"

            makeExcelURL += makeExcelParam

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


            ExcelActionURL = "https://ets.webofknowledge.com"
            ExcelAction = "/ETS/ets.do?"

            # summary_navigation = browser.get_form(id='summary_navigation')
            # print(summary_navigation.parsed)
            
            ExcelParam = "mark_from=1"
            ExcelParam += "&product=UA"
            ExcelParam += "&colName=WOS"
            ExcelParam += "&displayUsageInfo=true"
            ExcelParam += "&parentQid=" + qid
            ExcelParam += "&rurl=" + requests.utils.quote(rurl)
            ExcelParam += "&startYear=" + startYear
            ExcelParam += "&mark_to=" + mark_to
            ExcelParam += "&filters=" + requests.utils.quote(filters)
            ExcelParam += "&qid=" + str(int(qid)+1)
            ExcelParam += "&endYear=" + endYear
            ExcelParam += "&SID=" + self.SID
            ExcelParam += "&totalMarked=" + totalMarked
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
            excel = res.content
            resStr = res.text

            notFound = []
            for word in words:
                if resStr.find(word) == -1:
                    notFound.append(word)

            with open(str(idx) + "_excel_rs.xls", "wb") as rsFile:
                rsFile.write(res.content)

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
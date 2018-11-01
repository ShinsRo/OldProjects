from robobrowser import RoboBrowser
from bs4 import BeautifulSoup
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

        self.logState(state="0100", stateMSG="watting for running")

        # # 테스트용
        # self.SID = "E6iQIWCw3PkqehegemT"
        # self.jsessionid = "F1712DE4685F64BD8AC425E3B91FF108"

    def makeQueryFromFile(self, path, packSize, gubun):
        # if csv
        df = pd.read_csv(path, header=0)
        words = df.values[:, 0]

        wordsLen = len(words)
        if packSize <= 0 or packSize > wordsLen: packSize = wordsLen

        queryList = [words[idx*packSize:(idx+1)*packSize] for idx in range(wordsLen//packSize)]

        for idx, query in enumerate(queryList):
            queryList[idx] = "%s=(\"%s\")"%(gubun, "\" OR \"".join(query))

        return queryList

    def exportExcel(self, data, path):
        print('excel exported')

    def logState(self, state, stateMSG):
        self.state = state
        self.stateMSG = stateMSG
        print('[state %s] %s'%(self.state, self.stateMSG))

    # Run method
    ####################################
    def run(self, startDate, endDate, gubun, filePath):
        # 테스트 데이터
        # startDate = "201001"
        # endDate = "201001"
        # gubun = "TI" 
        # filePath = "C:\\Users\\F\\Desktop\\papers\\sju-paper-scraper-app\\src\\modules\\"

        # 단계 1/6 :
        ##############################################
        browser = self.browser

        self.logState(state="1001", stateMSG="Run WOS AdvancedSearch")
        actionURL = self.baseUrl
        action = "/WOS_AdvancedSearch_input.do"
        param = ""
        param += "?SID=" + self.SID
        param += "&product=WOS"
        param += "&search_mode=AdvancedSearch"
        
        actionURL += action
        actionURL += param

        self.logState(state="1002", stateMSG="Making Query")
        queryList = self.makeQueryFromFile(filePath, 0, gubun)

        browser.open(actionURL)
        queryListLen = len(queryList)
        for idx, query in enumerate(queryList):
            # self.logState(state="1010", stateMSG="Query : %s"%query)
            self.logState(state="1010", stateMSG="packaging queries %d/%d"%(idx+1, queryListLen))
            
            search_form = browser.get_form(id="WOS_AdvancedSearch_input_form")
            search_form['value(input1)'] = query

            browser.submit_form(search_form)

        historyResults = browser.select('div.historyResults')
        
        historyResultsLen = len(historyResults)        
        for idx,his in enumerate(historyResults):
            # fd = open("test.html", "a")
            self.logState(state="1020", stateMSG="Execute a query package %d/%d"%(idx+1, historyResultsLen))

            print(his.a["href"])
            browser.follow_link(his.a)

            reportLink = browser.select("a.citation-report-summary-link")
            browser.follow_link(reportLink[0])

            citeJSON = str((browser.find(id='raw_tc_data').string).encode('utf-8'))
            print(type(citeJSON))
            citeJSON = (""+citeJSON).replace("{", "{\"").replace("}", "\"}").replace("=", "\"=\""),replace(",","\",")
            print(json.loads(citeJSON))


            #####################################################################
            # UA_output_input_form = browser.get_form(id='summary_records_form')
            # qid = UA_output_input_form['qid'].value
            # filters = UA_output_input_form['filters'].value
            # sortBy = UA_output_input_form['sortBy'].value
            # timeSpan = UA_output_input_form['timeSpan'].value
            # endYear = UA_output_input_form['endYear'].value
            # startYear = UA_output_input_form['startYear'].value
            # rurl = UA_output_input_form['rurl'].value

            # # print(soup.find(id='raw_tc_data'))
            
            # print(browser.find(id=''))

            # for f in browser.find_all('form'):
            #     if f['action'] == "https://apps.webofknowledge.com/OutboundService.do?action=go&save_options=csv&":
            #         print("========")
            #         print(f)
            #         file_form = f
            # # print(browser.select('div#saveDiv'))
            # # print(browser.find(id='saveDiv'))
            # file_form.submit_form()


            # ExcelActionURL = "https://ets.webofknowledge.com"
            # ExcelAction = "/ETS/ets.do?"

            # CRTitle = browser.select("div.CRnewpageTitle")[0]
            # totalMarked = CRTitle.span.string.replace(",", "")
            # mark_to = totalMarked
            # # summary_navigation = browser.get_form(id='summary_navigation')
            # # print(summary_navigation.parsed)

            
            # ExcelParam = "mark_from=1"
            # ExcelParam += "&product=UA"
            # ExcelParam += "&colName=WOS"
            # ExcelParam += "&displayUsageInfo=true"
            # ExcelParam += "&parentQid=" + qid
            # ExcelParam += "&rurl=" + requests.utils.quote(rurl)
            # ExcelParam += "&startYear=" + startYear
            # ExcelParam += "&mark_to=" + mark_to
            # ExcelParam += "&filters=" + requests.utils.quote(filters)
            # ExcelParam += "&qid=" + str(int(qid)+1)
            # ExcelParam += "&endYear=" + endYear
            # ExcelParam += "&SID=" + self.SID
            # ExcelParam += "&totalMarked=" + totalMarked
            # ExcelParam += "&action=crsaveToFile"
            # ExcelParam += "&timeSpan=" + requests.utils.quote(timeSpan)
            # ExcelParam += "&sortBy=" + sortBy
            # ExcelParam += "&displayTimesCited=false"
            # ExcelParam += "&displayCitedRefs=true"
            # ExcelParam += "&fileOpt=xls"
            # ExcelParam += "&UserIDForSaveToRID=null"

            # # print(ExcelParam)

            # ExcelActionURL += ExcelAction
            # ExcelActionURL += ExcelParam

            # print(ExcelActionURL)
            
            # res = requests.get(ExcelActionURL)
            # print(res)
            # excel = res.text
            # print(excel)
            # f = open("_excel_rs.xls", "w")
            # f.write(excel)

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
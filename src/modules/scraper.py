from robobrowser import RoboBrowser
from bs4 import BeautifulSoup
from multiprocessing import Process, current_process
import urllib
import json
import re 
import requests
import time
import sys
import pandas as pd
import os
import copy

class WosProcess():
    def __init__(self, SID, jsessionid, baseUrl):
        self.SID = SID
        self.baseUrl = baseUrl
        self.jsessionid = jsessionid

    def getWOSExcelProcess(self, idx, url, totalMarked, mark):
        browser = RoboBrowser(history=True, parser="lxml")

        browser.open(url)
        reportLink = browser.select("a.citation-report-summary-link")
        # print(reportLink)

        browser.follow_link(reportLink[0])
        # print(browser.get_form(id='summary_records_form'))

        summary_records_form = browser.get_form(id='summary_records_form')
        qid = summary_records_form['qid'].value
        filters = summary_records_form['filters'].value
        sortBy = summary_records_form['sortBy'].value
        timeSpan = summary_records_form['timeSpan'].value
        endYear = summary_records_form['endYear'].value
        startYear = summary_records_form['startYear'].value
        rurl = summary_records_form['rurl'].value

        mark_from = str(mark)
        if mark + 499 > int(totalMarked):
            mark_to = totalMarked
        else:
            mark_to = str(mark + 499)
        # mark_from = "1"
        # mark_to = totalMarked

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
        # excel = res.content
        resStr = res.text

        proc_name = current_process().name
        ofileName = "{0}{1}_excel_rs.xls".format(proc_name, idx)
        with open(ofileName, "wb") as rsFile:
            rsFile.write(res.content)
            rsFile.close()

        return
   
class Scraper():
    """
    __init__
    세션 검증에 필요한 SID값과 jsessionid 값을 얻습니다.
    브라우저를 초기화하고 WOS의 AdvancedSearch 서비스에 접속합니다.
    run 메서드를 대기합니다.
    """
    def __init__(self):
        self.RSNO = 0
        self.baseUrl = "http://apps.webofknowledge.com"
        self.state = "waiting"
        self.logState(state="0000", stateMSG="SID와 jsessionid 값을 얻습니다.")

        self.browser = RoboBrowser(history=True, parser="lxml")
        # 테스트용
        # self.SID = "E2V7RPJe7OYXtfigmbk"
        # self.jsessionid = "506EB868497C83AB26B65AA714B04878"

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

    """
    makeQueryFromFile
        path : input 파일의 경로
        packSize : 한 번에 질의할 input 값의 갯수
        gubun : 쿼리 구분
            ex) TI, AU ...

        경로로부터 인풋값을 읽어들입니다.
        패킹 사이즈만큼 인풋을 나누고 나눈 인풋들에 따른 쿼리 리스트를 반환합니다.
    """
    def makeQueryFromFile(self, path, packSize, gubun):
        # if csv
        fname, ext = os.path.splitext(path)
        if ext == ".csv":
            df = pd.read_csv(path, header=0)
            words = df.values[:, 0]
        elif ext == ".xls":
            pass
        else:
            pass

        wordsLen = len(words)
        if packSize <= 0 or packSize > wordsLen: packSize = wordsLen

        queryList = [words[chunkIdx: chunkIdx + packSize] for chunkIdx in range(0, wordsLen, packSize)]
        
        for idx, query in enumerate(queryList):
            queryList[idx] = "%s=(\"%s\")"%(gubun, "\" OR \"".join(query))

        return (queryList, words)

    def logState(self, state, stateMSG):
        self.state = state
        self.stateMSG = stateMSG
        print('[state %s] %s'%(self.state, self.stateMSG))


    # Run method
    def run(self, startDate, endDate, gubun, inputFilePath, outputLocationPath, defaultQueryPackSize):
        self.logState(
            state="1001", 
            stateMSG="WOS AdvancedSearch를 시작합니다.")
        browser = self.browser

        self.logState(
            state="1002", 
            stateMSG="쿼리를 만듭니다.")
        queryList, words = self.makeQueryFromFile(inputFilePath, defaultQueryPackSize, gubun)

        queryListLen = len(queryList)
        for idx, query in enumerate(queryList):
            self.logState(
                state="1100", 
                stateMSG="쿼리를 시작합니다. %d/%d"%(idx+1, queryListLen))
            self.WOS_AdvancedSearch_input_form['value(input1)'] = query
            browser.submit_form(self.WOS_AdvancedSearch_input_form)

        historyResults = browser.select('div.historyResults')
        historyResultsLen = len(historyResults)  
        
        # notFound = list(map(lambda x: [x, False], words))

        procs = []
        for idx, his in enumerate(historyResults):
            totalMarked = his.a.text.replace(",", "")

            if int(totalMarked) >= 10000:
                responseToUI = ResponseEntity(
                    resCode=400, 
                    rsMsg="쿼리 결과가 10000개 이상입니다. 회당 쿼리 갯수를 조절해 주세요.",
                    payload={"qeury":words, "result":"", "totalMarked":totalMarked})

                return responseToUI.returnResponse()

            self.logState(state="1200", stateMSG="쿼리 결과의 엑셀 데이터를 가져옵니다. %d/%d"%(idx+1, historyResultsLen))
            
            for mark in range(1, int(totalMarked), 500):
                self.logState(
                    state="2001", 
                    stateMSG="%s개의 레코드 중 %d번 레코드부터 레코드를 가져옵니다."%(totalMarked, mark))
                
                processClass = WosProcess(self.SID, self.jsessionid, self.baseUrl)
                
                url = self.baseUrl + his.a["href"]
                proc = Process(
                    target=processClass.getWOSExcelProcess,
                    args=(idx, url, totalMarked, mark)
                )
                procs.append(proc)
                proc.start()

        for proc in procs:
            proc.join()
        
        # words 에 따라 검색해서 없는 결과를 추출해야함.
        # for idx, word in enumerate(notFound):
        #     notFound[idx][1] = ( -1 == resStr.find(word[0]) )

        # 세션을 파괴
        self.browser = RoboBrowser(history=True, parser="lxml")
        self.browser.open(self.baseUrl)

        responseToUI = ResponseEntity(
            resCode=200, 
            rsMsg="데이터를 성공적으로 전송했습니다.",
            payload={"qeury":words, "result":""}
            )

        return responseToUI.returnResponse()
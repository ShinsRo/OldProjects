# from multiprocessing import current_process
from robobrowser import RoboBrowser
import pandas as pd
import threading
import requests
import os

class WosProcess():
    def __init__(self, SID, jsessionid, baseUrl):
        self.SID = SID
        self.baseUrl = baseUrl
        self.jsessionid = jsessionid
        self.processData = ""

    def getWOSExcelProcess(self, idx, url, totalMarked, mark, outputLocationPath, returnDict, loggerID, state):
        self.procName = threading.get_ident()
        procName = self.procName

        state.printAndSetState(
            state="2000", 
            stateMSG="[Thread-%s] 브라우저를 초기화 합니다."%self.procName)

        browser = RoboBrowser(history=True, parser="lxml")

        browser.open(url)
        reportLink = browser.select("a.citation-report-summary-link")

        state.printAndSetState(
            state="2001", 
            stateMSG="[Thread-%s] 요약 보고서를 가져오는 중입니다."%self.procName)

        browser.follow_link(reportLink[0])

        summary_records_form = browser.get_form(id='summary_records_form')
        qid = summary_records_form['qid'].value
        filters = summary_records_form['filters'].value
        sortBy = summary_records_form['sortBy'].value
        timeSpan = summary_records_form['timeSpan'].value
        endYear = summary_records_form['endYear'].value
        startYear = summary_records_form['startYear'].value
        rurl = summary_records_form['rurl'].value

        returnDict.setQid(int(qid))
        mark_from = str(mark)
        if mark + 499 > int(totalMarked):
            mark_to = totalMarked
        else:
            mark_to = str(mark + 499)

        piChart = summary_records_form['piChart'].value
        toChart = summary_records_form['piChart'].value

        makeExcelURL = "http://apps.webofknowledge.com/OutboundService.do?"
        makeExcelParam = ""
        makeExcelParam += "action=go"
        makeExcelParam += "&save_options=xls"

        makeExcelURL += makeExcelParam

        state.printAndSetState(
            state="2002", 
            stateMSG="[Thread-%s] %s레코드부터 %s 레코드까지의 데이터 제작을 요청합니다."%(self.procName, mark_from, mark_to))

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

        state.printAndSetState(
            state="2003", 
            stateMSG="[Thread-%s] %s부터 %s까지의 엑셀 데이터를 다운로드 받습니다."%(self.procName, mark_from, mark_to))

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
        ExcelParam += "&qid=" + str(returnDict.countAndGetQid())
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
        
        state.printAndSetState(
            state="2004", 
            stateMSG="[Thread-%s] 검색 결과를 묶습니다."%self.procName)

        ofileName = "{0}_검색결과_{1}.xls".format(self.procName, idx)

        while(os.path.exists(outputLocationPath + "/" + ofileName)):
            ofileName = "_" + ofileName

        with open(outputLocationPath + "/" + ofileName, "wb") as rsFile:
            rsFile.write(res.content)
            rsFile.close()
        resPD = pd.read_excel(outputLocationPath + "/" + ofileName, header=26)
        os.remove(outputLocationPath + "/" + ofileName)
        
        returnDict[self.procName] = resPD

        state.printAndSetState(
            state="2200", 
            stateMSG="[Thread-%s] 스레드를 종료합니다."%self.procName)
        return
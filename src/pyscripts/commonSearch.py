import os
import sys
import time
import requests
import pandas as pd
import xlwt
import re
import random

from bs4 import BeautifulSoup
from robobrowser import RoboBrowser

import concurrent.futures
import sju_response

class MultiSearch():
    def __init__(self, sres):
        self.searchCnt = 0
        self.sres = sres
        self.browser = RoboBrowser(history=True, parser='lxml')
        self.baseUrl = "http://apps.webofknowledge.com"
        self.browser.open(self.baseUrl)

        self.SID = self.browser.session.cookies['SID'].replace("\"", "")
        self.jsessionid = self.browser.session.cookies['JSESSIONID']

        param = '?product=WOS'
        param += "&search_mode=AdvancedSearch"
        param += '&preferencesSaved='
        param += '&SID=' + self.SID
        self.browser.open(self.baseUrl + '/WOS_AdvancedSearch_input.do' + param)

    def backToAdvancedSearch(self):
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
        param += "&search_mode=AdvancedSearch"
        param += '&preferencesSaved='
        param += '&SID=' + self.SID
        self.browser.open(self.baseUrl + '/WOS_AdvancedSearch_input.do' + param)

        sres.print(command='log', msg='초기화가 완료되었습니다.')

    def makeQueryFromFile(self, path, packSize, gubun):
        fname, ext = os.path.splitext(path)
        if ext == ".csv":
            df = pd.read_csv(path, header=0)
            words = df.values[:, 0]
        elif ext == ".xls" or ext == ".xlsx":
            df = pd.read_excel(path, header=0)
            words = df.values[:, 0]
        elif ext == ".txt":
            pass
        else:
            raise Exception()

        for idx, word in enumerate(words):
            words[idx] = word.strip()

        wordsLen = len(words)
        if packSize <= 0 or packSize > wordsLen: packSize = wordsLen

        queryList = [words[chunkIdx: chunkIdx + packSize] for chunkIdx in range(0, wordsLen, packSize)]
        
        for idx, query in enumerate(queryList):
            queryList[idx] = "%s=(\"%s\")"%(gubun, "\" OR \"".join(query))

        return (queryList, words)
    
    def getExcelFromWoS(self, url, mark, totalMarked, outputLocationPath):
        mark_from = str(mark)
        sres = self.sres

        if mark + 499 > int(totalMarked):
            mark_to = totalMarked
        else:
            mark_to = str(mark + 499)

        sres.print(command='log', msg='[%s-%s레코드] 엑셀을 받을 준비를 합니다.'%(mark_from, mark_to))
        
        excelBrowser = RoboBrowser(history=True, parser='lxml')
        excelBrowser.open(url)
        reportLink = excelBrowser.select('a.citation-report-summary-link')

        if len(reportLink) == 0: 
            sres.print(command='err', msg='[%s-%s레코드] 요약 보고서가 없습니다.'%(mark_from, mark_to))
            return None

        sres.print(command='log', msg='[%s-%s레코드] 요약 보고서를 엽니다.'%(mark_from, mark_to))
        excelBrowser.follow_link(reportLink[0])

        summary_records_form = excelBrowser.get_form(id='summary_records_form')
        qid = summary_records_form['qid'].value
        filters = summary_records_form['filters'].value
        sortBy = summary_records_form['sortBy'].value
        timeSpan = summary_records_form['timeSpan'].value
        endYear = summary_records_form['endYear'].value
        startYear = summary_records_form['startYear'].value
        rurl = summary_records_form['rurl'].value


        piChart = summary_records_form['piChart'].value
        toChart = summary_records_form['piChart'].value

        makeExcelURL = "http://apps.webofknowledge.com/OutboundService.do?"
        makeExcelParam = ""
        makeExcelParam += "action=go"
        makeExcelParam += "&save_options=xls"

        makeExcelURL += makeExcelParam

        sres.print(command='log', msg='[%s-%s레코드] 엑셀 데이터 제작을 요청합니다.'%(mark_from, mark_to))
        excelBrowser.session.post(makeExcelURL, data={
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

        sres.print(command='log', msg='[%s-%s레코드] 엑셀 데이터를 다운로드 받습니다.'%(mark_from, mark_to))
        res = requests.get(ExcelActionURL)
        if res.text.find("<html>") > 0 or res.text.find("Error report</title>") > 0:
            sres.print(command='err', msg='%s-%s 레코드, 서버가 에러를 반환'%(mark_from, mark_to))
        
        ofileName = "%X"%random.getrandbits(128)
        with open(ofileName, 'wb') as rsFile:
            rsFile.write(res.content)
            rsFile.close()
        resPD = pd.read_excel(ofileName, header=26)
        os.remove(ofileName)

        return resPD

    def generalSearch(
        self,
        startYear,
        endYear,
        gubun,
        inputFilePath,
        defaultQueryPackSize):

        sres = self.sres
        browser = self.browser
        baseUrl = self.baseUrl
        SID = self.SID
        jsessionid = self.jsessionid

        sres.print('log', msg='단일 조회를 시작합니다.')

        WOS_AdvancedSearch_input_form = browser.get_form(id="WOS_AdvancedSearch_input_form")
        try:
            queryList, words = self.makeQueryFromFile(inputFilePath, defaultQueryPackSize, gubun)
        except Exception as e:
            sres.print(command='err', msg='파일을 읽는 중 오류가 발생했습니다.')
            sres.print(command='errObj', msg=e)
            return

        self.searchCnt += 1
        
        queryListLen = len(queryList)
        for idx, query in enumerate(queryList):
            sres.print(
                command='log', 
                msg='쿼리 히스토리를 만들고 있습니다. %d/%d'%(idx+1, queryListLen))
            
            WOS_AdvancedSearch_input_form['value(input1)'] = query
            WOS_AdvancedSearch_input_form['startYear'] = startYear
            WOS_AdvancedSearch_input_form['endYear'] = endYear
            WOS_AdvancedSearch_input_form['range'] = 'CUSTOM'
            browser.submit_form(WOS_AdvancedSearch_input_form)

        historyResults = browser.select('div.historyResults')
        historyResultsLen = len(historyResults)

        returnDict = {}
        for idx, his in enumerate(historyResults):
            totalMarked = his.a.text.replace(',', '')
            url = self.baseUrl + his.a["href"]

            if int(totalMarked) >= 10000:
                sres.print(command='log', msg='해당 패키지 결과가 10000개 이상입니다.')
                sres.print(command='log', msg='검색 범위를 좁혀주세요.')
                self.backToAdvancedSearch()
                return
            outputLocationPath = os.path.dirname(inputFilePath)
            with concurrent.futures.ThreadPoolExecutor(max_workers=20) as excecuter:
                future_report = {
                    excecuter.submit(
                        self.getExcelFromWoS, 
                        url, mark, totalMarked, outputLocationPath): mark for mark in range(1, int(totalMarked), 500) 
                }

                for future in concurrent.futures.as_completed(future_report):
                    mark_to = future_report[future]
                    try:
                        temp = future.result()
                        if temp is not None: returnDict[mark_to] = temp
                        sres.print(command='log', msg='%s번 레코드부터 성공적으로 데이터를 가져왔습니다.'%mark_to)
                    
                    except Exception as e:
                        print(e)
    
        resPD = pd.concat(returnDict.values())

        header = ""
        if gubun == "TI": header = "Title"
        elif gubun == "AU": header = "Authors"
        elif gubun == "DO": header = "DOI"
        else: pass
        
        if gubun is not 'AD':
            notFoundList = []
            resString = " ".join(resPD[header])
            for word in words:
                if not bool(re.search(word, resString, re.IGNORECASE)):
                    notFoundList.append(word)

            # notFoundDF = pd.DataFrame({
            #     header: notFoundList
            # })

        # temp = "_all_succeed.xls"
        # successResultFile = outputLocationPath + "/" + temp
        # while(os.path.exists(successResultFile)):
        #     temp = "_" + temp
        #     successResultFile = outputLocationPath + "/" + temp

        # excelWriter = pd.ExcelWriter(successResultFile)
        # resPD.to_excel(excelWriter, '전체 검색 결과')
        # excelWriter.save()

        # temp = "_failed.xls"
        # failedResultFile = outputLocationPath + "/" + temp
        # while(os.path.exists(failedResultFile)):
        #     temp = "_" + temp
        #     failedResultFile = outputLocationPath + "/" + temp

        # excelWriter = pd.ExcelWriter(failedResultFile)
        # notFoundDF.to_excel(excelWriter, '전체 검색 실패 결과')
        # excelWriter.save()
        cResList = []
        for idx in range(len(resPD['Title'])):
            cResList.append({})
            row = resPD.loc[idx]
            cResList[idx]['id'] = '%d'%random.getrandbits(16)
            cResList[idx]['DOI'] = row['DOI']
            cResList[idx]['Title'] = row['Title']
            cResList[idx]['Authors'] = row['Authors'].split(';')[:-1]
            cResList[idx]['Source Title'] = row['Source Title']
            cResList[idx]['Total Citations'] = str(row['Total Citations'])
            cResList[idx]['Publication Date'] = row['Publication Date']
            cResList[idx]['ivp'] = [
                '%s/%s'%(row['Issue'], row['Volume']),
                '%s-%s'%(row['Beginning Page'], row['Ending Page']),
            ]
        sres.print(
            command='cres', 
            target='result', 
            res={
                'cResList': cResList, 
                'notFoundList': notFoundList
            })
        sres.print(command='log', msg='논문들의 일반 정보를 가져왔습니다.')
        self.backToAdvancedSearch()

if __name__ == "__main__":
    cres = sju_response.SJUresponse('test')
    print('초기화')
    cs = MultiSearch(cres)

    print('검색시작')
    cs.generalSearch(
        startYear='2010',
        endYear='2018',
        gubun='TI',
        inputFilePath='C:\\input.csv',
        defaultQueryPackSize=0
    )

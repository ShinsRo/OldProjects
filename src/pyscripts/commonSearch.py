import os
import sys
import time
import requests
import pandas as pd
import xlwt
import re

from bs4 import BeautifulSoup
from robobrowser import RoboBrowser

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
        param += "&search_mode=AdvancedSearch"
        param += '&preferencesSaved='
        param += '&SID=' + self.SID
        self.browser.open(self.baseUrl + '/WOS_AdvancedSearch_input.do' + param)

        sres.print(command='log', msg='초기화가 완료되었습니다.')
        sres.print(command='res', target='loading', res=False)

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
    
    def generalSearch(
        self,
        startYear,
        endYear,
        gubun,
        inputFilePath):

        sres = self.sres
        browser = self.browser
        baseUrl = self.baseUrl
        SID = self.SID
        jsessionid = self.jsessionid

        sres.print(command='res', target='loading', res=True)

        sres.print('log', msg='단일 조회를 시작합니다.')
        
        WOS_AdvancedSearch_input_form = browser.get_form(id="WOS_AdvancedSearch_input_form")
        defaultQueryPackSize = 0
        queryList, words = self.makeQueryFromFile(inputFilePath, defaultQueryPackSize, gubun)

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

        sres.print(command='log', msg='논문들의 일반 정보를 가져왔습니다.')
        self.backToGeneralSearch()

if __name__ == "__main__":
    cres = sju_response.SJUresponse('test')
    print('초기화')
    cs = MultiSearch(cres)

    print('검색시작')
    cs.generalSearch(
        startYear='2010',
        endYear='2018',
        gubun='TI',
        inputFilePath='C:\\input.csv'
    )

from robobrowser import RoboBrowser
from bs4 import BeautifulSoup
import re 
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

        self.logState(state="0000", stateMSG="SID : %s"%self.SID)
        self.logState(state="0000", stateMSG="jsessionid : %s"%self.jsessionid)

        self.logState(state="0000", stateMSG="watting for running")

        # # 테스트용
        # self.SID = "D4FJwxtquTGB85hJGiN"
        # self.jsessionid = "5D764821CBCE103E76B73F4980223536"

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

    def exportExcel(self, path):
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

        self.logState(state="0000", stateMSG="Run WOS AdvancedSearch")
        actionURL = self.baseUrl
        action = "/WOS_AdvancedSearch_input.do"
        param = ""
        param += "?SID=" + self.SID
        param += "&product=WOS"
        param += "&search_mode=AdvancedSearch"
        
        actionURL += action
        actionURL += param

        self.logState(state="0000", stateMSG="Making Query")
        queryList = self.makeQueryFromFile(filePath, 0, gubun)

        for query in queryList:
            # self.logState(state="Query : %s"%query)
            self.logState(state="0000", stateMSG="Query")
            self.logState(state="0000", stateMSG="Access %s"%actionURL)
            browser.open(actionURL)
            
            search_form = browser.get_form(id="WOS_AdvancedSearch_input_form")
            search_form['value(input1)'] = query

            browser.submit_form(search_form)
            self.logState(state="0000", stateMSG="Query Done")

        historyResults = browser.select('div.historyResults')
        print()

        # # Main Logic
        # try:
        #     search_form = browser.get_form(id='WOS_AdvancedSearch_input_form')
        #     search_form['value(input1)'] = word
        #     search_form['value(select1)'] = "TI"

        #     papers = []

        #     self.logState(state="read file")

        #     df = pd.read_csv(path+"\\top20.csv", header=0)

        #     keywords = df.values[:, 0]
            
        #     f = open(path+"_rs.csv", "a")
        #     f.writelines("Title, Cited, DOI, Notes \n")

        #     for idx, word in enumerate(keywords):
        #         word = word.strip()
        #         print(word)

        # except FileNotFoundError as err:
        #     print("FileNotFoundError", err)
        # else:
        #     print("Unexpected error", sys.exc_info()[0])
        # finally:
        #     pass
        # self.logState(state="waiting")

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
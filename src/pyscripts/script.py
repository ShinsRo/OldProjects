import os
import sys
import time
import modules.requests as requests
import modules.pandas as pd
import pandas as pd
import xlwt
import re
import logging
# # bs4, robobrowser, multiprocessing, xlwt, pandas, requests
from bs4 import BeautifulSoup
from robobrowser import RoboBrowser
# from multiprocessing import Process, Queue, Manager

# from supports.constant import *
# from supports.state import State
# from models.entities import ResponseEntity
# from models.locking_dict import LockingDict
# from core.processes import WosProcess


# class WosUserInterface():
#     def __init__(self, SID=None, jsessionid=None, loggerID=None):
#         """
#         __init__
#             세션 검증에 필요한 SID값과 jsessionid 값을 얻습니다.
#             브라우저를 초기화하고 WOS의 AdvancedSearch 서비스에 접속합니다.
#             run 메서드를 대기합니다.
#         """
#         self.loggerID = loggerID
#         if SID==None or jsessionid==None:
#             self.RSNO = 0
#             self.baseUrl = "http://apps.webofknowledge.com"
#             self.state = State(loggerID)
#             self.state.printAndSetState(state="0000", stateMSG="SID와 jsessionid 값을 얻습니다. 이 작업은 다소 시간이 소요됩니다.")

#             self.browser = RoboBrowser(history=True, parser="lxml")
#             self.browser.open(self.baseUrl)

#             self.SID = self.browser.session.cookies['SID'].replace("\"", "")
#             self.jsessionid = self.browser.session.cookies['JSESSIONID']

#             self.state.printAndSetState(state="0001", stateMSG="SID : %s"%self.SID)
#             self.state.printAndSetState(state="0002", stateMSG="jsessionid : %s"%self.jsessionid)
#             self.state.printAndSetState(state="0200", stateMSG="질의를 시작할 수 있습니다.")
#         else:
#             self.state = State(loggerID)
#             self.state.printAndSetState(state="0000", stateMSG="기존 세션 값으로 인터페이스를 초기화합니다.")
#             self.SID = SID
#             self.jsessionid = jsessionid
#             self.RSNO = 0
#             self.baseUrl = "http://apps.webofknowledge.com"

#     def getStateObject(self):
#         return self.state

#     # Run method
#     def run(self, startYear, endYear, gubun, inputFilePath, outputLocationPath, defaultQueryPackSize):
#         state = self.state
#         browser = self.browser

#         state.printAndSetState(
#             state="1001", 
#             stateMSG="WOS AdvancedSearch를 시작합니다.")

#         browser = RoboBrowser(history=True, parser="lxml")
#         actionURL = self.baseUrl
#         action = "/WOS_AdvancedSearch_input.do"
#         param = ""
#         param += "?SID=" + self.SID
#         param += "&product=WOS"
#         param += "&search_mode=AdvancedSearch"
        
#         actionURL += action
#         actionURL += param

#         browser.open(actionURL)
#         WOS_AdvancedSearch_input_form = browser.get_form(id="WOS_AdvancedSearch_input_form")


#         state.printAndSetState(
#             state="1002", 
#             stateMSG="쿼리를 만듭니다.")
#         queryList, words = self.makeQueryFromFile(inputFilePath, defaultQueryPackSize, gubun)

#         queryListLen = len(queryList)
#         for idx, query in enumerate(queryList):
#             state.printAndSetState(
#                 state="1100", 
#                 stateMSG="쿼리 히스토리를 만들고 있습니다. %d/%d"%(idx+1, queryListLen))

#             WOS_AdvancedSearch_input_form['value(input1)'] = query
#             WOS_AdvancedSearch_input_form['range'] = "CUSTOM"
#             # WOS_AdvancedSearch_input_form['period'].value = "Year Range"
#             WOS_AdvancedSearch_input_form['startYear'] = startYear
#             WOS_AdvancedSearch_input_form['endYear'] = endYear
#             browser.submit_form(WOS_AdvancedSearch_input_form)

#         historyResults = browser.select('div.historyResults')
#         historyResultsLen = len(historyResults)  

#         threads = []
#         threadClasses = []
#         returnDict = LockingDict(lock=threading.RLock())
#         for idx, his in enumerate(historyResults):
#             totalMarked = his.a.text.replace(",", "")

#             if int(totalMarked) >= 10000:
#                 responseToUI = ResponseEntity(
#                     resCode=400, 
#                     rsMsg="쿼리 결과가 10000개 이상입니다. 회당 쿼리 갯수를 조절해 주세요.",
#                     payload={"qeury":words, "result":"", "totalMarked":totalMarked})
#                 errMSG="쿼리 결과가 10000개 이상입니다. 회당 쿼리 갯수를 조절해 주세요."
#                 state.setErrMSG(errMSG=errMSG)
#                 # raise 익셉션 정의 필요

#                 state.printAndSetState(
#                     state="-100",
#                     stateMSG=errMSG
#                 )
#                 return responseToUI.returnResponse()

#             state.printAndSetState(
#                 state="1200", 
#                 stateMSG="쿼리 결과의 엑셀 데이터를 가져옵니다. %d/%d"%(idx+1, historyResultsLen))
#             jdx = 1
#             for mark in range(1, int(totalMarked), 500):
#                 state.printAndSetState(
#                     state="1300", 
#                     stateMSG="%s개의 레코드 중 %d번 레코드부터 레코드를 가져옵니다."%(totalMarked, mark))
#                 threadClass = WosProcess(self.SID, self.jsessionid, self.baseUrl)
#                 threadClasses.append(threadClass)

#                 url = self.baseUrl + his.a["href"]
#                 thread = threading.Thread(
#                     target=threadClass.getWOSExcelProcess,
#                     args=((jdx*10 + idx), url, totalMarked, mark, outputLocationPath, returnDict, self.loggerID, state)
#                 )
#                 threads.append(thread)
#                 jdx += 1
#                 thread.start()

#         # for job in jobs: job.join()
#         for thread in threads: thread.join()
            
#         state.printAndSetState(
#             state="1400", 
#             stateMSG="모든 스레드가 종료되었습니다. 모든 검색결과를 합칩니다.")

#         del returnDict['qid']
#         resPD = pd.concat(returnDict.values(), sort=True)

#         temp = self.loggerID.split('.')[0] + "_all_succeed.xls"
#         successResultFile = outputLocationPath + "/" + temp
#         while(os.path.exists(successResultFile)):
#             temp = "_" + temp
#             successResultFile = outputLocationPath + "/" + temp

#         state.printAndSetState(
#             state="1401", 
#             stateMSG="검색에 실패한 항목을 조사합니다.")

#         header = ""
#         if gubun == "TI": header = "Title"
#         elif gubun == "AU": header = "Authors"
#         elif gubun == "DO": header = "DOI"
#         else: pass
        
#         notFoundList = []
#         temp = " ".join(map(lambda x: x.lower().replace(" ", ""), resPD[header]))
#         parse = re.sub("([;/<>,.!@#$%^&*()_+=-]|\[|\]|\\\\)", "", temp)
#         for word in words:
#             tempWord = re.sub("([;/<>,.!@#$%^&*()_+=-]|\[|\]|\\\\)", "", word.replace(" ", "").lower())
            
#             if not re.search(tempWord, parse):
#                 notFoundList.append(word)

#         notFoundDF = pd.DataFrame({
#             header: notFoundList
#         })
#         excelWriter = pd.ExcelWriter(successResultFile)
#         resPD.to_excel(excelWriter, '전체 검색 결과')
#         excelWriter.save()

#         temp = self.loggerID.split('.')[0] + "_failed.xls"
#         failedResultFile = outputLocationPath + "/" + temp
#         while(os.path.exists(failedResultFile)):
#             temp = "_" + temp
#             failedResultFile = outputLocationPath + "/" + temp

#         excelWriter = pd.ExcelWriter(failedResultFile)
#         notFoundDF.to_excel(excelWriter, '전체 검색 실패 결과')
#         excelWriter.save()

#         responseToUI = ResponseEntity(
#             resCode=200, 
#             rsMsg="데이터를 성공적으로 처리했습니다.",
#             payload={
#                 "qeury":words, 
#                 "resultFilePath":outputLocationPath, 
#                 "notFoundList": notFoundList,
#                 "state":self.state
#                 }
#             )
        
#         state.printAndSetState(
#             state="2000",
#             stateMSG="데이터를 성공적으로 처리했습니다."
#         )
#         state.printAndSetState(
#             state="2000",
#             stateMSG="검색결과가 없는 항목은 \n %s 파일을 참조하십시오."%(failedResultFile)
#         )
#         return 

#     def makeQueryFromFile(self, path, packSize, gubun):
#         """
#         makeQueryFromFile
#             path : input 파일의 경로
#             packSize : 한 번에 질의할 input 값의 갯수
#             gubun : 쿼리 구분
#                 ex) TI, AU ...
#             경로로부터 인풋값을 읽어들입니다.
#             패킹 사이즈만큼 인풋을 나누고 나눈 인풋들에 따른 쿼리 리스트를 반환합니다.
#         """
#         # if csv
#         fname, ext = os.path.splitext(path)
#         if ext == ".csv":
#             df = pd.read_csv(path, header=0)
#             words = df.values[:, 0]
#         elif ext == ".xls" or ext == ".xlsx":
#             df = pd.read_excel(path, header=0)
#             words = df.values[:, 0]
#             pass
#         elif ext == ".txt":
#             pass
#         else:
#             raise Exception()
#         "".strip()
#         for idx, word in enumerate(words):
#             words[idx] = word.strip()

#         wordsLen = len(words)
#         if packSize <= 0 or packSize > wordsLen: packSize = wordsLen

#         queryList = [words[chunkIdx: chunkIdx + packSize] for chunkIdx in range(0, wordsLen, packSize)]
        
#         for idx, query in enumerate(queryList):
#             queryList[idx] = "%s=(\"%s\")"%(gubun, "\" OR \"".join(query))

#         return (queryList, words)
class TextHandler(logging.Handler):

    def __init__(self, text):
        logging.Handler.__init__(self)
        self.text = text

    def emit(self, record):
        msg = self.format(record)
        def append():
            self.text.configure(state='normal')
            self.text.insert(END, msg + '\n')
            self.text.configure(state='disabled')
            self.text.yview(END)

        self.text.after(0, append)


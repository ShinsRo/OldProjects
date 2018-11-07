from dependencies import *

class WosUserInterface():
    def __init__(self):
        """
        __init__
            세션 검증에 필요한 SID값과 jsessionid 값을 얻습니다.
            브라우저를 초기화하고 WOS의 AdvancedSearch 서비스에 접속합니다.
            run 메서드를 대기합니다.
        """
        self.RSNO = 0
        self.baseUrl = "http://apps.webofknowledge.com"
        self.state = State()
        self.state.printAndSetState(state="0000", stateMSG="SID와 jsessionid 값을 얻습니다.")

        self.browser = RoboBrowser(history=True, parser="lxml")
        self.browser.open(self.baseUrl)

        self.SID = self.browser.session.cookies['SID'].replace("\"", "")
        self.jsessionid = self.browser.session.cookies['JSESSIONID']

        self.state.printAndSetState(state="0001", stateMSG="SID : %s"%self.SID)
        self.state.printAndSetState(state="0002", stateMSG="jsessionid : %s"%self.jsessionid)
        self.state.printAndSetState(state="0200", stateMSG="질의를 시작할 수 있습니다.")

    def getStateObject(self):
        return self.state

    # Run method
    def run(self, startYear, endYear, gubun, inputFilePath, outputLocationPath, defaultQueryPackSize):
        state = self.state
        browser = self.browser
        state.printAndSetState(
            state="1001", 
            stateMSG="WOS AdvancedSearch를 시작합니다.")

        browser = RoboBrowser(history=True, parser="lxml")
        actionURL = self.baseUrl
        action = "/WOS_AdvancedSearch_input.do"
        param = ""
        param += "?SID=" + self.SID
        param += "&product=WOS"
        param += "&search_mode=AdvancedSearch"
        
        actionURL += action
        actionURL += param

        browser.open(actionURL)
        WOS_AdvancedSearch_input_form = browser.get_form(id="WOS_AdvancedSearch_input_form")


        state.printAndSetState(
            state="1002", 
            stateMSG="쿼리를 만듭니다.")
        queryList, words = self.makeQueryFromFile(inputFilePath, defaultQueryPackSize, gubun)

        queryListLen = len(queryList)
        for idx, query in enumerate(queryList):
            state.printAndSetState(
                state="1100", 
                stateMSG="쿼리 히스토리를 만들고 있습니다. %d/%d"%(idx+1, queryListLen))

            WOS_AdvancedSearch_input_form['value(input1)'] = query
            WOS_AdvancedSearch_input_form['startYear'] = startYear
            WOS_AdvancedSearch_input_form['endYear'] = endYear
            browser.submit_form(WOS_AdvancedSearch_input_form)

        historyResults = browser.select('div.historyResults')
        historyResultsLen = len(historyResults)  

        jobs = []
        processes = []
        processManager = Manager()
        returnDict = processManager.dict()
        for idx, his in enumerate(historyResults):
            totalMarked = his.a.text.replace(",", "")

            if int(totalMarked) >= 10000:
                responseToUI = ResponseEntity(
                    resCode=400, 
                    rsMsg="쿼리 결과가 10000개 이상입니다. 회당 쿼리 갯수를 조절해 주세요.",
                    payload={"qeury":words, "result":"", "totalMarked":totalMarked})
                state.setErrMSG(errMSG="쿼리 결과가 10000개 이상입니다. 회당 쿼리 갯수를 조절해 주세요.")
                # raise 익셉션 정의 필요

                return responseToUI.returnResponse()

            state.printAndSetState(
                state="1200", 
                stateMSG="쿼리 결과의 엑셀 데이터를 가져옵니다. %d/%d"%(idx+1, historyResultsLen))
            
            for mark in range(1, int(totalMarked), 500):
                state.printAndSetState(
                    state="1300", 
                    stateMSG="%s개의 레코드 중 %d번 레코드부터 레코드를 가져옵니다."%(totalMarked, mark))
                
                processClass = WosProcess(self.SID, self.jsessionid, self.baseUrl)
                processes.append(processClass)

                url = self.baseUrl + his.a["href"]
                job = Process(
                    target=processClass.getWOSExcelProcess,
                    args=(idx, url, totalMarked, mark, returnDict)
                )
                jobs.append(job)
                job.start()

        for job in jobs: job.join()
        
        resStr = " ".join(returnDict.values())
        notFound = list(map(lambda x: [x, False], words))

        for idx, word in enumerate(notFound):
            notFound[idx][1] = ( -1 == resStr.find(word[0]) )

        # with open(outputLocationPath + "failed.txt", "w+") as failedListFile:
        #     for word in notFound:
        #         if word[1] == True:
        #             failedListFile.write(word[0] + " 의 검색 결과 : 0개\n")

        

        responseToUI = ResponseEntity(
            resCode=200, 
            rsMsg="데이터를 성공적으로 전송했습니다.",
            payload={
                "qeury":words, 
                "resultFilePath":outputLocationPath, 
                "notFound":[word[0] for word in notFound if word[1]],
                "state":self.state
                }
            )
        
        state.printAndSetState(
            state="21000",
            stateMSG="작업 완료"
            )
        return responseToUI.returnResponse()

    def makeQueryFromFile(self, path, packSize, gubun):
        """
        makeQueryFromFile
            path : input 파일의 경로
            packSize : 한 번에 질의할 input 값의 갯수
            gubun : 쿼리 구분
                ex) TI, AU ...

            경로로부터 인풋값을 읽어들입니다.
            패킹 사이즈만큼 인풋을 나누고 나눈 인풋들에 따른 쿼리 리스트를 반환합니다.
        """
        # if csv
        fname, ext = os.path.splitext(path)
        if ext == ".csv":
            df = pd.read_csv(path, header=0)
            words = df.values[:, 0]
        elif ext == ".xls" or ext == ".xlsx":
            df = pd.read_excel(path, header=0)
            pass
        elif ext == ".txt":
            pass
        else:
            pass

        wordsLen = len(words)
        if packSize <= 0 or packSize > wordsLen: packSize = wordsLen

        queryList = [words[chunkIdx: chunkIdx + packSize] for chunkIdx in range(0, wordsLen, packSize)]
        
        for idx, query in enumerate(queryList):
            queryList[idx] = "%s=(\"%s\")"%(gubun, "\" OR \"".join(query))

        return (queryList, words)
import os
import sys
from multiprocessing import Process, Queue, Manager

# src 경로 등록
sys.path.insert(0, os.path.abspath(os.path.join(os.path.dirname(__file__), 'src')))

from src.wos_as_interface import WosUserInterface

if __name__=='__main__':
    wos = WosUserInterface()
    results = ""
    while(True):
        print("alpha version 0.4")
        print("종료하려면 0, 검색을 시작하려면 1을 입력하세요.")
        cmd = input().strip()
        if cmd == "0": break
        else: pass

        print("검색 기간을 입력하세요. ex) 1945 2018")
        startYear, endYear = input().strip().split()
        print("검색 구분을 입력하세요. ex) TI")
        gubun = input().strip()
        print("인풋 파일의 경로를 입력하세요.")
        inputFilePath = input().strip()
        print("아웃풋 파일의 경로를 입력하세요.")
        outputLocationPath = input().strip()
        
        if inputFilePath == "n" :
            inputFilePath="C:\\input.csv"
            
        if outputLocationPath == "n" :
            outputLocationPath="C:\\"
        try:
            results = wos.run(
                startYear=startYear, 
                endYear=endYear, 
                gubun=gubun, 
                # inputFilePath="C:\\Users\\siotman\\Desktop\\Projects\\sju-paper-scraper-app\\testData\\files\\top20.csv",
                # outputLocationPath="C:\\Users\\siotman\\Desktop\\Projects\\sju-paper-scraper-app\\",
                inputFilePath=inputFilePath,
                outputLocationPath=outputLocationPath,
                defaultQueryPackSize=0
            )
            print("검색의 결과가 없는 항목")
            print(results.payload['notFound'])
        except Exception as e:
            print(e, wos.getStateObject().getState())
        else:
            print(
                wos.getStateObject().getErrMSG(), 
                wos.getStateObject().getState())
        finally:
            print(results)

    
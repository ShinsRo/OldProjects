import os
import sys
from multiprocessing import Process, Queue, Manager

# src 경로 등록
sys.path.insert(0, os.path.abspath(os.path.join(os.path.dirname(__file__), 'src')))

from src.wos_as_interface import WosUserInterface

if __name__=='__main__':
    wos = ""
    results = ""
    try:
        wos = WosUserInterface()
    except Exception as e:
        print("WosInterface를 초기화하는데에 실패했습니다. 접속 ip가 유효한 지 확인해주세요.")
        sys.exit()

    print("해당 프로그램은 alpha 버전입니다.")

    while(True):
        print("alpha version 0.4")
        print("종료하려면 0, 검색을 시작하려면 1을 입력하세요.")
        cmd = input().strip()
        if cmd == "0": break
        else: pass

        while(True):
            print("검색 기간을 입력하세요. 1900에서 금년 사이여야 합니다. ex) 1945 2018")
            startYear, endYear = input().strip().split()

            if len(startYear) != 4 or len(endYear) != 4:
                print("입력 형식이 올바르지 않습니다.")
                continue
            
            if not 1900 <= int(startYear) <= 2018:
                print("년도는 1900과 금년 사이여야 합니다.")
                continue

            if not 1900 <= int(endYear) <= 2018:
                print("년도는 1900과 금년 사이여야 합니다.")
                continue

            if not int(startYear) <= int(endYear):
                print("검색 기간이 올바르지 않습니다.")
                continue

            break

        while(True):
            print("검색 구분을 입력하세요. ex) TI")
            print("TI : 논문명 검색")
            print("AU : 저자 검색")
            print("DO : DOI 검색")
            gubun = input().strip()

            if gubun != "TI" and gubun != "AU" and gubun != "DO": 
                print("%s는 유효하지 않은 구분입니다."%(gubun))
                continue
                
            break

        while(True):
            print("인풋 파일의 경로를 입력하세요.")
            inputFilePath = input().strip()
            if not os.path.exists(inputFilePath) :
                print("인풋 파일의 경로가 존재하지 않습니다.")
                continue

            break

        while(True):
            print("아웃풋 디렉토리 경로를 입력하세요. Default 경로는 C:\\ 입니다.")
            outputLocationPath = input().strip()
            if not os.path.isdir(outputLocationPath) :
                print("아웃풋 디렉토리의 경로가 존재하지 않거나 디렉토리가 아닙니다.")
                continue

            break
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

    
import requests
import os
import sys

# src 경로
sys.path.insert(0, os.path.abspath(os.path.join(os.path.dirname(__file__), 'src')))

from wos_as_interface import WosUserInterface

"""
TS= 주제 
TI= 제목 
AU= 저자 [색인] 
AI= 저자 식별자 
GP= 그룹 저자 [색인] 
ED= 에디터 
SO= 저널명 [색인] 
DO= DOI 
PY= 출판 연도 
AD= 연구기관명 및 주소 
OG= 확장된 연구기관명 [색인] 
OO= 기관 
SG= 부서 
SA= 거리 주소 
CI= 구/군/시 
PS= 시/도 
CU= 국가/지역 
ZP= 우편 번호 
FO= 연구비 지원 기관 
FG= 선정 번호 
FT= 보조금 정보 
SU= 연구 분야 
WC= Web of Science 범주 
IS= ISSN/ISBN 
UT= 식별 번호 
PMID= PubMed ID 

1. 옵션이 많은데 TI, AU, SO, DO 정도만 받아도 충분할 것 같습니다.

2. defaultQueryPackSize 는 한 번에 질의할 양을 규정합니다.

3. 사용 시 주의 : 
    Scraper 객체는 한 번만 생성하고,
    질의마다 run을 실행하는 형태로 구성하세요.

    경로가 demo 1이랑 다르니 테스트 데이터 실험할 때 주의하세요.

    에러 처리가 아직 미흡
"""
if __name__ == "__main__":
    sp = WosUserInterface()

    sp.run(
        startDate="201801", 
        endDate="201901", 
        gubun="TI", 
        inputFilePath="C:\\Users\\siotman\\Desktop\\Projects\\sju-paper-scraper-app\\testData\\files\\top20.csv",
        outputLocationPath="C:\\Users\\siotman\\Desktop\\Projects\\sju-paper-scraper-app\\",
        defaultQueryPackSize=0)
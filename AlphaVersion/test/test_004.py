from context import WosUserInterface
"""
인풋 유닛 테스트
"""

if __name__=='__main__':
    srap = WosUserInterface(SID="a", jsessionid="a")
    print(srap.makeQueryFromFile(
        path="C:\\Users\\siotman\\Desktop\\Projects\\sju-paper-scraper-app\\test\\10pro_addcite.xlsx", 
        packSize=0, gubun="TI"))

from context import WosUserInterface
from context import ResponseEntity

if __name__=='__main__':
    wos = WosUserInterface()
    results = ""
    try:
        results = wos.run(
            startYear="1945", 
            endYear="2018", 
            gubun="TI", 
            inputFilePath="C:\\Users\\siotman\\Desktop\\Projects\\sju-paper-scraper-app\\testData\\files\\top20.csv",
            outputLocationPath="C:\\Users\\siotman\\Desktop\\Projects\\sju-paper-scraper-app\\",
            defaultQueryPackSize=0
        )
    except Exception as e:
        print(e, wos.getStateObject().getState())
    else:
        print(
            wos.getStateObject().getErrMSG(), 
            wos.getStateObject().getState())
    finally:
        print(results)
        pass
    

    
from context import WosUserInterface

if __name__=='__main__':
    srap = WosUserInterface()
    srap.run(
        startYear="2018", 
        endYear="2019", 
        gubun="TI", 
        inputFilePath="C:\\Users\\siotman\\Desktop\\Projects\\sju-paper-scraper-app\\testData\\files\\top20.csv",
        outputLocationPath="C:\\Users\\siotman\\Desktop\\Projects\\sju-paper-scraper-app\\",
        # inputFilePath="C:\\Users\\F\\Desktop\\papers\\sju-paper-scraper-app\\src\\modules\\top20.csv",
        # outputLocationPath="C:\\Users\\F\\Desktop\\papers\\sju-paper-scraper-app\\src\\modules\\",
        defaultQueryPackSize=0)
        # filePath="")

    
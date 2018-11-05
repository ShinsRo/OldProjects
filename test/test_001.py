from context import scraper as sp

srap = sp.Scraper()
srap.run(
    startDate="201801", 
    endDate="201901", 
    gubun="TI", 
    inputFilePath="C:\\Users\\siotman\\Desktop\\Projects\\sju-paper-scraper-app\\src\\modules\\top20.csv",
    outputLocationPath="C:\\Users\\siotman\\Desktop\\Projects\\sju-paper-scraper-app\\src\\modules\\",
    # inputFilePath="C:\\Users\\F\\Desktop\\papers\\sju-paper-scraper-app\\src\\modules\\top20.csv",
    # outputLocationPath="C:\\Users\\F\\Desktop\\papers\\sju-paper-scraper-app\\src\\modules\\",
    defaultQueryPackSize=200)
    # filePath="")

    
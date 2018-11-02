from context import scraper as sp

date = {"start":201801,"end":201901}



srap = sp.Scraper()
srap.run(
    startDate="201801", 
    endDate="201901", 
    gubun="TI", 
    filePath="C:\\Users\\F\\Desktop\\papers\\sju-paper-scraper-app\\src\\modules\\top20.csv")
    # filePath="C:\\Users\\siotman\\Desktop\\Projects\\sju-paper-scraper-app\\src\\modules\\top20.csv")

    
import pandas as pd

class Scraper:
    def __init__(self):
        self.paperList = []
        self.inFile = {'path':'.', 'fileType':'csv or excel'}
        self.outFile = {'path':'.', 'fileType':'csv or excel'}
        self.state = "waiting"

    def setInFile(self, path, fileType):
        # 에러 처리 필요
        self.inFile.path = path
        self.inFile.fileType = fileType

    def setOutFile(self, path, fileType):
        # 에러 처리 필요
        self.outFile.path = path
        self.outFile.fileType = fileType

    # Run method
    ####################################
    def run(self, date, gubun):
        self.state = "running"
        print('state :' + self.state)

        # Main Logic
        try:
            self.exportExcel()
        except:
            pass
        finally:
            pass

        self.state = "waiting"
        print('state :' + self.state)

    ####################################

    def exportExcel(self):
        print('excel exported')


class PaperEntity:
    def __init__(self, doi, title, cited, issn, journal, author, date):
        self.doi = doi
        self.title = title
        self.cited = cited
        self.issn = issn
        self.journal = journal
        self.author = author
        self.date = date
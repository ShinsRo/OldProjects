from multiprocessing import Process
from scraper import Scraper, WosProcess

class A():
    def run(self):
        print("AAAA")

class B():
    def run(self):
        for i in range(5):
            a = A()
            proc = Process(target=a.run)
            proc.start()

class C():
    def run(self):

        for i in range(5):
             # 테스트용
            SID = "E2V7RPJe7OYXtfigmbk"
            jsessionid = "506EB868497C83AB26B65AA714B04878"
            baseUrl = "http://apps.webofknowledge.com"
            s = WosProcess(SID, baseUrl)
            idx = 1
            his = ""
            mark = 1
            totalMarked = 263
            proc = Process(target=s.getWOSExcel, args=(idx, his, str(mark), str(mark + 500 - 1), totalMarked))

            proc.start()

if __name__ == "__main__":
    c = C()

    c.run()
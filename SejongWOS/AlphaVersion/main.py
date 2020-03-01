import os
import sys
import random

from tkinter import *
from tkinter.ttk import *
from tkinter import filedialog
from tkinter import messagebox

import time
import threading
import logging
import multiprocessing

sys.path.insert(0, os.path.abspath(os.path.join(os.path.dirname(__file__), 'src')))

from wos_as_interface import WosUserInterface
try:
    import tkinter.scrolledtext as ScrolledText
except ImportError:
    import ScrolledText

class TextHandler(logging.Handler):

    def __init__(self, text):
        logging.Handler.__init__(self)
        self.text = text

    def emit(self, record):
        msg = self.format(record)
        def append():
            self.text.configure(state='normal')
            self.text.insert(END, msg + '\n')
            self.text.configure(state='disabled')
            self.text.yview(END)

        self.text.after(0, append)

class MainFrame(Frame):
    def __init__(self, master):
        Frame.__init__(self, master)

        self.master = master
        self.master.title("논문 정보 조회 프로그램")
        self.pack(fill=BOTH, expand=True)
        
        # --------------변수-----------
        self.startYear = None
        self.endYear = None
        self.gubun = None
        self.inputFilePath = None
        self.outputLocationPath = None
        self.defaultQueryPackSize = 0
        self.nextLoggerID = 0
        self.processingID = ""
        self.wos = None

        self.entryStartYear=Entry()
        self.entryEndYear=Entry()

        # --------------------------옵션 프레임------------------------------
        frame_searchopt = Frame(self)
        frame_searchopt.pack(fill=X)

        RadioVariety_1 = StringVar()

        def checkRadioButton():
            self.gubun=str(RadioVariety_1.get())
            label_status.config(text=" 선택된 옵션 : " + str(RadioVariety_1.get()))

        label = Label(frame_searchopt, text="검색 옵션",width=20)
        label.pack(side=LEFT, padx=20, pady=10)

        radio1 = Radiobutton(frame_searchopt, text="논문명", value="TI", variable=RadioVariety_1, command=checkRadioButton)
        radio1.pack(side=LEFT, padx=0)

        radio2 = Radiobutton(frame_searchopt, text="저자", value="AU", variable=RadioVariety_1, command=checkRadioButton)
        radio2.pack(side=LEFT, padx=10)

        radio3 = Radiobutton(frame_searchopt, text="DOI", value="DO", variable=RadioVariety_1, command=checkRadioButton)
        radio3.pack(side=LEFT, padx=10)

        label_status = Label(frame_searchopt, text="")
        label_status.pack(side=LEFT)

        # --------------------------기간 조회 프레임------------------------------

        frame_period = Frame(self)
        frame_period.pack(fill=X)

        label = Label(frame_period, text="조회 기간",width=20)
        label.pack(side=LEFT, padx=20, pady=10)

        self.entryStartYear = Entry(frame_period, width=10)
        self.entryStartYear.pack(side=LEFT, padx=0)
        #self.startYear = entryStartYear.get()

        label = Label(frame_period, text=" ~ ")
        label.pack(side=LEFT)

        self.entryEndYear = Entry(frame_period, width=10)
        self.entryEndYear.pack(side=LEFT, padx=0)
        #self.endYear = entryEndYear.get()

        label = Label(frame_period, text="   ex) 2012 ~ 2017")
        label.pack(side=LEFT)

        # -------------------------파일 경로 프레임--------------------------
        #filePathPrame1 : input
        #fildPathPrame2 : output

        def selectinputpath():
            self.inputFilePath=filedialog.askopenfilename(initialdir="C:/", title="choose your file")
            label_checkInputPath.config(text= self.inputFilePath)
            print("입력 파일 경로 : ", self.inputFilePath)

        def selectoutputpath():
            self.outputLocationPath = filedialog.askdirectory()
            label_checkOutputPath.config(text=self.outputLocationPath)
            print("다운받을 폴더 경로 :",self.outputLocationPath)

        filepathFrame1 = Frame(self)
        filepathFrame1.pack(fill=X)

        # 인풋 파일 경로---------------------------------------------------------------------------------------------------
        input_path = Label(filepathFrame1, text="입력 파일 경로", width=20)
        input_path.pack(side=LEFT, padx=20, pady=10)

        label_checkInputPath = Label(filepathFrame1, text="",background="white",width=50)
        label_checkInputPath.pack(side=LEFT)

        # 파일경로찾기 버튼
        btninputSearch = Button(filepathFrame1, text="...", width=3,state="normal" ,command=selectinputpath)
        btninputSearch.pack(side=RIGHT, padx=10, pady=10)

        # 아웃풋 파일 경로----------------------------------------------------------------------------------------------------
        filepathFrame2 = Frame(self)
        filepathFrame2.pack(fill=X)

        output_path = Label(filepathFrame2, text="다운받을 폴더 경로", width=20)
        output_path.pack(side=LEFT, padx=20, pady=10)

        label_checkOutputPath = Label(filepathFrame2, text="",background="white" , width=50)
        label_checkOutputPath.pack(side=LEFT)

        btnoutputSearch = Button(filepathFrame2, text="...", width=3,state="normal",command=selectoutputpath)
        btnoutputSearch.pack(side=RIGHT, padx=10, pady=10)


        # 실행 버튼
        frame4 = Frame(self)
        frame4.pack(fill=X)
        btnExecute = Button(frame4, text="실행" ,command=self.execute)
        btnExecute.pack(side=RIGHT, padx=20, pady=10)


    def execute(self):
        self.startYear = self.entryStartYear.get()
        self.endYear = self.entryEndYear.get()
        print("---------------input--------------")
        print("구분 : ",self.gubun)
        print("조회 기간 : ",self.startYear ," ~ " , self.endYear)
        print("입력 파일 경로 : ", self.inputFilePath)
        print("다운받을 폴더 경로 : ",self.outputLocationPath)
        print("----------------------------------")

        startYear = self.startYear
        endYear = self.endYear
        gubun = self.gubun
        inputFilePath = self.inputFilePath
        outputLocationPath = self.outputLocationPath
        errMSG = ""
        try:
            if len(startYear) != 4 or len(endYear) != 4:
                errMSG = "입력 형식이 올바르지 않습니다."
                print(errMSG)
                raise Exception()

            if not 1900 <= int(startYear) <= 2018:
                errMSG = "년도는 1900과 금년 사이여야 합니다."
                print(errMSG)
                raise Exception()

            if not 1900 <= int(endYear) <= 2018:
                errMSG = "년도는 1900과 금년 사이여야 합니다."
                print(errMSG)
                raise Exception()

            if not int(startYear) <= int(endYear):
                errMSG = "검색 기간이 올바르지 않습니다."
                print(errMSG)
                raise Exception()
            if gubun != "TI" and gubun != "AU" and gubun != "DO":
                errMSG = "%s는 유효하지 않은 구분입니다." % (gubun)
                print(errMSG)
                raise Exception()

            if not os.path.exists(inputFilePath):
                errMSG = "인풋 파일의 경로가 존재하지 않습니다."
                print(errMSG)
                raise Exception()

            if not os.path.isdir(outputLocationPath):
                errMSG = "아웃풋 디렉토리의 경로가 존재하지 않거나 디렉토리가 아닙니다."
                print(errMSG)
                raise Exception()

            fname, ext = os.path.splitext(inputFilePath)
            if not (ext == '.csv' or ext == ".xls" or ext == ".xlsx"):
                errMSG = "인풋 파일의 형식이 올바르지 않습니다."
                print(errMSG)
                raise Exception()

        except Exception as e:
            messagebox.showinfo("Error", errMSG)
            print(e)
            return

        root = Tk()
        root.geometry("600x300")

        self.nextLoggerID = os.path.basename(inputFilePath)
        self.processingID = "%032x"%random.getrandbits(128)
        self.nextLoggerID += "." + self.processingID
        StateFrame(root, self.nextLoggerID)

        t1 = threading.Thread(target=self.runWOS, args=[
            self.startYear,
            self.endYear,
            self.gubun,
            self.inputFilePath,
            self.outputLocationPath,
            self.defaultQueryPackSize,
            self.nextLoggerID
        ])
        t1.start()

        root.mainloop()
        t1.join()
        
        self.refreshWOS()

    def runWOS(self, startYear, endYear, gubun, inputFilePath, outputLocationPath, defaultQueryPackSize, nextLoggerID):

        wos = WosUserInterface(loggerID=nextLoggerID)
        wos.run(
            startYear=startYear,
            endYear=endYear,
            gubun=gubun,
            inputFilePath=inputFilePath,
            outputLocationPath=outputLocationPath,
            defaultQueryPackSize=defaultQueryPackSize
        )

    def refreshWOS(self):
        self.wos = WosUserInterface(
            SID=self.wos.SID,
            jsessionid=self.wos.jsessionid)

    def close(master):
        master.quit()
        master.destroy()


class StateFrame(Frame):

    def __init__(self, parent, name):
        Frame.__init__(self, parent)
        self.root = parent
        self.name = name
        self.build_gui()

    def build_gui(self):
        # Build GUI
        self.root.title('%s 파일 처리 상태'%".".join(self.name.split('.')[:1]))
        self.root.option_add('*tearOff', 'FALSE')
        self.grid(column=0, row=1, sticky='ew')
        self.grid_columnconfigure(0, weight=1, uniform='a')
        self.grid_columnconfigure(1, weight=1, uniform='a')
        self.grid_columnconfigure(2, weight=1, uniform='a')
        self.grid_columnconfigure(3, weight=1, uniform='a')

        # Add text widget to display logging info
        st = ScrolledText.ScrolledText(self, state='disabled')
        st.configure(font='TkFixedFont')
        st.grid(column=0, row=1, sticky='w', columnspan=4)

        # Create textLogger
        text_handler = TextHandler(st)

        # Logging configuration
        handler = logging.FileHandler(filename="log.log", mode="w", encoding="utf-8")
        logging.basicConfig(handlers=[handler],
                            level=logging.INFO,
                            format='%(asctime)s - %(levelname)s - %(message)s')

        # Add the handler to logger
        logger = logging.getLogger(self.name)
        logger.addHandler(text_handler)

if __name__ == '__main__':
    root = Tk()
    root.geometry("600x300")
    MainFrame(root)
    root.mainloop()
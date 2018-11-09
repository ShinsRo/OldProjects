import os
import sys

from tkinter import *
from tkinter.ttk import *
from tkinter import filedialog

sys.path.insert(0, os.path.abspath(os.path.join(os.path.dirname(__file__), 'src')))

from wos_as_interface import WosUserInterface

class MyFrame(Frame):
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

        def checkYear():
            self.gubun=str(RadioVariety_1.get())
            label_status.config(text=" 선택된 옵션 : " + str(RadioVariety_1.get()))

        # -------------------------파일 경로 프레임--------------------------
        #filePathPrame1 : input
        #fildPathPrame2 : output

        def selectinputpath():
            #self.inputFilePath = filedialog.askdirectory()
            self.inputFilePath=filedialog.askopenfilename(initialdir="C:/", title="choose your file")
            #entryinput_path.setvar(str(self.inputFilePath))
            print("입력 파일 경로 : ", self.inputFilePath)

        def selectoutputpath():
            self.outputLocationPath = filedialog.askdirectory()
            #entryoutput_path.setvar(str(self.outputLocationPath))
            print("다운받을 폴더 경로 :",self.outputLocationPath)

        filepathFrame1 = Frame(self)
        filepathFrame1.pack(fill=X)

        # 인풋 파일 경로
        input_path = Label(filepathFrame1, text="입력 파일 경로", width=20)
        input_path.pack(side=LEFT, padx=20, pady=10)

        entryinput_path = Entry(filepathFrame1, width=50)
        entryinput_path.pack(side=LEFT, padx=0)

        # 파일경로찾기 버튼
        btninputSearch = Button(filepathFrame1, text="...", width=3,state="normal" ,command=selectinputpath)
        btninputSearch.pack(side=RIGHT, padx=10, pady=10)

        # 아웃풋 파일 경로

        filepathFrame2 = Frame(self)
        filepathFrame2.pack(fill=X)

        output_path = Label(filepathFrame2, text="다운받을 폴더 경로", width=20)
        output_path.pack(side=LEFT, padx=20, pady=10)

        entryoutput_path = Entry(filepathFrame2, width=50)
        entryoutput_path.pack(side=LEFT, padx=0, )

        # entryoutput_path.insert(0,self.outputLocationPath)
        # 파일경로찾기 버튼

        # btnoutputSearch = Button(filepathFrame2, text="...", command=selectinputpath(self.outputLocationPath), width=3)
        btnoutputSearch = Button(filepathFrame2, text="...", width=3,state="normal",command=selectoutputpath)
        btnoutputSearch.pack(side=RIGHT, padx=10, pady=10)

        '''
        #검색 옵션
        frame_searchOpt = Frame(self)
        frame_searchOpt.pack(fill=X)

        values = ['논문명','저자','DOI']

        #combobox = Combobox(frame_searchOpt,width=15, height=10, values=values,exportselection=False, postcommand=self.inputPaperName())
        combobox = Combobox(frame_searchOpt, width=15, height=10, values=values, exportselection=False)
        combobox.pack(side=LEFT, padx=20, pady=10)
        combobox.set("검색 옵션")
        '''


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

        try:
            if len(startYear) != 4 or len(endYear) != 4:
                print("입력 형식이 올바르지 않습니다.")
                raise Exception()

            if not 1900 <= int(startYear) <= 2018:
                print("년도는 1900과 금년 사이여야 합니다.")
                raise Exception()

            if not 1900 <= int(endYear) <= 2018:
                print("년도는 1900과 금년 사이여야 합니다.")
                raise Exception()

            if not int(startYear) <= int(endYear):
                print("검색 기간이 올바르지 않습니다.")
                raise Exception()
            if gubun != "TI" and gubun != "AU" and gubun != "DO":
                print("%s는 유효하지 않은 구분입니다." % (gubun))
                raise Exception()

            if not os.path.exists(inputFilePath):
                print("인풋 파일의 경로가 존재하지 않습니다.")
                raise Exception()

            if not os.path.isdir(outputLocationPath):
                print("아웃풋 디렉토리의 경로가 존재하지 않거나 디렉토리가 아닙니다.")
                raise Exception()

        except Exception as e:
            print(e)
            return

        if self.wos == None: self.wos = WosUserInterface()

        self.wos.run(
            startYear=self.startYear,
            endYear=self.endYear,
            gubun=self.gubun,
            inputFilePath=self.inputFilePath,
            outputLocationPath=self.outputLocationPath,
            defaultQueryPackSize=self.defaultQueryPackSize
        )
        """
            수정
        """
        self.refreshWOS()

    def refreshWOS(self):
        self.wos = WosUserInterface(
            SID=self.wos.SID,
            jsessionid=self.wos.jsessionid)

    def close(master):
        master.quit()
        master.destroy()


def main():
    root = Tk()
    root.geometry("600x300")
    MyFrame(root)
    root.mainloop()
    # input form 전달

    # radio button .get 이랑, 검색어, start year이랑 end year


if __name__ == '__main__':
    main()
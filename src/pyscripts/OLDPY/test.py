import os
import sys
import pandas as pd
import traceback
import robobrowser
import numpy as np
def getQueryListFromFile(path):
    fname, ext = os.path.splitext(path)
    encodings = ['utf-8', 'cp949', 'euc-kr']
    dec = ''
    for decodeCodec in encodings:
        dec = decodeCodec
        try:
            if ext == ".csv":
                df = pd.read_csv(path, header=0, encoding=decodeCodec)
                queryList = df.values[:].tolist()
            elif ext == ".xls" or ext == ".xlsx":
                df = pd.read_excel(path, header=0, encoding=decodeCodec)
                queryList = df.values[:].tolist()
            else:
                raise Exception()
        except UnicodeDecodeError as e:
            traceback.print_tb(e.__traceback__) 
        else:
            break

    returnQueryList = []
    for idx, qry in enumerate(queryList):
        if type(qry[0]) == type(np.nan) or not qry[0]:
            continue
        else :
            if (len(qry[0]) < 3) or qry[0].strip() == '':
                continue

            if len(qry) == 1:
                    queryList[idx] = (qry[0], '', '')
            elif len(qry) == 2:
                queryList[idx] = (qry[0], '' if type(qry[1]) == type(np.nan) else qry[1], '')
            else:
                queryList[idx] = (
                    qry[0], 
                    '' if type(qry[1]) == type(np.nan) else qry[1], 
                    '' if type(qry[2]) == type(np.nan) else qry[2])
            returnQueryList += [queryList[idx]]

    return returnQueryList, dec

def browerGetFormTest():
    browser = robobrowser.RoboBrowser(history=True, parser='lxml')

    print(requests.Session().get('http://www.naver.com'))
if __name__ == "__main__":
    t_list = range(1, 269)
    length = len(t_list)
    amount = 16
    portion = length // amount + 1

    for idx in range(0, length, portion):
        print(t_list[idx:idx+portion])
# if __name__ == "__main__":
#     print('test')
#     temp, dec = getQueryListFromFile('C:\\Users\\F\\Desktop\\testData\\test5.xlsx')
#     print(str(temp), dec)
#     print(sys.getdefaultencoding())
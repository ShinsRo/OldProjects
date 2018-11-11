from context import *
import pandas as pd
# from constant import *

"""
인풋 유닛 테스트
"""

if __name__=='__main__':
    aa = pd.read_excel("C:\\Users\\siotman\\Desktop\\input_검색결과_0.xls", header=26)
    
    print(aa[:, 0])
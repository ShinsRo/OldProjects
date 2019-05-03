from context import *
import pandas as pd
import re
# from constant import *

"""
인풋 유닛 테스트
"""

if __name__=='__main__':
    header = "Title"
    resPD = {"Title":["AS@#%@///$$%D,AS[]-=D", "AAA \\bb^!b CC*&^%$#C", "A<>Ac!@%^&*@*)D..D"]}
    temp = " ".join(map(lambda x: x.lower().replace(" ", ""), resPD[header]))
    parse = re.sub("([;/<>,.!@#$%^&*()_+=-]|\[|\]|\\\\)", "", temp)
    print(parse)
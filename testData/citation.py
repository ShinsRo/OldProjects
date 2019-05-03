import requests
from robobrowser import RoboBrowser
from bs4 import BeautifulSoup
import pandas as pd
import time
import sys



class Paper:
    no = 0
    title = ""
    author = ""
    journal = ""
    ISSN = ""
    year = 0
    cited = 0
    doi = ""
    IF = 0


browser = RoboBrowser(history=True)
browser.open("http://apps.webofknowledge.com/WOS_GeneralSearch_input.do;jsessionid=C0869EEDE01F91FB8B7F92ED05EB8972?product=WOS&search_mode=GeneralSearch&SID=E1XUuzYJfkILEXCQF8V&preferencesSaved=")

papers = []

if (len(sys.argv) == 2):
    filename = str(sys.argv[1])
else:
    filename = "files/top20.csv"

df = pd.read_csv(filename, header=0)
titles = df.values[:, 0]

fileparts = filename.split('.')
fileresult = fileparts[0] + "_result.csv"

f = open(fileresult, "a")
f.writelines("Title, Cited, DOI, Notes \n")
f.close()

i = 0

print("start %s papers" % str(len(titles)))

for title in titles:

    title = str(title).replace("\"","")
    start = time.time()

    search_form = browser.get_form(id='WOS_GeneralSearch_input_form')
    search_form['value(input1)'] = title
    search_form['value(select1)'] = "TI"

    browser.submit_form(search_form)

    url = browser.url

    soup = BeautifulSoup(str(browser.parsed), "html.parser")

    textTotalResults = soup.find(id='trueFinalResultCount')
    if textTotalResults == None:
        ''' no results'''
        f = open(fileresult, "a")
        f.writelines("%s, %s, %s, %s \n" % (title.replace(";","").replace(",","") , "0", "", "No result"))
        f.close()
    else:
        results = str(textTotalResults).split(">")[1].split("<")[0]
        items = soup.find_all("div", "search-results-item")
        paper = Paper()
        i_soup = BeautifulSoup(str(items[0]), "html.parser")

        paper.title = title.replace(";","").replace(",","")

        strYear = i_soup.find_all("span", text="Published: ")[0].find_next_siblings()

        if len(strYear) > 1:
          paper.doi = str(strYear[1]).split("\">")[1].split("</span>")[0]
        else:
          paper.doi = "NA"
        cited = i_soup.find_all("a", "snowplow-times-cited-link")
        if cited == []:
          paper.cited = 0
        else:
          paper.cited = str(cited[0]).split("\">")[1].split("</a>")[0]

        f = open(fileresult, "a")
        f.writelines("%s, %s, %s, %s \n" % (
        paper.title, paper.cited, paper.doi, results + " results"))
        f.close()
        browser.back()
    end = time.time()

    processtime = end - start
    i = i + 1
    print("processed paper %s with %s s "%(str(i), str(processtime)))




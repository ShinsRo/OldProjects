import os
import sys

sys.path.append(
    os.path.dirname(
        os.path.abspath(
            os.path.dirname(os.path.abspath(__file__))
        )
    )
)

import base64
import json
import requests

import parser_constants

JAXWS2REST_SERVER = parser_constants.JAXWS2REST_SERVER
YOUR_PAPER_SERVER = parser_constants.YOUR_PAPER_SERVER

def search(userQuery, begin, end, firstRecord, count):
    data = {
        "queryParameters": {
            "databaseId": "WOS",
            "userQuery": userQuery,
            "timeSpan": {  
                "begin": begin,
                "end": end
            },
            "queryLanguage": "en"
        },
        
        "retrieveParameters": {
            "firstRecord": firstRecord,
            "count": count
        }
    }
    http_res = requests.post(JAXWS2REST_SERVER + "WokSearchService/search", json = data)
    return json.loads(http_res.text)

def lamrSearch(searchResult):
    uids = [record['uid'] for record in searchResult['records']]
    data = {
        "uids": uids
    }

    http_res = requests.post(JAXWS2REST_SERVER + "LamrService/lamrCorCollectionData", json = data)
    return json.loads(http_res.text), uids

def addPapers(session, username, uids):
    data = {
        "username"    : username,
        "uids"        : [{'uid': uid, 'isReprint': True} for uid in uids],
    }
    http_res = session.post(YOUR_PAPER_SERVER + "myPaper/add", json = data)
    return json.loads(http_res.text)

def register(username, password):
    data = {
        "username"  : username,
        "password"  : password,
        "name"      : "김승신"
    }
    http_res = requests.post(YOUR_PAPER_SERVER + "auth/availableCheck", json = data)
    if http_res.text:
        requests.post(YOUR_PAPER_SERVER + "auth/register", json = data)
    return

def login(username, password):
    data = {
        "username"  : username,
        "password"  : password,
    }
    authorization = username + ":" + password
    
    authorization = "Basic " + str(base64.b64encode(authorization.encode("utf-8")), 'utf-8')
# cHl0aG9uMDE6cHl0aG9uMDE=
    session = requests.Session()
    session.headers.update({
        'Authorization': authorization
    })
    http_res = session.post(YOUR_PAPER_SERVER + "auth/login", json = data)
    
    return session

if __name__ == '__main__':
    username = "python01"
    password = "python01"
    register(username, password)
    session = login(username, password)

    searchResult = search("AD=(Sejong Univ)", "2019-01-01", "2019-02-01", 1, 50)
    lamrResult, uids = lamrSearch(searchResult)

    result = addPapers(session, username, uids)

    print(result)
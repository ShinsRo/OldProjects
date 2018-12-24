import requests
import base64
import time
from itertools import cycle
from lxml.html import fromstring
import random
import sju_CONSTANTS as _CONS
from fake_useragent import UserAgent
def get_proxies():
    url = 'https://free-proxy-list.net/'
    response = requests.get(url)
    parser = fromstring(response.text)
    proxies = set()
    for i in parser.xpath('//tbody/tr')[:20]:
        if i.xpath('.//td[7][contains(text(),"yes")]'):
            #Grabbing IP and corresponding PORT
            proxy = ":".join([i.xpath('.//td[1]/text()')[0], i.xpath('.//td[2]/text()')[0]])
            proxies.add(proxy)
    return proxies

def get_proxy_pool(proxies):
    return cycle(proxies)

if __name__ == "__main__":
    # proxies = get_proxies()
    # proxy_pool = cycle(proxies)
    # u_pool = cycle(user_agent_list)
    # ua = next(u_pool)
    # headers = {
    #   'User-Agent': random.choice(_CONS.USER_AGENT_LIST)
    # }
    print('test start')
    ua = UserAgent()
    st = time.time()
    s = requests.Session()
    # proxy = next(proxy_pool)
    testb = base64.b64encode(b'wsfuser1:password1')
    testbr = base64.b64encode(b'Sejong_HG:Welcome#%@0078')
    headers={
        "Authorization":testbr.decode("utf-8"), 
        "connection":"keep-alive", 
        "cache-control":"no-cache", 
        "SOAPAction":"", 
        "pragma":"no-cache", 
        "content-type":"text/xml; charset=UTF-8",
        "Accept":"*"
        }
    res = s.get("http://search.webofknowledge.com/esti/wokmws/ws/WOKMWSAuthenticate?wsdl", 
        headers=headers,
    )
    with open('res.xml', "wb") as fres:
        fres.write(res.content)
#     http://search.webofknowledge.com/esti/wokmws/ws/WOKMWSAuthenticate?wsdl
# http://search.webofknowledge.com/esti/wokmws/ws/WokSearchLite?wsdl
    end = time.time()
    print(end-st, 'sec')

    # print('test start')
    # ua = UserAgent()
    # st = time.time()
    # s = requests.Session()
    # # proxy = next(proxy_pool)
    # ua = UserAgent()
    # headers={'User-Agent': ua.random}
    # res = s.get("http://www.webofknowledge.com", 
    #     headers=headers,
    #     allow_redirects=False
    # )
    # res.headers.get('location')
    # for redirect in s.resolve_redirects(res, res.request):
    #     print(redirect.cookies) 
    #     print(redirect.headers.get('location')) 
        
    # end = time.time()
    # print(res.cookies)
    # print(end-st, 'sec')
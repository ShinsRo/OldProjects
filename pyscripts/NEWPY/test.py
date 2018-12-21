import requests
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
    ua = UserAgent()
    headers={'User-Agent': ua.random}
    res = s.get("http://www.webofknowledge.com", 
        headers=headers,
        allow_redirects=False
    )
    res.headers.get('location')
    for redirect in s.resolve_redirects(res, res.request):
        print(redirect.cookies) 
        print(redirect.headers.get('location')) 
        
    end = time.time()
    print(res.cookies)
    print(end-st, 'sec')
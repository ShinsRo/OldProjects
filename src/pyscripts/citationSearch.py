import logging
import sys
import time
import sju_response
import robobrowser

if __name__ == "__main__":
    sres = sju_response.SJUresponse('citationSearch')
    
    sres.print('log', 'init Browser')
    browser = robobrowser.RoboBrowser(history=True, parser="lxml")
    sres.print('log', 'init Done')

    time.sleep(2)
    sres.print('loading', 'All Done')
    sres.print('log', '1')
    sres.print('log', '2')
    sres.print('log', '3')
    sres.print('log', '4')
    sres.print('log', '5')
    
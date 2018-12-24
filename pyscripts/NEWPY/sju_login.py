import sju_utiles
import sju_models
import sju_CONSTANTS
import sju_exceptions

import re
import random
import requests

from sju_utiles import os
from sju_utiles import UserAgent
from sju_utiles import BeautifulSoup

class Login():
    '''
    '''
    def __init__(self):
        '''
        '''
        session = requests.Session()
        self.session = sju_utiles.set_user_agent(session)

    def login_wos(self, user_id, user_password):
        session = self.session
        SID = None
        JSESSIONID = None

        res = session.post('https://www.webofknowledge.com/?locale=ko_KR&Alias=WOK5&secure=false&auth=Roaming',
            data={
                'username': user_id,
                'password': user_password,
            }
        )
        print(res.cookies)
        return res

if __name__ == "__main__":
    Login().login_wos(
    )
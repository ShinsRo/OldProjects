import os
import sys
import time
import requests
import pandas as pd
import xlwt
import re
# bs4, robobrowser, multiprocessing, xlwt, pandas, requests
from bs4 import BeautifulSoup
from robobrowser import RoboBrowser
from multiprocessing import Process, Queue, Manager

from supports.constant import *
from supports.state import State
from models.entities import ResponseEntity
from models.locking_dict import LockingDict
from core.processes import WosProcess

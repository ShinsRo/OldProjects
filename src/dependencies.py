import os
import sys
import time
import requests
import pandas as pd

from bs4 import BeautifulSoup
from robobrowser import RoboBrowser
from multiprocessing import Process, Queue, Manager

from constants._CONSTANT import *
from supports.state import State
from models.entities import ResponseEntity
from core.processes import WosProcess
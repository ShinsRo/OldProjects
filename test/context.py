import os
import sys

# src 경로 등록
sys.path.insert(0, os.path.abspath(os.path.join(os.path.dirname(__file__), '..')))

import src.modules.scraper as scraper
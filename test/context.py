import os
import sys

# src 경로 등록
sys.path.insert(0, os.path.abspath(os.path.join(os.path.dirname(__file__), '../src')))

from wos_as_interface import WosUserInterface
from supports.state import State
from core.processes import WosProcess
from models.entities import ResponseEntity
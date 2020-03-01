class _PROJECT_INFO:
    """
    """
    def __init__(self):
        self.VERSION = "1.0.8"
        self.NAME = "세종대학교 WOS 인터페이스"
        self.AUTHOR = "--"

PROJECT_INFO = _PROJECT_INFO()

class _STATE:
    """
    상태 메세지
    """
    def __init__(self):
        self.INIT = PROJECT_INFO.NAME + "를 초기화합니다."
        self.GET_SESSION_INFO = "세션 정보를 가져옵니다."
        self.NEW_PROCESS = "새로운 프로세스를 생성합니다."
        self.CLOSE_PROCESS = "프로세스를 종료합니다."
        self.READ_EXCEL = "엑셀 데이터를 읽습니다."
        self.READ_INPUT = "입력 데이터를 읽습니다."
        self.MAKE_QRY = "쿼리 데이터를 생성합니다."
        self.TRY_CONNECT = "WOS에 연결을 시도합니다."

        self.WAIT_INPUT = "입력을 기다립니다."
        self.WAIT_PROCESS = "처리를 기다립니다."
        
STATE_MSG = _STATE()

class _ERR_GEN:
    """
    일반 오류 메세지
    """
    def __init__(self):
        self.UNKNOWN = "알 수 없는 에러가 일어났습니다."
        self.INVALID_INPUT = "올바르지 않는 입력입니다."
        self.MSG003 = ""
        self.MSG004 = ""
ERR_GEN_MSG = _ERR_GEN()

class _ERR_FIL:
    """
    파일 오류 메세지
    """
    def __init__(self):
        self.INVALID_PATH = "파일 경로가 올바르지 않습니다."
        self.FILE_NOT_FOUND = "파일이 존재하지 않습니다."
ERR_FIL_MSG = _ERR_FIL()

class _ERR_QRY:
    """
    """
    def __init__(self):
        self.MAXIMUM = "10000"
        self.TO_MANY = "쿼리 결과가 %s개 이상입니다. 회당 쿼리 갯수를 조절해주세요."%self.MAXIMUM
ERR_QRY_MSG = _ERR_QRY()

class _ERR_HTP:
    """
    """
    def __init__(self):
        self.CONNECT = "인터넷 연결에 실패했습니다."
        self.GET = ""
ERR_HTP_MSG = _ERR_HTP()
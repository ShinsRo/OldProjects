class InitSessionError(Exception):
    '''초기화 실패 예외'''
    pass

class LoginRequired(Exception):
    '''로그인이 필요한 경우'''
    pass
    
class InitMultiSessionErr(Exception):
    '''스레드가 충분하지 않을 경우'''
    pass

class NoPaperDataError(Exception):
    '''논문 정보가 없는 경우 예외'''
    pass

class FailedToParseError(Exception):
    def __init__(self, original, query):
        self.original = original
        self.query = query
    def get_original_and_query_exception(self):
        return self.original, query
    pass

class MultiplePaperDataError(Exception):
    '''논문 정보를 특정 불가한 경우 예외'''
    pass

class InputValidationError(Exception):
    '''논문 정보를 특정 불가한 경우 예외'''
    pass

class queryHasInvalidCharError(Exception):
    '''쿼리에 유효하지 않은 문자가 포함한 경우 예외'''
    pass

class NewConnectionError(Exception):
    '''sju_get, sju_post 연결지연으로 인한 접근불가 예외'''
    pass


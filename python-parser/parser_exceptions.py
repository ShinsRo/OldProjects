class AcessDeniedError(Exception):
    '''접근 거부 예외'''
    pass
    
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

class RecordNotAvailableError(Exception):
    '''sju_get, sju_post 구독 권한 없음 예외'''
    pass


class InitSessionError(Exception):
    '''초기화 실패 예외'''
    pass

class RequestsError(Exception):
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

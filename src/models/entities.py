class ResponseEntity:
    def __init__(self, resCode, rsMsg, payload): 
        self.resCode = resCode
        self.rsMsg = rsMsg
        self.payload = payload

    def returnResponse(self):
        res = {
            "code" : self.resCode,
            "msg" : self.rsMsg,
            "payload" : self.payload
        }
        return res
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

    def toString(self):
        string = "처리결과코드 : %s\n"%self.resCode
        string += "결과 메시지 : %s\n"%self.resCode
        string += "payload\n"
        for key in self.payload.keys():
            string += "%s : %s"%(key, self.payload[key])

        return string
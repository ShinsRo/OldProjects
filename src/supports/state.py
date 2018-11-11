import logging

class State:
    def __init__(self, loggerID):
        self.state = "init"
        self.stateMSG = ""
        self.errMSG = ""
        self.loggerID = loggerID
    
    def getState(self):
        return self.state, self.stateMSG

    def setState(self, state, stateMSG):
        self.state = state
        self.stateMSG = stateMSG
    
    def setErrMSG(self, errMSG):
        self.errMSG = errMSG

    def getErrMSG(self):
        return self.errMSG

    def printState(self):
        print('[state %s] %s'%(self.state, self.stateMSG))
        msg = '[state %s] %s'%(self.state, self.stateMSG)
        logging.getLogger(self.loggerID).info(msg)

    def printAndSetState(self, state, stateMSG):
            self.setState(state, stateMSG)
            self.printState()


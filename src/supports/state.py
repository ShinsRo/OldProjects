class State:
    def __init__(self):
        self.state = "init"
        self.stateMSG = ""
    
    def getState(self):
        return self.state, self.stateMSG

    def setState(self, state, stateMSG):
        self.state = state
        self.stateMSG = stateMSG

    def printState(self):
        print('[state %s] %s'%(self.state, self.stateMSG))

    def printAndSetState(self, state, stateMSG):
        self.setState(state, stateMSG)
        self.printState()


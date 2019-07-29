class Logger():
    def __init__(self, name):
        self.name = name
    
    def log(self, level, msg):
        print("[%s] [%s]"%(level, self.name), msg)
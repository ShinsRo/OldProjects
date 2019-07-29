from parser_impl_1 import WosParser

class ParserInterface():
    def __init__(self):
        self.parser = WosParser()
    
    def run(self, SID: str, targetType: str, targetURL: str):
        self.parser.run(SID, targetType, targetURL);
from parser_impl_1 import WosParser

class ParserInterface():
    def __init__(self):
        self.parser = WosParser()
    def run(self, targetType: str, uid: str, SID: str, targetURL: str):
        self.parser.run(targetType, uid, SID, targetURL)
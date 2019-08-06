from parser_impl_1  import WosParser
from parser_mailman import Mailman

class ParserInterface():
    def __init__(self, mailman: Mailman):
        self.parser = WosParser(mailman)
    def run(self, targetType: str, uid: str, targetURL: str):
        self.parser.run(targetType, uid, targetURL)
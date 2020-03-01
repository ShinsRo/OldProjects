import os
import sys

sys.path.append(
    os.path.dirname(
        os.path.abspath(
            os.path.dirname(os.path.abspath(__file__))
        )
    )
)

from parser_mailman import Mailman

def mail_test():
    mailman = Mailman()
    for idx in range(0, 100):
        mailman.send("", "", "", "", "%d" % idx)
    
if __name__ == "__main__":
    mail_test()
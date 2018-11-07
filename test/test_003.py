from context import WosUserInterface as sp
from context import ResponseEntity

if __name__=='__main__':
    res = ResponseEntity(resCode="200", rsMsg="test", payload={"data":"test"})

    print(res.returnResponse())    

    
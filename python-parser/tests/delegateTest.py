def delegate(arg: str):
    print("deligate test: %s", arg)

class DelegateTest():
    def __init__(self, delegate: classmethod):
        self._deligates = {
            'delegate_1': delegate
        }

    def delegate_1(self):
        self._deligates['delegate_1']("test arg")

if __name__ == '__main__':
    print(type(delegate))
    test_obj = DelegateTest(delegate)
    test_obj.delegate_1()
    
    
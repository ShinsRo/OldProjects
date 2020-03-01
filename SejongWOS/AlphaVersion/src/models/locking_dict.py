from collections.abc import MutableMapping
import threading

class LockingDict(MutableMapping):
    def __init__(self, *args, **kw):
        self._storage = dict(*args, **kw)
        if kw is not None:
            for key, value in kw.items():
                if key == 'lock':
                    self.lock = value
                
    def __getitem__(self, key):
        with self.lock:
            return self._storage[key]

    def __delitem__(self, key):
        with self.lock:
            del self._storage[key]

    def __setitem__(self, key, val):
        with self.lock:
            self._storage.__setitem__(key, val)

    def __iter__(self):
        with self.lock:
            return iter(self._storage)

    def __len__(self):
        with self.lock:
            return len(self._storage)    

    def values(self):
        temp = {}
        for key, value in self._storage.items():
            if not key is 'lock':
                temp[key] = value
        with self.lock:
            return temp.values()

    def items(self):
        with self.lock:
            return self._storage.items()

    def keys(self):
        with self.lock:
            return self._storage.keys()

    def setQid(self, qid):
        if 'qid' in self._storage.keys():
            return
        with self.lock:
            self._storage.__setitem__('qid', qid)

    def countAndGetQid(self):
        with self.lock:
            self._storage.__setitem__('qid', self._storage.__getitem__('qid') + 1)
            return self._storage.__getitem__('qid')
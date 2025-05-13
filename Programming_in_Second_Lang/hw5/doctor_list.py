class Doc_list():
    def __init__(self, db_list):
        self._doctors = db_list
        self._cur_val = -1
        self._max_val = len(db_list) - 1

    def __next__(self):

        if self._cur_val == self._max_val or self._cur_val == -1:
            self._cur_val = 0
            return self._doctors[0]

        self._cur_val += 1
        return self._doctors[self._cur_val]

    def prev(self):
        if self._cur_val == 0 or self._cur_val == -1:
            self._cur_val = self._max_val
            return self._doctors[self._cur_val]

        self._cur_val -= 1
        return self._doctors[self._cur_val]
    def search(self, key):
        for i in range(len(self._doctors)):
            if key == self._doctors[i].get_full_name():
                self._cur_val = i
                return self._doctors[i]

    def size(self):
        return len(self._doctors)
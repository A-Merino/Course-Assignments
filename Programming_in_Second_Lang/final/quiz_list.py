class Quiz_list():
    # creates an array, an index and a stopper index
    def __init__(self, q_list):
        self._questions = q_list
        self._cur_val = -1
        self._max_val = len(q_list) - 1

    # when next is called it returns the next item in list
    def __next__(self):
        self._cur_val += 1
        if self._cur_val > self._max_val:
            return None
        return self._questions[self._cur_val]

    # returns len of list
    def size(self):
        return len(self._questions)

    # gets current index
    def index(self):
        return self._cur_val

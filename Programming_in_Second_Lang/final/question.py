class Question:

    # Initializes answer and question
    def __init__(self):
        self._question_text = ""
        self._answer = ""

    def set_text(self, t):
        self._question_text = t.strip()

    def set_answer(self, at):
        self._answer = at.strip()

    # Function to check if the given answer is correct
    def check_answer(self, ans_t):
        if ans_t.strip() == self._answer:
            return True
        return False

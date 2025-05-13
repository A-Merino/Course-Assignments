from question import Question


class ChoiceQuestion(Question):
    # Calls Question class while also instanciating comments and choices
    def __init__(self):
        self._choices = []
        self._answer_comments = ""
        super().__init__()

    def add_choice(self, choice, corr):
        self._choices.append(choice)
        if corr:
            self.set_answer(choice)

    def set_answer_comments(self, comm):
        self._answer_comments = comm

    def display(self, layout):
        pass

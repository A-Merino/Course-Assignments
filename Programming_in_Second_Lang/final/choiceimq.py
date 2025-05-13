from choiceq import ChoiceQuestion


class ChoiceImageQuestion(ChoiceQuestion):
    # Calls form super and adds variable for image
    def __init__(self):
        self._img_bytes = None
        super().__init__()

    # Sets and reads image frome given file
    def set_image(self, img):
        self._img_bytes = open(img, "rb").read()

    def display(self, layout):
        pass
        
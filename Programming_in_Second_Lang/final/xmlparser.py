from lxml import etree
from choiceimq import ChoiceImageQuestion
from choiceq import ChoiceQuestion


class XMLParser():
    def __init__(self, xml_file):
        self._tree = etree.parse(xml_file)

    def parse_questions(self):
        # Creates a list for questions and finds all question tags
        quiz = []
        questions = self._tree.findall("question")

        for quest in questions:
            currQ = None
            # Determines if the question has an image and
            # creates certain question object based on that
            if quest.find("questionImage") is not None:
                currQ = ChoiceImageQuestion()
                currQ.set_image(quest.find("questionImage").attrib.get("path"))
            else:
                currQ = ChoiceQuestion()

            # adds all information needed from xml
            currQ.set_text(quest.find("questionText").text)
            currQ.set_answer_comments(quest.find("answerComments").text)
            choices = quest.findall("answer")
            for choice in choices:
                currQ.add_choice(choice.text, choice.attrib.get("correct"))
            quiz.append(currQ)

        return quiz

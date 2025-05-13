from xmlparser import XMLParser
from DMVDriverTestUI import DMVTestUI
from PyQt5 import QtWidgets
import sys
from start_screen import StartScreen


def main():

    # Calls xml parser and runs a Qt Application
    quiz_parse = XMLParser("florida_drivers_test.xml").parse_questions()
    app = QtWidgets.QApplication(sys.argv)

    # Creates and calls a new driver test window
    main_ui = DMVTestUI()
    main_ui.setupUi(main_ui, quiz_parse)

    # Calls the start screen from tme main window
    start_screen = StartScreen(main_ui)
    start_screen.show()

    sys.exit(app.exec_())


main()

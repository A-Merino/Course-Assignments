from doctor_popup import Doc_pop
from search_doctor import Main_ui
import sys
from doctor import Doctor
from PyQt5 import QtWidgets 
from doctor_list import Doc_list


def main():
    app = QtWidgets.QApplication(sys.argv)

    ui = Main_ui()
    ui.setupUi(ui)
    ui.show()


    sys.exit(app.exec_())


main()
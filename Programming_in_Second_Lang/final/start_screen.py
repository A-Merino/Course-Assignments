from PyQt5 import QtWidgets, QtCore


class StartScreen(QtWidgets.QWidget):
    def __init__(self, main_ui):
        super().__init__()
        self.main_ui = main_ui
        self.initUI()

    def initUI(self):
        self.setWindowTitle("Welcome to the DMV Driver's Test")
        self.setGeometry(100, 100, 600, 400)  # Adjust the size as needed

        # Styling
        self.setStyleSheet("background-color: #f0f0f0; font-size: 14px;")

        # Title Label
        title_label = QtWidgets.QLabel("Florida DMV Driver's Test", self)
        title_label.setAlignment(QtCore.Qt.AlignCenter)
        title_label.setStyleSheet("font-size: 24px; font-weight: bold; margin-bottom: 20px;")

        # Start Button
        start_button = QtWidgets.QPushButton("Start Test", self)
        start_button.setStyleSheet(
            "QPushButton { background-color: #4CAF50; color: white; padding: 10px; font-size: 18px; border: none; border-radius: 4px; }"
            "QPushButton:hover { background-color: #45a049; }")
        start_button.setFixedSize(200, 40)
        start_button.clicked.connect(self.startTest)

        # Layout
        layout = QtWidgets.QVBoxLayout()
        layout.addWidget(title_label)
        layout.addWidget(start_button, 0, QtCore.Qt.AlignCenter)
        self.setLayout(layout)

    def startTest(self):
        self.hide()
        self.main_ui.show()

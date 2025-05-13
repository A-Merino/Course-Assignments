"""
    CSE 2050 Homework 4
    Name: Alex Merino
    Email: amerino2022@my.fit.edu
    Description: UI to show pictures of dogs with their breed below
"""

from ezgraphics import GraphicsImage, GraphicsWindow


class DogUI():

    # Sets gap between photos with size of UI window
    # and calls to start window setup
    def __init__(self):
        self._margin = 30
        self._win_h = 700
        self._win_w = 1000
        self.setup_window()

    # Creates a Window and Canvas for the window along with title
    def setup_window(self):
        self._window = GraphicsWindow(self._win_w, self._win_h)
        # Hides the window so it can be shown when processing is finished
        self._window.hide()
        self._window.setTitle("Alex Merino Dog Viewer")
        self._canvas = self._window.canvas()

    # Takes a list of picture urls and iterates through it
    # displaying the picture and a caption
    def layout_ui(self, dogs):
        # position variables
        x = self._margin
        y = self._margin
        maxY = 0
        for i in range(12):
            # gets the image path from the folder
            pic = GraphicsImage(dogs[i].get_image())

            # checks if image can be placed on same line
            # if not then it is put below first row of images
            maxY = max(maxY, pic.height())
            if x + pic.width() > self._win_w:
                x = self._margin
                y = y + maxY + self._margin
                maxY = 0

            # displays image and calls function to print caption
            self._canvas.drawImage(x, y, pic)
            self.show_dog_breed(dogs[i], x, y + pic.height() + 3)
            # moves position along x-axis of window for next picture
            x = x + pic.width() + self._margin

            
        self._window.show()
        self._window.wait()

    # prints the breed of the dog at a position specified when called
    def show_dog_breed(self, dog, x, y):
        self._canvas.drawText(x, y, " ".join(("Breed:", dog.get_breed_name())))

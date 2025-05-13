"""
    CSE 2050 Homework 4
    Name: Alex Merino
    Email: amerino2022@my.fit.edu
    Description: Dog object that stores breed, list of images and a description
"""

# imports
import urllib.request
import random
from PIL import Image


class Dog():

    # constructor for Dog object
    def __init__(self, breed, image_files, desc):
        self._breed = breed
        self._imgs = image_files
        self._description = desc
        self._img = None

    # returns dog breed
    def get_breed_name(self):
        return self._breed

    # returns full description of breed
    def get_description(self):
        return self._description

    def random_link(self):   
        return random.choice(self._imgs)

    # chooses a random image from list and returns
    def get_image(self):
        return self._img
        
    # Creates a .gif image and returns the path to the image
    def set_image(self, url):
        # retreives img data from url in parameter
        urllib.request.urlretrieve(url, "start.jpg")

        # Rescaling method given in homework for size
        scaled_width = 200
        img = Image.open("start.jpg")
        percent_width = (scaled_width / float(img.size[0]))
        h_size = int((float(img.size[1]) * float(percent_width)))
        img = img.resize((scaled_width, h_size), Image.BICUBIC)

        # Saves image with name of dog breed form url
        img.save("".join((url.split('/')[4], ".gif")))
        self._img = "".join((url.split('/')[4], ".gif"))

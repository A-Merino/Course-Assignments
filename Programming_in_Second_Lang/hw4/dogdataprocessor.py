"""
    CSE 2050 Homework 4
    Name: Alex Merino
    Email: amerino2022@my.fit.edu
    Description: Processes data from a json file
"""

import json
from dog import Dog


class DogDataProcessor():

    # Constructor for the file
    def __init__(self, json):
        self._dog_list = []
        self._file = json
        self.load_dogs(self._file, 50)

    # traverses through json file up until integer specified in parameter
    def load_dogs(self, path, num):

        with open(path, "r") as dogs_file:
            # creates a json dictionary and list of keys
            json_obj = json.load(dogs_file)
            all_dogs = list(json_obj.keys())

            for i in range(num):
                # selects a singular dog breed
                dog_items = json_obj[all_dogs[i]]
                if(isinstance(dog_items, dict)):
                    # creates Dog object and appends to list of Dogs
                    self._dog_list.append(Dog(all_dogs[i], dog_items.get("images"), dog_items.get("description")))
                    self._dog_list[i].set_image(self._dog_list[i].random_link())
                    # prints and updates progress bar
                    progress = "▓" * (i)
                    rest = "░" * (num - i)
                    print(f"Downloading {progress}{rest}", end="\r")
                

        # finishes the progress bar and shows the window until user closes it
        print("▓" * num, end="\r")


    # returns the list of Dog objects
    def get_dogs(self):
        return self._dog_list

    # prints out the list of Dog objects in a tabulated form
    def tabulate_dogs(self):
        count = 0
        for dog in self._dog_list:
            if count == 12:
                break
            print(f"{dog.get_breed_name():25}{dog.get_description().split('.')[0]}")
            count += 1
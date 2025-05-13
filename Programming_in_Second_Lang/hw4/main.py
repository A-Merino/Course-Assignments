"""
    CSE 2050 Homework 4
    Name: Alex Merino
    Email: amerino2022@my.fit.edu
    Description: Main method for homework 4 to run DogProcessor and DogUI
"""

# imports
from dogdataprocessor import DogDataProcessor
from dogui import DogUI


def main():
    # creates processor and ui, then calls tabulate function from processor
    process = DogDataProcessor("dog_breeds.json")
    gui = DogUI()
    gui.layout_ui(process._dog_list)
    process.tabulate_dogs()


main()

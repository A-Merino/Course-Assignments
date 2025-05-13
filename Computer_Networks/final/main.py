from graph import LinkState
from distanceVec import DistVec
import sys
from glob import glob
import os


def main():
    # Grab file from CLI
    file = str(sys.argv[1])

    # create folder for file
    if not os.path.exists('./' + file[:-4] + "/"):
        os.mkdir('./' + file[:-4] + "/")

    # Run Link State Algorithm
    linkState(file)
    print("Link State/Dijkstra's Algorithm/SPF Complete")
    # Run Distance Vector Algorithm
    distanceVector(file)
    print("Distance Vector Complete")


def linkState(file):
    # create folder path for Link State
    folderPath = './' + file[0:-4] + "/SPF/"

    # Create folder if it doesn't already exist
    if not os.path.exists(folderPath):
        os.mkdir(folderPath)
    else:
    # Remove all files from folder
        files = glob(''.join((folderPath, "*")))
        for f in files:
            os.remove(f)

    # Collect all inputs from the input file
    inputs = []
    with open(file, 'r') as f:
        for line in f:
            if line.strip() != "":
                # Clean input and save to array
                time, rest = line.split(":")
                node1, node2, dist = rest.replace(" ", "").replace("\n", "").split(",")
                inputs.append((int(time), node1, node2, int(dist)))

    # Collect the first time
    prevTime = inputs[0][0]
    cons = []
    for (time, n1, n2, dist) in inputs:

        # if the time is still the same for the input then 
        # add to list to send to Graph
        if time == prevTime:
            cons.append((n1, n2, dist))

        else:
            # if not then run the algorithm with all inputs so far
            # Older connections will be over written by newer ones in algorithm
            LinkState(cons, folderPath, file[:-4], prevTime)
            # Change time
            prevTime = time
            # Add new time connection to list
            cons.append((n1, n2, dist))
    # Run agorithm at the end of the file
    LinkState(cons, folderPath, file[:-4], prevTime)


def distanceVector(file):
    # All the same code as LinkState up to line 87
    folderPath = './' + file[0:-4] + "/DV/"

    if not os.path.exists(folderPath):
        os.mkdir(folderPath)
    else:
        files = glob(''.join((folderPath, "*")))
        for f in files:
            os.remove(f)

    inputs = []
    with open(file, 'r') as f:
        for line in f:
            if line.strip() != "":
                time, rest = line.split(":")
                node1, node2, dist = rest.replace(" ", "").replace("\n", "").split(",")
                inputs.append((int(time), node1, node2, int(dist)))

    # Initialize time, patience and an array to add to graph
    curTime = 0
    patience = 5
    add2nodes = []

    # Create graph
    graph = DistVec(folderPath, file[:-4])

    while patience != 0 and curTime <= 100:

        # Go through the inputs and add all connections
        # from current time and add to array
        for (time, n1, n2, dist) in inputs:
            if time != curTime:
                continue
            add2nodes.append((n1, n2, dist))
        # Check if it is a calculate or share step
        if curTime % 2 != 0:
            # Update Calculations
            graph.calculate()
            add2nodes = []  # Empty array
        else:
            # share with neighbors
            graph.shareInfo(add2nodes)

        # Check if there is a change in the graph
        if graph.changeInGraph():
            # Reset patience if so
            patience = 5
        else:
            # Take away one if not
            patience -= 1

        graph.write2File(curTime)  # Write to file
        curTime += 1


main()

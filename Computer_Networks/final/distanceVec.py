import os


class DistVec:

    # Initialize object
    def __init__(self, folder, file):
        self.size = 0
        self.nodes = []
        self.first = True
        self.changed = False
        self.folder = folder
        self.fn = file

    def shareInfo(self, information):
        '''
            Collects changes in the graph and send
            the information to the nodes

            Parameters:
                - information
                    - An array of tuples containing:
                        - Name of first node
                        - Name of second node
                        - Distance between nodes
        '''

        # Go through each piece of information
        for (n1, n2, dist) in information:
            # add to graph
            self.add(n1, n2, dist)

        # If this is the first time called (Time step 0 essentially)
        if self.first:
            self.first = False
            # Relay information of graph to all nodes
            # for writing to file purposes
            self.relayInfo()

        # Check if the graph has changed
        for node in self.nodes:
            node.talk2Neighbors()
            if node.wasChanged():
                self.changed = True

    # Return bool to see if graph changed
    # Set bool to false before returning
    def changeInGraph(self):
        out = self.changed
        self.changed = False
        return out

    def write2File(self, time):
        for node in self.nodes:
            node.dvw(time + 1, self.folder, self.fn)

            if time % 2 != 0:
                node.others = []

    def calculate(self):
        '''
            Goes through each node in the graph and
            calls the updateDist function in each node
        '''
        for node in self.nodes:
            node.updateDist()

    # Function to add Nodes to a graph
    def add(self, n1, n2, dist):

        # If both nodes are in the graph
        if self.inGraph(n1) and self.inGraph(n2):
            # Find both nodes
            node1 = self.findNode(n1)
            node2 = self.findNode(n2)
            # Check if they are already connected
            if node1.alrCon(node2):
                # Change the distance in both nodes
                node1.changeDist(node2, dist)
                node2.changeDist(node1, dist)
            else:
                # Add a connection to each other if not
                node1.addCon(node2, dist)
                node2.addCon(node1, dist)

        # If names given are not in the graph already
        # then create new Node and increase the size
        else:
            if not self.inGraph(n1):
                self.nodes.append(DVNode(n1))
                self.size += 1
            if not self.inGraph(n2):
                self.nodes.append(DVNode(n2))
                self.size += 1

            # Call Node obj function to add a connection to each other
            node1 = self.findNode(n1)
            node2 = self.findNode(n2)
            node1.addCon(node2, dist)
            node2.addCon(node1, dist)

    # Function to check if a Node is in the graph already
    def inGraph(self, node):
        for n in self.nodes:
            if node == n.name:
                return True

        return False

    # Return node that has same name as parameter
    def findNode(self, name):
        for n in self.nodes:
            if name == n.name:
                return n

    def relayInfo(self):
        # For each node
        for node in self.nodes:

            # Go through every node again
            for n in self.nodes:
                # if the same node do nothing
                if node.name == n.name:
                    continue
                # Add to nodes' list then set to "infinity"
                else:
                    node.raddCon(n, 10e10)
        # Writes to file for timeStep 0 to match
        # given example files
        self.write2File(-1)

    def __sizeof__(self):
        return self.size

    def __str__(self):
        out = ''
        for n in self.nodes:
            out += ''.join((str(n), "\n"))

        return out


class DVNode:
    '''
        A class which represents a Node in a graph that employs
        a Distance Vector Algorithm
    '''

    def __init__(self, name):
        self.name = name
        # List of all direct connections
        self.dirCon = []
        self.dirCon.append({'node': self, 'dist': 0, 'next': self, 'dir': True})
        # List of nodes to send to other nodes
        self.send = []
        self.send.append({'node': self, 'dist': 0, 'dir': True})
        # List of nodes to show distance
        self.distList = []
        self.distList.append({'node': self, 'dist': 0, 'next': self})
        self.others = []
        self.prevOthers = []
        self.mod = False
        self.first = True

    def addCon(self, node, dist):
        '''
            Adds a connection
        '''
        self.dirCon.append({'node': node, 'dist': dist, 'next': node, 'dir': True})

    def raddCon(self, node, dist):
        '''
            Adds a connection which dist is set to infinity
        '''
        self.send.append({'node': node, 'dist': dist, 'dir': False})
        self.distList.append({'node': node, 'dist': dist, 'next': None})

    def __str__(self):
        out = ' '.join(("Node", self.name, "Connections:\n"))
        for con in self.distList:
            out += ''.join(("Node ", con['node'].name, ":\n\tDistance: ", str(con['dist']),
                "\nNext: ", self.strNext(con['next']), "\n"))

        return out

    def strNext(self, node):
        '''
            Prints the next Node name / N if none
        '''
        if node is None:
            return "N"
        return node.name

    def alrCon(self, other):
        '''
            Check if node is already connected
        '''
        for node in self.dirCon:
            if other.name == node['node'].name:
                return True
        return False

    def getDist(self, node):
        '''
            Return a Node from the current Distance List
        '''
        for n in self.distList:
            if node.name == n['node'].name:
                return n

    def checkDL(self):
        '''
            Check if the current Dist List is still true
        '''

        # For each node in the Dist List
        for dl in self.distList:
            # if self, then skip
            if dl['node'].name == self.name or dl['next'] is None:
                continue
            # get the direct distance to the connected node
            dirDistNext = self.getDirDist(dl['next'])
            # for each connection recieved in the connected node
            for g in self.getCons(dl['next']):
                # if the node in connection matches the current node
                if g['node'].name == dl['node'].name:
                    # check if the distances match
                    if dirDistNext + g['dist'] != dl['dist']:
                        self.mod = True
                        # if not then reset to infinity
                        dl['dist'] = 10e10
                        dl['next'] = None

    def updateDist(self):
        '''
            Called from the calculate() function
            in DistVec class. Calls CheckDL()
        '''
        self.checkDL()

        # Go through each direct connection
        for dc in self.dirCon:
            # If self then skip since dist will always be 0
            if dc['node'].name == self.name:
                continue
            # get the direct distance to connection
            dirDist = dc['dist']

            # for each node sent by the direct connection
            for con in self.getCons(dc['node']):
                """
                    dlItem comes from the Distance List, which holds:
                    - The node in the graph
                    - The distance from this node to that node
                    - The node of the next jump (Which will always be connected
                        to this node)

                    The dlItem node that is collect is the one that matches
                    the node being checked currently

                    EX:
                    Say we are running the function in Node A, 'dc' is Node B
                    currently,
                    and the 'con' variable is (Node G, Distance from B to G)
                        - 'con' is from the perspective of Node B

                    'dirDist' will be the distance between A and B
                    'dlItem' will be (Node G, distance from A to G, Next Node)

                    We then check if dirDist + con['dist'] < dlItem['dist']

                """
                dlItem = self.getDist(con['node'])
                if dirDist + con['dist'] < dlItem['dist']:
                    # If less, then update distance and next hop
                    self.mod = True
                    dlItem['dist'] = dirDist + con['dist']
                    dlItem['next'] = dc['node']
                    self.updateSend(dirDist + con['dist'], con['node'])

    def getDirDist(self, node):
        """
            Look through direct connections and
            return the distance between self and
            parameter node
        """
        for n in self.dirCon:
            if node.name == n['node'].name:
                return n['dist']

    def updateSend(self, newDist, node):
        '''
            Goes through each node in the send list
            and updates the distance once the node
            parameter matches the node in the send list

            Parameters:
                newDist: The updated distance between this node and 'node'
                        parameter
                node: Node in send list we are checking against to update
        '''
        for n in self.send:
            if n['node'].name == node.name:
                n['dist'] = newDist

    def getCons(self, node):
        '''
            Goes through each node received from
            talking to neighbors in the graph.
            When it matches the parameter node,
            we return the connection

            Parameters:
                node: The node in which we want to return the connections
                        to that node
        '''
        name = node.name
        for f in self.others:
            if f['node'].name == name:
                return f['cons']

    def getCon(self, node):
        '''
            Go through each direct connection and
            return the connection which matches the
            parameter node
        '''
        for conn in self.dirCon:
            if conn['node'].name == node.name:
                return conn

    def alSort(self):
        '''
            Function which implements insertion sort
            for the 'distList' variable that sorts
            by distance
        '''
        cons = self.distList
        for i in range(len(cons)):
            ind = i
            for j in range(i, len(cons[i:]) + i):
                if cons[j]['node'].name < cons[ind]['node'].name:
                    ind = j

            temp = cons[ind]
            cons[ind] = cons[i]
            cons[i] = temp
        return cons

    def talk2Neighbors(self):
        '''
            Goes through each connection to a neighbor
            in the graph and sends everything this node
            knows about the graph
        '''
        for neigh in self.dirCon:
            if neigh['node'].name != self.name:
                neigh['node'].recieve(self, self.send.copy())

    def recieve(self, node, connections):
        '''
            Function that takes in information from neighbors
            in the graph and saves to 'others' list

            Parameters:
                node: The node of where the information came from
                connections: The list of information ('send' list)
                             from said node

        '''
        na = []
        for c in connections:
            na.append({'node': c['node'], 'dist': c['dist'], 'dir': True})
        self.others.append({'node': node, 'cons': na})

    def wasChanged(self):
        '''
            Returns the 'mod' variable, which tells whether
            the node was changed in the time step or not
        '''
        out = self.mod
        self.mod = False
        return out

    def changeDist(self, node, newDist):
        '''
            Function that changes the distance between
            this node and a given node
        '''
        # Get the connection you want to update
        updatee = self.getCon(node)
        updatee['dist'] = newDist

        # Send the changes to other variables
        self.updateSend(newDist, node)
        self.changeDL(newDist, node)

    def changeDL(self, newDist, node):
        '''
            Looks through the 'distList' list and updates
            the distance to the node
        '''
        for n in self.distList:
            if n['node'].name == node.name:
                self.mod = True
                n['dist'] = newDist
                n['next'] = node

    def dvw(self, time, fp, fn):
        '''
            'Distance Vector Write'
            Writes the node information to file
        '''
        dists = self.alSort()  # Sort distances by name alphabetically
        # Saves last other list so it doesn't get overwritten
        self.prevOthers = self.others.copy()

        fileName = ''.join((fp, fn, "_DV_", self.name, '.txt'))

        # Write to file
        if not os.path.exists(fileName):
            with open(fileName, 'w') as file:

                for node in dists:
                    file.write(f"{time}\t{node['node'].name}\t{self.strNext(node['next'])}\t{self.checkDist(node['dist'])}\t|{self.getOther(node['node'])}\n")
                    # print(f"{time}\t{node['node'].name}\t{self.strNext(node['next'])}\t{self.checkDist(node['dist'])}\t|{self.getOther(node['node'])}")
                file.write('\n')

        else:
            with open(fileName, 'a') as file:

                for node in dists:
                    file.write(f"{time}\t{node['node'].name}\t{self.strNext(node['next'])}\t{self.checkDist(node['dist'])}\t|{self.getOther(node['node'])}\n")
                    # print(f"{time}\t{node['node'].name}\t{self.strNext(node['next'])}\t{self.checkDist(node['dist'])}\t|{self.getOther(node['node'])}")
                file.write('\n')

    def checkDist(self, dist):
        '''
            If the distance is 'infinity', then return 'N'
        '''
        if dist >= 10e10:
            return "N"
        return str(dist)

    def getOther(self, node):
        '''
            Returns the information from one singular node
            in a format which complies with assignment description
        '''

        # if returning self then call other function to return
        if node.name == self.name:
            return self.pss()
        # If first time then save others to previous
        if self.first:
            self.first = False
            self.prevOthers = self.others.copy()

        # Go through list and print it's connections
        for rec in self.prevOthers:
            if rec['node'].name == node.name:
                # print(rec)
                return self.printOther(rec)
        return "\tN" * len(self.distList)

    def pss(self):
        '''
            Goes through each connection, sorted by name,
            and prints the distance to them
        '''
        out = ''
        for s in self.acSort(self.send):
            if s['dist'] >= 10e10:
                out += "\tN"
            else:
                out += "\t" + str(s['dist'])
        return out

    def printOther(self, info):
        '''
            Given a connection, then print the distances
            that the node on the other side of the connection
            knows
        '''
        out = ""
        for node in self.acSort(info['cons']):
            # print(node)
            if node['dist'] >= 10e10:
                out += "\tN"
            else:
                out += "\t" + str(node['dist'])
        return out

    def acSort(self, thing):
        '''
            Insertion sort for connections based on node name, alphabetically
        '''
        cons = thing.copy()
        for i in range(len(cons)):
            ind = i
            for j in range(i, len(cons[i:]) + i):
                if cons[j]['node'].name < cons[ind]['node'].name:
                    ind = j

            temp = cons[ind]
            cons[ind] = cons[i]
            cons[i] = temp
        return cons

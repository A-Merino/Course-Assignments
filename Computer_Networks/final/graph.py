import os


class LinkState:

    # Initialize object
    def __init__(self, connections, folder, file, time):
        self.size = 0
        self.nodes = []
        self.first = True
        self.folder = folder
        self.fn = file
        self.time = time

        # Goes through each bit of information and add to graph
        for n1, n2, dist in connections:
            self.add(n1, n2, dist)
        # Complete rest of pipeline
        self.relayInfo()
        self.findPaths()
        self.write2file()

    def add(self, n1, n2, dist):
        '''Function to add Nodes to a graph'''

        # If names given are not in the graph already
        # then create new Node and increase the size
        if not self.inGraph(n1):
            self.nodes.append(Node(n1))
            self.size += 1
        if not self.inGraph(n2):
            self.nodes.append(Node(n2))
            self.size += 1

        # Call Node obj function to add a connection to each other
        node1 = self.findNode(n1)
        node2 = self.findNode(n2)
        node1.addCon(node2, dist)
        node2.addCon(node1, dist)

    def relayInfo(self):
        # For each node
        for node in self.nodes:

            # Go through every node again
            for n in self.nodes:
                # if the same node do nothing
                if node.name == n.name:
                    continue
                # if already connected then do nothing
                elif node.alrCon(n):
                    continue
                # if not connected then set to "infinity"
                else:
                    node.raddCon(n, 10e10)
        self.first = False

    def findPaths(self):
        '''
            Funciton that implements a for-loop
            which calls Dijkstra's for each node
        '''

        for node in self.nodes:
            self.shortestPath(node)

    def shortestPath(self, node):
        '''Function that implements Dijkstra's'''
        # Create a queue of all nodes in graph
        queue = self.nodes.copy()
        while len(queue) != 0:
            # Grabs the closest node that is not in the cloud
            # essentially adds it to the cloud
            curNode = self.getClosest(queue, node)
            # Get the distance between starting node and current node
            curDist = node.getDist(curNode)

            # Look at each connection in the closest node
            for edge in curNode.conSort():

                # if not a connection already checked
                if edge['node'] in queue and edge['dir']:

                    # if the distance from starting node + distance to new connection
                    # is less than the current distance from starting to new then modify it
                    if curDist + edge['dist'] < node.getDist(edge['node']):
                        if len(edge['path']) > 1:
                            node.updateDist(edge['node'], edge['dist'] + curDist, curNode, True, edge['path'])
                        else:
                            node.updateDist(edge['node'], edge['dist'] + curDist, curNode, False)

            # Remove node from queue
            ind = queue.index(curNode)
            queue.pop(ind)

    def getClosest(self, queue, node):
        closest = None
        # Return self if it is the first iteration
        if len(queue) == self.size:
            return node
        else:
            # Go through each connection of the node
            for edge in node.conSort():
                # Check if connection is in quque
                if self.inQueue(edge, queue):

                    # For first, it is considered closest
                    if closest is None:
                        closest = edge
                    # if current connection is closer than previous
                    elif edge["dist"] < closest["dist"]:
                        closest = edge
        # Return the node from the closest connection
        return closest['node']

    def inQueue(self, edge, queue):
        '''Function to each if a node is in the queue'''
        # Go through each node check it against given node
        for node in queue:
            # Return true if it is in the queue
            if node.name == edge['node'].name:
                return True

        # Return false if not in queue
        return False

    def inGraph(self, node):
        '''Function to check if a Node is in the graph already'''
        for n in self.nodes:
            if node == n.name:
                return True
        return False

    def findNode(self, name):
        '''Return node that has same name as parameter'''
        for n in self.nodes:
            if name == n.name:
                return n

    def write2file(self):
        '''Allows each node in the graph to write to file'''
        for node in self.nodes:

            fileName = ''.join((self.folder, self.fn, "_SPF_", node.name, '.txt'))

            if not os.path.exists(fileName):
                with open(fileName, "w") as file:
                    file.write(node.spf50(fileName, self.time))
            else:
                with open(fileName, 'a') as file:
                    file.write(node.spf50(fileName, self.time))

    def __sizeof__(self):
        return self.size

    def __str__(self):
        out = ''
        for n in self.nodes:
            out += ''.join((str(n), "\n"))

        return out


class Node:

    def __init__(self, name):
        self.name = name
        self.connections = []
        self.connections.append({'node': self, 'dist': 0, 'path': [self], 'dir': True})
        self.visited = False
        self.og = None

    def addCon(self, node, dist):
        '''
            Adds a connection to the node. If the the conneciton already
            exists then remove it and add it back to graph
        '''
        if self.alrCon(node):
            conn = self.getCon(node)
            self.connections.remove(conn)
        self.connections.append({'node': node, 'dist': dist, 'path': [node], 'dir': True})

    def raddCon(self, node, dist):
        '''
            Adds a 'connection' to the node, which the node knows it is not 
            directly connected. Similar to addCon()
        '''
        self.connections.append({'node': node, 'dist': dist, 'path': [node], 'dir': False})

    def updateDist(self, node, newDist, through, isList, edge=None):
        '''
            Function which updates the distance and path
            of a connection

            Parameters:
                node: the node we want to update
                newDist: the new distance
                through: the node just added to the cloud in this step
                isList: Boolean to say if the path to 'through' is larger
                        than 1
                edge: The path of 'through' when it is larger than 1
        '''

        # Find the node we want to update in connections list
        for n in self.connections:
            if n['node'].name == node.name:
                # Update distance
                n['dist'] = newDist

                # Get the connection to 'through' and collect it's path
                conn = self.getCon(through)
                path = conn['path']
                newPath = []

                # Go through the path
                for p in path:
                    # Add the path to an array
                    newPath.append(p)

                # If 'through' path is more than one
                if isList:
                    # Add the path to the array
                    for e in edge:
                        newPath.append(e)
                else:
                    # Add just the node to path if not
                    newPath.append(node)
                n['path'] = newPath
                return

    def alrCon(self, other):
        '''Checks if node is already connected'''
        for node in self.connections:
            if other.name == node['node'].name:
                return True

        return False

    def getDist(self, node):
        '''Get the distance from this node to given node'''
        for n in self.connections:
            if n['node'].name == node.name:
                return n['dist']
        # Return -1 if not in graph
        # (which shouldn't happen theoretically if all nodes are known)
        return -1

    def getCon(self, node):
        '''
            Get a connection from list given a node
        '''
        for conn in self.connections:
            if conn['node'].name == node.name:
                return conn


    def conSort(self):
        '''Insertion sort by distance'''
        cons = self.connections.copy()
        for i in range(len(cons)):
            ind = i
            for j in range(i, len(cons[i:])+i):
                if cons[j]['dist'] < cons[ind]['dist']:
                    ind = j

            temp = cons[ind]
            cons[ind] = cons[i]
            cons[i] = temp 
        return cons

    def spf50(self, fn, time):
        '''
            spf50 = Shortest Path first, then 50 since spf is also sunscreen

            Returns the information about the node in a manner consistent with
            the assignment description
        '''
        out  = ''
        for con in self.conSort():
            out += ''.join((str(time), '\t|\t',con['node'].name, "\t", str(con['dist']), '\t', self.strPath(con['path']),"\n"))
        out += '\n'
        return out

    def strPath(self, path):
        '''
            Given a path of nodes, it writes the path to string
        '''
        out = ''
        for n in path:
            out += ''.join((n.name, ", "))

        return out[:-2]

    def __str__(self):
        out = ' '.join(("Node", self.name, "Connections:\n"))
        for con in self.connections:
            out += ''.join(("Node ", con['node'].name, ":\n\tDistance: ", str(con['dist']), 
                    "\n\tPath: ", self.strPath(con['path']), "\n"))
        return out

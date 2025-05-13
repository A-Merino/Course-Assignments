import java.util.ArrayList;

public class Graph {


   /*
      Class variables
         size = holds the size of the graph when initialized
         graph = the list of verticies in the graph
         queue = linkedQueue which holds paths for breadth-first search
   */
   private int size;
   private ArrayList<GraphNode> graph = new ArrayList<GraphNode>();
   private LinkedQueue<ArrayList<GraphNode>> queue = new LinkedQueue<>();


   // Class constructor 
   public Graph(int s) {
      size = s;
   }


   public String addFriend(String f, String s) {

      // Finds the users node in the graph
      GraphNode first = findUser(f);
      GraphNode second = findUser(s);

      /*
         If either of the users are not already in the graph then
            add a new node to the graph with the user name
            sort the graph
            ensure node named "first / second" is set to new node
      */ 
      if (first == null) {
         graph.add(new GraphNode(f));
         graph.sort(new NodeComp());
         first = findUser(f);
      } 

      if (second == null) {
         graph.add(new GraphNode(s));
         graph.sort(new NodeComp());
         second = findUser(s);
      }

      // calls method to check if the two nodes are already connected
      // if they are then return error
      if (curFriend(first, s)) {
         return "ExistingFriendshipError";
      }

      // Add the nodes to list of edges of each and sort
      first.edges.add(second);
      first.edges.sort(new NodeComp());
      second.edges.add(first);
      second.edges.sort(new NodeComp());

      return f + " " + s;
   }



   public String removeFriend(String f, String s) {

      // Finds nodes of each user
      GraphNode first = findUser(f);
      GraphNode second = findUser(s);

      // if they are already not friends return error
      if (!curFriend(first, s)) {
         return "NoFriendshipError";
      }

      //remove each other from the edges list of each node
      first.edges.remove(second);
      second.edges.remove(first);


      return f + " " + s;
   }

   // Method which goes through each node of the edges list of a node to see if they are friends
   public boolean curFriend (GraphNode s, String o) {
      for (GraphNode e : s.edges) {
         if (o.equals(e.getPerson())) {
            return true;
         }
      }
      return false;      
   }




   public String wantFriend(String user, String target) {

      // sets up StringBuilder and finds nodes being used
      StringBuilder out = new StringBuilder();
      out.append(user + " " + target + "\n");
      GraphNode first = findUser(user);
      GraphNode second = findUser(target);

      // checks to see if they are already friends, if so returns error
      if (curFriend(first, target)) {
         return user + " " + target + "AlreadyAFriendError";
      }

      resetVis(); // resets each node to being unvisited

      // calls breadth-first search from first node until it reaches second
      ArrayList<GraphNode> path = bfs(first, second);

      // if there is no path possible
      if (path == null) {
         out.append("- Sorry, none of your friends can help introduce you to " + target);
         return out.toString();
      }

      // removes redundant user starting node
      path.remove(first);

      out.append("- Length of the shortest path: " + path.size() + ".\n");

      // if there are more than one users between two users then it is "intermediate"
      if (path.size() > 2) {
         out.append("- Your intermediate friend is " + path.get(0) + "\n");
      } else if (path.size() > 0) { // "mutual" if not
         out.append("- Your mutual friend is " + path.get(0) + ".\n");
         
      } 

      out.append("Path: " + user);

      // add each node in path to StringBuilder
      for (GraphNode d : path) {
         out.append(" " + d);
      }

      return out.toString();
   }


   // Finds the user node based on name specified in parameter
   private GraphNode findUser(String a) {
      for (GraphNode node : graph) {
         if (node.getPerson().equals(a)) {
            return node;
         }
      } 

      return null;
   }


   public ArrayList<GraphNode> bfs(GraphNode cur, GraphNode end) {

      // creates a list of nodes to hold current path search is on
      ArrayList<GraphNode> path = new ArrayList<>();

      // adds starting node to path and path to queue
      path.add(cur);
      queue.add(path);

      // until there is nothing left in the queue
      while (!queue.isEmpty()) {

         // take top path off and grab last node from path
         path = queue.remove();
         GraphNode v = path.get(path.size() - 1);

         // if the node is the node needed ot be found then end
         if (v.equals(end)) {
            return path;
         }

         // for all the edges that are not visited in the current node
         //    set visited to true
         //    create a copy of path list and add current node to list
         //    add that path to queue 
         for (GraphNode n : v.edges) {
            if (!n.getVis()) {
               n.setVis(true);
               ArrayList<GraphNode> next = new ArrayList<>(path);
               next.add(n);
               queue.add(next);
            }
         }
      }
      return null;
   }

   // method which returns all nodes visited variable to false
   private void resetVis() {
      for (GraphNode n : graph) {
         n.setVis(false);
      }
   }

}
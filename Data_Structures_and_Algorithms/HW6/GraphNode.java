import java.util.ArrayList;

public class GraphNode implements Comparable<GraphNode> {


   /*
      Class variables:
         person = The name of the user
         edges = A List of all other nodes that this current node is connected to 
         vis = boolean variable that is mutable for bfs search
   */
   private String person;
   public ArrayList<GraphNode> edges = new ArrayList<>();
   private boolean vis = false;


   // Node constructor that sets up user name
   public GraphNode (String p) {
      person = p;
   }

   // Getter methods for user name, edges, and if visited
   public String getPerson() {
      return person;
   }

   public ArrayList<GraphNode> getEdges() {
      return edges;
   }

   public boolean getVis() {
      return vis;
   }

   // Setter method for vis to change if visited or not
   public void setVis(boolean b) {
      vis = b;
   }


   // compareTo method that is used by NodeComp to sort edges alphabetically
   public int compareTo(GraphNode o) {
      if (person.compareTo(o.person) > 0) {
         return 1;
      } else if (person.compareTo(o.person) < 0) {
         return -1;
      } else {
         return 0;
      }
   }

   // toString method for printing
   public String toString() {
      return person;
   }
}
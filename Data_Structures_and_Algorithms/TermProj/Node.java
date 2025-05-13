import java.util.ArrayList;

// Node class that holds an arraylist of children
   public class Node {

      /*
         Class variables:
            parent = pointer to the parent of the Node
            sib = pointer to the next sibling of the Node
            children = ArrayList of pointers to the children of the Node
            name = Data of the Node
            amount = frequency it showed up in original text file
            wordEnd = determines whether the currednt node is the end of a query or not
            full = if end of the word then attach full word for quicker printing
            best = array of the words with the highest frequency below this current node
                   that have also not been used in parent nodes
      */
      public Node parent;
      public ArrayList<Node> children = new ArrayList<>();
      public String name;
      public int amount;
      public String full;
      public Node [] best = new Node[5];

      // constructor method of the Node class
      public Node(String n, int a, Node p) {
         name = n;
         amount = a;
         parent = p;
      }

      // returns the parent Node of the current Node
      public Node getParent() {
         return parent;
      }

      public String toString() {
         return name;
      }

      public int getAmount() {
         return amount;
      }

      public String getName() {
         return name;
      }

   }
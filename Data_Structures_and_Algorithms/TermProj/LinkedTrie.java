import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class LinkedTrie<E> {
   /*
      Class variables:
         root = the root of the tree, the highest ordered node
         finder = Node that stores the current Node being used by the findNode() method
   */
   public Node root = new Node(null, -1, null);
   public Node finder = new Node(null, -1, null);

   // Constructor for the LinkedTree
   public LinkedTrie () {

   }

   // method to add a Node as a child of a certain node
   public void add (String name, int value) {
      // starting at the root if the Trie
      Node cur = root;
      String sub = "";
      
      for (int i = 0; i < name.length(); i++) {
         // gather the current character of the string
         sub = name.substring(i, i+1);
         boolean found = false;   

         /*
            If the current node has no children
               add a new node with the current letter to the child
               set cur to node just created
            Else
               Go through each child and mark if current character is found
               if found set the cur not to that Node
         */
         if (cur.children == null) {
            cur.children.add(new Node(sub, 0, cur));
            cur = cur.children.get(0);
         } else {
            for (Node n : cur.children) {
               if (n.name.equals(sub)) {
                  cur = n;
                  found = true;
                  break;
               }
            }

            // if the leter is not found
            // add to children and set cur to that new Node

            if (!found) {
               cur.children.add(new Node(sub, 0, cur));
               cur = cur.children.get(cur.children.size() - 1);
            }
         }
         
      }
      // when last letter is reached, cur node is end of query so that and amount are added 
      cur.amount = value;
      cur.full = name;
      // use method to input word into best array of parent
      setBest(cur);
   }

   public void setBest(Node e) {
      if (e == null) {
         return;
      }

      /*
         Collect a list of parent nodes to iterate through  
      */
      Node cur = e.parent;
      ArrayList<Node> parList = new ArrayList<>();
      Node temp = null;

      while(cur != root) {
         parList.add(cur);
         cur = cur.parent;
      }

      /*
         for each parent starting at highest up tree
            for all Nodes of best array
               if the parent has a null entry then add current Node and end loop
               if current best Node frequency is less than current Node
                  replace with current node
                  start setBest with Node that was replaced 
      */
      boolean pres = false;
      for (int j = parList.size() - 1; j >= 0; j--) {      
         for (int i = 0; i < e.best.length; i++) {
            if (parList.get(j).best[i] == null) {
               parList.get(j).best[i] = e;
               pres = true;
               break;
            } else if (parList.get(j).best[i].amount < e.amount) {
               temp = parList.get(j).best[i];
               parList.get(j).best[i] = e;
               setBest(temp);
               
               pres = true;
               break;
            }
         }

         // if the current not is present then work is complete
         if (pres) {
            break;
         }
      }
   }

   public Node find(String q) {
      // since it must be first node cur is root
      Node cur = root;
      String sub = "";
      // go through length of string to find exact end node and return
      for (int i = 0; i < q.length(); i++) {
         sub = q.substring(i, i+1);
         for (Node n : cur.children) {
            if (n.name.substring(0,1).equals(sub)) {
               cur = n;
               break;
            }
         }
         
      }
      // set finder for quicker search later in word
      finder = cur;
      return cur;
   }

   public Node find(char c) {
      // using finder from overloaded method
      Node cur = finder;

      // find node that is child with correct name and set to cur 
      for (Node n : cur.children) {
         if (n.name.equals(Character.toString(c))) {
            cur = n;
            break;
         }
      }

      if (finder.equals(cur)) {
         finder = root;
         return null;
      }

      // finder is set to cur for quicker search
      finder = cur;
      return cur;
   }

   public String[] best(Node e) {
      // using full names from Nodes return best Node paths
      String[] out = new String[5];
      for (int i = 0; i < out.length; i++) {
         if (e.best[i] != null) {
            out[i] = e.best[i].full;
            
         }
      }

      return out;
   }

   // post order traversal through nodes to sort children
   public void startComp(Node e) {
      for (Node n : e.children) {
         startComp(n);    
         if (n.children == null) {
            break;
         }       
         Collections.sort(n.children, new QComp());
      }
   }


   // toString method for debugging
   public String toString() {
      StringBuilder out = new StringBuilder();
      ArrayList<Node> list = root.children;
      for (Node n : list) {
         out.append(n.name + " 1\n");

         for (Node m : n.children) {
            out.append(m.name + " 2\n");
         for (Node a : m.children) {
            out.append(a.name + " ");
         }
         out.append("3\n");
         }
      out.append("\n");
      }
      return out.toString();
   }


}



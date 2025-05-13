import java.util.ArrayList;
import java.util.List;

public class LinkedTrie3<E> {
   /*
      Class variables:
         emp = boolean that tells whether or not the tree is empty
         root = the root of the tree, the highest ordered node
         finder = Node that stores the current Node being used by the findNode() method
         aSub = ArrayList for all children of a particular Node 
   */
   private boolean emp = true;
   public Node root = new Node(null, -1, null);
   public Node finder = new Node(null, -1, null);
   private ArrayList<Node> press = new ArrayList<>();

   

   // Constructor for the LinkedTree
   public LinkedTrie3 () {

   }

   // method to add a Node as a child of a certain node
   public void add (String name, int value) {
      Node cur = root;
      String sub = "";
      for (int i = 0; i < name.length(); i++) {
         sub = name.substring(i, i+1);

         boolean found = false;   
         ;
         if (cur.children == null) {
            cur.children.add(new Node(sub, -1, cur));
            cur = cur.children.get(0);
         } else {
            for (Node n : cur.children) {
               if (n.name.equals(sub)) {
                  cur = n;
                  found = true;
                  break;
               }
            }

            if (!found) {
               cur.children.add(new Node(sub, -1, cur));
               cur = cur.children.get(cur.children.size() - 1);
            }
         }
         
      }
      cur.amount = value;
      setBest(cur);
   }

   public void setBest(Node e) {
      Node cur = e.parent;
      while(cur != root) {
         boolean pres = false;
         for (int i = 0; i < cur.best.length; i++) {
            if (cur.best[i] == null && !pres) {
               cur.best[i] = e;
               pres = true;
            } else if (cur.best[i] != null && cur.best[i].amount < e.amount && !pres) {
               cur.best[i] = e;
               pres = true;
            }
         }
         cur = cur.parent;
      }
   }

   public String createWord(Node start) {
      String out = start.name;
      start = start.parent;
      while(start != root) {
         out = start.name + out;
         start = start.parent;
      }

      return out;
   }

   public Node find(String q) {
      Node cur = root;
      String sub = "";

      for (int i = 0; i < q.length(); i++) {
         sub = q.substring(i, i+1);
         for (Node n : cur.children) {
            if (n.name.equals(sub)) {
               cur = n;
               break;
            }
         }
         
      }
      finder = cur;
      return cur;
   }

   public String[] best(Node e) {
      String[] out = new String[5];
      for (int i = 0; i < out.length; i++) {
         if (e.best[i] != null) {
            out[i] = createWord(e.best[i]);
            
         }
      }

      return out;
   }
   // public void compress(Node e) {
   //    System.out.println(e.name + " before");
   //    e.name = e.parent.name + e.name;
   //    System.out.println(e.name + " after");
   //    e.parent.parent.children.add(e);
   //    e.parent.parent.children.remove(e.parent);
   //    e.parent = e.parent.parent;
   //    compress(e);
      
   // }

   // public void startComp(Node e) {
   //    for (Node n : e.children) {
   //       if (n.parent != root && !n.parent.wordEnd && n.parent.children.size() == 1 && n.parent.children.get(0) == e) {
   //          press.add(n);
   //       }
   //       startComp(n);
   //    }

   //    while(press.size() != 0) {
   //       compress(press.get(0));
   //       press.remove(0);

   //    }
   // }



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



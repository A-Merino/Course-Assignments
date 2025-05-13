/*

   Author: Alex Merino
   Email: amerino2022@my.fit.edu
   Course: CSE 2010
   Section: Section E1
   Description of this file: Create a taxonomy tree and allow queries to
                             run through the taxonomy tree

*/

// Import classes that will be used in the program
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.ArrayList;

public class HW3 {



   /*
      LinkedTree object which allows for the creation and use of a tree
      using any object
   */
   static class LinkedTree <E> {
      /*
         Class variables:
            emp = boolean that tells whether or not the tree is empty
            root = the root of the tree, the highest ordered node
            finder = Node that stores the current Node being used by the findNode() method
            aSub = ArrayList for all children of a particular Node 
      */
      private boolean emp = true;
      private Node<E> root = new Node<E>(null, null, null, null);
      private Node<E> finder = new Node<E>(null, null, null, null);
      private ArrayList<String> aSub = new ArrayList<String>();

      // Nested Node class
      private static class Node<E> {

         /*
            Class variables:
               parent = pointer to the parent of the Node
               sib = pointer to the next sibling of the Node
               child = pointer to the parent of the Node
               name = Data of the Node
         */
         private Node<E> parent;
         private Node<E> sib;
         private Node<E> child;
         private E name;

         // constructor method of the Node class
         public Node(E n, Node<E> p, Node<E> s, Node<E> c) {
            name = n;
            parent = p;
            sib = s;
            child = c;
         }

         // returns the parent Node of the current Node
         public Node<E> getParent() {
            return parent;
         }

      }

      // Constructor for the LinkedTree
      public LinkedTree () {
         
      }

      // starting case for findNode method
      public void findNode (E find) {
         // if the Node being found is the root then the one currently being found
         if (root.name.equals(find)) {
            finder = root;
         }
         else {
            // if not the root then recursively call method
            findNode(root, find);
         }
      }

      // Overloaded method for findNode()
      public void findNode(Node<E> node, E find) {
         Node<E> cur = node;
         // If the current node is the node needed to be found it is finder
         if (cur.name.equals(find)) {
            finder = cur; 
         } 
         // s
         if (cur.sib != null) {
            findNode(cur.sib, find);
         } if (cur.child != null) {
            findNode(cur.child, find);
         }
      }

      // method to add a Node as a child of a certain node
      public void add (E sup, E cur) {
         // finds the parent of the node being found
         findNode(sup);
         Node<E> high = finder;

         // if the parent doesn't have a child then
         // the new Node is the direct child of the parent
         if (high.child == null) {
            high.child = new Node<E>(cur, high, null, null);

         /*
            Since the tree is Linked in such a way that 
            there is only one child of the parent, the 
            new child must be a sibling of the first child
            so the else statement loops until there is not a 
            sibling of the current child 
         */
         } else {
            high = high.child;
            while (true) {
               if (high.sib == null) {
                  high.sib = new Node<E>(cur, high.parent, null, null);
                  break;
               }
               high = high.sib;
            }
         }
      }

      // Overloaded add method to add the root of the tree
      public void add (E e) {
         root.name = e;
         emp = false;
      }

      // returns if the tree is empty or has
      // at least one Node
      public boolean isEmpty() {
         if (emp) {
            return true;
         }
         return false;
      }


      /*
         Method that finds and returns the parent
         of the Node. First finds the location of
         the Node in the tree then if it is the root
         return nothing since it doesn't have a parent.
         If not then return the name of the parent node
      */
      public String dirSup (E e) {
         findNode(e);
         Node<E> cur = finder;

         if (cur.name.equals(root.name)) {
            return "";
         } else {
            return cur.parent.name.toString();
         }
      }


      /*
         This method returns all children of 
         the parameter node as a String array
      */
      public String[] dirSub (E e) {
         // Finds the curent Node and initializes a variable
         findNode(e);
         Node<E> cur = finder;
         String all = "";

         // If there are no children then return nothing
         if (cur.child == null) {
            return new String[] {};
         }

         /*
            If there are children then add the first child to the
            String variable and the current Node is the child
         */
         all += cur.child.name.toString() + " ";
         cur = cur.child;

         // As long as the sibling isn't null then add it to string
         while (cur.sib != null) {
             all += cur.sib.name.toString() + " ";
             cur = cur.sib;
          } 

         // turn string into array and return array
         String [] subs = all.split(" ");
         return subs;

      }

      
      
      // Finds every super category of the parameter
      public String[] allSup (E e) {
         // Finds node and initializes String variable
         findNode(e);
         Node<E> cur = finder;
         String all = "";

         // if root then return nothing
         if (cur.parent == null) {
            return new String[] {} ;
         }
         // if not then add parent to out String
         // and current node is now parent
         all += cur.parent.name.toString() + " ";
         cur = cur.parent;

         // Until the root Node add Node name to out String
         while (cur.parent != null) {
             all += cur.parent.name.toString() + " ";
             cur = cur.parent;
          } 

         // Turn String into String array and return array
         String [] sups = all.split(" ");
         return sups;
      }


      /*
         Method that finds every child of the 
         parameter
      */
      public Object[] allSub (E e) {
         // Use parameter to find current Node
         findNode(e);
         Node<E> cur = finder;

         // If a leaf Node then return nothing
         if (cur.child == null) {
            return new String[] {};
         }

         // Ensure that class ArrayList is empty and
         // adds the first child to the list and current is now child
         aSub.removeAll(aSub);
         aSub.add(cur.child.name.toString());
         cur = cur.child;

         //Run preOrd method, create Object array from ArrayList, and return array
         preOrd(cur);
         Object [] allB = aSub.toArray();
         return allB;
      }


      /*
         Recursive method that finds every child of a specific Node
      */
      public void preOrd(Node<E> e) {
         // Initializes variable that checks if the Node has been added already
         boolean put = false;
         // For loop to check if Node has been added
         for (String el : aSub) {
            if (el.equals(e.name)) {
               put = true;
            }
         }
         // If it hasn't then add
         if (!put) {
            aSub.add(e.name.toString());
            
         }

         /*
            If the current Node has a child and/or a sibling
            then recursively call method to go through every Node
         */
         if (e.child != null) {
            preOrd(e.child);
         }
         if (e.sib != null) {
            preOrd(e.sib);
         }
      }

      // method that finds amount of supercategories of current Node
      public int allSupNum (E e) {
            // finds Node
            findNode(e);
            Node<E> cur = finder;

            // increments count variable until the current Node is the root
            int count = 0;
            while (cur.parent != null) {
               count++;
               cur = cur.parent;
             } 

            return count;
      }

      /*
         Method to find number of Subcategories
         of a Node. Calls the allSub method to
         fill class ArrayList and returns the value 
      */
      public int allSubNum (E e) {
            allSub(e);
            return aSub.size();
      }


      /*
         Method to check if the Node given is a child
         of the comparing node
      */
      public String isSup (E e, E comp) {
         // finds Node then current becomes parent
         findNode(e);
         Node<E> cur = finder;
         cur = cur.parent;

         /*
            Checks Node and if it is a child then
            boolean variable is true, if not then
            it stays false
         */
         boolean found = false;
         if (cur.name.equals(comp)) {
            found = true;
         }
         while (cur.parent != null) {
            if (cur.name.equals(comp)) {
               found = true;
            }
             cur = cur.parent;
          } 

         // if true return yes, if not return no
         if (found) {
            return " yes";    
         }
         return " no";
      }


      // Checks to see if the Node being compared to is 
      // a child of the first Node
      public String isSub (E e, E comp) {
         // Calls allSub method to fill ArrayList
         allSub(e);

         /*
            Goes through every element of the ArrayList to find if
            the comp element is in the ArrayList 
         */
         boolean found = false;
         for (String sub : aSub) {
            if (sub.equals(comp)) {
               found = true;
            }
         }
         // Return yes if it is a subcategory, return no if not
         if (found) {
            return " yes";    
         }
         return " no";
      }

      // Method to find the closest common super category
      public String closeComSup (E f, E s) {
         // colect an array of all the super categories of both Nodes
         String [] first = allSup(f);
         String [] second = allSup(s);
         for (int i = 0; i < first.length; i++) {
            for (int j = 0; j < second.length; j++) {
               if (first[i].equals(second[j])) {
                  return first[i];
               }
            }
         }
         // return nothing if no super category, which is impossible
         return "";

      }

         
   }




   // Main method to collect input and distribute query functions 
   public static void main(String[] args) throws FileNotFoundException {
      // Initialize Scanner and File variables for both input files
      File in1 = new File(args[0]);
      File queries = new File(args[1]);
      Scanner tScan = new Scanner(in1, "US-ASCII");
      Scanner qScan = new Scanner(queries, "US-ASCII");
      LinkedTree<String> tree = new LinkedTree<String>(); // LinkedTree

      while (tScan.hasNextLine()) {
         // Collects the line as a String then splits the string into an array
         String line = tScan.nextLine();
         String [] names = line.split(" ");
         // Since first word is the super category then make a super String and array with the rest
         String sup = names[0];
         String [] rest = Arrays.copyOfRange(names, 1, names.length);
         // sort the rest alphabetically
         Arrays.sort(rest);
         // if tree is empty then the first super is the root
         if (tree.isEmpty()) {
            tree.add(sup);
         }
         // add rest array to tree 
         for (int i = 0; i < rest.length; i++) {
            tree.add(sup, rest[i]);
         }

      }

      // while there is a line in the queries file collect it and form into an array 
      while (qScan.hasNextLine()) {
         String in = qScan.nextLine();
         String [] quest = in.split(" ");

         // Call the dirSup method 
         if (quest[0].equals("DirectSupercategory")) {
            System.out.println(quest[0] + " " + quest[1] + " " + tree.dirSup(quest[1]));
             
         } else if (quest[0].equals("DirectSubcategories")) {
            // Call the dirSub method 
            System.out.print(quest[0] + " " + quest[1] + " ");
            String [] dirSb = tree.dirSub(quest[1]);
            for (int k = 0; k < dirSb.length; k++) {
               System.out.print(dirSb[k] + " ");
            }
            System.out.println();
            
         } else if (quest[0].equals("AllSupercategories")) {
            // Call the allSup method 
            System.out.print(quest[0] + " " + quest[1] + " ");
            String [] allSp = tree.allSup(quest[1]);
            for (int k = 0; k < allSp.length; k++) {
               System.out.print(allSp[k] + " ");
            }
            System.out.println();    

         } else if (quest[0].equals("AllSubcategories")) {
            // Call the allSub method 
            System.out.print(quest[0] + " " + quest[1] + " ");
            Object [] dirSb = tree.allSub(quest[1]);
            for (int k = 0; k < dirSb.length; k++) {
               System.out.print(dirSb[k] + " ");
            }
            System.out.println();            
            
         } else if (quest[0].equals("NumberOfAllSupercategories")) {
            // Call the allSupNum method 
            System.out.println(quest[0] + " " + quest[1] + " " + tree.allSupNum(quest[1]));
            
         } else if (quest[0].equals("NumberOfAllSubcategories")) {
            // Call the allSubNum method 
            System.out.println(quest[0] + " " + quest[1] + " " + tree.allSubNum(quest[1]));
            
         } 
         else if (quest[0].equals("IsSupercategory")) {
            // Call the isSup method 
            System.out.println(quest[0] + " " + quest[1] + " " + quest[2] + tree.isSup(quest[1], quest[2]));
            
         } else if (quest[0].equals("IsSubcategory")) {
            // Call the isSub method 
            System.out.println(quest[0] + " " + quest[1] + " " + quest[2] + tree.isSub(quest[1], quest[2]));
            
         } else if (quest[0].equals("ClosestCommonSupercategory")) {
            // Call the closeComSup method 
            System.out.println(quest[0] + " " + quest[1] + " " + quest[2] + " " + tree.closeComSup(quest[1], quest[2]));
            
         }

      }
   }
}
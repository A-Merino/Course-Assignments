/*

   Author: Alex Merino
   Email: amerino2022@my.fit.edu
   Course: cse2010
   Section: 1
   Description of this file: recursively finds the shrotest route possible
      for a robot to move across stations 

 */

// imports all the classes needed
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;

public class HW2 {

   // Global variables to hold the smallest path distance and smallest path order
   public static double MAX = 0.0;
   public static ArrayList<Station> quick = new ArrayList<Station>();


   /* 
      The Station class holds the data for each station from the input
      Class variables:
         name = holds the name of the station
         x = holds the x-coordinate of the station
         y = holds the y-coordinate of the station
         lineDist = holds the distance of the line from the previous station to the current one 
   */
   static class Station implements Comparable<Station>{
      String name;
      int x;
      int y;
      double lineDist;

      // Class constructor
      public Station (String n, int a, int b) {
         name = n;
         x = a;
         y = b;

      }


      // allows for the Station class to be printed 
      @java.lang.Override
      public String toString() {
         String out = String.format("%s %d %d %.2f", name, x, y, lineDist);
         return out; 
      }


      // Implements the Comparable class for the collections 
      @java.lang.Override
      public int compareTo(Station o) {
         if (this.name.compareTo(o.name) > 0)  {
            return 1;
         } else if (this.name.compareTo(o.name) < 0) {
            return -1;
         } else {
            return 0;
         }
      }
   }

   /*
      Main method collects the input from the file and puts it into
      an ArrayList form and sends it to the recursion method
   */
   public static void main (final String [] args) throws FileNotFoundException {

      // Sets up the scanner
      File input = new File(args[0]);
      Scanner scan = new Scanner(input, "US-ASCII");

      // uses first line as the number of stations in warehouse
      final int places = scan.nextInt();

      // Creates two ArrayLists
      // locations collect the input which will turn into the unOrdered sequence
      // set will be the actual queue of ordered  
      ArrayList<Station> locations = new ArrayList<Station>();
      ArrayList<Station> set = new ArrayList<Station>();

      // for the duration of the text file create new Station objects and put them in the unordered ArrayList
      for (int i = 0; i < places; i++) {
         locations.add(new Station(scan.next(), scan.nextInt(), scan.nextInt()));
      }

      // create a copy of the PackingStation and set the first location of the
      // orderlist to be the PackingStation
      Station last = new Station(locations.get(0).name, locations.get(0).x, locations.get(0).y);
      set.add(locations.remove(0));

      // sorts the rest of the stations in alphabetical order
      // then adds the return to the packing station at the end of the unordered list
      Collections.sort(locations);
      locations.add(last);

      // Calls the recursion method to find smallest path
      findPath(places, locations, set, 0);

         /*
            Now that the smallest path with the total of that path
            is the global variable print each station and the total after
         */
         for (int j = 0; j < places + 1; j++) {
            System.out.println(quick.get(j));
         }
         System.out.printf("Total Distance: %.2f%n", MAX);

   }


   /*
      findPath is the recursive method that finds the quickest path
      Method parameters
         num - holds the number of stations left in unordered ArrayList
         unOrd - the ArrayList which holds the unordered stations
         seq - ArrayList that holds the ordered sequence
         total - holds the total distance the path takes
   */

   public static void findPath (int num, ArrayList<Station> unOrd, ArrayList<Station> seq, double total) {
      // makes a copy of the ArrayList which can be mutated
      ArrayList<Station> copy = new ArrayList<Station>();
      copy.addAll(unOrd);
      
      // loops through the original unOrdered list which will not be modified 
      for (Station loc : unOrd) {
         // PackingStation can only be last so if it is before then skip
         if (loc.name.equals("PackingStation")) {
            continue;
         }

         /*
            remove the station from the copy ArrayList
            create the line distance from current station to last placed
            add that distance to the total and the Station object
            then add the station to the ordered ArrayList
         */
         copy.remove(loc);
         double line = Math.sqrt(Math.pow((seq.get(seq.size()-1).x - loc.x), 2) + Math.pow((seq.get(seq.size()-1).y - loc.y), 2));
         total += line;
         loc.lineDist = line;
         seq.add(loc);

         /*
            When there are only two stations left then check for the 
            total distance since the last station will always be PackingStation 
         */

         if (num == 2) {

            /*
               Remove PackingStation and find the distance and total
            */
            seq.add(copy.remove(0));
            double fin = Math.sqrt(Math.pow((seq.get(seq.size()-1).x - loc.x), 2) + Math.pow((seq.get(seq.size()-1).y - loc.y), 2));
            seq.get(seq.size() - 1).lineDist = fin;
            total += fin;

            /*
               For every other time that is not the first time check
               to see if the current total is less than global total
            */
            if (total < MAX || MAX == 0) {

               // The new shortest distance is now saved and the global ArrayList is removed
               MAX = total;
               quick.removeAll(quick);

               /*
                  Now the order of the current ArrayList is saved into 
                  global variable 
               */
               for (Station placed : seq) {
                  quick.add(new Station (placed.name, placed.x, placed.y));                     
                  quick.get(quick.size() - 1).lineDist = placed.lineDist;
               }        
            }
         } else { // If more than two numbers in unordered ArrayList then recursively call 
            findPath(num - 1, copy, seq, total);
         }

         /*
            Resets the PackingStation to unordered list and 
            clears distance variable as well as takes away from total
         */
         if (num == 2) {
            copy.add(seq.remove(seq.size() - 1));
            total -= copy.get(copy.size() - 1).lineDist;
            copy.get(copy.size() - 1).lineDist = 0.0;
         }

         /*
            Resets the current station to unordered list and 
            clears distance variable as well as takes away from total
         */
         total -= line;
         seq.remove(loc);
         loc.lineDist = 0.0;
         copy.add(copy.size() - 1, loc);
      }
      

   }
}

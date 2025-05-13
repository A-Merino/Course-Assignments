/*
 * Author: Alex Merino, amerino2022@my.fit.edu
 * Course: CSE 1002, Section 4, Fall 2022
 * Project: Fire Marshall Count
*/

import java.util.Scanner;

public final class Fire { 
   // creates Person object
   static record Person (String name, int entered, int duration) {
      public int tLeft () { // total minutes when left
         return entered + duration;
      }

      // calculates whether they were in building during time
      public boolean isPart (final int start, final int end) {
         if (entered > end || this.tLeft() < start) {
            return false;
         }
         return true;
      }
   }

   public static void main (final String[] args) {
      // initializes variables
      final Scanner scan = new Scanner(System.in, "US-ASCII");
      final int entries = scan.nextInt();
      final int queries = scan.nextInt();
      final int [] start = new int [queries];
      final int [] end = new int [queries];
      final Person [] people = new Person[entries]; // to hold all people

      for (int i = 0; i < entries; i++) {
         // collects log input
         final String name = scan.next();
         final int entered = scan.nextInt();
         final int duration = scan.nextInt();

         people[i] = new Person(name, entered, duration); // creates new Person object
      }

      for (int j = 0; j < queries; j++) {
         // collects query input
         start[j] = scan.nextInt();
         end[j] = start[j] + scan.nextInt();
      }
      
      for (int m = 0; m < queries; m++) { // prints names in nested loop
         for (int k = 0; k < entries; k++) {
            if (people[k].isPart(start[m], end[m])) { // Runs object method to calculate
               System.out.println(people[k].name()); // if within range print
            }
         }
      }
   }
}

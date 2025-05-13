/*
 * Author: Alex Merino, amerino2022@my.fit.edu
 * Course: CSE 1002, Section 3, Fall 2022
 * Project: Josephus Problem
*/

import java.util.Scanner;
import java.util.List;
import java.time.Duration;
import java.util.ListIterator;


// java -Dseed=4321567 Main 100 java.util.ArrayList
// java -Dseed=4321567 Main 100 java.util.LinkedList

public final class Josephus {


   public static void main (final String[] args) throws Exception {
      // variables from command line
      final int skip = Integer.parseInt(args[0]);
      assert skip >= 1;

      final Class<?> clazz = Class.forName(args[1]);
      @SuppressWarnings("unchecked")
      final java.util.List<String> list = (java.util.List<String>)
            clazz.getDeclaredConstructor().newInstance();

      final Scanner scan = new Scanner(System.in, "US-ASCII");
      while (scan.hasNext()) {
         list.add(scan.next());
      }
      
      final int runThru = 10;
      String survivor = "";
      long tTime = 0;
      for (int i = 0; i < runThru; i++) {
         final long startTime = System.nanoTime(); // time start
         final String lastPer = jose(list, skip);
         final long endTime = System.nanoTime(); // time end
         tTime += endTime - startTime;
         survivor = lastPer;
      }
      tTime = tTime / runThru; // average time
      System.out.printf("Last soldier: %s.  Time: %s%n", survivor, Duration.ofNanos(tTime));

   }

   public static String jose (final List<String> par, final int skip) {
      final ListIterator<String> names = par.listIterator();
      int size = par.size();
      while (size > 1) {
         for (int k = 0; k < skip; k++) {
            if (names.nextIndex() % size == 0) {
               prev(names);
            }
            names.next();   
         }
         names.remove();
         size--;
      }
      prev(names);
      return names.next();
   }

   public static void prev (final ListIterator<String> res) {
      while (res.previousIndex() >= 0) {
         res.previous();
      }
   }
}

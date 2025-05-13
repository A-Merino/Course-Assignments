/*
 * Author: Alex Merino, amerino2022@my.fit.edu
 * Course: CSE 1002, Section 3, Fall 2022
 * Project: Seaweed
*/

import java.util.Scanner;
import java.util.Arrays;

public final class Seaweed {
   private record Manatee (double hours, double seaweed) implements Comparable<Manatee> {

      @java.lang.Override
      public int compareTo (final Manatee other) {
         if (this.hours / this.seaweed < other.hours / other.seaweed) { // finds ratio
            return -1; // less than
         } else if (this.hours / this.seaweed > other.hours / other.seaweed) {
            return +1; // greater than
         } else {
            return 0; // equals
         }
      }
   }

   public static void main (final String[] args) {
      // initializes variables
      final Scanner scan = new Scanner(System.in, "US-ASCII");
      final int mNum = scan.nextInt();
      final Manatee[] mans = new Manatee[mNum]; // array to hold manatee object

      for (int n = 0; n < mNum; n++) {
         // for all manatees add to array and make a new one
         mans[n] = new Manatee(scan.nextInt(), scan.nextInt());
      }

      Arrays.sort(mans); // sorts through Array class
      System.out.printf("%.0f%n", calc(mans)); // prints without double decimal
   }

   public static double calc (final Manatee[] list) {
      double seaAte = 0; // total seaweed ate
      for (int w = 0; w < list.length; w++) {
         final double time = list[w].hours * 2; // time for first to travel
         for (int v = w + 1; v < list.length; v++) {
            // totals amount of seaweed each left has ate
            seaAte += list[v].seaweed * time;
         }
      }
      return seaAte;
   }
}

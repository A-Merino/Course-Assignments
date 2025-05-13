/*
 * Author: Alex Merino, amerino2022@my.fit.edu
 * Co-Authors: Robert Sedgewick and Kevin Wayne (writers of book)
 * Course: CSE 1002, Section 3, Fall 2022
 * Project: Page Rank
*/

// java "-Dformat= %.2f" -Dleap=0.1 Transition < medium.txt | java -Dseed=1234 -Dtrials=1000 RandomSurfer
import java.util.Scanner;

public final class Transition {
   public static void main (final String[] args) {
      final String leap = System.getProperty("leap");
      final String format = System.getProperty("format");
      // initializes variables
      final Scanner scan = new Scanner(System.in, "US-ASCII");
      final int n = scan.nextInt();         // collects number of pages
      final int[][] counts = new int[n][n]; // counts[i][j] = # links from page i to page j
      final int[] outDegree = new int[n];   // outDegree[i] = # links from page i to anywhere

      // Accumulate link counts
      while (scan.hasNextInt()) {
         int i = scan.nextInt();
         int j = scan.nextInt();
         outDegree[i]++;
         counts[i][j]++;
      }

      System.out.println(n + " " + n);


      // Print probability distribution for row i.
      for (int i = 0; i < n; i++) {
         // Print probability for column j.
         for (int j = 0; j < n; j++) {
            double p = 0.90*counts[i][j]/outDegree[i] + 0.10/n; // calculation for prob
            if (outDegree[i] == 0) {
               p = 1.0/n; // prob if there are no outgoing links
            }   
            System.out.printf("%.2f ", p);
         }
         System.out.println();
      }
   }
}

/*
 * Author: Alex Merino, amerino2022@my.fit.edu
 * Co-Authors: Robert Sedgewick and Kevin Wayne (writers of book)
 * Course: CSE 1002, Section 3, Fall 2022
 * Project: Page Rank
*/
import java.util.Random;
import java.util.Scanner;

public final class RandomSurfer {
   private static final Random RNG = new Random(Long.getLong("seed", System.nanoTime()));

   public static void main (final String[] args) {
      final Scanner scan = new Scanner(System.in, "US-ASCII");
      final String trial = System.getProperty("trials");
      final int trials = Integer.parseInt(trial);   // number of moves
      final int m = scan.nextInt();                  // number of pages  - ignore since m = n
      final int n = scan.nextInt();                  // number of pages
      if (m != n) {
         System.out.println("m does not equal n");
         return;
      }

      // Read transition matrix.
      double[][] p = new double[n][n];          // p[i][j] = prob. that surfer moves from page i to page j
      for (int i = 0; i < n; i++) {
         for (int j = 0; j < n; j++) {
            p[i][j] = scan.nextDouble();
         }
      }


      final int[] freq = new int[n];                  // freq[i] = # times surfer hits page i

      // Start at page 0.
      int page = 0;

      for (int t = 0; t < trials; t++) {

         // Make one random move.
         double r = RNG.nextDouble();
         double sum = 0.0;
         for (int j = 0; j < n; j++) {
            // Find interval containing r.
            sum += p[page][j];
            if (r < sum) {
               page = j;
               break;
            }
         }
         freq[page]++;
      }


      // Print page ranks.
      for (int i = 0; i < n; i++) {
         // avoids typecasting
         final Integer freqq = freq[i];
         final Integer tri = trials;
         final double out = freqq.doubleValue() / tri.doubleValue(); 

         System.out.printf("%2d: %6.3f%n", i+1, out);
      }
      System.out.println();
   }
}

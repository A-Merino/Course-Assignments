/*
 * Author: Alex Merino, amerino2022@my.fit.edu
 * Course: CSE 1002, Section 1, Fall 2022
 * Project: Shuffling Shells
*/

// java -Dseed=5463785 Shuffle 25 5

import java.util.Random;

public final class Shuffle {
   private static final Random RNG = new Random(Long.getLong("seed", System.nanoTime()));

   public static void main (final String[] args) {
      // initializes variables and arrays
      final int n = Integer.parseInt(args[0]);
      final int m = Integer.parseInt(args[1]);
      final int [] perm = new int[m];
      final int [][] values = new int[m][m]; 
      
      for (int f = 0; f < n; f++) { // shuffle n amount of times
         for (int i = 0; i < m; i++) { // resets perm to 0, 1, 2, 3, 4
            perm[i] = i;
         }

         
         for (int j = 0; j < m; j++) { // shuffles numbers
            final int q = j + (RNG.nextInt(m - j));
            // switching positions
            final int v = perm[q];
            perm[q] = perm[j];
            perm[j] = v;
         }

         for (int x = 0; x < m; x++) {
            values[perm[x]][x]++; // adds incrememnt to each row and column
         }

      }

      // formats the print for a matrix
      for (int y = 0; y < m; y++) {
         for (int z = 0; z < m; z++) {
            System.out.print(values[y][z] + " ");
         }
         System.out.println();
      }
   }
}

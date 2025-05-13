/*
 * Author: Alex Merino, amerino2022@my.fit.edu
 * Course: CSE 1002, Section 1, Fall 2022
 * Project: Sand Searching
*/

import java.util.Scanner;

public final class Muck {
   public static void main (final String[] args) {
      // initalizes scanner, collects table length and creates 2d array 
      final Scanner scan = new Scanner(System.in, "US-ASCII");
      final int row = scan.nextInt();
      final int column = scan.nextInt();
      final String [][] irl = new String [row][column];

      // sets positions visited to false and separates each letter to its own position
      for (int i = 0; i < row; i++) {
         final String collect = scan.next();
         for (int j = 0; j < column; j++) {
            final String gather = collect.substring(j, j + 1);
            irl [i][j] = gather;
         }
      }

      // starts recursion
      filter(irl);

   }
   static void filter (final String [][] lag) {
      // begins nested for loop to check if visited 
      int count = 0;
      String [][] lagoon = lag;

      for (int k = 0; k < lag.length; k++) {
         for (int m = 0; m < lag[0].length; m++) {
               if (lagoon[k][m].equals(".")) {
                  lagoon = queue(k, m, lagoon);
                  count++;
               }
            
         }
      }
      System.out.print(count); // prints final tally
   }

   static String[][] queue (final int r, final int c, final String [][] lag) {
      // checks if its valid around all 8 sides
      if (r < 0 || r >= lag.length) { // exceeds row
         return lag;
      }
      if (c < 0 || c >= lag[0].length) { // exceeds column
         return lag;
      }
      if (lag[r][c].equals(".")) {
         lag[r][c] = "#"; // recursion
      
         queue(r, c + 1, lag);     // right
         queue(r, c - 1, lag);     // left
         queue(r + 1, c, lag);     // bottom
         queue(r - 1, c, lag);     // top
         queue(r + 1, c + 1, lag); // bottom right
         queue(r + 1, c - 1, lag); // bottom left
         queue(r - 1, c + 1, lag); // top right
         queue(r - 1, c - 1, lag); // top left
      } 

      return lag;
   }

}

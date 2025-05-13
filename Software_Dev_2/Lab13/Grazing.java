/*
 * Author: Alex Merino, amerino2022@my.fit.edu
 * Course: CSE 1002, Section 3, Fall 2022
 * Project: Seagrass Grazing
*/
import java.util.Scanner;

public final class Grazing {

   // counts total routes
   public static int total = 0;

   public static void main (final String[] args) {
      // initialize scanner and variables
      final Scanner scan = new Scanner(System.in, "US-ASCII");
      final int barren = scan.nextInt();
      final Boolean [][] grass = new Boolean [5][5];
      // Amount of spaces left after midopoint, start, and barren taken out 
      final int highest = 22 - barren;

      // creates boolean table, if it is grass it is true
      for (int i = 0; i < grass.length; i++) {
         for (int j = 0; j < grass.length; j++) {
            grass[i][j] = true;
         }
      }

      // changes each spot to barren based on input
      for (int k = 0; k < barren; k++) {
         final int x = scan.nextInt();
         final int y = scan.nextInt();
         grass[x-1][y-1] = false;
      }

      
      findGrass(0, 0, grass, 0, highest); // calls recursion
      System.out.println(total); // prints total
   }


   static void findGrass (final int x, final int y, final Boolean [][] grass,
            final int c, final int highest) {
      // increments each time the manatee moves and if arrives at end then it is a route
      final int count = c + 1;
      if (count >= highest) {
         total++;

      } 
      // if visited or out of bounds
      if (x < 0 || x >= grass.length || y < 0 || y >= grass.length) {
         return;
      }
      if (!grass[x][y]) {
         return;
      } else {
         grass[x][y] = false;
      }
      // recursion
      findGrass(x, y + 1, grass, count, highest); // right
      findGrass(x, y - 1, grass, count, highest); // left
      findGrass(x + 1, y, grass, count, highest); // down
      findGrass(x - 1, y, grass, count, highest); // up
   }

}

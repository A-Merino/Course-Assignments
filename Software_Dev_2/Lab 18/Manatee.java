/*
 * Author: Alex Merino, amerino2022@my.fit.edu
 * Course: CSE 1002, Section 4, Fall 2022
 * Project: Lost at Sea
*/

public final class Manatee {
   private static int x = 0;
   private static int y = 0;
   public static String grid (final char[] move, final Danger[] dSpots) {
      String out = "Lost at sea"; // default value
      for (int j = 0; j < move.length; j++) {
         // moves Manatee based on direction
         if (move[j] == 'W') {
            x--;
         } else if (move[j] == 'N') {
            y++;
         } else if (move[j] == 'E') {
            x++;
         } else if (move[j] == 'S') {
            y--;
         }
         out = checks(dSpots); // goes through special cases

         if (out.compareTo("Lost at sea") != 0) {
            break; // if one of special cases is true then immediately return
         }
      }
      return out;
   }

   public static String checks (final Danger[] spots) {
      if (y == 0 && x < 0) { // back into river
         return "Home at last";
      } else if (y != 0 && x < 0) { // along coast
         return "Beached";
      } else if (y < Danger.GRID_MIN || y > Danger.GRID_MAX || x > Danger.GRID_MAX) { // outside grid
         return "Terra incognita";
      } else {
         // searches through Danger array to see if one has been hit
         for (int k = 0; k < spots.length; k++) {
            if (x == spots[k].getX() && y == spots[k].getY()) {
               return "The end";
            }
         }
      }
      return "Lost at sea"; // ensures default value
   }
}

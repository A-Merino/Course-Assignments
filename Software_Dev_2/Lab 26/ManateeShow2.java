/*
 * Author: Alex Merino, amerino2022@my.fit.edu
 * Course: CSE 1002, Section 3, Fall 2022
 * Project: Manatee Talent Show
*/

import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class ManateeShow2 {

   // record for input from command line
   record ManH (String judge, int id, int score) {

   }

   public static void main (final String[] args) {
      // initializes scanner and map for manatees
      final Scanner scan = new Scanner(System.in, "US-ASCII");
      final Map<Integer, ManH> start = new HashMap<>();
      // until all data sets have been entered
      final int max = scan.nextInt(); // max score
      final int[][] out = new int[max + 1][max + 1];
      boolean ent = true;

      while (ent) {
         String judge = scan.next();
         final ManH cur = new ManH(judge, scan.nextInt(), scan.nextInt());

         if (start.get(cur.id) != null) {
            ManH alr = start.get(cur.id);
            if (alr.judge.compareTo("H") == 0) {
               out[cur.score][alr.score]++;               
            } else {
               out[alr.score][cur.score]++;
            }
            start.remove(cur.id);
         } else {
            start.put(cur.id, cur);
         }

         if (max <= 0) { // stops while loop
            break;
         }

         if (judge.compareTo("E") == 0) {
            ent = false;
         }
      }
      out[0][3]++;

      for (int i = 0; i <= max; i++) {
         for (int j = 0; j <= max; j++) {
            System.out.print(out[i][j] + " ");
         }
         System.out.println();
      }
   }
}

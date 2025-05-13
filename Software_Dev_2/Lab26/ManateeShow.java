/*
 * Author: Alex Merino, amerino2022@my.fit.edu
 * Course: CSE 1002, Section 3, Fall 2022
 * Project: Manatee Talent Show
*/

import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Map;

public final class ManateeShow {

   // record for Manatee, combined scores from both teachers
   record Manatee (int id, int stanS, int shoaffS) {

   }
   // record for input from command line
   record ManH (String judge, int id, int score) {

   }

   public static void main (final String[] args) {
      // initializes scanner and map for manatees
      final Scanner scan = new Scanner(System.in, "US-ASCII");
      final Map<Integer, Integer> map = new HashMap<>();
      // until all data sets have been entered

      while (true) {
         final int max = scan.nextInt(); // max score
         
         if (max <= 0) { // stops while loop
            break;
         }

         // sets all points in map to 0 becuase no manatees have been counted
         for (int i = 0; i < (max + 1) * (max + 1); i++) {
            map.put(i, 0);
         }

         // creates ArrayList of Manatees that have scores from stansifer and shoaff
         final ArrayList<Manatee> list = dataSet(max);

         for (final Manatee man : list) {
            // because of grid pattern stansifers score times amount of rows
            // determines row and adding shoaffs score determines column
            // add one to the amount of manatees already in same spot
            map.put(man.shoaffS + (man.stanS * (max + 1)),
                  map.get(man.shoaffS + (man.stanS * (max + 1))) + 1);
         }

         int count = 0;
         for (final Entry<Integer, Integer> entry: map.entrySet()) {
            System.out.print(entry.getValue() + " ");
            // after the max amount of columns has been reached make a new line
            count++;
            if (count % (max + 1) == 0) {
               System.out.println();
            }
         }
         // adds new line to go onto next data set
         System.out.println();
      }
   }


   public static ArrayList<Manatee> dataSet (final int max) {
      
      // creates new scanner and arraylists for both manatee classes
      final Scanner scanD = new Scanner(System.in, "US-ASCII");
      final ArrayList<ManH> holder = new ArrayList<ManH>();
      final ArrayList<Manatee> full = new ArrayList<Manatee>();

      // while loop will run always until "E" is typed
      final boolean sameData = true;
      while (sameData) {

         // teacher or E
         final String grader = scanD.next();
         // checks for E and returns all manatees with two scores
         if (grader.compareTo("E") == 0) {
            return full;      
         }
         // if not E then collect id and score
         final int idNum = scanD.nextInt();
         final int score = scanD.nextInt();

         assert score <= max; // ensures score is less than max

         // creates ManH which contains teacher and only one score
         final ManH currMan = new ManH(grader, idNum, score);

         for (final ManH man : holder) {
            if (man.id == currMan.id) { // if the id is same as array
               // if just inputed ManH was stansifer then score goes first
               if (man.judge.compareTo("H") == 0) {
                  full.add(new Manatee(man.id, currMan.score, man.score));
               } else {
                  full.add(new Manatee(man.id, man.score, currMan.score));
                                    
               }
            }
         }
         // add ManH created before for loop to check with ManH input after
         holder.add(currMan);
      }
   }

}

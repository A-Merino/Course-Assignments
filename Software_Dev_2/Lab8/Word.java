/*
 * Author: Alex Merino, amerino2022@my.fit.edu
 * Course: CSE 1002, Section 1, Fall 2022
 * Project: Word Wrap
*/

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

public final class Word {
   public static void main (final String[] args) {
      // initialize scanner and ArryList
      final Scanner scan = new Scanner(System.in, "US-ASCII");
      final ArrayList<String> list = new ArrayList<String>();
      final int width = scan.nextInt();
      final int gutter = scan.nextInt();
      boolean tooLong = false;
      while (scan.hasNext()) { // until all names
         list.add(scan.next()); // adds name to list
      }
      Collections.sort(list);
      int longName = 0;
      for (int i = 0; i <= list.size()-1; i++) { // for each name in the list
         final String name = list.get(i);
         if (name.length() > longName) {
            longName = name.length();
         }
         if (name.length() > width) { // if the length of the name is greater than page width
            System.out.println("See Professor Smith for the names.");
            tooLong = true;
         }
      }
      
      double column = Math.floor(width / longName); // creates a base for amount of columns
      final double characters = column * longName; // sets possible amount of total characters

      // adds gutter and changes amount of columns to be less than width
      if (width < characters + (gutter * (column - 1))) {
         column--;
      }
      // Converts the amount of columns needed to an int
      final Double col = column;
      final int columns = col.intValue();

      // Calculates and converts to an int the amount names in each column
      final double colLeng = Math.ceil(list.size() / column);
      final Double coll = colLeng;
      final int colLength = coll.intValue();

      if (!tooLong) { // doesn't run through if one name exceeds width 
         for (int k = 0; k < colLeng; k++) { // for the length of the columns
            for (int m = 0; m <= column; m++) { 
               final int getName = k + (colLength * m); // finds location of name for each row
               if (getName >= list.size()) { // if on last column and doesn't have name
                  System.out.println();
                  m = columns;
               } else {
                  System.out.print(list.get(getName)); // prints name and space
                  for (int j = 0; j < gutter; j++) {
                     System.out.print(" ");         
                  }
               }
            }
         }
      }
   }
}

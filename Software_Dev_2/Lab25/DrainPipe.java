/*
 * Author: Alex Merino, amerino2022@my.fit.edu
 * Course: CSE 1002, Section 3, Fall 2022
 * Project: Wye Drainpipe
*/

import java.util.Scanner;
import java.util.ArrayDeque;
import java.util.ArrayList;

public final class DrainPipe {

   public static void main (final String[] args) {
      // initialize variables
      final Scanner scan = new Scanner(System.in, "US-ASCII");
      final ArrayDeque<Integer> manatee = new ArrayDeque<Integer>();
      final ArrayDeque<Integer> order = new ArrayDeque<Integer>();
      final ArrayList<Boolean> answers = new ArrayList<Boolean>(); 
      boolean endCases = false;
      boolean endBlock = false;


      while (!endCases) {
         final int amount = scan.nextInt(); // ArrayDeque size
         if (amount == 0) { // if second zero to end tests
            endCases = true;
            break;
         }
         for (int i = 1; i >= amount; i++) {
            manatee.addLast(i);
         }
         endBlock = false;
         while (!endBlock) {     
            for (int d = 0; d < amount; d++) {
               final int nextNum = scan.nextInt();
               if (nextNum == 0) { // if 0 then stop set of cases
                  endBlock = true;
                  manatee.clear();
                  break;
               }
               order.add(nextNum);
            }
            if (!endBlock) {
               answers.add(reOrg(manatee, order)); // test if can be reorganized
               order.clear();
               
            }
            
         }
      }

      for (final boolean trial : answers) { // prints yes or no based on true or false
         if (trial) {
            System.out.println("Yes");
         } else {
            System.out.println("No");
         }
      }
      
   }

   public static boolean reOrg (final ArrayDeque<Integer> act,
         final ArrayDeque<Integer> mixed) {
      final ArrayDeque<Integer> temp = mixed;
      final ArrayDeque<Integer> passed = new ArrayDeque<>();
      final ArrayDeque<Integer> pipe = new ArrayDeque<Integer>();
      boolean possible = true;
      while (possible) {
         final int next = temp.pop();
         if (pipe.size() != 0 && pipe.pollFirst() == next) {
            act.push(pipe.pop());
         } else {
            while (act.size() != 0 && act.pollFirst() != next) {
               pipe.push(act.pop());
            }
            if (act.size() == 0) {
               possible = false;
               break;
            } else {
               passed.addLast(act.pop());
            }
         }
         if (temp.size() == 0) {
            possible = false;
            break;
         }
      }

      if (passed.equals(mixed)) {
         return true;
      }
      return false;
   }
}


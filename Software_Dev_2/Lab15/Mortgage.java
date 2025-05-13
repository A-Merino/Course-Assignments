/*
 * Author: Alex Merino, amerino2022@my.fit.edu
 * Course: CSE 1002, Section 3, Fall 2022
 * Project: Mortgage
*/

import java.util.Scanner;
import java.math.BigDecimal; 
import java.math.RoundingMode;

public final class Mortgage {
   public static void main (final String[] args) {
      // initializes variables
      final Scanner scan = new Scanner(System.in, "US-ASCII");
      // takes in loan and interest as a string
      BigDecimal loan = new BigDecimal(scan.nextLine());
      final BigDecimal interest = new BigDecimal(scan.nextLine());
      final BigDecimal zero = new BigDecimal(0); // creates zero to compare to
      // values for print
      final String bal = "balance";
      String last = "left";

      while (scan.hasNext()) { // until no more lines
         // takes in next input
         final String line = scan.nextLine();
         final String lowerLine = line.toLowerCase(); // if it's balance put it in lowercase

         if (loan.compareTo(zero) > 0) { // positive
            last = "left";
         } else if (loan.compareTo(zero) < 0) { // negative
            last = "over";
         } else { // 0
            last = "";
         }

         if (lowerLine.compareTo(bal) == 0) { // if asking for balance
            System.out.printf("balance: %.2f %s%n", loan.abs(), last);
         } else { // must be number taking away
            final BigDecimal sub = new BigDecimal(line); // BigDec from string
            loan = loan.add(loan.multiply(interest)); // adds interest
            loan = loan.setScale(2, RoundingMode.HALF_UP); // rounds
            loan = loan.subtract(sub); // takes away 
         }
      }
   } 
}

/*
 * Author: Alex Merino, amerino2022@my.fit.edu
 * Course: CSE 1002, Section 1, Fall 2022
 * Project: Save the Manatees
*/
import java.util.Scanner;

public final class Trend {
   public static void main (final String[] args) {
      // initialze variables
      final Scanner scan = new Scanner(System.in, "US-ASCII");
      final int p = scan.nextInt();
      final int a = scan.nextInt();
      final int b = scan.nextInt();
      final int c = scan.nextInt();
      final int d = scan.nextInt();
      final int n = scan.nextInt();
      double price = 0;
      double posPrice = 0;
      double highPrice = 0;

      for (int k = 1; k <= n; k++) { // for all of n
         posPrice = p * (Math.sin((a * k) + b) + Math.cos((c * k) + d) + 2);
         if (posPrice > highPrice) { // if a higher value from before
            highPrice = posPrice;
         } else {
            posPrice = highPrice - posPrice; // to find lower value
            // if the difference is larger then that is the largest
            if (posPrice > price) {
               price = posPrice;
            }
         }
      }
      System.out.printf("%.2f%n", price);
   }
}

/*
 * Author: Alex Merino, amerino2022@my.fit.edu
 * Course: CSE 1002, Section 1, Fall 2022
 * Project: Cardboard Manatee
*/
import java.util.Scanner;

public final class Cardboard {
   public static void main (final String[] args) {
      // initializes variables
      Scanner scan = new Scanner(System.in, "US-ASCII"); // creates scanner
      final int volume = scan.nextInt();
      double length = 1;
      double height = 1;
      double width  = 1;
      final double posPrime = volume / 10;
      // if volume is a perfect cube
      final double startPoint = Math.floor(Math.cbrt(volume));
      if (startPoint == Math.cbrt(volume)) { 
         length = startPoint;
         width = startPoint;
         height = startPoint;
      }

         for (int i = 2; i <= Math.sqrt(volume) + 1; i++) { // checks if number is a prime number
            if (volume % i != 0) { // if the number is not divisible by i then can still be prime
               length = volume; // if prime then this is only way to get box
            } else {
               i = volume + 1; // escapes loop
               length = 1;
            }
            if (i % 2 == 0 && i != 2) { // ensures i isn't even since evens are divisible by 2
                i++;
            }         
         }
       

      // Any prime number times 10 has the best box to be 2 * 5 * the number itself
      if (volume % 10 == 0 && length != startPoint) { // checks if number is divisible by 10
            for (int q = 2; q <= Math.sqrt(posPrime); q++) { // same for loop as above
               if (posPrime % q != 0) { 
                  length = posPrime; 
               } else {
                  q = volume + 1; 
               }
            if (q % 2 == 0 && q != 2) {
               q++;
            }         
         }
         // sets width and height too other dimensions needed,
         // will be overriden if posPrime is not prime
         width = 2; 
         height = 5;
      }

      if (length != volume) { // if the number is not a prime or prime * 10 or perfect cube
         // at least one factor is before sqrt of volume
         double largestPosInt = Math.floor(Math.sqrt(volume)) + 1;
         double bestSA = 1000000; // starting var
         double sa = 0;
         for (int j = 1; j <= largestPosInt; j++) {
            for (int k = 1; k <= largestPosInt; k++) {
               for (int m = 1; m <= largestPosInt; m++) {
                  if (j * k * m == volume) {
                     sa = 2 * ((k * j) + (m * j) + (k * m));
                     if (sa < bestSA) { // if surface area is smaller
                        width = m;
                        height = k;
                        length = j;
                        bestSA = sa;
                     }
                  }
               }
            }
         }
      } if (width == 1) { // if number is a product of two primes
            for (int r = 2; r <= volume / 2; r++) {
               if (volume % r == 0) {
                  height = r;
                  width = volume / height;
                  length = 1;
               }
            }

            if (length == 1) { // if number is a product of three primes 
               for (int y = 2; y <= height; y++) {
                  if (height % y == 0) {
                     height = y;
                     length = volume / (height * width);
                  }
               }
            } if (length % width == 0) { // if length can be divisible by width
               length = length / width; // makes area smaller
               width = width * width;
            } if (length % height == 0) { // same as above
               length = length / height;
               height = height * height;
            } for (double z = 2; z <= length / 2; z++) { // if length still is div by prime
               if (length % z == 0) {
                  width = width * z;
                  length = length / z;
               }
            } while (width % height == 0) { // makes sure the width and height are close
               width = width / height;
               height = height * height;
            }
         }

      // calculates cost and prints
      final double cost = 2 * ((length * height) + (length * width) + (height * width));
      System.out.printf("%.0f", cost);     
   }
}

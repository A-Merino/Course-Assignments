/*
 * Author: Alex Merino, amerino2022@my.fit.edu
 * Course: CSE 1002, Section 4, Fall 2022
 * Project: Lost at Sea
*/
import java.util.Scanner;

public final class Main {
   public static void main (final String[] args) {
      // initalizes variables
      final Scanner scan = new Scanner (System.in, "US-ASCII");
      final int dangers = scan.nextInt(); // collects amount of danger spots
      final Danger [] dSpots = new Danger[dangers];

      // collects danger spots and creates Danger object
      for (int i = 0; i < dangers; i++) {
         final int xCord = scan.nextInt();
         final int yCord = scan.nextInt();
         dSpots[i] = new Danger(xCord, yCord);
      }

      // collects moves and makes char array
      final String line = scan.next();
      final char [] moves = line.toCharArray();

      System.out.println(Manatee.grid(moves, dSpots)); // output
   }
}

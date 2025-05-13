/* 
 * Author: Alex Merino, amerino2022@my.fit.edu
 * Course: CSE 1002, Section 1, Fall 2022
 * Project: Lab 3, Scanner, High
 */
import java.util.Scanner;

public final class High {
   public static void main (final String[] args) {
      final Scanner scan = new Scanner (System.in, "US-ASCII");
      int bestGrade = 0;
      String firstName = "";
      String lastName = "";
      int grade = 0;
      String full = "";

      while (scan.hasNext()) { 
         firstName = scan.next();
         lastName = scan.next();
         grade = scan.nextInt();
         if (grade > bestGrade) {
            bestGrade = grade;
            full = firstName + " " + lastName + " " + grade;
         }
      }
      System.out.println(full);
   }
}

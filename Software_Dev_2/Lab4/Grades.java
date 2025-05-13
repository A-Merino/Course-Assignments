/* 
 * Author: Alex Merino, amerino2022@my.fit.edu
 * Course: CSE 1002, Section 1, Fall 2022
 * Project: Lab 4, Scanner, Grades
 */
import java.util.Scanner;

public final class Grades {
   public static void main (final String[] args) {
      final Scanner scan = new Scanner (System.in, "US-ASCII");
      double mean = 0;
      double standDev = 0;
      final int totalStudents = scan.nextInt();
      final String[] firstNames = new String[totalStudents];
      final String[] lastNames = new String[totalStudents];
      final int[] scores = new int[totalStudents];
      final double [] zScores = new double[totalStudents];
      final String[] letterGrade = new String[totalStudents];
      for (int i = 0; i < totalStudents; i++) {
         final String firstName = scan.next();
         firstNames[i] = firstName;
         final String lastName = scan.next();
         lastNames[i] = lastName;
         final int score = scan.nextInt();
         scores[i] = score;
      }

      for (int k = 0; k < totalStudents; k++) { // calculate mean
         mean += scores[k];
      }
      mean = mean / totalStudents;

      for (int j = 0; j < totalStudents; j++) { // calculates standard Dev
         standDev += Math.pow((scores[j] - mean), 2);
      }

      standDev = (double) Math.sqrt(standDev / totalStudents);
      System.out.printf("Name Name %27s %5s %-5s%n", "Score", "Z", "Grade");
      
      if (standDev == 0) { // incase 1 or 0 students is inputted
         for (int m = 0; m < totalStudents; m++) {
            zScores[m] = 0;
            letterGrade[m] = "B";
            System.out.printf("%15s %-15s %5d %5.2f %5s%n",
                     firstNames[m], lastNames[m], scores[m], zScores[m], letterGrade[m]);
         }
      } else {

         for (int q = 0; q < totalStudents; q++) {
            zScores[q] = (scores[q] - mean) / standDev;
            if (zScores[q] < -2) { // Assigns a letter grade based on the Z-Score
               letterGrade[q] = "F";
            } else if (zScores[q] >= -2 && zScores[q] < -1) {
               letterGrade[q] = "D";
            } else if (zScores[q] >= -1 && zScores[q] < 0) {
               letterGrade[q] = "C";
            } else if (zScores[q] <= 0 && zScores[q] > 1) {
               letterGrade[q] = "B";
            } else {
               letterGrade[q] = "A";
            }
            // Prints out the data of the table
            System.out.printf("%15s %-15s %5d %5.2f %5s%n",
                     firstNames[q], lastNames[q], scores[q], zScores[q], letterGrade[q]);


         }
         System.out.println("Average " + mean);
      }
   }
}

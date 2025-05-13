/* 
 * Author: Alex Merino, amerino2022@my.fit.edu
 * Course: CSE 1002, Section 1, Fall 2022
 * Project: Lab 3, Table
 */
import java.time.LocalDate;
import java.util.Locale.Builder;
import java.util.Locale;
import java.time.format.DateTimeFormatter;

public final class Table {
   public static void main (final String[] args) {
      // args[] is an array of strings. If we want to use the value,
      // we have to convert it from string to int
      final int n = Integer.parseInt (args[0]);
      int deFault = 10;   
      if (n > 0) {
         deFault = n;
      }
      final int tableWidth = 68;
      final String in = "i";
      final String hex = "hex";
      final String bits = "bits";
      final String log = "log2(i*i)";
      final String ii = "i*i";
      final String iii = "i*i*i";
      final String pcnt = "pcnt";
      // prints top row of table
      System.out.printf("%9s %9s %9s %9s %9s %9s %9s%n", in, hex, bits, log, ii, iii, pcnt);
      for (int k = 0; k <= tableWidth; k++) {
         System.out.print("-");
      }
      System.out.println();
      // the number of loops is the number of your input argument.
      for (int i = 1; i <= deFault; i++) {
         final double col3 = Math.ceil((Math.log(i)/Math.log(2))); // calculates bits
         final double col4 = Math.log(i * i)/Math.log(2); // calculates log
         final int col5 = i * i; 
         final int col6 = col5 * i;
         final double divider = i; // Used to not Typecast
         final double col7 = (divider / deFault) * 100;
         System.out.printf("%9d %09X %9.0f %9.3f %9d %,9d %8.0f%%%n",
               i, i, col3, col4, col5, col6, col7);
      }

      for (int k = 0; k <= tableWidth; k++) { // Closes out table
         System.out.print("-");
      }
      System.out.println();
      // sets region to Mexico for date portion
      final Locale mexico = new Builder().setLanguage("es").setRegion("MX").build();
      final LocalDate date = LocalDate.now();
      final DateTimeFormatter formatter = 
            DateTimeFormatter.ofPattern("EEEE, dd LLLL yyyy", mexico);
      final String bottomLine = date.format(formatter);
      // final LocalDate parsedDate = LocalDate.parse(bottomLine, formatter);
      System.out.printf("%45s%n", bottomLine);
   }

}

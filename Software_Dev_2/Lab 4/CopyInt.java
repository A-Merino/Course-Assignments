import java.util.Scanner;

/*
 *  System.in  (java.io.InputStream) is the standard input
 *  System.out (java.io.PrintStream) is the standard output
 */

public final class CopyInt {

   public static void main (final String[] args) {

      final Scanner stdin = new Scanner (System.in, "US-ASCII");

      // Read the standard input stream
      while (stdin.hasNextInt()) {
         final int n = stdin.nextInt(); // get the next int from input
         System.out.println(n);         // write the int to output
      }
   }
}
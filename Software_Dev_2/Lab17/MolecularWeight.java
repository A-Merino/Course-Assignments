/*
 * Author: Alex Merino, amerino2022@my.fit.edu
 * Course: CSE 1002, Section 4, Fall 2022
 * Project: Molecular Weight
*/

import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

public final class MolecularWeight {
   
   // creates new Element object   
   static record Element (String name, int number, String symbol, double weight) {
      
   } 

   public static void main (final String[] args) throws IOException {
      // creates scanners for input stream and file
      final Scanner input = new Scanner(System.in, "US-ASCII");
      final Path path = Paths.get(args[0]);
      final Scanner files = new Scanner(path, "US-ASCII");
      final ArrayList<Element> elements = new ArrayList<Element>();
      final String header = files.nextLine(); // skips header in data
      final String printStart = "Molecular weight of ";
      final ArrayList<String> output = new ArrayList<String>();


      // collects all the data and creates a new Element object
      while (files.hasNextLine()) {
         final String line = files.nextLine();
         final String [] comp = line.split(",");

         // since data is formatted we can take each singularly
         final String name = comp[0];
         final int num = Integer.valueOf(comp[1]);
         final String sym = comp[2]; 
         final double weight = Double.valueOf(comp[3]);

         elements.add(new Element(name, num, sym, weight)); // new Element to ArrayList
      }

      while (input.hasNextLine()) {
         // loop variables
         String out = printStart;
         double sum = 0;
         double last = 0;
         boolean isFormula = true;
         // collects input and splits to array
         final String formula = input.nextLine();
         final String [] parts = formula.split(" ");

         for (int i = 0; i < parts.length; i++) {
            // to find if it is not element or number
            boolean isElement = false;
            // begins format for output
            parts[i] = parts[i].trim();
            out = out + parts[i] + " ";
            // searches all elements to see if a match
            for (final Element el : elements) { 
               if (parts[i].equals(el.symbol())) {
                  sum += el.weight();
                  last = el.weight(); // saves weight for if coefficient
                  isElement = true;
               }
            }
            // checks if it's a number
            boolean isNumber = false;
            if (parts[i].compareTo("9") <= 0) {
               // minus one because one time was already added
               sum = sum + (last * (Integer.valueOf(parts[i])-1));
               isNumber = true;  
            } else if (!isElement && !isNumber) {
               // if it's not an element or a number it is invalid
               isFormula = false;
            }
         }
         if (isFormula) {
            out = String.format("%s= %.2f", out, sum); // adds sum
         } else {
            out = out + "= ??"; // adds ?? because invalid argument
         }
         output.add(out);  
      }

      for (final String outs : output) {
         System.out.println(outs); // prints all lines of input at once
      }
   }
}

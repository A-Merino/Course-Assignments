/*
 * Author: Alex Merino, amerino2022@my.fit.edu
 * Course: CSE 1002, Section 3, Fall 2022
 * Project: Newtons Chaos
*/

import java.awt.Color;

// javac -cp .;stddraw.jar Chaos.java
// java -cp .;stddraw.jar Chaos.java 500

public final class Chaos {
   public static void main (final String[] args) {
      // initializes variables and StdDraw Canvas
      StdDraw.enableDoubleBuffering();
      final int pixels = Integer.parseInt(args[0]); // collects pixels
      StdDraw.setCanvasSize(pixels, pixels);
      final double min = -1.0;
      final double max = 1.0;
      final double span = 2.0; // total Scale for deciding pixels
      StdDraw.setScale(min, max);
      StdDraw.setPenRadius();

      // nested for loop to grab every spot on canvas
      for (int x = 0; x < pixels; x++) {
         for (int y = 0; y < pixels; y++) {
            // formula to grab x and y cords past on pixel point of graph
            final double realVal = min + x * span / pixels;
            final double imagVal = min + y * span / pixels;
            final Complex point = new Complex(realVal, imagVal);
            choose(point); // calls checking method
            StdDraw.point(realVal, imagVal); // draws point
         }
      }  
      StdDraw.show();
   }

   public static void choose (final Complex q) {
      // initializes constants needed for calculations
      final double converge = 0.001; 
      final Complex quart = new Complex(4, 0);
      final Complex r1    = new Complex(1, 0);
      final Complex r2    = new Complex(-1, 0);
      final Complex r3    = new Complex(0, 1);
      final Complex r4    = new Complex(0, -1);
      Complex x = q;

      for (int  i = 0; i < 100; i++) {
         // numerator of equation
         final Complex function = x.times(x).times(x).times(x).minus(r1);
         // denomenator of equation
         final Complex prime = quart.times(x).times(x).times(x);
         // finished equation which carries to next iteration
         x = x.minus(function.divides(prime));
         if (x.minus(r1).abs() <= converge) {
            StdDraw.setPenColor(Color.YELLOW);
            return;
         }
         if (x.minus(r2).abs() <= converge) {
            StdDraw.setPenColor(Color.RED);
            return;
         }
         if (x.minus(r3).abs() <= converge) {
            StdDraw.setPenColor(Color.BLUE);
            return;         
         } 
         if (x.minus(r4).abs() <= converge) {
            StdDraw.setPenColor(Color.GREEN);
            return;
         }
      }
      StdDraw.setPenColor(Color.BLACK); // if it doesn't converge
      return;
   }
}

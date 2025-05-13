/*
 * Author: Alex Merino, amerino2022@my.fit.edu
 * Course: CSE 1002, Section 1, Fall 2022
 * Project: Spirograph
*/

// javac -cp .;stddraw.jar Spirograph.java
// java -cp .;stddraw.jar Spirograph 180 40 15

public final class Spirograph {
   public static void main (final String[] args) {
      // initializes and parses command line arguments
      final double bigRad = Double.parseDouble (args[0]);
      final double smRad = Double.parseDouble (args[1]);
      final double offset = Double.parseDouble (args[2]);
      final int xScale = -400;
      final int yScale = 400;
      final double penRad = 0.005; // radius for pen
      final double counter = 0.01; // counter for 0 <= t <= 100

      StdDraw.setScale(xScale, yScale); // sets x and y axis large enough to fit shape
      StdDraw.setPenColor(StdDraw.WHITE); 
      StdDraw.clear(StdDraw.BLACK);
      StdDraw.setPenRadius(penRad);

      double t = 0.0;
      while (t <= 200) {
         final double x = (bigRad + smRad) * Math.cos(t) - (smRad + offset) // equation for x
                     * Math.cos(((bigRad + smRad) / smRad) * t);

         final double y = (bigRad + smRad) * Math.sin(t) - (smRad + offset) // equation for y
                     * Math.sin(((bigRad + smRad) / smRad) * t);
         t += counter; // counter for t
         StdDraw.point(x, y); // draws point
      }
   }
}

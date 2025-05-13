/*
 * Author: Alex Merino, amerino2022@my.fit.edu
 * Course: CSE 1002, Section 1, Fall 2022
 * Project: Fun with Squares
*/

// javac -cp .;stddraw.jar Square.java
// java -cp .;stddraw.jar Square 2.2 4 3

public final class Square {
   public static void main (final String[] args) {
      // initialize variables
      final double ratio = Double.parseDouble (args[0]);
      final int level = Integer.parseInt (args[1]);
      final int pattern = Integer.parseInt (args[2]);
      final double xScale = -400;
      final double yScale = 400;
      final double xCorner = 0;
      final double yCorner = 0;

      // sets up Canvas
      final double penRad = 0.005;
      StdDraw.setScale(xScale, yScale); 
      StdDraw.clear(StdDraw.WHITE);
      StdDraw.setPenRadius(penRad);

      // decides which pattern is chosen based on command argument
      if (pattern == 1) {
         drawRec1(level, xCorner, yCorner, yScale, ratio);  
      } else if (pattern == 2) {
         drawRec2(level, xCorner, yCorner, yScale, ratio);
      } else if (pattern == 3) {
         drawRec3(level, xCorner, yCorner, yScale, ratio);
      } else {
         drawRec4(level, xCorner, yCorner, yScale, ratio);
      }
        
   }

   static void drawer1 (final double x, final double y, final double sl) {   
      StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
      StdDraw.filledSquare(x, y, sl/2);
      StdDraw.setPenColor(StdDraw.BLACK);
      StdDraw.square(x, y, sl/2);
   }

   static void drawRec1 (final int lev, final double x, final double y,
         final double sl, final double r) {   
      if (lev != 0) {
         drawRec1(lev-1, x - sl/2, y + sl/2, sl/r, r);    // top left
         drawRec1(lev-1, x + sl/2, y + sl/2, sl/r, r);    // top right
         drawRec1(lev-1, x - sl/2, y - sl/2, sl/r, r);    // bottom left
         drawRec1(lev-1, x + sl/2, y - sl/2, sl/r, r);    // bottom right
         drawer1(x, y, sl); // smallest boxes print first
      }
   }

   static void drawer2 (final double x, final double y, final double sl) {   
      StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
      StdDraw.filledSquare(x, y, sl/2);
      StdDraw.setPenColor(StdDraw.BLACK);
      StdDraw.square(x, y, sl/2);
   }

   static void drawRec2 (final int lev, final double x, final double y, 
         final double sl, final double r) {   
      if (lev != 0) {
         drawRec2(lev-1, x - sl/2, y + sl/2, sl/r, r);    // top left
         drawRec2(lev-1, x + sl/2, y + sl/2, sl/r, r);    // top right
         drawRec2(lev-1, x - sl/2, y - sl/2, sl/r, r);    // bottom left
         drawer2(x, y, sl); // only the bottom right prints on top 
         drawRec2(lev-1, x + sl/2, y - sl/2, sl/r, r);    // bottom right
      }
   }

   static void drawer3 (final double x, final double y, final double sl) {   
      StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
      StdDraw.filledSquare(x, y, sl/2);
      StdDraw.setPenColor(StdDraw.BLACK);
      StdDraw.square(x, y, sl/2);
   }

   static void drawRec3 (final int lev, final double x, final double y, 
         final double sl, final double r) {   
      if (lev != 0) {
         drawer3(x, y, sl); // biggest square prints first
         drawRec3(lev-1, x - sl/2, y + sl/2, sl/r, r);    // top left
         drawRec3(lev-1, x + sl/2, y + sl/2, sl/r, r);    // top right
         drawRec3(lev-1, x - sl/2, y - sl/2, sl/r, r);    // bottom left
         drawRec3(lev-1, x + sl/2, y - sl/2, sl/r, r);    // bottom right
      }
   }

   static void drawer4 (final double x, final double y, final double sl) {   
      StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
      StdDraw.filledSquare(x, y, sl/2);
      StdDraw.setPenColor(StdDraw.BLACK);
      StdDraw.square(x, y, sl/2);
   }

   static void drawRec4 (final int lev, final double x, final double y, 
         final double sl, final double r) {   
      if (lev != 0) {
         drawRec4(lev-1, x - sl/2, y + sl/2, sl/r, r);    // top left
         drawRec4(lev-1, x + sl/2, y + sl/2, sl/r, r);    // top right
         drawer4(x, y, sl); // bottom prints last
         drawRec4(lev-1, x - sl/2, y - sl/2, sl/r, r);    // bottom left
         drawRec4(lev-1, x + sl/2, y - sl/2, sl/r, r);    // bottom right
      }
   }

}

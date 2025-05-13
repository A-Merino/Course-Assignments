/*
 * Author: Alex Merino, amerino2022@my.fit.edu
 * Course: CSE 1002, Section 4, Fall 2022
 * Project: Lost at Sea
*/

public final class Danger {
   // immutable class variables
   private final int xCord;
   private final int yCord;
   public static final int GRID_MIN = -10000;
   public static final int GRID_MAX = 10000;


   public Danger (final int x, final int y) { // constructor
      // ensures x and y are inside grid
      assert 0 < x && x <= GRID_MAX;
      assert GRID_MIN <= y && y <= GRID_MAX;
      // assigns values
      xCord = x;
      yCord = y;
   }

   public int getX () { // getter for x
      return xCord;
   }

   public int getY () { // getter for y
      return yCord;
   }
}

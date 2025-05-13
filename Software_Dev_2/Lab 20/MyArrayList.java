/*
 * Author: Alex Merino, amerino2022@my.fit.edu
 * Course: CSE 1002, Section 3, Fall 2022
 * Project: My ArrayList
*/


public final class MyArrayList extends MyListInterface {

   // class variables initialized
   private static final int DEFAULT_CAP = 10; // default size
   private static int currentCap;
   private int size; // records size
   public Integer[] arraylist;


   public MyArrayList () {
      this.arraylist = new Integer[DEFAULT_CAP];
      currentCap = 10; // ensures divide by 0 doesnt occur
   }

   public MyArrayList (final int initialCapacity) {
      if (initialCapacity < 0) {
         throw new IndexOutOfBoundsException(); // if a negative number
      }
      this.arraylist = new Integer[initialCapacity]; // makes a size
   }

   public int size () {
      return size;
   }

   public void clear () {
      for (int c = 0; c < size; c++) {
         arraylist[c] = null; // resets all Integers to null
      }
      size = 0; // resets size
   }

   public boolean add (final Integer element) {
      capacity(size); // makes sure capacity isn't full
      arraylist[size] = element;
      size++;
      return true;
   }

   public Integer get (final int index) {
      if (index > size || index < 0) {
         throw new IndexOutOfBoundsException(); // if not in array size
      }
      return arraylist[index];
   }

   public Integer set (final int index, final Integer element) {
      final Integer old = arraylist[index]; 
      arraylist[index] = element; // changes to new element
      return old;
   }

   public Integer remove (final int index) {
      if (index > size || index < 0) {
         throw new IndexOutOfBoundsException(); // if outside array
         
      }

      final Integer removed = arraylist[index];
      for (int i = index + 1; i < size; i++) {
         arraylist[i-1] = arraylist[i]; 

            
      }
      size--;
      arraylist[size] = null;
      return removed;
   }

   public boolean remove (final Integer element) {
      for (int r = 0; 0 < size; r++) {
         if (element.equals(arraylist[r])) { // if equal to element
            final int mover = size - r - 1;
            if (mover > 0) {
               System.arraycopy(arraylist, r + 1, arraylist, r, mover); // copies
               size--;
               arraylist[size] = null; // sets last element to null
               return true;
            }
         }
      }
      return false;
   }

   @java.lang.Override
   public String toString () {
      if (size == 0) { // if nothing in array
         return "[]";
      }
      String output = "[" + String.valueOf(arraylist[0]); // first value
      for (int s = 1; s < size; s++) {
         // for all values
         output = output + ", " + String.valueOf(arraylist[s]);
      }
      output += "]"; // end part
      return output;
   }

   private void capacity (final int capNeed) {
      if (capNeed >= currentCap) {
         currentCap = capNeed * 2; // double capacity
         final Integer[] temp = new Integer[capNeed * 2];
         for (int z = 0; z < size; z++) {
            temp[z] = arraylist[z]; // new array with larger capacity
         }
         this.arraylist = temp; // sets back to class array
      }
   }
}

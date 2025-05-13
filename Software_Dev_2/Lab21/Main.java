/*
 * Author: Alex Merino, amerino2022@my.fit.edu
 * Course: CSE 1002, Section 3, Fall 2022
 * Project: Selection Sort
*/

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.time.Duration;


// java -Dseed=4321567 Main 100 java.util.ArrayList
// java -Dseed=4321567 Main 100 java.util.LinkedList

public final class Main {

   private static final Random RNG = new Random(Long.getLong("seed", System.nanoTime()));

   public static void main (final String[] args) throws Exception {
      // variables from command line
      final int lSize = Integer.parseInt(args[0]);

      // reflection which I couldn't implement correctly
      final Class<?> clazz = Class.forName(args[1]);
      @SuppressWarnings("unchecked")
      final java.util.List<Integer> list = (java.util.List<Integer>) clazz.getDeclaredConstructor().newInstance();

      // sets all list indexes in order
      final int runThru = 10;
      for (int a = 1; a <= lSize; a++) {
         list.add(a);
      }

      long totalTime = 0;
      for (int i = 0; i < runThru; i++) {
         Collections.shuffle(list, RNG); // shuffle
         final long startTime = System.nanoTime(); // time start
         sort(list);
         final long endTime = System.nanoTime(); // time end
         totalTime += endTime - startTime;
      }
      totalTime = totalTime / runThru; // average time
      System.out.println(Duration.ofNanos(totalTime));

   }

   public static void sort (final List<Integer> data) {
      for (int i = 0; i < data.size(); i++) {
         int min = i;
         /* find the min element in the unsorted data[i, i+1, .., n-1] */
         /* assume initially that min is the first element in the range */
         for (int j = i + 1; j < data.size(); j++) {
            /* if the 'j'th element is less, then it is the new minimum */
            if (data.get(j) < data.get(min)) {
               min = j;
            }
         }
         /* swap data at 'min' with data at 'i' */
         final Integer temp = data.get(min);
         data.set(min, data.get(i));
         data.set(i, temp);
      }
   }
}

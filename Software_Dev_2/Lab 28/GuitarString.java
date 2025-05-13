/*
 * Author: Alex Merino, amerino2022@my.fit.edu
 * Course: CSE 1002, Section 3, Fall 2022
 * Project: Guitar
*/

import java.util.ArrayDeque;

public final class GuitarString extends ArrayDeque<Double> {

   // class variables
   private final ArrayDeque<Double> strum = new ArrayDeque<Double>();
   private final double freq;

   public GuitarString (final double frequency) {
      this.freq = frequency;
   }

   public void pluck () { // fills buffer with random values
      final double buffer = Math.ceil(MyAudio.SAMPLE_RATE / freq);
      for (int k = 0; k < buffer; k++) {
         strum.addLast(Math.random() - 0.5);
      }

   } 

   // karplus-strong algorithm
   public void tic () {
      final double karplus = 0.996;
      final double gone = strum.poll();
      final double update = karplus * (0.5 * (gone + strum.peekFirst()));
      strum.addLast(update);

   }

   public double sample () { // grabs the first value
      return strum.peekFirst();
   }

   public double getFreq () { // to get frequency
      return freq;
   }
}

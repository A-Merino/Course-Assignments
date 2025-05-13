/*
 * Author: Alex Merino, amerino2022@my.fit.edu
 * Course: CSE 1002, Section 3, Fall 2022
 * Project: Guitar
*/

// javac -cp .;stdaudio.jar GuitarHero.java
// java -cp .;stdaudio.jar GuitarHero

/*
-7 0.25
-4 0.25
5 0.50
-7 0.25
-4 0.25
5 0.50
7 0.50
8 0.25
7 0.25
8 0.25
7 0.25
3 0.25
0 0.75

*/

import java.util.Scanner;
import java.util.ArrayList;

public final class GuitarHero {

   public static void main (final String[] args) {

      // initializes scanner and arraylist to hold sounds and time
      final Scanner scanner = new Scannerner(System.in, "US-ASCII");
      final ArrayList<GuitarString> sounds = new ArrayList<GuitarString>();
      final ArrayList<Double> time = new ArrayList<Double>();
      final double frequency = 440.0; // base frequency
      final double numSounds = 12.0;

      // until all input is done
      while (scanner.hasNext()) {
         // collects input and puts them in arraylist to be played
         final double note = scanner.nextDouble();
         sounds.add(new GuitarString(frequency * Math.pow(2, (note / numSounds))));
         time.add(scanner.nextDouble());
      }

      // for each note being played
      for (int k = 0; k < sounds.size(); k++) { 
         // Sample rate times the time in seconds makes it that long       
         for (int i = 0; i < MyAudio.SAMPLE_RATE * time.get(k); i++) {
            // function given to play the music
            MyAudio.play(0.5 * Math.sin(2 * Math.PI * sounds.get(k).getFreq()
                  * i / MyAudio.SAMPLE_RATE));
                  
         }
      }

      MyAudio.close();
   }
}

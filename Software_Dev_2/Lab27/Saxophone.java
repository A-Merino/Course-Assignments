/*
 * Author: Alex Merino, amerino2022@my.fit.edu
 * Course: CSE 1002, Section 3, Fall 2022
 * Project: Eb Alto Saxophone
*/


// importing classes
import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;


public final class Saxophone {
   public static void main (final String[] args) {
      final Scanner scan = new Scanner(System.in, "US-ASCII");
      // sets the notes to the fingers
      final Map<Character, Set<Integer>> keys = setKeys();
      // for all the songs until no more output
      final ArrayList<int[]> output = new ArrayList<int[]>();

      while (scan.hasNext()) {
         // initializes output for fingers, and puts song into char array
         int[] fingers = new int[10];
         final String song = scan.nextLine();
         final char[] letters = song.toCharArray();

         // for all the notes add fingers if necessary 
         for (int i = 0; i < letters.length; i++) {
            if (i == 0) {
               // for the first note there is no previous note
               fingers = addFing(keys, fingers, letters[i]);
            } else {
               fingers = addFing(keys, fingers, letters[i], letters[i - 1]);
            }
         }
         // adds the array to the output
         output.add(fingers);
      }

      // prints out amount of fingers for all the songs
      for (final int [] fingers : output) {
         for (final int fin : fingers) {
            System.out.print(fin + " ");
         }
         System.out.println();
      }
   }

   public static HashMap<Character, Set<Integer>> setKeys () {
      // sets all the notes and keys and returns to main function
      final HashMap<Character, Set<Integer>> key = new HashMap<>();
      key.put('c', Set.of(2, 3, 4, 7, 8, 9, 10));
      key.put('d', Set.of(2, 3, 4, 7, 8, 9));
      key.put('e', Set.of(2, 3, 4, 7, 8));
      key.put('f', Set.of(2, 3, 4, 7));
      key.put('g', Set.of(2, 3, 4));
      key.put('a', Set.of(2, 3));
      key.put('b', Set.of(2));
      key.put('C', Set.of(3));
      key.put('D', Set.of(1, 2, 3, 4, 7, 8, 9));
      key.put('E', Set.of(1, 2, 3, 4, 7, 8));
      key.put('F', Set.of(1, 2, 3, 4, 7));
      key.put('G', Set.of(1, 2, 3, 4));
      key.put('A', Set.of(1, 2, 3));
      key.put('B', Set.of(1, 2));

      return key;
   }


   public static int[] addFing (final Map<Character, Set<Integer>> keys,
         final int[] fingers, final char note) {

      // for the first note all fingers are used
      for (int j = 0; j <= fingers.length; j++) {
         if (keys.get(note).contains(j)) {
            fingers[j - 1]++;
         }
      }

      return fingers;
   }

   public static int[] addFing (final Map<Character, Set<Integer>> keys,
         final int[] fingers, final char note, final char lastNote) {

      for (int k = 0; k <= fingers.length; k++) {
         // same as first note with added constraint of finger not being in previous note
         if (keys.get(note).contains(k)
               && isSame(lastNote, keys.get(note), keys.get(lastNote), k)) {
            fingers[k - 1]++;
         }
      }

      return fingers;
   }

   
   // similar to intersection function
   public static boolean isSame (final char note, final Set<Integer> first,
         final Set<Integer> second, final int finger) {
      // create new set to take away from
      Set<Integer> mixed = new HashSet<>();
      // if this note has more fingers than last then take away same fingers in last as first
      if (first.size() >= second.size()) {
         mixed = new HashSet<>(first);
         mixed.removeAll(second);
      } else { // if second is bigger do the same thing
         mixed = new HashSet<>(second);
         mixed.removeAll(first);
      }

      // if the current finger is still in note then add one
      if (mixed.contains(finger)) {
         return true;
      }

      return false;
   }

}

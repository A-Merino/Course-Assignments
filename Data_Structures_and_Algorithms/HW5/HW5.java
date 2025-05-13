/*

   Author: Alex Merino
   Email: amerino2022@my.fit.edu
   Course: CSE 2010
   Section: E1
   Description of this file: Implements a SkipList data structure which holds
                             a list of elements with a key and value which can 
                             be added or removed in O(logN) time

*/

// imports classes
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class HW5 {

   public static void main(String[] args) throws FileNotFoundException {

      // initializes scanner and SkipList
      File file = new File(args[0]);
      Scanner scan = new Scanner(file);
      int min = 0;
      int max = 2400;
      SkipList<Integer, String> list = new SkipList<Integer, String>(-1, 2401);
      
      while(scan.hasNext()) { // until end of file
         // prints actions
         String com = scan.next();
         System.out.print(com);

         if (com.equals("DisplayActivity")) {

            // calls get() function of SkipList to return the exact key specified
            // returns null if key is not in list
            int time = scan.nextInt();
            System.out.printf(" %04d ", time);
            String dis = list.get(time);

            if (dis == null) {
               System.out.println("none");
            } else {
               System.out.println(dis);
            }

         } else if (com.equals("AddActivity")) {

            // Calls put() function of SkipList to add a new item
            int time = scan.nextInt();
            String a = scan.next();
            System.out.printf(" %04d %s%n", time, list.put(time, a));

         } else if (com.equals("DeleteActivity")) {

            // Calls remove() function and returns the value of removed item
            // if key doesn't exists outputs "none" for null
            int time = scan.nextInt();
            System.out.printf(" %04d ", time);            
            String rem = list.remove(time);

            if (rem == null) {
               System.out.println("none");
            } else {
               System.out.println(rem);
            }
            
         } else if (com.equals("DisplayActivitiesBetweenTimes")) {

            // Calls subMap function which returns an array of the item from specified start and end times
            // uses ceilingEntry() to grab start time and floorEntry() to grab end time
            int start = scan.nextInt();
            int end = scan.nextInt();
            Object[] arr = list.subMap(start, end);

            System.out.printf(" %04d %04d ", start, end);
            for(Object act : arr) {
               System.out.printf("%s ", act);
            }
            System.out.println();


         } else if (com.equals("DisplayActivitiesFromStartTime")) {

            // Calls subMap function which returns an array of the item from specified start time to end of list
            // uses ceilingEntry() to grab start time and floorEntry() to grab end time
            int start = scan.nextInt();
            Object[] arr = list.subMap(start, max);

               System.out.printf(" %04d", start);
            if(arr == null) {
               System.out.println(" none");
            } else {
               for(Object act : arr) {
                  System.out.printf(" %s", act);
               }
               System.out.println();
            }

         } else if (com.equals("DisplayActivitiesToEndTime")) {

            // Calls subMap function which returns an array of the item from beginning of list to specified end time
            // uses ceilingEntry() to grab start time and floorEntry() to grab end time
            int end = scan.nextInt();
            Object[] arr = list.subMap(min, end);

               System.out.printf(" %04d", end);
            if(arr == null) {
               System.out.println(" none");
            } else {
               for(Object act : arr) {
                  System.out.printf(" %s", act);
               }
               System.out.println();
            }

         } else if (com.equals("DisplayAllActivities")) {

            // Calls subMap function which returns an array of all the items in the list
            Object[] arr = list.subMap(min, max);

            if(arr == null) {
               System.out.println(" none");
            } else {
               for(Object act : arr) {
                  System.out.printf(" %s", act);
               }
               System.out.println();
            }
            
         } else if (com.equals("PrintSkipList")) {
            // prints entire SkipList data structure
            System.out.println();
            System.out.print(list);
         }
      }
   }
}
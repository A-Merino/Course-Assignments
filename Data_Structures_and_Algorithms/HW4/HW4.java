/*

   Author: Alex Merino
   Email: amerino2022@my.fit.edu
   Course: CSE 2010
   Section: E1
   Description of this file: Uses the heap data structure to efficiently sort data in a way where the 
                             highest bid is at index zero (the root) of the data structure for easy access

*/

// import all classes needed
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.Comparable;

public class HW4 {


   /*
      Bid class which stores all of the data of a bid
      also implements comparable class
      Variables:
         time: holds time as an integer value
         name: holds the name of the buyer
         price: holds the price the buyer 
         amount: holds the amount of items the buyer wishes to buy
   */
   static class Bid implements Comparable<Bid> {
      int time;
      String name;
      double price;
      int amount;

      // public constructor of Bid class
      public Bid (int t, String n, double p, int a) {
         time   = t;
         name   = n;
         price  = p;
         amount = a;

      }

      // compareTo method to set a natural ordering of Bid object 
      @java.lang.Override
      public int compareTo(Bid o) {
         // checks which price is larger
         if (this.price < o.price) {
            return -1;
         } else if (this.price > o.price) {
            return 1;
         } else {
            // If price is the same check which time is smaller (earlier) and return
            if (this.time < o.time) {
               return 1;
            }
            return -1;
         }
      }


   }


   // main method
   public static void main(String[] args) throws FileNotFoundException {
      // creates File object and Scanner for program
      File input = new File(args[0]);
      Scanner scan = new Scanner(input);
      // initializes PriorityQueue
      HeapPriorityQueue<Bid, Integer, String> heap = new HeapPriorityQueue<Bid, Integer, String>();
      
      double minPrice = 0.0; // double variable that holds minimum price

      // until end of file
      while(scan.hasNext()) {

         // Each line of input starts with event and time, so collect once
         String event = scan.next();
         int time = scan.nextInt();

         /* 
            If file calls for the update price function:
               Collect new price
               print new price with rest of information
         */
         if (event.equals("UpdateMinimumAcceptablePrice")) {
            minPrice = scan.nextDouble();
            System.out.printf("%s %04d %.2f%n", event, time, minPrice);
         } 

         /*
            If file calls for enter bid function
               Collect data
               create new Bid object and print information
               insert item as an entry and sort heap
         */
         else if (event.equals("EnterBid")) {

            String name = scan.next();
            double price = scan.nextDouble();
            int amount = scan.nextInt();

            Bid cur = new Bid(time, name, price, amount);
            System.out.printf("%s %04d %s %.2f %d%n", event, time, name, price, amount);

            heap.insert(cur, time, name);
            heap.heapify();

         }

         // If file calls for Selling an item
         else if (event.equals("SellOneItem")) {
            System.out.printf("%s %04d ", event, time);

            //check to see if there are any entries in tree
            // if no then print no bids
            if (heap.isEmpty()){
               System.out.println("NoBids");
            }

            // check to see if the highest bidding price is high enough
            // print highest bidding is too low if not
            else if (heap.max().getBid().price < minPrice) {
               System.out.println("HighestBiddingPriceIsTooLow");
            }

            /*
               If the highest bidding person only wants to buy one item:
                  remove them from heap (heap will automatically sort)
                  print the name and bidding price of bid removed
            */
            else if (heap.max().getBid().amount == 1) {
                Entry<Bid, Integer, String> out = heap.removeMax();
                System.out.printf("%s %.2f%n", out.getName(), out.getBid().price);
             }

             /*
               If the highest bidding person wants to buy more than one item:
                  Lower the amount they want to buy by one
                  print name and bidding price

             */
             else {
               heap.max().getBid().amount = heap.max().getBid().amount - 1;
               System.out.printf("%s %.2f%n", heap.max().getName(), heap.max().getBid().price);
             }
               

         }

         /*
            If file calls for highest bid to be displayed
               print starting information
               if heap is not empty print max bid information
         */
         else if (event.equals("DisplayHighestBid")) {
            System.out.printf("%s %04d ", event, time);
            if (!heap.isEmpty()) {
               Entry<Bid, Integer, String> display = heap.max();
               System.out.printf("%s %04d %.2f %d", display.getName(), display.getTime(), display.getBid().price, display.getBid().amount);
            }
            System.out.println();
         }
      }
   }
} 
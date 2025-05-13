/*

   Author: Alex Merino
   Email: amerino2022@my.fit.edu
   Course: CSE 2010
   Section: E1
   Description of this file: Collects data from txt files of friends
                             and can and or remove friends based off
                             the queries asked 

*/


// imports important Classes for scanning
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;


public class HW6 {

   public static void main(String[] args) throws FileNotFoundException {

      // sets up both scanners for collection of data and queries
      File frPath = new File(args[0]);
      Scanner frScan = new Scanner(frPath);
      File actPath = new File(args[1]);
      Scanner actScan = new Scanner(actPath);

      int userNum = frScan.nextInt(); // collects amount of people in the graph
      Graph graph = new Graph(userNum); // creates graph with amount of people

      // for the starting friendships, they must be added
      while (frScan.hasNext()) {
         graph.addFriend(frScan.next(), frScan.next());
      }

      // for all of the queries in the action file
         // add a friendship and print completion
         // remove a friendship and print completion
         // Check to see if there is a path for the two users to be friends
      while(actScan.hasNext()) {
         String act = actScan.next();
         System.out.print(act + " ");
         if (act.equals("AddFriendship")) {
            System.out.println(graph.addFriend(actScan.next(), actScan.next()));
            
         } else if (act.equals("RemoveFriendship")) {
            System.out.println(graph.removeFriend(actScan.next(), actScan.next()));

         } else if (act.equals("WantToBefriend")) {
            System.out.println(graph.wantFriend(actScan.next(), actScan.next()));
         }
      }
   }
}
/*

  Author: Alex Merino
  Email:amerion2022@my.fit.edu
  Course: CSE 2010
  Section: E1
  Description of this file:

 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Comparator;
import java.util.ArrayList;

public class HW1 {

public static class SinglyLinkedList<E> implements Cloneable {
  
  private static class Node<E> {

    /** The element stored at this node */
    E element;            // reference to the element stored at this node

    /** A reference to the subsequent node in the list */
   Node<E> next;         // reference to the subsequent node in the list

   
    public Node(E e, Node<E> n) {
      element = e;
      next = n;
    }

   // Accessor Methods
    public E getElement() { return element; }

   
    public Node<E> getNext() { return next; }

    // Modifier methods
    public void setNext(Node<E> n) { next = n; }


   public boolean hasNext() {
      if (next.getElement() == null) {
         return false;
      }
      return true;
   }
  } //----------- end of nested Node class -----------

  // instance variables of the SinglyLinkedList
  /** The head node of the list */
  private Node<E> head = null;               // head node of the list (or null if empty)

  /** The last node of the list */
  private Node<E> tail = null;               // last node of the list (or null if empty)

  /** Number of nodes in the list */
  private int size = 0;                      // number of nodes in the list

  /** Constructs an initially empty list. */
  public SinglyLinkedList() { }              // constructs an initially empty list

  // access methods
  
  public int size() { return size; }

  
  public boolean isEmpty() { return size == 0; }

 
  public E first() {             // returns (but does not remove) the first element
    if (isEmpty()) return null;
    return head.getElement();
  }

  public Node<E> next() {             // returns (but does not remove) the first element
    if (isEmpty()) return null;
    return head.getNext();
  }

  
  public E last() {              // returns (but does not remove) the last element
    if (isEmpty()) return null;
    return tail.getElement();
  }

  // update methods
  
  public void addFirst(E e) {                // adds element e to the front of the list
    head = new Node<>(e, head);              // create and link a new node
    if (size == 0)
      tail = head;                           // special case: new node becomes tail also
    size++;
  }

  
  public void addLast(E e) {                 // adds element e to the end of the list
    Node<E> newest = new Node<>(e, null);    // node will eventually be the tail
    if (isEmpty())
      head = newest;                         // special case: previously empty list
    else
      tail.setNext(newest);                  // new node after existing tail
    tail = newest;                           // new node becomes the tail
    size++;
  }

  
  public E removeFirst() {                   // removes and returns the first element
    if (isEmpty()) return null;              // nothing to remove
    E answer = head.getElement();
    head = head.getNext();                   // will become null if list had only one node
    size--;
    if (size == 0)
      tail = null;                           // special case as list is now empty
    return answer;
  }

  public E removeNext() {                   // removes and returns the first element
    if (isEmpty()) return null;              // nothing to remove
    E answer = head.getNext().getElement();
    head = head.getNext().getNext();                   // head stays the same
    size--;
    if (size == 0)
      tail = null;                           // special case as list is now empty
    return answer;
  }

  @SuppressWarnings({"unchecked"})
  public boolean equals(Object o) {
    if (o == null) return false;
    if (getClass() != o.getClass()) return false;
    SinglyLinkedList other = (SinglyLinkedList) o;   // use nonparameterized type
    if (size != other.size) return false;
    Node walkA = head;                               // traverse the primary list
    Node walkB = other.head;                         // traverse the secondary list
    while (walkA != null) {
      if (!walkA.getElement().equals(walkB.getElement())) return false; //mismatch
      walkA = walkA.getNext();
      walkB = walkB.getNext();
    }
    return true;   // if we reach this, everything matched successfully
  }

  @SuppressWarnings({"unchecked"})
  public SinglyLinkedList<E> clone() throws CloneNotSupportedException {
    // always use inherited Object.clone() to create the initial copy
    SinglyLinkedList<E> other = (SinglyLinkedList<E>) super.clone(); // safe cast
    if (size > 0) {                    // we need independent chain of nodes
      other.head = new Node<>(head.getElement(), null);
      Node<E> walk = head.getNext();      // walk through remainder of original list
      Node<E> otherTail = other.head;     // remember most recently created node
      while (walk != null) {              // make a new node storing same element
        Node<E> newest = new Node<>(walk.getElement(), null);
        otherTail.setNext(newest);     // link previous node to this one
        otherTail = newest;
        walk = walk.getNext();
      }
    }
    return other;
  }

  public int hashCode() {
    int h = 0;
    for (Node walk=head; walk != null; walk = walk.getNext()) {
      h ^= walk.getElement().hashCode();      // bitwise exclusive-or with element's code
      h = (h << 5) | (h >>> 27);              // 5-bit cyclic shift of composite code
    }
    return h;
  }

  
  public String toString() {
    StringBuilder sb = new StringBuilder("(");
    Node<E> walk = head;
    while (walk != null) {
      sb.append(walk.getElement());
      if (walk != tail)
        sb.append(", ");
      walk = walk.getNext();
    }
    sb.append(")");
    return sb.toString();
  }


      

}


   /*
      The Time class holds the way the program tells time
      Also implements the comparable interface
      Useful to help regulate not going over 60 minutes in an hour
   */

   static class Time implements Comparable<Time> {

      // time integer holds the number for the time
      int time;

      // class constructor
      public Time (int t) {
         time = t;
      }

      /*
         addTime function changes the time integer the 
         method is being applied to with the parameter
      */

      public int addTime(int p) {
         /*
            Create an integer with just the minutes
            copy that integer for use if the addition goes to new hour
            add parameter to minutes 
         */
         int mins = time % 100;
         int copy = mins;
         mins += p;

         /*
            if the total amount of minutes goes over an hour
               then modulo 60 and add an hour to the time minus
               the minutes previous held plus the new minutes
            if not then just add the parameter and nothing changes  
         */
         if (mins > 60) {
            mins -= 60;
            time += 100 + mins - copy;
         } else {
            time += p;
         }

         return time; // return the new time if needed to output
      }


      /* 
         toString method used to output as an integer
      */
      @java.lang.Override
      public String toString() {
         StringBuilder out = new StringBuilder();
         out.append(time);
         return out.toString();
      }

      /*
         compareTo method used to compare times
            if first parameter is less than second return negative number 
            if first parameter is greater than second return positive number 
            if neither then they are equal which returns 0 
      */

      public int compareTo(Time o) {
         if (this.time < o.time) {
            return -1;
         } else if (this.time > o.time) {
            return 1;
         } else {
            return 0;
         }
      }


      /*
         boolean method that checks if it is bundleable within 5 minutes
         it adds 5 minutes to parameter and checks if new time is still in 5 minutes
      */

      public boolean withinFive(Time o) {
         Time checker = new Time(this.time);
         checker.addTime(5);
         if (o.time > checker.time) {
            return false;
         }
         return true;
      }

   }

   /* 
      The order class is for storing the orders that come in
      from the txt file
   */

   static class Order {


      /* 
         Class variables 
         time holds the time
         customer holds the customer name
         bookNum holds the number of books
         deviceNum hold the number of devices
      */
      Time time;
      String customer;
      int bookNum;
      int deviceNum;

      // Class constructor
      public Order (Time t, String c, int b, int d) {
         time = t;
         customer = c;
         bookNum = b;
         deviceNum = d;
      }


      /*
         toString method for when the order needs to be printed
      */
      @java.lang.Override
      public String toString() {
         String out = "CustomerOrder " + time + " " + customer + " " + bookNum + " " + deviceNum;
         return out;
      }

   }



   /*
      Worker object class is used as a place holder for worker names
   */

   static class Worker {

      String worker; // worker holds the worker name

      // Class constructor
      public Worker(String w) {
         worker = w;
      }

      // toString method to print worker
      @java.lang.Override
      public String toString() {
         return worker;
      }
   }





   /*
      Assignment class is used for when a worker is assigned to an order
   */
   
   static class Assignments {
      /*
         Class Variables:
            worker holds the name of the worker
            order holds the order object
            assignTime holds the time assigned
      */
      Worker worker;
      Order order;
      Time assignTime;

      // Class constructor
      public Assignments(Worker w, Order o, Time t) {
         worker = w;
         order = o;
         assignTime = t;
      }


      /*
         Set time method used for if the order has to wait since all workers are busy
      */

      public void setTime(Time t) {
         assignTime = t;
      }

      /*
         toString method for when the assignment needs to be printed
      */

      @java.lang.Override
      public String toString() {
         StringBuilder out = new StringBuilder();
         out.append("WorkerAssignment " + assignTime + " " + worker + " " + order.customer);
         return out.toString();
      }

   }







   
   /*
      The Complete object class is the direct aftermath of the Assignments
      class, where we hold for the orders that are completed
   */

   static class Complete {

      /*
         Class Variables:
            time holds the time
            cust holds the customer name for printing purposes
            worker holds the worker to put back into available worker linkedlist
            totalTime holds vaule for max time used
      */
      Time time;
      String cust;
      Worker worker;
      int totalTime;


      // Class constructor
      public Complete (Time t, String c, Worker w, int p) {
         time = t;
         cust = c;
         worker = w;
         totalTime = p;
      }

      // toString method which is used to print order completion
      @java.lang.Override
      public String toString() {
         StringBuilder out = new StringBuilder();
         out.append("OrderCompletion " + time + " " + cust);
         return out.toString();
      }
   }

   /*
      The Task object class holds all input that isn't a CustomerOrder
   */

   static class Task {

      /*
         Class Variables:
            input holds the type of assignment from the input
            time holds when the task was called 
      */
      String input;
      Time time;

      // Class constructor
      public Task (String n, Time t) {
         input = n;
         time = t;
      }

   }



   /*
     Main function that collects the input from the first into linkedlists
     Throws FileNotFoundException and CloneNotSupportedException 
   */
   public static void main(String[] args) throws FileNotFoundException, CloneNotSupportedException {
      // Creates the Scanner for the .txt input
      final File input = new File(args[0]); 
      final Scanner scan = new Scanner(input, "US-ASCII");


      /*
         Method variables:
            orderList holds all of the orders in a linkedlist
            lines holds all of the tasks in a linkedlist
            avail holds all of the workers that are available
            custOrd holds the string input to see if it needs to be put into orderList
      */
      SinglyLinkedList<Order> orderList = new SinglyLinkedList<Order>();
      SinglyLinkedList<Task> lines = new SinglyLinkedList<Task>();
      SinglyLinkedList<Worker> avail = new SinglyLinkedList<Worker>();
      final String custOrd = "CustomerOrder";

      // Worker Names
      avail.addLast(new Worker("Alice"));
      avail.addLast(new Worker("Bob"));
      avail.addLast(new Worker("Carol"));
      avail.addLast(new Worker("David"));
      avail.addLast(new Worker("Emily"));


      /*
         For all of the input in the .txt file it will sort them into a 
         CustomerOrder or a non CustomerOrder (Task)
      */

      while(scan.hasNext()) {
         final String event = scan.next(); // Collect identifier
         final Time time = new Time(scan.nextInt()); // Collect time

         /*
            if the identifier is a CustomerOrder
               then collect the rest of the data
               insert it at the end of the orderList
            if not
               The input is a Task and is inserted at end of lines

         */
         if (event.equals(custOrd)) {
            String customer = scan.next();
            int books = scan.nextInt();
            int dev = scan.nextInt();
            assert books + dev <= 10;
            orderList.addLast(new Order(time, customer, books, dev));
         }
         else {
            lines.addLast(new Task(event, time));
         }
      }

      /*
         Create a copy of the orderList for the method
         Run the completeOrders method with
            orderList copy
            avail worker list
            lines Task list
         print the rest of the completed orders 
      */

      SinglyLinkedList<Order> copy = orderList.clone();
      completeOrders(copy, avail, lines);


   }



   /*
      completeOrders goes through every minute of the day to see when the output needs to be printed
   */
   public static void completeOrders (SinglyLinkedList<Order> orders, SinglyLinkedList<Worker> workers, SinglyLinkedList<Task> others) 
         throws CloneNotSupportedException{

            /*
               Method Variables:
                  done holds the completeed orders as a Complete Object
                  working temporarily holds the Assignments Objects before they are translated to Complete
                  The Strings are used for the Task objects
                  MaxTime is used to hold the maximum amount of time an order takes
                  bundy is to check if the previous Assignment can be bundled with the next assignment
            */
      SinglyLinkedList<Complete> done = new SinglyLinkedList<Complete>();
      SinglyLinkedList<Assignments> working = new SinglyLinkedList<Assignments>();
      final String wrkLst = "PrintAvailableWorkerList";
      final String asgnLst = "PrintWorkerAssignmentList";
      final String fillTime = "PrintMaxFulfillmentTime";
      int maxTime = 0;
      Assignments bundy = new Assignments(null, null, new Time(0));


      /*
         This for loop goes through every minute of the day to check when
         an object needs to be output
      */

      for (int i = 0;i < 2400; i++) {

         // the if statement skips numbers that end in 60-99 since they are not minutes of the day
         if (i % 100 >= 60) {
            continue;
         }

         Time current = new Time(i); // creates the current time as a Time object


         /*
            If there are any completed orders that are at this time or previously then
               add the worker back to the available list
               update the maxTime if needed
               print and remove the first object from the done linkedlist
         */
         if (done.size() != 0 && current.compareTo(done.first().time) >= 0) {
            workers.addLast(done.first().worker);
            if (done.first().totalTime > maxTime) {
               maxTime = done.first().totalTime;
            }

            System.out.println(done.removeFirst());
            
         }

         // if the time matches the time of the order then print order
         if (orders.size() != 0 && current.compareTo(orders.first().time) == 0) {    
            System.out.println(orders.first());
         }



         // same as previous if statement, just also checks if there are available workers
         if (orders.size() != 0 && workers.size() != 0 && current.compareTo(orders.first().time) >= 0) {

            /*
               if there is a possibility of bundling then 
                  add the extra books/devices needed to the first order
                  Add 5 minutes and the other extra to the totalTime for maxTime
                  add a new Assignment with the previous worker
                  print out WorkerAssignment
                  compute Assignment and convert to Complete 
            */
            if (bundy.assignTime.withinFive(current) && findBund(bundy, orders.first())) {
               done.last().time.addTime(orders.first().deviceNum + orders.first().bookNum);
               done.last().totalTime += 5 + orders.first().deviceNum + orders.first().bookNum;
               working.addLast(new Assignments(done.first().worker, orders.removeFirst(), current));
               System.out.println(working.last());
               done.addLast(completer(working.removeFirst()));
               
            } else {

               /*
               if no bundling then 
                  add a new Assignment with the next worker
                  assign Assignment to see if it can get bundled
                  print out WorkerAssignment
                  compute Assignment and convert to Complete 
            */
                  
               working.addLast(new Assignments(workers.removeFirst(), orders.removeFirst(), current));
               bundy = working.last();
               System.out.println(working.last());
               done.addLast(completer(working.removeFirst()));
            }
            
         }


         
         // if time is congruent with a task object then it will print out that task

         if (others.size() != 0 && current.compareTo(others.first().time) >= 0) {
            Task printer = others.removeFirst(); // take Task out of linkedlist
            

            /*
               if the task is to print Availalbe worker list:
                  create a clone of current worker list
                  create new StringBuilder for output
                  then for all the worker print with a space after
                  and print to output
            */
            if (printer.input.equals(wrkLst)) {
               SinglyLinkedList<Worker> workCop = workers.clone();
               StringBuilder out = new StringBuilder();
               out.append("AvailableWorkerList " + printer.time + " ");
               while (workCop.size() != 0) {
                  out.append(workCop.removeFirst().worker + " ");
               }

               System.out.println(out);

            } else if (printer.input.equals(asgnLst)) {
               /*
                  if the task is to print Assignment list:
                     create a clone of current Complete list
                     create new StringBuilder for output
                     while loop
                     and print to output
               */

               SinglyLinkedList<Complete> comCop = done.clone();
               StringBuilder out = new StringBuilder();
               out.append("WorkerAssignmentList " + printer.time + " ");

               while(comCop.size() != 0) {
                  /*
                     for all of the completed orders in the list
                        create a Worker to check if the next worker is the same
                        add the output formatting
                        if the next worker is the same then do special formatting
                        if not then it skips
                  */

                  Worker bun = comCop.first().worker;
                  out.append(comCop.first().worker + ":" + comCop.first().cust);
                  comCop.removeFirst();
                  while (comCop.first() != null && bun.equals(comCop.first().worker)) {
                     out.append("," + comCop.removeFirst().cust);
                  }
                  out.append(" ");
               }

               System.out.println(out);

            } else if (printer.input.equals(fillTime)) {
               // if Task is maxTime then print the current maxTime 
               StringBuilder out = new StringBuilder();
               out.append("MaxFulfillmentTime " + printer.time + " " + (maxTime+5));

               System.out.println(out);

            }
            

         }
      }
   }

   /*
      findBund checks to see if the previous Assignments "s"
      and the current Order "r" can be bundled 
   */

   public static boolean findBund (Assignments s, Order r) {
      /*
         If both of one category are the zero and the sum of the other
         is not greater than 10 they can be bundled since the 5 minute
         rule was checked already
      */
      if (s.order.deviceNum == 0 && r.deviceNum == 0 && s.order.bookNum + r.bookNum <= 10) {
         return true;
      } else if (s.order.bookNum == 0 && r.bookNum == 0 && s.order.deviceNum + r.deviceNum <= 10) {
         return true;
      }
      return false;
   }


   /*
      The completer method transforms Assignments into Compete objects
      It takes an Assignments as the parameter
   */
   
   public static Complete completer (Assignments order) {

      /*
         Create a new time that can be changed
         create new ints with the amount of books and devices
         create total int to track total number of minutes needed to add
      */
      Time updated = new Time (order.order.time.time);
      int books = order.order.bookNum;
      int dev = order.order.deviceNum;
      int total = 0;

      // if there is only one category then total is 10 + amount of that category
      // if there is both then it is 15 + amount of both books and devices
      if (order.order.bookNum == 0 || order.order.deviceNum == 0) {
         total = 10 + books + dev;
      } else {
         total = 15 + books + dev;
      }
      // update updated
      updated.addTime(total);
      
   // return new Complete Object
   return new Complete(updated, order.order.customer, order.worker, total);
   

   }

}
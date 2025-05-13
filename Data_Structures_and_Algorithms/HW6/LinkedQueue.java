public class LinkedQueue<E> {
  
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

  private Node<E> tail = null;

  
  /** Number of nodes in the list */
  private int size = 0;                      // number of nodes in the list

  /** Constructs an initially empty list. */
  public LinkedQueue() { }              // constructs an initially empty list

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
  
  public void add(E e) {                // adds element e to the front of the list
    head = new Node<>(e, head);              // create and link a new node
    if (size == 0)
      tail = head;                           // special case: new node becomes tail also
    size++;
  }

  
  

  
  public E remove() {                   // removes and returns the first element
    if (isEmpty()) return null;              // nothing to remove
    E answer = head.getElement();
    head = head.getNext();                   // will become null if list had only one node
    size--;
    if (size == 0)
      tail = null;                           // special case as list is now empty
    return answer;
  }
  
  @java.lang.Override
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

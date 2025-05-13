public interface PriorityQueue<B,T,N> {

  // returns number of items in heap
  int size();

  // checks if heap is empty
  boolean isEmpty();

  
  Entry<B,T,N> insert(B bid, T time, N name) throws IllegalArgumentException;

  // returns max
  Entry<B,T,N> max();

  // removes and returns max
  Entry<B,T,N> removeMax();
}
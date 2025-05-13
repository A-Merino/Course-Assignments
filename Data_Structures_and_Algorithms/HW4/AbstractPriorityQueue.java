import java.util.Comparator;

public abstract class AbstractPriorityQueue<B,T,N> implements PriorityQueue<B,T,N> {
  //---------------- nested PQEntry class ----------------
  /**
   * A concrete implementation of the Entry interface to be used within
   * a PriorityQueue implementation.
   */
  protected static class PQEntry<B,T,N> implements Entry<B,T,N> {
    private B b;  // key
    private T t;
    private N n;  // value

    public PQEntry(B bid, T time, N name) {
      b = bid;
      t = time;
      n = name;
    }

    // methods of the Entry interface
    public B getBid() { return b; }
    public T getTime() { return t; }
    public N getName() { return n; }

    // utilities not exposed as part of the Entry interface
    protected void setBid(B bid) { b = bid; }
    protected void setTime(T time) { t = time; }    
    protected void setValue(N name) { n = name; }
  } //----------- end of nested PQEntry class -----------

  // instance variable for an AbstractPriorityQueue
  /** The comparator defining the ordering of keys in the priority queue. */
  private Comparator<B> comp;

  /**
   * Creates an empty priority queue using the given comparator to order keys.
   * @param c comparator defining the order of keys in the priority queue
   */
  protected AbstractPriorityQueue(Comparator<B> c) { comp = c; }

  /** Creates an empty priority queue based on the natural ordering of its keys. */
  protected AbstractPriorityQueue() { this(new DefaultComparator<B>()); }

  /** Method for comparing two entries according to key */
  protected int compare(Entry<B,T,N> a, Entry<B,T,N> b) {
    return comp.compare(a.getBid(), b.getBid());
  }

  /** Determines whether a key is valid. */
  protected boolean checkKey(B bid) throws IllegalArgumentException {
    try {
      return (comp.compare(bid,bid) == 0);  // see if key can be compared to itself
    } catch (ClassCastException e) {
      throw new IllegalArgumentException("Incompatible bid");
    }
  }

  /**
   * Tests whether the priority queue is empty.
   * @return true if the priority queue is empty, false otherwise
   */
  @Override
  public boolean isEmpty() { return size() == 0; }
}
abstract class MyListInterface extends java.util.AbstractList<Integer> {
    abstract public int size ();
    abstract public void clear ();
    abstract public boolean add (final Integer element);
    abstract public Integer get (final int index);
    abstract public Integer set (final int index, final Integer element);
    abstract public Integer remove (final int index);
    abstract public boolean remove (final Integer element);
    abstract public String toString();
}


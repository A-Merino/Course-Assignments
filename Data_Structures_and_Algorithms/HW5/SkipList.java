import java.util.ArrayList;
public class SkipList<K extends Comparable<K>, V> {

    static FakeRandHeight rand = new FakeRandHeight(); // initializes random height generator

    // Nested Node class for each item 
    protected static class Node<K extends Comparable<K>, V> {

        // initializes class variables
        private K key;
        private V value;
        private int level; // stores level of current Node

        // quad node implementation
        private Node<K, V> above;
        private Node<K, V> below;
        private Node<K, V> next;
        private Node<K, V> prev;

        public Node(K k, V v, int l) {
            key = k;
            value = v;
            level = l;
        }

        // formats correct output for item
        @java.lang.Override
        public String toString() {
            String out = String.format("%04d:%s",key, value);
            return out;
        }


        // getter methods
        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public int getLevel() {
            return level;
        }

        public Node<K, V> getBelow() {
            return below;
        }

        public Node<K, V> getAbove() {
            return above;
        }

        public Node<K, V> getNext() {
            return next;
        }

        public Node<K, V> getPrev() {
            return prev;
        }


        // setter methods
        public void setKey(K key) {
            this.key = key;
        }

        public void setValue(V value) {
            this.value = value;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public void setAbove(Node<K, V> above) {
            this.above = above;
        }

        public void setBelow(Node<K, V> below) {
            this.below = below;
        }

        public void setNext(Node<K, V> next) {
            this.next = next;
        }

        public void setPrev(Node<K, V> prev) {
            this.prev = prev;
        }
    } // end of Node class

    // initializes important class variables 
    private Node<K, V> head;
    private Node<K, V> tail;
    private int height;
    private K max;
    private K min;
    private int size;

    // constructor for the SkipList
    // parameters set min and max values while head and tail Nodes are set
    public SkipList(K s, K l) {
        min = s;
        max = l;
        head = new Node<K, V>(min, null, 0);
        tail = new Node<K, V>(max, null, 0);
        head.setNext(tail);
        tail.setPrev(head);
    }

    // calls floorEntry to search and return node, if not found then return null
    public String get(K key) {
        Node<K, V> finder = floorEntry(key);
        if (finder.getKey().compareTo(key) == 0) {
            return finder.getValue().toString();
        }
        return null;
    }

    // adds new item to SkipList
    public String put(K key, V value) {

        // finds closest node to
        Node<K, V> finder = floorEntry(key);

        // if node is already found then return error
        if (finder.getKey().compareTo(key) == 0) {
            return new String(value.toString() + " existingTimeError");
        }

        // create a new node with parameters and set to previous node
        Node<K, V> newNode = new Node<K, V>(key, value, finder.getLevel());
        insert(finder, newNode);
        int nLevel = rand.get(); // get height for new node
        int curLevel = finder.getLevel(); // get height for previous node
        int hLevel = head.getLevel(); // get height for head node
        while (nLevel > 0) {

            // if there is no empty level then one needs to be created
            if (curLevel >= hLevel) {
                // moves head up one level and increases height
                Node<K, V> newHead = new Node<K, V>(null, null, hLevel + 1);
                fixUpsAndDowns(newHead, head);
                head = newHead;
                hLevel = head.getLevel();
                height++;
            }

            // moves the current node being created up a level
            while (finder.getAbove() == null) {
                finder = finder.getPrev();
            }
            finder = finder.getAbove();

            // creates a new current node and sets it below one just created
            Node<K, V> cur = new Node<K, V>(key, value, finder.getLevel());
            insert(finder, cur);
            fixUpsAndDowns(cur, newNode);
            newNode = cur;
            curLevel++;
            nLevel--;
        }
        size++;

        return value.toString();
    }


    // removes an item from SkipList
    public String remove(K key) {

        // finds item using floor, if exact item isn't found then return null
        // stores old value to output
        Node<K, V> node = floorEntry(key);
        String old = node.getValue().toString();
        if (node == null || node.getKey().compareTo(key) != 0){
            return null;
        }

        // Ensure current node is at bottom of SkipList
        while (node.getBelow() != null) {
            node = node.getBelow();
        }

        // Creates temporary prev and next nodes to set to each other after current Node is removed
        Node<K, V> prev = null;
        Node<K, V> next = null;
        while (node != null) {
            prev = node.getPrev();
            next = node.getNext();
            if (prev != null){
                prev.setNext(next);
            }
            if (next != null){
                next.setPrev(prev);
            }
            node = node.getAbove();
        }

        // Adjusts head if item removed creates another empty list and decreases height 
        while (head.getNext() == null && head.getBelow() != null) {
            head = head.getBelow();
            head.setAbove(null);
            height--;
        }
        size--;
        return old;
    }

    public int size() { // size function for ease
        return size;
    }

    public boolean empty() { // checks to see if list is empty
        return size == 0;
    }

    protected Node<K, V> floorEntry(K key) {
        // creates
        Node<K, V> node = head;
        Node<K, V> next = null;
        Node<K, V> below = null;
        K nodeKey = null;

        while (true) {
            // Until desired Node is achieved continue to next node to see if is it is still less than key
            // if so keep going until it is not
            next = node.getNext();
            while (node.getNext() != null && lessThanOrEqual(next.getKey(), key)) {
                node = next;
                next = node.getNext();
            }
            nodeKey = node.getKey();
            if (nodeKey != null && nodeKey.compareTo(key) == 0) {
                break;
            }

            // If next is not less than or equal to then go down a level and continue
            below = node.getBelow();
            if (below != null) {
                node = below;
            } else {
                break;
            }
        }
        return node;
    }

    protected Node<K, V> ceilingEntry(K key) { // same as floor function but returns Node after
        Node<K, V> node = floorEntry(key);
        return node.getNext();
    }


    // method to return an array of items between time specified
    public Object[] subMap(K f, K l) {
        // gets the first item or closest above and the last item or closest below
        Node<K, V> first = ceilingEntry(f);
        Node<K, V> last = floorEntry(l);
        ArrayList<Node<K, V>> list = new ArrayList<>(); // creates ArrayList

        // get items from bottom row in order
        while(first.getValue() != null && first.getKey().compareTo(last.getKey()) <= 0) {
            // if the first Node is the minimum then skip adding
            if (first.getKey().compareTo(min) > 0) {
                list.add(first);  
            }
            first = first.getNext();
        }


        if (list.size() == 0) {
            return null;
        }
        return list.toArray();
    }


    protected boolean lessThanOrEqual(K a, K b) { // less than function for ease
        return a.compareTo(b) <= 0;
    }

    protected void insert(Node<K, V> x, Node<K, V> y) {
        // sets first node to the left of the second node
        y.setPrev(x);
        y.setNext(x.getNext());
        if (x.getNext() != null) {
            x.getNext().setPrev(y);
        }
        x.setNext(y);
    }

    protected void fixUpsAndDowns(Node<K, V> x, Node<K, V> y) { // sets first node on top of second Node
        x.setBelow(y);
        y.setAbove(x);
    }

    public String toString() { // toString function for printing all the Lists
        StringBuilder sb = new StringBuilder();
        Node<K, V> start = head;
        Node<K, V> node = head;
        int counter = height;
        if (!empty()) {
            sb.append("(S" + (height + 1) + ") empty\n");
                
            while (node != null && node.getNext() != null) {
                sb.append("(S" + counter + ") ");
                    node = node.getNext();
                while(node != null && node.getValue() != null) {
                    sb.append(node + " ");
                    node = node.getNext();
                }
                sb.append("\n");
                start = start.getBelow();
                node = start;
                counter--;
            }
        } else { 
            sb.append("(S" + height + ") empty\n");
        }
        return sb.toString();
    }
}
package hashmap;

import java.util.*;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    private int size;
    private double maxLoadFactor;
    private static final int DEFAULT_SIZE = 16;
    private static final double DEFAULT_MAX_LF = 0.75;
    private static final double DEFAULT_MIN_LF = 0.25;


    private double loadFactor() {
        return (double) size/buckets.length;
    }

    /** Constructors */
    public MyHashMap() {
        this(DEFAULT_SIZE, DEFAULT_MAX_LF);
    }

    public MyHashMap(int initialSize) {
        this(initialSize, DEFAULT_MAX_LF);
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        this.buckets = createTable(initialSize);
        this.maxLoadFactor = maxLoad;
        this.clear();
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key, value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new LinkedList<Node>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        return new Collection[tableSize];
    }

    private int hash(K key) {
        if (key == null) {
            return 0;
        }

        int numBuckets = buckets.length;
        return Math.floorMod(key.hashCode(), numBuckets);
    }


    private void resizeHashTable(int targetBucketsM) {
        MyHashMap<K,V> tempHashMap = new MyHashMap<>(targetBucketsM, this.maxLoadFactor);
        //int counter = 0;
        for (K key : this) {
            tempHashMap.put(key, this.get(key));
            //counter++;
        }
        //System.out.println("copying ran " + counter + " times");
        this.size = tempHashMap.size;
        this.buckets = tempHashMap.buckets;
    }

    //Implement the method of Interface Map61B

    /** Removes all of the mappings from this map */
    @Override
    public void clear() {
        this.size = 0;
        for(int i = 0; i < this.buckets.length; i++) {
            this.buckets[i] = createBucket();
        }
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        int bucketIndex = hash(key);
        Collection<Node> singleBucket = buckets[bucketIndex];
        for (Node p : singleBucket) {
            if (p.key.equals(key)) {
                return p.value;
            }
        }
        return null;
    }

    @Override
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key,
     * the old value is replaced.
     */
    @Override
    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("argument to put(key == null) is not allowed");
        }
        int bucketIndex = hash(key);
        for (Node p : buckets[bucketIndex]) {
            if (p.key.equals(key)) {
                p.value = value;
                return;
            }
        }
        Node nodeToPut = createNode(key, value);
        buckets[bucketIndex].add(nodeToPut);
        size = size + 1;


        if (loadFactor() > maxLoadFactor) {
            int currentMBuckets = buckets.length;
            //System.out.println("The size before resize and after put() is: " + size);
            //System.out.println("Calling resize()");
            resizeHashTable(2*currentMBuckets);
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Set<K> keySet() {
        if (size == 0) {
            return null;
        }
        Set<K> setKeys = new HashSet<>();
        for (K key : this) {
            setKeys.add(key);
        }
        return setKeys;
    }

    /** boolean matchValue: false if only looking at Key k */
    private V removeHelper(K key, V value, boolean matchValue) {
        if (key == null) {
            throw new IllegalArgumentException("argument to remove(key) was null");
        }

        int hashCode = hash(key);

        Iterator<Node> curBucketIterator =  buckets[hashCode].iterator();

        while (curBucketIterator.hasNext()) {
            Node curNode = curBucketIterator.next();
            boolean keyMatch = curNode.key.equals(key);


            boolean valueMatch = !matchValue || curNode.value.equals(value);

            if (keyMatch && valueMatch) {
                V removedValue = curNode.value;
                curBucketIterator.remove();
                size = size - 1;

                //resize only after successful removal
                if (loadFactor() < DEFAULT_MIN_LF && buckets.length > DEFAULT_SIZE) {
                    int currentMBuckets = buckets.length;
                    resizeHashTable((int) currentMBuckets/2);
                }
                return removedValue;
            }
        }
        return null;
    }

    /**
     * Removes the mapping for the specified key from this map if present.
     * matchValue: true
     */
    @Override
    public V remove(K key) {
        return removeHelper(key, null, false);
    }

    /**
     * Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 8. If you don't implement this,
     * throw an UnsupportedOperationException.
     * matchValue: false
     */
    @Override
    public V remove(K key, V value) {
       return removeHelper(key, value, true);
    }

    @Override
    public Iterator<K> iterator() {
        return new KeyIterator();
    }


    private class KeyIterator implements Iterator<K> {

        private int currentBucketIndex;
        private Iterator<Node> currentBucketIterator;


        private int findNextBucketIndex(int cur) {
            while (cur < buckets.length && buckets[cur].isEmpty()) {
                cur++;
            }
            return cur;
        }

        public KeyIterator() {
            //find the first non-null bucket
            currentBucketIndex = findNextBucketIndex(0);
            currentBucketIterator = buckets[currentBucketIndex].iterator();
        }

        @Override
        public boolean hasNext() {

            return currentBucketIterator.hasNext() || findNextBucketIndex(currentBucketIndex + 1) < buckets.length;
        }

        @Override
        public K next() {
            if (currentBucketIterator.hasNext()) {
                Node p = currentBucketIterator.next();
                //System.out.println("hasNext() true for current bucket: " + p.key);
                return p.key;
            } else {
                //this currentBucket is out of items. need to move to the next valid bucket
                currentBucketIndex = findNextBucketIndex(currentBucketIndex+1);
                currentBucketIterator = buckets[currentBucketIndex].iterator();
                Node p = currentBucketIterator.next();
                return p.key;
            }

        }

    }
}

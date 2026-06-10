package deque;

import java.util.Formatter;
import java.util.Iterator;

/**
 * add and remove operations should take constant time (except during resizing)
 *
 * get and size must take constant time
 *
 * starting size should be 8
 *
 * for arrays of length 16 or more, your usage factor should always be
 * at least 25%.
 *
 * before performing a remove operation that will bring the number of elements in the array under 25% the length of the array, you should resize the size of the array down.
 * For smaller arrays, your usage factor can be arbitrarily low.
 * @param <T>
 */


public class ArrayDeque<T> implements Iterable<T>, Deque<T> {
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;
    private final static double USAGE_MIN = 0.25;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        nextFirst = 1;
        nextLast = 2;
    }

    public int getCapacity() {
        return items.length;
    }

    /** Resizes up the underlying array to target capacity **/
    private void resizeUp(int capacity) {
        T[] a = (T[]) new Object[capacity];
        int firstTrueIndex = Math.floorMod((nextFirst + 1), items.length);
        for(int i = 0; i < size; i++) {
            int itIndex = Math.floorMod((firstTrueIndex + i), items.length);
            a[size - 1 + i] = items[itIndex];
        }
        items = a;
        nextFirst = size - 2;
        nextLast = items.length - 1;
    }

    private void resizeDown() {
        T[] a = (T[]) new Object[items.length/2];
        int firstTrueIndex = Math.floorMod((nextFirst + 1), items.length);

        //copy from items into this new array a
        for (int i = 0; i < size; i++) {
            int itIndex = Math.floorMod((firstTrueIndex + i), items.length);
            a[items.length/2 - 1 - size + i] = items[itIndex];
        }
        items = a;
        nextFirst = items.length - 1 - size - 1;
        nextLast = items.length - 1;
    }


    public boolean equals(Object o) {
        //check for reference identity
        if (this == o) return true;

        //check type (implicitly returns false if 'o' is null
        if (!(o instanceof Deque<?>)) return false;

        //explicitly case the object to your type
        Deque<T> other = (Deque<T>) o;

        if (other.size() != this.size) return false;

        Iterator<T> iterator1 = this.iterator();
        int otherIndex = 0;

        //simultaneously traverse through each
        while(iterator1.hasNext()) {
            T item1 = iterator1.next();
            T item2 = other.get(otherIndex);
            if(!item1.equals(item2)) {
                return false;
            }
            otherIndex++;
        }
        return true;
    }

    @Override
    public void addFirst(T x) {
        if (size == items.length) {
            resizeUp(2*size);
        }
        items[nextFirst] = x;
        nextFirst = Math.floorMod((nextFirst - 1), items.length);
        size +=1;
    }

    @Override
    public void addLast(T x) {
        if (size == items.length) {
            resizeUp(2*size);
        }
        items[nextLast] = x;
        nextLast = Math.floorMod((nextLast + 1), items.length);
        size += 1;
    }

    /**COMMENTED out per specs
    @Override
    public boolean isEmpty() {
        return size == 0;
    }
     */

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        if (this.isEmpty()) {
            System.out.println("()");
        }
        Formatter out = new Formatter();
        String sep;
        sep = "(";

        int firstTrueIndex = Math.floorMod((nextFirst + 1), items.length);

        for (int i = 0; i < size; i++) {
            int itIndex = Math.floorMod((firstTrueIndex + i), items.length);
            out.format("%s%s", sep, items[itIndex].toString());
            sep = ",";
        }
        out.format(")");
        System.out.println(out.toString());
    }

    @Override
    public T removeFirst() {
        int firstTrueIndex = Math.floorMod((nextFirst + 1), items.length);
        T returnItem = items[firstTrueIndex];
        items[firstTrueIndex] = null;
        nextFirst = Math.floorMod((nextFirst + 1), items.length);
        if (returnItem != null) {
            size -= 1;
            if ((double) size/items.length < USAGE_MIN && items.length >= 16) {
                resizeDown();
            }
        }
        return returnItem;
    }

    @Override
    public T removeLast() {
        int lastTrueIndex = Math.floorMod((nextLast - 1), items.length);
        T returnItem = items[lastTrueIndex];
        items[lastTrueIndex] = null;
        nextLast = Math.floorMod((nextLast - 1), items.length);
        if (returnItem != null) {
            size -= 1;
            if ((double) size/items.length < USAGE_MIN && items.length >= 16) {
                resizeDown();
            }
        }
        return returnItem;
    }

    @Override
    public T get(int index) {
        int firstTrueIndex = Math.floorMod((nextFirst + 1), items.length);
        int getIndex = Math.floorMod(firstTrueIndex + index, items.length);
        return items[getIndex];
    }

    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }

    private class ArrayDequeIterator implements Iterator<T> {
        private int trueFirstIndex;
        private int itIndex;

        public ArrayDequeIterator() {
            trueFirstIndex = Math.floorMod((nextFirst + 1), items.length);
            itIndex = 0;
        }

        public boolean hasNext() {
            return itIndex < size;
        }

        public T next() {
            int getIndex = Math.floorMod(trueFirstIndex + itIndex, items.length);
            T returnItem = items[getIndex];
            itIndex += 1;
            return returnItem;
        }
    }
}

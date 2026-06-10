package deque;

import java.util.Formatter;
import java.util.Iterator;

/**
 * add and remove operations must not involve any looping or recursion
 * (should take constant time)
 *
 * get must use iteration, not recursion
 *
 * size must take constant time
 *
 * iterating over the LinkedListDeque using a for-each loop should take time
 * proportional to the number of items
 *
 * do not maintain references to items that are no longer in the deque
 * @param <T>
 */

public class LinkedListDeque<T> implements Iterable<T>, Deque<T>{

    private static class Node<T> {
        public Node<T> prev;
        public T item;
        public Node<T> next;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    /** instance variables for class LinkedListDeque **/
    private Node<T> sentinel;
    private int size;


    public LinkedListDeque() {
        sentinel = new Node<>(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    public boolean equals(Object o) {
        //check for reference identity
        if (this == o) return true;

        //check type (implicitly returns false if 'o' is null
        if (!(o instanceof LinkedListDeque<?>)) return false;

        //explicitly case the object to your type
        LinkedListDeque<T> other = (LinkedListDeque<T>) o;

        if (other.size != this.size) return false;

        //simultaneously traverse through each
        Node<T> otherCurrNode = other.sentinel.next;
        Node<T> thisCurrNode = sentinel.next;

        for (int traverseIndex = 0; traverseIndex < size; traverseIndex++) {
            if (!otherCurrNode.item.equals(thisCurrNode.item)) {
                return false;
            } else {
                otherCurrNode = otherCurrNode.next;
                thisCurrNode = thisCurrNode.next;
            }
        }
        return true;
    }

    public void addFirst(T x) {
        //don't copy just insert directly into first
        //1.) set new node's prev pointer to reference the sentinel directly
        //2.) set item to x
        //3.) set new node's next pointer to reference where the sentinel is currently referencing head (sentinel.next)
        Node<T> insert;
        insert = new Node<T>(sentinel, x, sentinel.next); //(red)

        //change current head (sentinel.next)'s prev pointer now needs (blue)
        // to point at this new insert
        sentinel.next.prev = insert;

        //(sentinel.next must always point to head) (green) //THE HEAD IS NOW INSERT
        sentinel.next = insert;

        size += 1;
    }

    public void addLast(T x) {
        //don't copy just insert directly into first
        //1.) set new node's prev pointer to reference what was the tail (sentinel.prev)
        //2.) set item to x
        //3.) set new node's next pointer to reference the sentinel (invariant rule)
        Node<T> insert;
        insert = new Node<T>(sentinel.prev, x, sentinel); //(red)

        //change current tail's next pointer to point to this new insert (blue)
        sentinel.prev.next = insert;

        //make insert the official new tail (green)
        sentinel.prev = insert;

        size += 1;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        if (this.isEmpty()) {
            System.out.println("()");
        }
        Formatter out = new Formatter();
        String sep;
        sep = "(";
        Node<T> nodeIterate = this.sentinel;

        for (int i = 0; i < size; i++) {
            out.format("%s%s", sep, nodeIterate.next.item.toString());
            sep = ",";
            nodeIterate = nodeIterate.next;
        }
        out.format(")");
        System.out.println(out.toString());
    }

    public T removeFirst() {
        T itemReturn = sentinel.next.item;

        //sentinel.next should point at the head's (sentinel.next) .next
        sentinel.next = sentinel.next.next; //(red)

        //the new head's .prev needs to point at sentinel
        sentinel.next.prev = sentinel;

        if (itemReturn != null) size -= 1;

        return itemReturn;
    }

    public T removeLast() {
        T itemReturn = sentinel.prev.item;
        //sentinel.prev should point at the tail's (sentinel.prev) previous
        sentinel.prev = sentinel.prev.prev; //(red)

        //the new tail's .next needs to point at sentinel
        sentinel.prev.next = sentinel;

        if (itemReturn != null) size -= 1;

        return itemReturn;
    }

    public T get(int index) {
        Node<T> current = sentinel.next;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.item;
    }

    private T getRecursive(Node<T> current, int index) {
        if (index == 0) {
            return current.item;
        } else {
            return getRecursive(current.next, index - 1);
        }
    }

    public T getRecursive(int index) {
        return getRecursive(sentinel.next, index);
    }


    public Iterator<T> iterator() {
        return new LinkedListDequeIterator();
    }

    private class LinkedListDequeIterator implements Iterator<T> {

        private Node<T> itCurrent;
        private int itIndex;
        public LinkedListDequeIterator() {
            itCurrent = sentinel.next;
            itIndex = 0;
        }

        public boolean hasNext() {
            return itIndex < size;
        }

        public T next() {
            T returnItem = itCurrent.item;
            itCurrent = itCurrent.next;
            itIndex += 1;
            return returnItem;
        }
    }

    public static void main(String[] args) {
        LinkedListDeque<Integer> aLLDeque = new LinkedListDeque<>();
        aLLDeque.addFirst(1);
        aLLDeque.addFirst(2);
        aLLDeque.addFirst(3);

        LinkedListDeque<Integer> copyALLDeque = new LinkedListDeque<>();
        copyALLDeque.addFirst(1);
        copyALLDeque.addFirst(2);
        copyALLDeque.addFirst(3);

        aLLDeque.printDeque();

        System.out.println("at index 2: " + aLLDeque.getRecursive(2));


        //iteration
        for (int i : aLLDeque) {
            System.out.println("Index i: " + i);
        }

    }


}

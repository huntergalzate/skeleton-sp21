package SLList;

public class SLList {
    public static class IntNode {
        public int item;
        public IntNode next;

        public IntNode(int i, IntNode n) {
            item = i;
            next = n;
        }
    }

    private IntNode first;

    public SLList(int x) {
        first = new IntNode(x, null);
    }
    public int getFirst() {
        return this.first.item;
    }

    public void addFirst(int x) {
        first = new IntNode(x, first);
    }

    public void addLast(int x) {
        IntNode p = this.first;
        while (p.next != null) {
            p = p.next;
        }
        p.next = new IntNode(x, null);
    }

    /**
     *  My original writing.. works and passes tests
     */
    /*
    public int size() {
        if (first.next == null) {
            return 1;
        }
        first = first.next;
        return 1 + this.size();
    }
     */

    /**
     * create a private helped method that interacts with the underlying
     * naked recursive structure... See Lecture
     */
    private static int size(IntNode p) {
        if (p.next == null) {
            return 1;
        }
        return 1 + size(p.next);
    }

    public int size() {
        return size(this.first);
    }


}
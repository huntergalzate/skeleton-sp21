package deque;

import org.junit.Test;
import static org.junit.Assert.*;

public class ArrayDequeTest {

    @Test
    /** Adds a few things to the list, checking isEmpty() and size() are correct,
     * finally printing the results.
     *
     * && is the "and" operation. */
    public void addIsEmptySizeTest() {

        //System.out.println("Make sure to uncomment the lines below (and delete this print statement).");

        ArrayDeque<String> lld1 = new ArrayDeque<String>();

        assertTrue("A newly initialized ADeque should be empty", lld1.isEmpty());
        lld1.addFirst("front");

        // The && operator is the same as "and" in Python.
        // It's a binary operator that returns true if both arguments true, and false otherwise.
        assertEquals(1, lld1.size());
        assertFalse("lld1 should now contain 1 item", lld1.isEmpty());

        lld1.addLast("middle");
        assertEquals(2, lld1.size());

        lld1.addLast("back");
        assertEquals(3, lld1.size());

        System.out.println("Printing out deque: ");
        lld1.printDeque();
    }

    @Test
    /** Adds an item, then removes an item, and ensures that dll is empty afterwards. */
    public void addRemoveTest() {

        //System.out.println("Make sure to uncomment the lines below (and delete this print statement).");

        ArrayDeque<Integer> lld1 = new ArrayDeque<Integer>();
        // should be empty
        assertTrue("lld1 should be empty upon initialization", lld1.isEmpty());

        lld1.addFirst(10);
        // should not be empty
        assertFalse("lld1 should contain 1 item", lld1.isEmpty());

        lld1.removeFirst();
        // should be empty
        assertTrue("lld1 should be empty after removal", lld1.isEmpty());
    }

    @Test
    /* Tests removing from an empty deque */
    public void removeEmptyTest() {

        //System.out.println("Make sure to uncomment the lines below (and delete this print statement).");

        ArrayDeque<Integer> lld1 = new ArrayDeque<>();
        lld1.addFirst(3);

        lld1.removeLast();
        lld1.removeFirst();
        lld1.removeLast();
        lld1.removeFirst();

        int size = lld1.size();
        String errorMsg = "  Bad size returned when removing from empty deque.\n";
        errorMsg += "  student size() returned " + size + "\n";
        errorMsg += "  actual size() returned 0\n";

        assertEquals(errorMsg, 0, size);
    }

    @Test
    /* check if null is return when removing from an empty LinkedListDeque. */
    public void emptyNullReturnTest() {

        //System.out.println("Make sure to uncomment the lines below (and delete this print statement).");

        ArrayDeque<Integer> lld1 = new ArrayDeque<Integer>();

        boolean passed1 = false;
        boolean passed2 = false;
        assertEquals("Should return null when removeFirst is called on an empty Deque,", null, lld1.removeFirst());
        assertEquals("Should return null when removeLast is called on an empty Deque,", null, lld1.removeLast());
    }

    @Test
    public void resizeUpAddFirstOnlyTest() {
        ArrayDeque<Integer> lld1 = new ArrayDeque<Integer>();
        for (int i = 0; i < 100; i++) {
            lld1.addFirst(i);
        }
        assertEquals(128, lld1.getCapacity());
        assertEquals(100, lld1.size());
        assertEquals(99, (int) lld1.get(0));
        assertEquals(0, (int) lld1.get(99));
    }

    @Test
    public void resizeUpAddLastOnlyTest() {
        ArrayDeque<Integer> lld1 = new ArrayDeque<Integer>();
        for (int i = 0; i < 20; i++) {
            lld1.addLast(i);
        }
        assertEquals(32, lld1.getCapacity());
        assertEquals(20, lld1.size());
        assertEquals(0, (int) lld1.get(0));
        assertEquals(19, (int) lld1.get(19));
    }

    @Test
    /** add 40 items. addFirst() only
     * remove 29 items. check capacity
     * remove 30th item. check capacity
     */
    public void resizeDownRemoveFirstOnlyTest() {
        ArrayDeque<Integer> lld1 = new ArrayDeque<Integer>();
        for (int i = 0; i < 50; i++) {
            lld1.addFirst(i);
        }
        assertEquals(64, lld1.getCapacity());
        for (int i = 0; i < 34; i++) {
            lld1.removeFirst();
        }
        assertEquals("checking capacity before resize", 64, lld1.getCapacity());
        lld1.removeFirst();
        assertEquals("checking capacity after resize", 32, lld1.getCapacity());
    }

    @Test
    public void resizeDownRemoveLastOnlyTest() {
        ArrayDeque<Integer> lld1 = new ArrayDeque<Integer>();
        for (int i = 0; i < 50; i++) {
            lld1.addFirst(i);
        }
        assertEquals(64, lld1.getCapacity());
        for (int i = 0; i < 34; i++) {
            lld1.removeLast();
        }
        assertEquals("checking capacity before resize", 64, lld1.getCapacity());
        lld1.removeLast();
        assertEquals("checking capacity after resize", 32, lld1.getCapacity());
    }

    @Test
    /** add 40 items. addFirst() only
     * remove 29 items. check capacity
     * remove 30th item. check capacity
     */
    public void resizeDownAllButOneTest() {
        ArrayDeque<Integer> lld1 = new ArrayDeque<Integer>();
        for (int i = 0; i < 64; i++) {
            lld1.addFirst(i);
        }
        for (int i = 0; i < 63; i++) {
            lld1.removeFirst();
        }
        assertEquals("checking capacity after resize", 8, lld1.getCapacity());
    }

    @Test
    /**
     * alternate addFirst() and addLast()
     */
    public void alternateAddDequeTest() {
        ArrayDeque<Integer> lld1 = new ArrayDeque<Integer>();
        int numbersToAdd = 1000;
        for (int i = 0; i < numbersToAdd; i++) {
            if (Math.floorMod(i, 2) == 0) {
                lld1.addFirst(i);
            } else {
                lld1.addLast(i);
            }
        }
        assertEquals(0, Math.floorMod(lld1.get(0), 2));
        assertEquals(1, Math.floorMod(lld1.get(lld1.size() - 1), 2));
        assertEquals(999, (int) lld1.removeLast());
        assertEquals(998, (int) lld1.removeFirst());
        assertEquals(998, lld1.size());
    }

    @Test
    /* Add large number of elements to deque; check if order is correct. */
    public void bigADequeTest() {

        //System.out.println("Make sure to uncomment the lines below (and delete this print statement).");

        ArrayDeque<Integer> lld1 = new ArrayDeque<Integer>();
        for (int i = 0; i < 1000000; i++) {
            lld1.addLast(i);
        }

        for (double i = 0; i < 500000; i++) {
            assertEquals("Should have the same value", i, (double) lld1.removeFirst(), 0.0);
        }

        for (double i = 999999; i > 500000; i--) {
            assertEquals("Should have the same value", i, (double) lld1.removeLast(), 0.0);
        }
    }

    @Test
    /*Test equals() method */
    public void isEqualsTest() {
        ArrayDeque<Integer> aLLDeque = new ArrayDeque<>();
        ArrayDeque<Integer> b = new ArrayDeque<>();
        aLLDeque.addFirst(1);
        aLLDeque.addFirst(2);
        aLLDeque.addFirst(3);

        b.addFirst(2);
        b.addFirst(3);
        b.addLast(1);

        assertEquals(aLLDeque, b);

        assertEquals("should be false, comparing ADeque to a single int", false, aLLDeque.equals(1));
    }
}

package deque;

import org.junit.Test;

import java.util.Comparator;

import static org.junit.Assert.*;

public class MaxArrayDequeTest {

    @Test
    public void stringNaturalOrderTest() {
        MaxArrayDeque<String> Q = new MaxArrayDeque<>(Comparator.<String>naturalOrder());
        Q.addFirst("One");
        Q.addFirst("Twoo");
        Q.addFirst("Three");
        assertEquals("Testing natural order of strings", "Twoo", Q.max());
    }

    @Test
    public void stringLengthTest() {
        StringLengthComparator c = new StringLengthComparator();
        MaxArrayDeque<String> Q = new MaxArrayDeque<>(Comparator.<String>naturalOrder());
        Q.addFirst("*");
        Q.addFirst("**");
        Q.addFirst("***");
        assertEquals("Testing length of strings", "***", Q.max(c));
    }

    @Test
    public void emptyStringListTest() {
        MaxArrayDeque<String> Q = new MaxArrayDeque<>(Comparator.<String>naturalOrder());
        assertNull(Q.max());
    }

    @Test
    public void integerNaturalOrderTest() {
        MaxArrayDeque<Integer> Q = new MaxArrayDeque<>(Comparator.<Integer>naturalOrder());
        Q.addFirst(1);
        Q.addFirst(2);
        Q.addFirst(3);
        Q.addLast(4);
        assertEquals("testing integer natural order with max", 4, (int) Q.max());
    }

    @Test
    public void integerAbsoluteValueTest() {
        AbsValueInteger c = new AbsValueInteger();
        MaxArrayDeque<Integer> Q = new MaxArrayDeque<>(c);
        Q.addFirst(-1);
        Q.addFirst(-10);
        Q.addFirst(11);
        Q.addLast(-20);
        assertEquals("testing integer natural order with max", -20, (int) Q.max());
    }

    // ----- PRIVATE custom COMPARATOR CLASSES GO AT THE BOTTOM  ----- //
    private static class StringLengthComparator implements Comparator<String> {
        @Override
        public int compare(String s1, String s2) {
            return s1.length() - s2.length();
        }
    }

    private static class AbsValueInteger implements Comparator<Integer> {
        @Override
        public int compare(Integer x1, Integer x2) {
            return Math.abs(x1) - Math.abs(x2);
        }
    }

    //

}

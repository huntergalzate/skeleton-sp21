package SLList;

import static org.junit.Assert.*;

import IntList.IntList;
import org.junit.Test;

import java.util.Formatter;

public class SLListTests {

    @Test
    public void testSize3() {
        SLList L = new SLList(10);
        L.addFirst(20);
        L.addFirst(30);
        assertEquals(3, L.size());
    }

    @Test
    public void testSize1() {
        SLList L = new SLList(10);
        assertEquals(1, L.size());
    }
}

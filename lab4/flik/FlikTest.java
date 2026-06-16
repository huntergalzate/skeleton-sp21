package flik;

import org.junit.Test;
import static org.junit.Assert.*;


public class FlikTest {

    @Test
    public void testIsSameNumber() {
        assertFalse(Flik.isSameNumber(10, 20));

        assertTrue(Flik.isSameNumber(0, 0));
        assertTrue(Flik.isSameNumber(-1, -1));
        assertTrue(Flik.isSameNumber(10, 10));
        assertTrue("testing 128", Flik.isSameNumber(128, 128));


        assertFalse(Flik.isSameNumber(0, 1));
    }

}

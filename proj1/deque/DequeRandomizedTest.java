package deque;
import edu.princeton.cs.algs4.StdRandom;
import org.checkerframework.checker.units.qual.A;
import org.junit.Test;
import static org.junit.Assert.*;
public class DequeRandomizedTest {

    @Test
    public void randomizedTest() {
        LinkedListDeque<Integer> L = new LinkedListDeque<>();
        ArrayDeque<Integer> A = new ArrayDeque<>();

        int N = 500000;
        for (int i = 0; i < N; i++) {
            int operationNumber = StdRandom.uniform(0,6);

            if (operationNumber == 0) {
                //addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                A.addLast(randVal);
                System.out.println("addLast(" + randVal + ")");
            } else if (operationNumber == 1) {
                //addFirst
                int randVal = StdRandom.uniform(0, 100);
                L.addFirst(randVal);
                A.addFirst(randVal);
                System.out.println("addFirst(" + randVal + ")");

            } else if (operationNumber == 2) {
                //removeFirst
                if (!L.isEmpty()) {
                    int lRemoveFirstItem = L.removeFirst();
                    int aRemoveFirstItem = A.removeFirst();
                    assertEquals("Checking removeFirst(): ", lRemoveFirstItem, aRemoveFirstItem);
                }

            } else if (operationNumber == 3) {
                //removeLast
                if (!L.isEmpty()) {
                    int lRemoveLastItem = L.removeLast();
                    int aRemoveLastItem = A.removeLast();
                    assertEquals("Checking removeFirst(): ", lRemoveLastItem, aRemoveLastItem);
                }


            } else if (operationNumber == 4) {
                //get
                if (!L.isEmpty()) {
                    int randValIndex = StdRandom.uniform(0, L.size());
                    assertEquals("Checking get(): ", L.get(randValIndex), A.get(randValIndex));
                }


            } else if (operationNumber == 5) {
                //size
                assertEquals("Checking size(): ", L.size(), A.size());
            }
        }
    }
}

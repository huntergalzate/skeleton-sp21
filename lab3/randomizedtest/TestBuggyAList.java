package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {

    @Test
    public void testThreeAddThreeRemove(){
        AListNoResizing<Integer> noResizing = new AListNoResizing<>();
        noResizing.addLast(4);
        noResizing.addLast(5);
        noResizing.addLast(6);
        //int noResizeResult = noResizing.removeLast();

        BuggyAList<Integer> buggyAList = new BuggyAList<>();
        buggyAList.addLast(4);
        buggyAList.addLast(5);
        buggyAList.addLast(6);
        //int bugResult = buggyAList.removeLast();

        int sizeOfLists = buggyAList.size();
        while(buggyAList.size() > 0) {
            //System.out.println("Index i: " + sizeOfLists);
          assertEquals("Index i: " + sizeOfLists, noResizing.removeLast(), buggyAList.removeLast());
          sizeOfLists -= 1;
        }
    }

    @Test
    public void randomizedTest() {
        AListNoResizing<Integer> L = new AListNoResizing<>();
        BuggyAList<Integer> Lb = new BuggyAList<>();

        int N = 1000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                System.out.println("addLast(" + randVal + ")");

                Lb.addLast(randVal);
            } else if (operationNumber == 1) {
                // size
                int size = L.size();
                System.out.println("size: " + size);
                assertEquals("Checking size: ", L.size(), Lb.size());
            } else if (operationNumber == 2) {
                //getLast
                int size = L.size();
                if (size > 0) {
                    int lItem = L.getLast();
                    int bugItem = Lb.getLast();
                    System.out.println("getLast(" + lItem + ")");
                    assertEquals("Checking getLast: ", lItem, bugItem);
                }

            } else if (operationNumber == 3) {
                //removeLast
                int size = L.size();
                if (size > 0) {
                    int lastRemoveItem = L.removeLast();
                    int lastBugItemItem = Lb.removeLast();
                    System.out.println("removeLast(" + lastRemoveItem + ")");
                    assertEquals("Checking removeLast: ", lastRemoveItem, lastBugItemItem);
                }
            }
        }
    }
}

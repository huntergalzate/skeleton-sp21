package deque;

import java.util.Comparator;
import java.util.Iterator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {

    private final Comparator<T> comparator;

    public MaxArrayDeque(Comparator<T> c) {
        this.comparator = c;
    }

    /**  returns the max element in the deque as governed by the previously given Comparator*/
    public T max() {
        return max(this.comparator);
    }

    public T max(Comparator<T> c) {
        Iterator<T> it = this.iterator();

        //Step 1.) check if empty deque. if it is, return null
        if (!it.hasNext()) return null;

        //Step 2.) not empty, set max to first element
        T maxElement = it.next();

        //Step 3.) step into while loop and reset maxElement with contender if the comparator.compare returns -1 (contender bigger)
        while(it.hasNext()) {
            T contender = it.next();
            int compareValue = c.compare(maxElement, contender);
            if(compareValue < 0) {
                maxElement = contender;
            }
        }
        return maxElement;
    }
}

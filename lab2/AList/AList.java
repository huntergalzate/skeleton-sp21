package AList;

/** Array based list.
 *  @author Josh Hug
 */

//         0 1  2 3 4 5 6 7
// items: [6 9 -1 2 0 0 0 0 ...]
// size: 4

/* Invariants:
 addLast: The next item we want to add, will go into position size
 getLast: The item we want to return is in position size - 1
 size: The number of items in the list should be size.
*/

public class AList<Item> {
    private Item[] items;
    private int size;

    public AList() {
        items = (Item[]) new Object[100];
        size = 0;
    }

    /** Resizes the underlying array to target capacity */
    private void resize(int capacity) {
        if (size == items.length) {
            Item[] a = (Item[]) new Object[capacity];
            System.arraycopy(items, 0, a, 0, size);
            items = a;
        }
    }

    public void addLast(Item x) {
        if (size == items.length) {
            resize(2*size);
        }
        items[size] = x;
        size += 1;
    }

    public Item deleteBack() {
        Item returnItem = getLast();
        items[size - 1] = null;
        size -= 1;
        return returnItem;
    }


    public Item getLast() {
        return items[size - 1];
    }

    public Item get(int i) {
        return items[i];
    }

    public int size() {
        return size;
    }

    public Item removeLast() {
        size -= 1;
        return items[size];
    }

}

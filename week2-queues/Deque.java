/* *****************************************************************************
 *  Name: Reg Almonte
 *  Date: 2021/03/14
 *  Description: Programming Assignment - Week 2: Queues
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node first, last;
    private int count;

    // construct an empty LinkedDeque
    public Deque() {
        first = null;
        last = null;
        count = 0;
    }

    // is the LinkedDeque empty?
    public boolean isEmpty() {
        return count == 0;
    }

    // return the number of items on the LinkedDeque
    public int size() {
        return count;
    }

    // add the item to the front
    public void addFirst(Item item) {
        validateAddAction(item);
        first = new Node(item, first);
        if (first.next != null) {
            first.next.prev = first;
        }
        else {
            last = first;
        }

    }

    // add the item to the back
    public void addLast(Item item) {
        validateAddAction(item);
        last = new Node(item, last, null);
        if (last.prev != null) {
            last.prev.next = last;
        }
        else {
            first = last;
        }
    }

    // remove and return the item from the front
    public Item removeFirst() {
        validateRemoveAction();
        Item value = first.item;
        if (first.next != null) {
            first.next.prev = null;
        }
        first = first.next;
        if (isEmpty()) last = null;
        return value;
    }

    // remove and return the item from the back
    public Item removeLast() {
        validateRemoveAction();
        Item value = last.item;
        if (last.prev != null) {
            last.prev.next = null;
        }
        last = last.prev;
        if (isEmpty()) first = null;
        return value;
    }

    private void validateAddAction(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        count++;
    }

    private void validateRemoveAction() {
        if (count == 0) {
            throw new NoSuchElementException();
        }
        count--;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    public static void main(String[] args) {
        StdOut.println("Deque Test:");
        Deque<Integer> deque = new Deque<Integer>();
        int size = 10000000;
        boolean errorFound = false;

        if (args.length > 0) {
            size = Integer.parseInt(args[0]);
        }

        StdOut.println("Test isEmpty(): Check if queue is empty.");
        if (!deque.isEmpty()) {
            StdOut.println("ERROR: Expected that queue is empty.");
            errorFound = true;
        }

        StdOut.println("Test addFirst()/addLast(): Add [0," + size + ") elements to the queue");
        StdOut.println("(For even integers, use addFirst(). For odd integers, use addLast()).");
        int separator = 0;
        for (int i = 0; i < size; i++) {
            switch (separator) {
                case 0:
                    deque.addFirst(i);
                    break;
                default:
                    deque.addLast(i);
                    break;
            }
            separator = (separator + 1) % 2;
        }

        StdOut.println("Test isEmpty(): Check if queue is NOT empty.");
        if (deque.isEmpty()) {
            StdOut.println("ERROR: Expected that queue is NOT empty.");
            errorFound = true;
        }

        System.out.println("Test size(): Compare queue size to expected value: " + size);
        if (deque.size() != size) {
            StdOut.println("ERROR: Expected queue size did not match.");
            errorFound = true;
        }

        StdOut.println("Test iterator(): Check each sample if within [0," + size + ").");
        for (Integer integer : deque) {
            if (integer >= size || integer < 0) {
                StdOut.println("ERROR: An element was out of bound: " + integer);
                errorFound = true;
                break;
            }
        }

        int toRemove = size / 4;
        StdOut.println("Test removeFirst(): Remove " + toRemove + " elements from the queue");
        int lastRemovedItem = 0;
        for (int i = 0; i < toRemove; i++) {
            lastRemovedItem = deque.removeFirst();
        }
        int expectedLast = size % 2 == 0 ? size - toRemove * 2 : size + 1 - toRemove * 2;
        if (lastRemovedItem != expectedLast) {
            StdOut.println("ERROR: output of removeFirst() [" + lastRemovedItem
                                   + "] is not equal to the expected value: "
                                   + expectedLast + ".");
            errorFound = true;
        }
        StdOut.println("Test removeLast(): Remove " + toRemove + " elements from the queue");
        lastRemovedItem = 0;
        for (int i = 0; i < toRemove; i++) {
            lastRemovedItem = deque.removeLast();
        }
        expectedLast = size % 2 != 0 ? size - toRemove * 2 : size + 1 - toRemove * 2;
        if (lastRemovedItem != expectedLast) {
            StdOut.println("ERROR: output of removeFirst() [" + lastRemovedItem
                                   + "] is not equal to the expected value: "
                                   + expectedLast + ".");
            errorFound = true;
        }

        int remainingItems = size - toRemove * 2;
        System.out.println("Test size(): Compare queue size to expected value: " + remainingItems);
        if (deque.size() != remainingItems) {
            StdOut.println("ERROR: Expected queue size did not match.");
            errorFound = true;
        }

        StdOut.println("Test deque(): Remove all remaining elements.");
        separator = deque.size() % 2;
        for (int i = 0; i < remainingItems; i++) {
            switch (separator) {
                case 1:
                    lastRemovedItem = deque.removeFirst();
                    break;
                default:
                    lastRemovedItem = deque.removeLast();
                    break;
            }
            separator = (separator + 1) % 2;
        }

        if (lastRemovedItem != 0) {
            StdOut.println("ERROR: output of removeFirst() [" + lastRemovedItem
                                   + "] is not equal to the expected value: 0.");
            errorFound = true;
        }

        StdOut.println("Test isEmpty(): Check if queue is empty.");
        if (!deque.isEmpty()) {
            StdOut.println("ERROR: Expected that queue is empty.");
            errorFound = true;
        }

        if (errorFound) {
            StdOut.println("\nFinal Test Result: An error was found");
        }
        else {
            StdOut.println("\nFinal Test Result: No error was found");
        }
    }

    private class Node {
        public Item item;
        public Node next;
        public Node prev;

        Node(Item item, Node next) {
            this.item = item;
            this.next = next;
        }

        Node(Item item, Node prev, Node next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }

    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (hasNext()) {
                Item item = current.item;
                current = current.next;
                return item;
            }
            throw new NoSuchElementException();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}

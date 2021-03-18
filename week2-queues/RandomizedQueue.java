/* *****************************************************************************
 *  Name: Reg Almonte
 *  Date: 2021/03/14
 *  Description: Programming Assignment - Week 2: Queues
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private int elementCount, arraySize;
    private Item[] items;

    // construct an empty randomized queue
    public RandomizedQueue() {
        arraySize = 1;
        items = (Item[]) new Object[arraySize];
        elementCount = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return elementCount;
    }

    // add the item
    public void enqueue(Item item) {
        validateAddAction(item);
        if (isFull()) duplicateSize();
        items[elementCount++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        validateRemoveAction();
        Item item = removeRandomItem();
        if (isAlmostEmpty()) halfSize();
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        int n = StdRandom.uniform(0, elementCount);
        return items[n];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private boolean isFull() {
        return size() == arraySize;
    }

    private boolean isAlmostEmpty() {
        return size() <= arraySize / 4;
    }

    private void duplicateSize() {
        resize(2 * arraySize);
    }

    private void halfSize() {
        resize(arraySize / 2);
    }

    private void resize(int capacity) {
        if (isEmpty()) {
            return;
        }

        arraySize = capacity;
        Item[] copy = (Item[]) new Object[capacity];

        int currSize = size();
        for (int i = 0; i < currSize; i++) copy[i] = items[i];

        items = copy;
    }

    private int randomIndex() {
        return StdRandom.uniform(size());
    }

    private Item removeRandomItem() {
        int n = randomIndex();
        Item item = items[n];

        items[n] = items[--elementCount];
        items[elementCount] = null;
        return item;
    }

    private void validateAddAction(Item item) {
        if (item == null) throw new IllegalArgumentException("Input to enqueue cannot be null");
    }

    private void validateRemoveAction() {
        if (isEmpty()) throw new java.util.NoSuchElementException("The queue is empty");
    }

    // unit testing (required)
    public static void main(String[] args) {
        StdOut.println("RandomizedQueue Test:");
        RandomizedQueue<Integer> randomQueue = new RandomizedQueue<Integer>();
        int size = 10000000;
        boolean errorFound = false;

        if (args.length > 0) {
            size = Integer.parseInt(args[0]);
        }

        StdOut.println("Test isEmpty(): Check if queue is empty.");
        if (!randomQueue.isEmpty()) {
            StdOut.println("ERROR: Expected that queue is empty.");
            errorFound = true;
        }

        StdOut.println("Test enqueue(): Add " + size + " elements to the queue");
        for (int i = 0; i < size; i++) {
            randomQueue.enqueue(i);
        }

        StdOut.println("Test isEmpty(): Check if queue is NOT empty.");
        if (randomQueue.isEmpty()) {
            StdOut.println("ERROR: Expected that queue is NOT empty.");
            errorFound = true;
        }

        System.out.println("Test size(): Compare queue size to expected value: " + size);
        if (randomQueue.size() != size) {
            StdOut.println("ERROR: Expected queue size did not match.");
            errorFound = true;
        }

        StdOut.println("Test sample(): Check that the sample element is within [0," + size + ").");
        Integer sample = randomQueue.sample();
        if (sample >= size || sample < 0) {
            StdOut.println("ERROR: Sample was out of bound: " + sample);
            errorFound = true;
        }

        StdOut.println("Test iterator(): Check each sample if within [0," + size + ").");
        for (Integer integer : randomQueue) {
            if (integer >= size || integer < 0) {
                StdOut.println("ERROR: An element was out of bound: " + integer);
                errorFound = true;
                break;
            }
        }

        int toRemove = size / 2;
        StdOut.println("Test deque(): Remove " + toRemove + " elements.");
        for (int i = 0; i < toRemove; i++) {
            Integer integer = randomQueue.dequeue();
            if (integer >= size || integer < 0) {
                StdOut.println("ERROR: An element was out of bound: " + integer);
                errorFound = true;
                break;
            }
        }

        int remainingItems = size - toRemove;
        System.out.println("Test size(): Compare queue size to expected value: " + remainingItems);
        if (randomQueue.size() != remainingItems) {
            StdOut.println("ERROR: Expected queue size did not match.");
            errorFound = true;
        }

        StdOut.println("Test deque(): Remove all remaining elements.");
        for (int i = 0; i < remainingItems; i++) {
            Integer integer = randomQueue.dequeue();
            if (integer >= size || integer < 0) {
                StdOut.println("ERROR: An element was out of bound: " + integer);
                errorFound = true;
                break;
            }
        }

        StdOut.println("Test isEmpty(): Check if queue is empty.");
        if (!randomQueue.isEmpty()) {
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

    private class ListIterator implements Iterator<Item> {
        private int currCount = elementCount;
        private Item[] copyOfArr;

        public ListIterator() {
            copyOfArr = (Item[]) new Object[elementCount];
            for (int i = 0; i < elementCount; i++) {
                copyOfArr[i] = items[i];
            }
        }

        public boolean hasNext() {
            return currCount > 0;
        }

        public Item next() {
            if (hasNext()) {
                return getRandomAndRemove();
            }
            throw new NoSuchElementException();
        }

        public void remove() {
            throw new UnsupportedOperationException(".remove is not supported");
        }

        private Item getRandomAndRemove() {
            int n = StdRandom.uniform(0, currCount);
            Item item = copyOfArr[n];
            copyOfArr[n] = copyOfArr[--currCount];
            copyOfArr[currCount] = null;
            return item;
        }
    }
}

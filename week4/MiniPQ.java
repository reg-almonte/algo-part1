/* *****************************************************************************
 *  Name: Reg Almonte
 *  Date: 2021/04/14
 *  Description: Implementation of Min Priority Queue based on the Algo 1
 *               lectures
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MiniPQ<Key> implements Iterable<Key> {

    private Key[] pq; // pq[i] = ith element on pq
    private int count; // number of elements on pq
    private int capacity = 1;
    private Comparator<Key> comparator;  // optional comparator

    public MiniPQ(int initCapacity) {
        pq = (Key[]) new Object[initCapacity + 1];
        count = 0;
    }

    public MiniPQ(int initCapacity, Comparator<Key> comparator) {
        this.comparator = comparator;
        pq = (Key[]) new Object[initCapacity + 1];
        count = 0;
    }

    public MiniPQ(Key[] a) {pq = a; }

    void insert(Key v) {
        pq[++count] = v;
        swim(count);
    }

    Key delMin() {
        if (count > 0) {
            Key max = pq[1];
            exch(1, count--);
            sink(1);
            pq[count+1] = null;
            return max;
        }
        throw new NoSuchElementException("The queue is empty");
    }

    boolean isEmpty() {
        return count == 0;
    }

    Key min() {
        if (count > 0) return pq[1];
        throw new NoSuchElementException("The queue is empty");
    }

    int size() {
        return count;
    }

    private void swim(int k) {
        while (k > 1 && less(k, k/2)) {
            exch(k, k/2);
        }
    }

    private void sink(int k) {
        while (2*k <= count) {
            int j = k*2;
            if (j < count && less(j+1, j)) j++;
            if (less(k, j)) break;
            exch(k, j);
            k = j;
        }
    }

    private boolean less(int i, int j) {
        if (comparator == null) {
            return ((Comparable<Key>) pq[i]).compareTo(pq[j]) < 0;
        }
        else {
            return comparator.compare(pq[i], pq[j]) < 0;
        }
    }

    public void exch(int hi, int lo) {
        Key swap = pq[hi];
        pq[hi] = pq[lo];
        pq[lo] = swap;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    public Iterator<Key> iterator() {
        return new MiniPQ.MiniPQIterator();
    }

    private class MiniPQIterator implements Iterator<Key> {
        // create a new pq
        private MiniPQ<Key> copy;

        // add all items to copy of heap
        // takes linear time since already in heap order so no keys move
        public MiniPQIterator() {
            if (comparator == null) copy = new MiniPQ<Key>(size());
            else                    copy = new MiniPQ<Key>(size(), comparator);
            for (int i = 1; i <= count; i++)
                copy.insert(pq[i]);
        }

        public boolean hasNext()  { return !copy.isEmpty();                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Key next() {
            if (!hasNext()) throw new NoSuchElementException();
            return copy.delMin();
        }
    }

    public static void main(String[] args) {
        StdOut.println("Sample use of Max-Priority Queue/Max Heap");
        Double[] inputs = {1.0, 2.1, 0.1, 3.4, 2.0, 4.2, 9.4, 5.4};
        MiniPQ<Double> minPQ = new MiniPQ<Double>(inputs.length);

        StdOut.print("From: ");
        for (Double in: inputs) {
            StdOut.print(in + " ");
            minPQ.insert(in);
        }
        StdOut.print("\nTo:   ");
        for (Double item: minPQ) {
            StdOut.print(item + " ");
        }
    }
}

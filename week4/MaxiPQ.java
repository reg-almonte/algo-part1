/* *****************************************************************************
 *  Name: Reg Almonte
 *  Date: 2021/04/14
 *  Description: Implementation of Max Priority Queue based on the Algo 1
 *               lectures
 **************************************************************************** */

import java.util.Iterator;

public class MaxiPQ<Key> implements Iterable<Key> {

    private Key[] pq; // pq[i] = ith element on pq
    private int count; // number of elements on pq
    private int capacity = 1;

    public MaxiPQ() {
        pq = (Key[]) new Comparable[capacity];
    }


    public static void main(String[] args) {

    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    public Iterator<Key> iterator() {
        return new MaxiPQIterator();
    }

    private class MaxiPQIterator implements Iterator<Key> {

        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        public boolean hasNext() {
            return false;
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        public Key next() {
            return null;
        }
    }
}

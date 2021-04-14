/* *****************************************************************************
 *  Name: Reg Almonte
 *  Date: 2021/03/16
 *  Description: Merge Sort implementation based on the lecture
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

public class MergeSort extends SortHelper {
    public MergeSort() {
    }

    private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {
        assert isSorted(a, lo, mid); // precondition: a[lo..mid] sorted
        assert isSorted(a, mid + 1, hi); // precondition: a[mid+1..hi] sorted
        for (int k = lo; k <= hi; k++)
            aux[k] = a[k];
        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) a[k] = aux[j++];
            else if (j > hi) a[k] = aux[i++];
            else if (less(aux[j], aux[i])) a[k] = aux[j++];
            else a[k] = aux[i++];
        }
        assert isSorted(a, lo, hi); // postcondition: a[lo..hi] sorted
    }

    private static boolean sort(Comparable[] a, Comparable[] aux, int lo, int hi) {
        if (hi <= lo) return true;
        int mid = lo + (hi - lo) / 2;
        sort(a, aux, lo, mid);
        sort(a, aux, mid + 1, hi);
        merge(a, aux, lo, mid, hi);
        if (isTimesUp()) {
            return false;
        }
        return true;
    }

    public static boolean sort(Comparable[] a) {
        startTime();
        Comparable[] aux = new Comparable[a.length];
        if (!sort(a, aux, 0, a.length - 1)) {
            StdOut.println("'Merge sort' was stopped because it was taking too long.");
            return false;
        }
        return true;
    }

    public static void main(String[] args) {

    }
}

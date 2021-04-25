/* *****************************************************************************
 *  Name: Reg Almonte
 *  Date: 2021/03/29
 *  Description: Quick Sort implementation based on the lecture
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class QuickSort extends SortHelper {
    private static int partition(Comparable[] a, int lo, int hi) {
        int i = lo, j = hi + 1;
        while (true) {
            while (less(a[++i], a[lo]))
                if (i == hi) break;
            while (less(a[lo], a[--j]))
                if (j == lo) break;

            if (i >= j) break;
            exch(a, i, j);
        }
        exch(a, lo, j);
        return j;
    }

    private static boolean sort(Comparable[] a, int lo, int hi) {
        if (isTimesUp()) return false;
        if (hi <= lo) return true;
        int j = partition(a, lo, hi);
        if (!sort(a, lo, j - 1)) return false;
        return sort(a, j + 1, hi);
    }

    public static boolean sort(Comparable[] a) {
        startTime();
        StdRandom.shuffle(a);
        if (!sort(a, 0, a.length - 1)) {
            StdOut.println("'Quick sort' was stopped because it was taking too long.");
            return false;
        }
        return true;
    }
}

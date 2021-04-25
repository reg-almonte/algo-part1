/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class QuickSortDemo extends SortHelper {
    private static void printArray(Comparable[] a) {
        for (Comparable i : a) {
            StdOut.print(i + " ");
        }
        StdOut.println("");
    }

    private static int partition(Comparable[] a, int lo, int hi) {
        int i = lo, j = hi + 1;
        while (true) {
            while (less(a[++i], a[lo]))
                if (i == hi) break;
            while (less(a[lo], a[--j]))
                if (j == lo) break;

            if (i >= j) break;

            StdOut.println("Swap: " + a[i] + " and " + a[j]);
            exch(a, i, j);
        }
        StdOut.println("Final swap: " + a[lo] + " and " + a[j]);
        exch(a, lo, j);
        return j;
    }

    private static boolean sort(Comparable[] a, int lo, int hi) {
        if (isTimesUp()) return false;
        if (hi <= lo) return true;
        String loVal = a[lo].toString();
        String hiVal = a[hi].toString();
        StdOut.print("* " + loVal + "-" + hiVal + ": ");
        printArray(a);
        int j = partition(a, lo, hi);
        StdOut.print("End: ");
        printArray(a);
        if (!sort(a, lo, j - 1)) return false;
        if (!sort(a, j + 1, hi)) return false;
        StdOut.println("... Done ...");
        return true;
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

    public static void main(String[] args) {
        Integer[] a = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
        QuickSortDemo.sort(a);
    }
}

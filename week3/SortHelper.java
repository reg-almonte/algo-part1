/* *****************************************************************************
 *  Name: Reg Almonte
 *  Date: 2021/03/16
 *  Description: Sorting helper
 **************************************************************************** */

public class SortHelper {
    static final int TIMEOUT = 20000; // in milliseconds
    private static long end;

    public SortHelper() {
    }

    public static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    public static void exch(Comparable[] a, int hi, int lo) {
        Comparable swap = a[hi];
        a[hi] = a[lo];
        a[lo] = swap;
    }

    public static boolean isSorted(Comparable[] a, int lo, int hi) {
        for (int i = lo + 1; i < hi; i++)
            if (less(a[i], a[i - 1])) return false;
        return true;
    }

    public static boolean isSorted(Comparable[] a) {
        return isSorted(a, 0, a.length);
    }


    public static void startTime() {
        end = System.currentTimeMillis() + TIMEOUT;
    }

    public static boolean isTimesUp() {
        return System.currentTimeMillis() >= end;
    }

    public static void main(String[] args) {
    }
}

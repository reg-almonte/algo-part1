/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

public class SelectionSort extends SortHelper {

    public static boolean sort(Comparable[] a) {
        int len = a.length;
        startTime();
        for (int i = 0; i < len; i++) {
            int min = i;
            for (int j = i + 1; j < len; j++)
                if (less(a[j], a[min]))
                    min = j;
            exch(a, i, min);
            assert isSorted(a, 0, i);
            if (isTimesUp()) {
                StdOut.println("'Selection sort'  was stopped because it was taking too long.");
                return false;
            }
        }
        assert isSorted(a);
        return true;
    }

    public static void main(String[] args) {

    }
}

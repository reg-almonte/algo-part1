/* *****************************************************************************
 *  Name: Reg Almonte
 *  Date: 2021/03/16
 *  Description: Insert Sort implementation based on the lecture
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

public class InsertSort extends SortHelper {

    public static boolean sort(Comparable[] a) {
        int n = a.length;
        startTime();
        for (int i = 1; i < n; i++) {
            for (int j = i; j > 0 && less(a[j], a[j - 1]); j--) {
                exch(a, j, j - 1);
            }
            assert isSorted(a, 0, i);
            if (isTimesUp()) {
                StdOut.println("'Insert sort' was stopped because it was taking too long.");
                return false;
            }
        }
        assert isSorted(a);
        return true;
    }

    public static void main(String[] args) {
    }
}

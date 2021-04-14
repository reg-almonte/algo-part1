/* *****************************************************************************
 *  Name: Reg Almonte
 *  Date: 2021/03/17
 *  Description: Test experiments for different sorting algorithms
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Experiment {
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        for (int m = 0; m < n; m++) {
            sortExperiment((int) Math.pow(10, m));
        }
    }

    private static void sortExperiment(int size) {
        Double[] a = new Double[size];
        for (int i = 0; i < size; i++)
            a[i] = StdRandom.uniform();

        StdOut.println("Sorting " + size + " element(s):");
        for (int i = 0; i < sortActions.length; i++) {
            Double[] copy = a.clone();
            long start = System.currentTimeMillis();
            if (sortActions[i].sort(copy)) {
                long timeElapsed = System.currentTimeMillis() - start;
                StdOut.println("Sort Type #" + i + ": " + timeElapsed
                                       + " ms");
            }
        }
    }

    interface SortAction {
        boolean sort(Comparable[] a);
    }

    private static SortAction[] sortActions = new SortAction[] {
            a -> QuickSort.sort(a),
            a -> MergeSort.sort(a),
            // a -> SelectionSort.sort(a),
            // a -> InsertSort.sort(a)
    };
}

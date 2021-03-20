/* *****************************************************************************
 *  Name: Reg Almonte
 *  Date: 2021/03/18
 *  Description: Implementation of DutchNational Flag problem as presented in the
 *  interview part of Algorithm 1 class, week 2.
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

public class DutchNationalFlag {
    public static void main(String[] args) {
        String[] colors = {
                "red1", "blue1", "white1", "white2", "blue2", "blue3", "red2", "white3", "blue4",
                "white4", "red3", "red4", "blue5", "white5", "blue6", "red5"
        };

        sortColors(colors);
        int len = colors.length;
        for (int i = 0; i < len; i++) {
            StdOut.print(colors[i] + " ");
        }
    }

    public static void sortColors(String[] a) {
        int len = a.length;
        int redIndex = 0;
        int blueIndex = len - 1;
        while (a[blueIndex].contains("blue")) {
            blueIndex--;
        }
        for (int i = 0; i < blueIndex; i++) {
            String curr = a[i];
            if (curr.contains("blue")) {
                swap(a, i, blueIndex--);
                while (a[blueIndex].contains("blue")) {
                    blueIndex--;
                }
                curr = a[i]; // New color
            }
            if (curr.contains("red")) {
                swap(a, i, redIndex++);
            }

        }
    }

    private static void swap(Comparable[] a, int hi, int lo) {
        Comparable swap = a[hi];
        a[hi] = a[lo];
        a[lo] = swap;
    }
}

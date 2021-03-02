/* *****************************************************************************
 *  Name:    Reg Almonte
 *  NetID:   ralmonte
 *  Precept: P00
 *
 *  Description:  Implementatin of percolation
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final boolean[] openness; // Percolation matrix
    private final int matLen;
    private final WeightedQuickUnionUF fullness;
    private final WeightedQuickUnionUF percolate;
    private final int topIndex;
    private final int bottomIndex;
    private int numOfOpenSites = 0;

    // creates matLen-by-matLen grid, with all sites initially blocked
    public Percolation(int n) {
        if (n < 1) {
            throw new IllegalArgumentException();
        }
        this.matLen = n;
        topIndex = 0;
        bottomIndex = n * n + 1;
        openness = new boolean[bottomIndex];
        percolate = new WeightedQuickUnionUF(bottomIndex + 1);
        fullness = new WeightedQuickUnionUF(bottomIndex);

        openness[topIndex] = true;
        // Connect the top level
        for (int i = 1; i <= matLen; i++) {
            percolate.union(topIndex, i);
            fullness.union(topIndex, i);
        }

        // Connect the bottom level
        for (int i = (n - 1) * n + 1; i <= bottomIndex; i++) {
            percolate.union(bottomIndex, i);
        }
    }

    private void checkBounds(int row, int col) {
        if (row > matLen || row < 1) {
            throw new IllegalArgumentException("row index is out of bounds");
        }
        if (col > matLen || col < 1) {
            throw new IllegalArgumentException("column index is out of bounds");
        }
    }

    private int serialIndex(int row, int col) {
        checkBounds(row, col);
        return (matLen * (row - 1) + col);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        int p = serialIndex(row, col);
        if (!openness[p]) {
            openness[p] = true;
            numOfOpenSites++;

            // Connect to top
            if (row > 1 && isOpen(row - 1, col)) {
                connectTwoPoints(p, row - 1, col);
            }

            // Connect to left
            if (col > 1 && isOpen(row, col - 1)) {
                connectTwoPoints(p, row, col - 1);
            }

            // Connect to right
            if (col < matLen && isOpen(row, col + 1)) {
                connectTwoPoints(p, row, col + 1);
            }

            // Connect to bottom
            if (row < matLen && isOpen(row + 1, col)) {
                connectTwoPoints(p, row + 1, col);
            }
        }
    }

    private void connectTwoPoints(int p, int row, int col) {
        int q = serialIndex(row, col);
        percolate.union(p, q);
        fullness.union(p, q);
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        int p = serialIndex(row, col);
        return openness[p];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        int p = serialIndex(row, col);
        return openness[p] && fullness.find(topIndex) == fullness
                .find(serialIndex(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        if (matLen > 1) {
            return percolate.find(topIndex) == percolate.find(bottomIndex);
        }
        return openness[1];
    }
}

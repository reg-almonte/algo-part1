/* *****************************************************************************
 *  Name: Reg Almonte
 *  Date: 2021/05/03
 *  Description: Implementation of Board.java
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Board {

    private final int[][] tiles;
    private final int n;
    private int row0 = 0, col0 = 0;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        n = tiles.length;
        this.tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.tiles[i][j] = tiles[i][j];
                if (tiles[i][j] == 0) {
                    row0 = i;
                    col0 = j;
                }
            }
        }
    }

    // string representation of this board
    public String toString() {
        String[] sb = new String[n+1];
        sb[0] = Integer.toString(n);
        for (int i = 0; i < n; i++) {
            sb[i+1] = Arrays.toString(this.tiles[i]).replaceAll("\\[|\\]", " ").replaceAll(",", " ");
        }
        return Arrays.toString(sb).replaceAll("\\[|\\]", "").replaceAll(", ", "\n");
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        int distance = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if ((i != n-1 || j != n-1) && this.tiles[i][j] != i*n + j + 1) {
                    distance++;
                }
            }
        }
        return distance;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int distance = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int val = this.tiles[i][j];
                if (val != 0) {
                    int row = (val-1) / n;
                    int col = (val - row*n - 1) % n;
                    distance += Math.abs(i - row);
                    distance += Math.abs(j - col);
                }
            }
        }
        return distance;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return this.manhattan() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) return false;
        if (!(y.getClass() == this.getClass())) return false;
        Board that = (Board) y;
        return this.toString().equals(that.toString());
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        return new Neighbors();
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int row1 = 0;
        int col1 = 0;
        int swap = -1;
        boolean done = false;
        boolean isFar = hamming() > 1;

        int[][] twinTiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] != 0 &&
                        ((tiles[i][j] != i*n + j + 1 && isFar) || !isFar)
                        && !done) {
                    if (swap == -1) {
                        row1 = i;
                        col1 = j;
                        swap = tiles[i][j];
                    } else {
                        twinTiles[row1][col1] = tiles[i][j];
                        twinTiles[i][j] = swap;
                        done = true;
                    }
                } else {
                    twinTiles[i][j] = tiles[i][j];
                }
            }
        }

        return new Board(twinTiles);
    }

    private class Neighbors implements Iterable<Board> {
        public Iterator<Board> iterator() {
            return new BoardIterator();
        }

        public class BoardIterator implements Iterator<Board> {
            private int nNeighbors;
            private int counter = 0;
            private int[][] newTiles = new int[n][n];
            private Board[] neighbors = new Board[4];

            public BoardIterator() {
                nNeighbors = 0;
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        newTiles[i][j] = tiles[i][j];
                    }
                }
                if (col0 > 0) { // Swap 0 to left
                    swapZero(0, -1);
                    addNewNeighbors(newTiles);
                    swapZero(0, -1);
                }
                if (col0 < n-1) { // Swap 0 to right
                    swapZero(0, 1);
                    addNewNeighbors(newTiles);
                    swapZero(0, 1);
                }
                if (row0 > 0) { // Swap 0 to the top
                    swapZero(-1, 0);
                    addNewNeighbors(newTiles);
                    swapZero(-1, 0);
                }
                if (row0 < n-1) { // Swap 0 to the bottom
                    swapZero(1, 0);
                    addNewNeighbors(newTiles);
                    swapZero(1, 0);
                }
            }

            private void swapZero(int rowOffset, int colOffset) {
                int swapVal = newTiles[row0][col0];
                newTiles[row0][col0] = newTiles[row0+rowOffset][col0+colOffset];
                newTiles[row0+rowOffset][col0+colOffset] = swapVal;
            }

            private void addNewNeighbors(int[][] neighborTiles) {
                neighbors[nNeighbors] = new Board(neighborTiles);
                nNeighbors++;
            }

            public boolean hasNext() {
                return counter < nNeighbors;
            }

            public Board next() {
                if (!hasNext()) throw new NoSuchElementException();
                return neighbors[counter++];
            }

            public void remove() { throw new UnsupportedOperationException();  }
        }
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] tiles = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
        int[][] answer = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        String strForm = "8 1 3\n4 0 2\n7 6 5";
        Board board = new Board(tiles);

        StdOut.println("Test toString():");
        String tilesStr = board.toString();
        StdOut.println(tilesStr);
        assert tilesStr.equals(strForm);

        StdOut.println("Test hamming():");
        int hamming = board.hamming();
        StdOut.println(hamming);
        assert hamming == 5;

        StdOut.println("Test manhattan():");
        int manhattan = board.manhattan();
        StdOut.println(manhattan);
        assert manhattan == 11;

        StdOut.println("Test equals():");
        Board boardCopy = new Board(tiles);
        boolean isEqual = board.equals(boardCopy);
        StdOut.println(isEqual);
        assert isEqual;

        Board boardCopy2 = new Board(new int[][] {{1, 2}, {3, 0}});
        isEqual = board.equals(boardCopy2);
        StdOut.println(isEqual);
        assert !isEqual;

        boardCopy2 = new Board(answer);
        isEqual = board.equals(boardCopy2);
        StdOut.println(isEqual);
        assert !isEqual;

        int i = 0;
        for (Board neighbor: board.neighbors()) {
            i++;
            StdOut.println("Neighbor # " + i);
            StdOut.println(neighbor.toString());
        }
        assert i == 4;
    }
}

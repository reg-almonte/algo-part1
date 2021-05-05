/* *****************************************************************************
 *  Name: Reg Almonte
 *  Date: 2021/05/03
 *  Description: Implementation of Solver.java
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;
import java.util.Iterator;

public final class Solver {
    Node lastNode;
    boolean isSolved = false;
    int pqCap = 100;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        MinPQ<Node> pq = new MinPQ<>(pqCap, new boardComparator());
        MinPQ<Node> pq2 = new MinPQ<>(pqCap, new boardComparator());
        Node root = new Node(initial, null, 0);
        Node root2 = new Node(initial.twin(), null, 0);
        pq.insert(root);
        pq2.insert(root2);
        lastNode = null;
        Node lastNode2 = null;
        while (!pq.isEmpty()) {
            lastNode = pq.delMin();
            if (pq2.isEmpty()){
                lastNode2 = new Node(lastNode.board.twin(), null, 0);
            } else {
                lastNode2 = pq2.delMin();
            }

            if (lastNode.board.hamming() == 0) {
                isSolved = true;
                break;
            }
            if (lastNode2.board.hamming() == 0) {
                break;
            }

            for (Board neighbor: lastNode.board.neighbors()) {
                if (lastNode.parent == null || !neighbor.equals(lastNode.parent.board)) {
                    Node newNode = new Node(neighbor, lastNode, lastNode.moves+1);
                    pq.insert(newNode);
                }
            }
            for (Board neighbor: lastNode2.board.neighbors()) {
                if (lastNode2.parent == null || !neighbor.equals(lastNode2.parent.board)) {
                    Node newNode = new Node(neighbor, lastNode2, lastNode2.moves+1);
                    pq2.insert(newNode);
                }
            }
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return isSolved;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return isSolvable() ? lastNode.moves : -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return new SolutionIterable();
    }

    private class Node {
        public Board board;
        public Node parent;
        private int moves;
        public int priority1, priority2;

        public Node(Board board, Node parent, int moves) {
            this.board = board;
            this.parent = parent;
            this.moves = moves;
            this.priority1 = moves + board.hamming();
            this.priority2 = moves + board.manhattan();
        }
    }

    private class boardComparator implements Comparator<Node> {
        public int compare(Node o1, Node o2) {
            return o1.priority2 - o2.priority2;
        }
    }

    private class SolutionIterable implements Iterable<Board> {

        public Iterator<Board> iterator() {
            return new SolutionIterator();
        }

        private class SolutionIterator implements Iterator<Board> {
            Stack<Board> history;

            public SolutionIterator() {
                history = new Stack<Board>();
                for(Node curr = lastNode; curr != null; curr = curr.parent) {
                    history.push(curr.board);
                }
            }

            public boolean hasNext() {
                return !history.isEmpty();
            }

            public Board next() {
                return history.pop();
            }

            public void remove() { throw new UnsupportedOperationException();  }
        }
    }

    // test client (see below)
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}

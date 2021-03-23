/* *****************************************************************************
 *  Name: Reg Almonte
 *  Date: 2021/03/23
 *  Description: Sort implementation of 'Collinear Points'.
 *               Algorithm 1 - Week 2 Programming Assignment
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class FastCollinearPoints {
    private int count;
    private LineSegment[] solution;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        count = 0;
        int arraySize = points.length;
        solution = new LineSegment[arraySize];

        for (int i = 0; i < arraySize - 3; i++) {
            sort(points, i); // Sort points[i+1] to points[arraySize-1]
            double currentSlope = points[i].slopeTo(points[i]);
            int counter = 0;
            int largest = i;
            int smallest = i;
            for (int j = i + 1; j < arraySize - 2; j++) {
                if (currentSlope == points[i].slopeTo(points[j])) {
                    counter++;
                    if (points[largest].compareTo(points[j]) < 0) {
                        largest = j;
                    }
                    else if (points[smallest].compareTo(points[j]) > 0) {
                        smallest = j;
                    }
                }
                else {
                    if (counter >= 4) {
                        LineSegment newLineSegment = new LineSegment(points[smallest],
                                                                     points[largest]);
                        solution[count++] = newLineSegment;
                    }
                    counter = 1;
                    largest = i;
                    smallest = i;
                    if (points[largest].compareTo(points[j]) < 0) {
                        largest = j;
                    }
                    else if (points[smallest].compareTo(points[j]) > 0) {
                        smallest = j;
                    }
                }

            }
        }
    }

    private static void merge(Point[] a, Point[] aux, int base, int lo, int mid, int hi) {
        // assert isSorted(a, lo, mid); // precondition: a[lo..mid] sorted
        // assert isSorted(a, mid + 1, hi); // precondition: a[mid+1..hi] sorted
        for (int k = lo; k <= hi; k++)
            aux[k] = a[k];
        int i = lo, j = mid + 1;
        Comparator<Point> slopeOrder = a[base].slopeOrder();
        for (int k = lo; k <= hi; k++) {
            if (i > mid) a[k] = aux[j++];
            else if (j > hi) a[k] = aux[i++];
            else if (slopeOrder.compare(aux[j], aux[i]) < 0) a[k] = aux[j++];
            else a[k] = aux[i++];
        }
    }

    public static void sort(Point[] a, int base) {
        int N = a.length - base - 1;
        Point[] aux = new Point[N];
        for (int sz = 1; sz < N; sz = sz + sz)
            for (int lo = base; lo < N - sz; lo += sz + sz)
                merge(a, aux, base, lo, lo + sz - 1, Math.min(lo + sz + sz - 1, N - 1));
    }


    // the number of line segments
    public int numberOfSegments() {
        return count;
    }

    // the line segments
    public LineSegment[] segments() {
        int currSize = numberOfSegments();
        LineSegment[] copy = new LineSegment[currSize];
        for (int i = 0; i < currSize; i++) copy[i] = solution[i];
        return copy;
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);

        StdOut.println("Count: " + collinear.numberOfSegments());
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}

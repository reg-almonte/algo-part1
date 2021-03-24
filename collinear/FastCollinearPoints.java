/* *****************************************************************************
 *  Name: Reg Almonte
 *  Date: 2021/03/23
 *  Description: Sort implementation of 'Collinear Points'.
 *               Algorithm 1 - Week 2 Programming Assignment
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Comparator;

public class FastCollinearPoints {
    private int count;
    private LineSegment[] solution;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        for (Point point : points) {
            if (point == null) {
                throw new IllegalArgumentException();
            }
        }

        Arrays.sort(points);
        for (int i = 0; i < points.length; i++) {
            if (i > 0 && Double.compare(points[i].slopeTo(points[i - 1]),
                                        Double.NEGATIVE_INFINITY) == 0) {
                throw new IllegalArgumentException();
            }
        }

        process(points);
    }

    private static void merge(Point[] a, Point[] aux, Point base, int lo, int mid, int hi) {
        // assert isSorted(a, lo, mid); // precondition: a[lo..mid] sorted
        // assert isSorted(a, mid + 1, hi); // precondition: a[mid+1..hi] sorted
        for (int k = lo; k <= hi; k++)
            aux[k] = a[k];
        int i = lo, j = mid + 1;
        Comparator<Point> slopeOrder = base.slopeOrder();
        for (int k = lo; k <= hi; k++) {
            if (i > mid) a[k] = aux[j++];
            else if (j > hi) a[k] = aux[i++];
            else if (slopeOrder.compare(aux[j], aux[i]) < 0) a[k] = aux[j++];
            else a[k] = aux[i++];
        }
    }

    private static void sort(Point[] a, Point base) {
        int N = a.length;
        Point[] aux = new Point[N];
        for (int sz = 1; sz < N; sz = sz + sz)
            for (int lo = 0; lo < N - sz; lo += sz + sz) {
                merge(a, aux, base, lo, lo + sz - 1, Math.min(lo + sz + sz - 1, a.length - 1));
            }
    }

    private void process(Point[] points) {
        count = 0;
        int arraySize = points.length;
        if (arraySize < 4) return; // Cannot form a 'line segment'
        solution = new LineSegment[arraySize];
        for (int i = 0; i < arraySize - 3; i++) {
            Point[] copy = Arrays.copyOf(points, arraySize);
            sort(copy, points[i]); // Sort points[i+1] to points[arraySize-1]
            double currentSlope = points[i].slopeTo(points[i]);
            int counter = 0;
            int smallest = 0, largest = i;
            for (int j = 0; j < arraySize; j++) {
                if (currentSlope == points[i].slopeTo(copy[j])) {
                    counter++;
                    largest = j;
                    if (copy[smallest].compareTo(copy[j]) > 0) smallest = j;
                }
                else {
                    if (counter >= 3 && 0 == smallest) {
                        LineSegment newLineSegment = new LineSegment(points[i],
                                                                     copy[largest]);
                        solution[count++] = newLineSegment;
                    }
                    currentSlope = points[i].slopeTo(copy[j]);
                    counter = 1;
                    largest = j;
                    if (copy[0].compareTo(copy[j]) > 0) smallest = j;
                    else smallest = 0;
                }
            }
            if (counter >= 3 && 0 == smallest) {
                LineSegment newLineSegment = new LineSegment(points[i],
                                                             copy[largest]);
                solution[count++] = newLineSegment;
            }
        }
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
        // print and draw the line segments
        long start = System.currentTimeMillis();
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        long timeElapsed = System.currentTimeMillis() - start;
        StdOut.println("Time elapsed: " + timeElapsed + " ms");

        StdOut.println("Count: " + collinear.numberOfSegments());
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}

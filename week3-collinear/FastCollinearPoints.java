/* *****************************************************************************
 *  Name: Reg Almonte
 *  Date: 2021/03/23
 *  Description: Sort implementation of 'Collinear Points'.
 *               Algorithm 1 - Week 2 Programming Assignment
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private final Point[] points;
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

        this.points = Arrays.copyOf(points, points.length);
        Arrays.sort(this.points);
        for (int i = 0; i < points.length; i++) {
            if (i > 0 && Double.compare(this.points[i].slopeTo(this.points[i - 1]),
                                        Double.NEGATIVE_INFINITY) == 0) {
                throw new IllegalArgumentException();
            }
        }
        solution = new LineSegment[0];
        process();
    }

    private void process() {
        int arraySize = points.length;
        if (arraySize < 4) return; // Cannot form a 'line segment'
        ArrayList<LineSegment> list = new ArrayList<>();
        for (Point p : points) {
            Point[] copy = Arrays.copyOf(points, arraySize);
            Arrays.sort(copy, p.slopeOrder());

            int counter = 1, smallest = 0, largest = 0;
            double currSlope = p.slopeTo(copy[largest]);
            for (int j = 1; j < arraySize; j++) {
                double thisSlope = p.slopeTo(copy[j]);
                if (currSlope == thisSlope) {
                    counter++;
                    largest = j;
                    if (copy[smallest].compareTo(copy[j]) > 0) smallest = j;
                }
                if (thisSlope != currSlope || j == arraySize - 1) {
                    if (counter >= 3 && 0 == smallest) list.add(new LineSegment(p, copy[largest]));
                    currSlope = p.slopeTo(copy[j]);
                    counter = 1;
                    largest = j;
                    if (copy[0].compareTo(copy[j]) > 0) smallest = j;
                    else smallest = 0;
                }
            }
        }
        solution = new LineSegment[list.size()];
        list.toArray(solution);
    }


    // the number of line segments
    public int numberOfSegments() {
        return solution.length;
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

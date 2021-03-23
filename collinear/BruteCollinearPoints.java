/* *****************************************************************************
 *  Name: Reg Almonte
 *  Date: 2021/03/23
 *  Description: Brute-force implementation of 'Collinear Points'.
 *               Algorithm 1 - Week 2 Programming Assignment
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class BruteCollinearPoints {
    private int count;
    private LineSegment[] solution;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
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

    private void process(Point[] points) {
        count = 0;
        int arraySize = points.length;
        if (arraySize < 4) return; // Cannot form a 'line segment'
        solution = new LineSegment[arraySize - 3];
        boolean[] doneIndexes = new boolean[arraySize];
        double[] slopes = new double[arraySize];
        for (int i = 0; i < arraySize; i++) {
            for (int j = i + 1; j < arraySize; j++) {
                double slope = points[i].slopeTo(points[j]);
                if (doneIndexes[j] && slopes[j] == slope) continue;

                int largest = j, smallest = i;
                boolean positive = false;

                for (int k = j + 1; k < arraySize; k++) {
                    if (slope != points[i].slopeTo(points[k])) continue;
                    for (int l = 0; l < arraySize; l++) {
                        if (l == i || l == j || l == k) continue;
                        if (slope == points[i].slopeTo(points[l])) {
                            // StdOut.println(
                            //         "Points and slope: " + i + "," + j + "," + k + "," + l + ","
                            //                 + slope);
                            positive = true;
                            doneIndexes[i] = true;
                            doneIndexes[j] = true;
                            doneIndexes[k] = true;
                            doneIndexes[l] = true;
                            slopes[i] = slope;
                            slopes[j] = slope;
                            slopes[k] = slope;
                            slopes[l] = slope;
                            if (l > k) largest = l;
                            else largest = k;
                            if (l < smallest) smallest = l;
                        }
                    }
                }
                if (positive && smallest == i) {
                    LineSegment newLineSegment = new LineSegment(points[i],
                                                                 points[largest]);
                    // StdOut.println("Recorded: " + i + "= " + newLineSegment.toString());
                    solution[count++] = newLineSegment;
                }
            }
        }
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

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
    private final Point[] points;
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
        this.points = Arrays.copyOf(points, points.length);
        Arrays.sort(this.points);
        for (int i = 0; i < this.points.length; i++) {
            if (i > 0 && Double.compare(this.points[i].slopeTo(this.points[i - 1]),
                                        Double.NEGATIVE_INFINITY) == 0) {
                throw new IllegalArgumentException();
            }
        }
        process();
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

    private void process() {
        count = 0;
        int arraySize = points.length;
        if (arraySize < 4) return; // Cannot form a 'line segment'
        solution = new LineSegment[arraySize - 3];

        for (int i = 0; i < arraySize; i++) {
            double[] slopes = new double[(arraySize - 1) / 3];
            int numSlopes = 0;
            int largest = i;
            for (int j = i + 1; j < arraySize; j++) {
                double slope = points[i].slopeTo(points[j]);
                boolean processed = false;
                for (int n = 0; n < numSlopes; n++) {
                    if (slopes[n] == slope) {
                        processed = true;
                        break;
                    }
                }
                if (processed) continue;
                int counter = 0;
                for (int k = j + 1; k < arraySize; k++) {
                    if (slope != points[i].slopeTo(points[k])) continue;
                    for (int f = 0; f < arraySize; f++) {
                        if (f >= i && f <= k) continue;
                        if (slope == points[i].slopeTo(points[f])) {
                            if (f < i) break;
                            counter++;
                            largest = f;
                        }
                    }
                    if (counter > 0) break;
                }
                if (counter > 0) {
                    LineSegment newLineSegment = new LineSegment(points[i], points[largest]);
                    solution[count++] = newLineSegment;
                    slopes[numSlopes++] = slope;
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
        long start = System.currentTimeMillis();
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
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

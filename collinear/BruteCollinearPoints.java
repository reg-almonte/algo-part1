/* *****************************************************************************
 *  Name: Reg Almonte
 *  Date: 2021/03/23
 *  Description: Brute-force implementation of 'Collinear Points'.
 *               Algorithm 1 - Week 2 Programming Assignment
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
    private int count;
    private LineSegment[] solution;
    private boolean[] doneIndexes;
    private double[] slopes;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        count = 0;
        int arraySize = points.length;
        solution = new LineSegment[arraySize];
        doneIndexes = new boolean[arraySize];
        slopes = new double[arraySize];
        for (int i = 0; i < arraySize - 3; i++) {
            for (int j = i + 1; j < arraySize - 2; j++) {
                double slope = points[i].slopeTo(points[j]);
                if (doneIndexes[j] && slopes[j] == slope) continue;

                int largest = i, smallest = i;
                if (points[i].compareTo(points[j]) > 0) smallest = j;
                else largest = j;

                boolean positive = false;
                for (int k = j + 1; k < arraySize; k++) {
                    if (slope != points[i].slopeTo(points[k])) continue;
                    for (int l = k + 1; l < arraySize; l++) {
                        if (slope == points[i].slopeTo(points[l])) {
                            positive = true;
                            doneIndexes[i] = true;
                            doneIndexes[j] = true;
                            doneIndexes[k] = true;
                            doneIndexes[l] = true;
                            slopes[i] = slope;
                            slopes[j] = slope;
                            slopes[k] = slope;
                            slopes[l] = slope;
                            // StdOut.println(
                            //         "Points and slope: " + i + "," + j + "," + k + "," + l + ","
                            //                 + slope);
                            if (points[largest].compareTo(points[k]) < 0) {
                                largest = k;
                            }
                            else if (points[smallest].compareTo(points[k]) > 0) {
                                smallest = k;
                            }
                            if (points[largest].compareTo(points[l]) < 0) {
                                largest = l;
                            }
                            else if (points[smallest].compareTo(points[l]) > 0) {
                                smallest = l;
                            }
                        }
                    }
                }
                if (positive) {
                    LineSegment newLineSegment = new LineSegment(points[smallest],
                                                                 points[largest]);
                    solution[count++] = newLineSegment;
                    // StdOut.println("Recorded: " + newLineSegment.toString());
                }
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);

        StdOut.println("Count: " + collinear.numberOfSegments());
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}

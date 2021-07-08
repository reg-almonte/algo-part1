/* *****************************************************************************
 *  Name: Reg Almonte
 *  Date: July 4, 2021
 *  Description: Week5-Programming Assignment
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.ArrayList;
import java.util.TreeSet;

public class PointSET {

    TreeSet<Point2D> setOfPoints;
    ArrayList<Point2D> rangeSet = new ArrayList<Point2D>();

    // construct an empty set of points
    public PointSET() {
        setOfPoints = new TreeSet();
    }

    // is the set empty?
    public boolean isEmpty() {
        return setOfPoints.isEmpty();
    }

    // number of points in the set
    public int size() {
        return setOfPoints.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        setOfPoints.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        return setOfPoints.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D p : setOfPoints) {
            p.draw();
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        for (Point2D p : setOfPoints) {
            if (rect.contains(p)) {
                rangeSet.add(p);
            }
        }
        return rangeSet;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (isEmpty()) return null;
        double min = 2; // unit square max distance is sqrt(2)
        Point2D result = null;
        for (Point2D that : setOfPoints) {
            double dist = p.distanceTo(that);
            if (dist < min) {
                min = dist;
                result = that;
            }
        }
        return result;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        int n = 10;

        PointSET ptSet = new PointSET();
        Point2D finalPt = new Point2D(0, 0);
        for (int i = 0; i < n; i++) {
            double x = StdRandom.uniform(0.0, 1.0);
            double y = StdRandom.uniform(0.0, 1.0);
            StdOut.printf("%d : %8.6f %8.6f\n", i, x, y);
            finalPt = new Point2D(x, y);
            ptSet.insert(finalPt);
        }
        ptSet.draw();
        RectHV rectangle = new RectHV(finalPt.x() - 0.01, finalPt.y() - 0.01,
                                      finalPt.x() + 0.01, finalPt.y() + 0.01);
        rectangle.draw();
        StdOut.println(rectangle.toString());
        Iterable<Point2D> rangeSet = ptSet.range(rectangle);
        for (Point2D p : rangeSet) {
            StdOut.println(p + " is in range");
        }

    }
}

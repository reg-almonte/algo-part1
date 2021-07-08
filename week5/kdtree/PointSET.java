/* *****************************************************************************
 *  Name: Reg Almonte
 *  Date: July 4, 2021
 *  Description: Week5-Programming Assignment
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.awt.Color;
import java.util.ArrayList;
import java.util.TreeSet;

public class PointSET {

    private TreeSet<Point2D> setOfPoints;
    private ArrayList<Point2D> rangeSet;

    // construct an empty set of points
    public PointSET() {
        setOfPoints = new TreeSet<Point2D>();
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
        checkPoint2DInput(p);
        setOfPoints.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        checkPoint2DInput(p);
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
        if (rect == null) {
            throw new IllegalArgumentException();
        }
        rangeSet = new ArrayList<Point2D>();
        for (Point2D p : setOfPoints) {
            if (rect.contains(p)) {
                rangeSet.add(p);
            }
        }
        return rangeSet;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        checkPoint2DInput(p);
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

    private void checkPoint2DInput(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        int n = 20;

        KdTree ptSet = new KdTree();
        Point2D finalPt = new Point2D(0, 0);
        for (int i = 0; i < n; i++) {
            double x = StdRandom.uniform(0.0, 1.0);
            double y = StdRandom.uniform(0.0, 1.0);
            StdOut.printf("%d : %8.6f %8.6f\n", i, x, y);
            finalPt = new Point2D(x, y);
            ptSet.insert(finalPt);
        }
        ptSet.draw();
        // RectHV rectangle = new RectHV(finalPt.x() - 0.01, finalPt.y() - 0.01,
        //                               finalPt.x() + 0.01, finalPt.y() + 0.01);
        RectHV rectangle = new RectHV(0.1, 0.1, 0.65, 0.5);
        StdDraw.setPenColor(0, 255, 0);
        rectangle.draw();

        StdOut.println(rectangle.toString());
        Iterable<Point2D> rangeResult = ptSet.range(rectangle);
        int i = 1;
        StdOut.println("Range Result: ");
        for (Point2D p: rangeResult) {
            StdOut.println(i + ": " + p.toString());
            i++;
        }
        Point2D myPoint = new Point2D(0.5, 0.5);
        StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor(Color.red);
        myPoint.draw();
        Point2D nearestToMyPoint = ptSet.nearest(myPoint);
        StdDraw.setPenRadius(0.002);
        StdDraw.setPenColor(Color.magenta);
        StdDraw.line(myPoint.x(), myPoint.y(), nearestToMyPoint.x(), nearestToMyPoint.y());
    }
}

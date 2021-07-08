/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.awt.Color;
import java.util.ArrayList;

public class KdTree {
    private KdPoint root = null;
    private int count = 0;
    private ArrayList<Point2D> rangeResult;
    private double minDist = 2;
    private Point2D nearestPoint;

    // construct an empty set of points
    public KdTree() {
        root = null;
        count = 0;
    }

    // is the set empty?
    public boolean isEmpty() {
        return count == 0;
    }

    // number of points in the set
    public int size() {
        return count;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        root = insertRecursive(root, p, 0);
    }

    private KdPoint insertRecursive(KdPoint curr, Point2D p, int level) {
        if (curr == null) {
            return new KdPoint(p, level);
        }
        if (curr.isLessThan(p)) {
            curr.right = insertRecursive(curr.right, p, level+1);
        } else {
            curr.left = insertRecursive(curr.left, p, level+1);
        }
        return curr;
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        return searchRecursive(root, p);
    }

    private boolean searchRecursive(KdPoint curr, Point2D p) {
        if (curr == null) {
            return false;
        }
        if (curr.data.equals(p)) {
            return true;
        }
        if (curr.isLessThan(p)) {
            return searchRecursive(curr.left, p);
        }
        return searchRecursive(curr.right, p);
    }

    // draw all points to standard draw
    public void draw() {
        if (root != null) {
            StdDraw.setPenColor(255, 0, 0);
        }
        drawRecursive(root, 0, 0, 1, 1);
    }

    private void drawRecursive(KdPoint curr,
                               double minx, double miny,
                               double maxx, double maxy) {
        if (curr != null) {
            StdDraw.setPenColor(0, 0, 0);
            StdDraw.setPenRadius(0.01);
            curr.data.draw();
            curr.draw(minx, miny, maxx, maxy); // draw lines of children
            if (curr.level % 2 == 0) {
                drawRecursive(curr.left, minx, miny, curr.data.x(), maxy);
                drawRecursive(curr.right, curr.data.x(), miny, maxx, maxy);
            } else {
                drawRecursive(curr.left, minx, miny, maxx, curr.data.y());
                drawRecursive(curr.right, minx, curr.data.y(), maxx, maxy);
            }

        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        rangeResult = new ArrayList<>();
        rangeRecursive(root, rect);
        return rangeResult;
    }

    private void rangeRecursive(KdPoint curr, RectHV rect) {
        if (curr == null) { return; }
        if (rect.contains(curr.data)) {
            rangeResult.add(curr.data);
        }
        if (curr.level % 2 == 0) {
            if (rect.xmin() < curr.data.x()) {
                rangeRecursive(curr.left, rect);
            }
            if (rect.xmax() > curr.data.x()) {
                rangeRecursive(curr.right, rect);
            }
        } else {
            if (rect.ymin() < curr.data.y()) {
                rangeRecursive(curr.left, rect);
            }
            if (rect.ymax() > curr.data.y()) {
                rangeRecursive(curr.right, rect);
            }
        }
    }


    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        minDist = 2;
        nearestRecursive(root, p);
        return nearestPoint;
    }

    private void nearestRecursive(KdPoint curr, Point2D p) {
        if (curr == null) { return; }
        double thisDist = p.distanceTo(curr.data);
        StdOut.println("Checking: " + curr.data.toString());
        if (thisDist < minDist) {
            StdOut.println("New Champions: " + thisDist);
            nearestPoint = curr.data;
            minDist = thisDist;
        }

        if (curr.level % 2 == 0) {
            if (curr.data.x() > p.x()) {
                nearestRecursive(curr.left, p);
                if (thisDist == minDist) {
                    nearestRecursive(curr.right, p);
                }
            } else {
                nearestRecursive(curr.right, p);
                if (thisDist == minDist) {
                    nearestRecursive(curr.left, p);
                }
            }
        } else {
            if (curr.data.y() > p.y()) {
                nearestRecursive(curr.left, p);
                if (thisDist == minDist) {
                    nearestRecursive(curr.right, p);
                }
            } else {
                nearestRecursive(curr.right, p);
                if (thisDist == minDist) {
                    nearestRecursive(curr.left, p);
                }
            }
        }
    }

    public static void main(String[] args) {
        int n = 20;

        KdTree ptSet = new KdTree();
        Point2D finalPt = new Point2D(0, 0);
        // finalPt = new Point2D(0.5, 0.7);
        // ptSet.insert(finalPt);
        // finalPt = new Point2D(0.2, 0.4);
        // ptSet.insert(finalPt);
        // finalPt = new Point2D(0.6, 0.2);
        // ptSet.insert(finalPt);
        // finalPt = new Point2D(0.8, 0.6);
        // ptSet.insert(finalPt);
        // finalPt = new Point2D(0.6, 0.7);
        // ptSet.insert(finalPt);
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

    private class KdPoint {
        public int level;
        public Point2D data;
        public KdPoint left;
        public KdPoint right;

        public KdPoint(Point2D p, int lvl) {
            data = p;
            left = null;
            right = null;
            level = lvl;
        }

        public boolean isLessThan(Point2D that) {
            if (level % 2 == 0) {
                return this.data.x() < that.x();
            } else {
                return this.data.y() < that.y();
            }
        }

        public void draw(double minx, double miny, double maxx, double maxy) {
            StdDraw.setPenRadius(0.002);
            if (level % 2 == 0) {
                // Draw vertically from, use x-coordinate
                StdDraw.setPenColor(255, 0, 0);
                StdDraw.line(data.x(), miny, data.x(), maxy);
            } else {
                // Draw horizontally from, use y-coordinate
                StdDraw.setPenColor(0, 0, 255);
                StdDraw.line(minx, data.y(), maxx, data.y());
            }
        }
    }
}

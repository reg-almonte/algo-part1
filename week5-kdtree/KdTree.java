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
        validatePoint2DInput(p);
        root = insertRecursive(root, p, 0);
    }

    private KdPoint insertRecursive(KdPoint curr, Point2D p, int level) {
        if (curr == null) {
            count++;
            return new KdPoint(p, level);
        }
        if (curr.getData().equals(p)) {
            return curr;
        }
        if (curr.isLessThan(p)) {
            curr.setRight(insertRecursive(curr.getRight(), p, level + 1));
        }
        else {
            curr.setLeft(insertRecursive(curr.getLeft(), p, level + 1));
        }
        return curr;
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        validatePoint2DInput(p);
        return searchRecursive(root, p);
    }

    private boolean searchRecursive(KdPoint curr, Point2D p) {
        if (curr == null) {
            return false;
        }
        if (curr.getData().equals(p)) {
            return true;
        }
        if (curr.isLessThan(p)) {
            return searchRecursive(curr.getRight(), p);
        }
        return searchRecursive(curr.getLeft(), p);
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
            curr.getData().draw();
            curr.draw(minx, miny, maxx, maxy); // draw lines of children
            if (curr.getLevel() % 2 == 0) {
                drawRecursive(curr.getLeft(), minx, miny, curr.getData().x(), maxy);
                drawRecursive(curr.getRight(), curr.getData().x(), miny, maxx, maxy);
            }
            else {
                drawRecursive(curr.getLeft(), minx, miny, maxx, curr.getData().y());
                drawRecursive(curr.getRight(), minx, curr.getData().y(), maxx, maxy);
            }

        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException();
        }
        rangeResult = new ArrayList<>();
        rangeRecursive(root, rect, 0, 0, 1, 1);
        return rangeResult;
    }

    private void rangeRecursive(KdPoint curr, RectHV rect,
                                double minx, double miny,
                                double maxx, double maxy) {
        if (curr == null) {
            return;
        }
        RectHV currRange = new RectHV(minx, miny, maxx, maxy);
        if (!rect.intersects(currRange)) {
            return;
        }
        if (rect.contains(curr.getData())) {
            rangeResult.add(curr.getData());
        }
        if (curr.getLevel() % 2 == 0) {
            if (rect.xmin() <= curr.getData().x()) {
                rangeRecursive(curr.getLeft(), rect, minx, miny, curr.getData().x(), maxy);
            }
            if (rect.xmax() > curr.getData().x()) {
                rangeRecursive(curr.getRight(), rect, curr.getData().x(), miny, maxx, maxy);
            }
        }
        else {
            if (rect.ymin() <= curr.getData().y()) {
                rangeRecursive(curr.getLeft(), rect, minx, miny, maxx, curr.getData().y());
            }
            if (rect.ymax() > curr.getData().y()) {
                rangeRecursive(curr.getRight(), rect, minx, curr.getData().y(), maxx, maxy);
            }
        }
    }


    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        validatePoint2DInput(p);
        minDist = 2;
        nearestRecursive(root, p, 0, 0, 1, 1);
        return nearestPoint;
    }

    private void nearestRecursive(KdPoint curr, Point2D p,
                                  double minx, double miny,
                                  double maxx, double maxy) {
        if (curr == null) {
            return;
        }
        RectHV currRange = new RectHV(minx, miny, maxx, maxy);

        // StdOut.println(
        //         "Nearest Processing: " + curr.data.toString() + " | " + currRange.toString() + " | "
        //                 + currRange.distanceTo(p));

        if (currRange.distanceTo(p) > minDist) {
            // StdOut.println("Pruning...");
            return;
        }

        double thisDist = p.distanceTo(curr.getData());
        // StdOut.println("Checking: " + curr.data.toString());
        if (thisDist < minDist) {
            // StdOut.println("New Champions: " + thisDist);
            nearestPoint = curr.getData();
            minDist = thisDist;
        }

        if (curr.getLevel() % 2 == 0) {
            if (curr.getData().x() > p.x()) {
                nearestRecursive(curr.getLeft(), p, minx, miny, curr.getData().x(), maxy);
                // if (thisDist >= minDist) {
                nearestRecursive(curr.getRight(), p, curr.getData().x(), miny, maxx, maxy);
                //}
            }
            else {
                nearestRecursive(curr.getRight(), p, curr.getData().x(), miny, maxx, maxy);
                // if (thisDist >= minDist) {
                nearestRecursive(curr.getLeft(), p, minx, miny, curr.getData().x(), maxy);
                //}
            }
        }
        else {
            if (curr.getData().y() > p.y()) {
                nearestRecursive(curr.getLeft(), p, minx, miny, maxx, curr.getData().y());
                // if (thisDist == minDist) {
                nearestRecursive(curr.getRight(), p, minx, curr.getData().y(), maxx, maxy);
                //}
            }
            else {
                nearestRecursive(curr.getRight(), p, minx, curr.getData().y(), maxx, maxy);
                // if (thisDist == minDist) {
                nearestRecursive(curr.getLeft(), p, minx, miny, maxx, curr.getData().y());
                // }
            }
        }
    }

    private void validatePoint2DInput(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
    }

    public static void main(String[] args) {
        int n = 20;
        KdTree ptSet = new KdTree();
        for (int i = 0; i < n; i++) {
            double x = StdRandom.uniform(0.0, 1.0);
            double y = StdRandom.uniform(0.0, 1.0);
            StdOut.printf("%d : %8.6f %8.6f\n", i, x, y);
            Point2D finalPt = new Point2D(x, y);
            ptSet.insert(finalPt);
        }
        ptSet.insert(new Point2D(0.25, 0.25));
        ptSet.insert(new Point2D(0.25, 0.25));

/*
        Point2D samplePt = new Point2D(0.241, 0.422);
        ptSet.insert(samplePt);
        samplePt = new Point2D(0.711, 0.288);
        ptSet.insert(samplePt);
        samplePt = new Point2D(0.117, 0.544);
        ptSet.insert(samplePt);
        samplePt = new Point2D(0.128, 0.118);
        ptSet.insert(samplePt);
        samplePt = new Point2D(0.059, 0.034);
        ptSet.insert(samplePt);
        samplePt = new Point2D(0.053, 0.788);
        ptSet.insert(samplePt);
        samplePt = new Point2D(0.920, 0.245);
        ptSet.insert(samplePt);
        samplePt = new Point2D(0.335, 0.249);
        ptSet.insert(samplePt);
        samplePt = new Point2D(0.384, 0.450);
        ptSet.insert(samplePt);
        samplePt = new Point2D(0.028, 0.705);
        ptSet.insert(samplePt);
        samplePt = new Point2D(0.301, 0.799);
        ptSet.insert(samplePt);
        samplePt = new Point2D(0.898, 0.505);
        ptSet.insert(samplePt);
        samplePt = new Point2D(0.937, 0.737);
        ptSet.insert(samplePt);
        samplePt = new Point2D(0.704, 0.108);
        ptSet.insert(samplePt);
        samplePt = new Point2D(0.197, 0.678);
        ptSet.insert(samplePt);
        samplePt = new Point2D(0.576, 0.518);
        ptSet.insert(samplePt);
        samplePt = new Point2D(0.621, 0.436);
        ptSet.insert(samplePt);
        samplePt = new Point2D(0.073, 0.437);
        ptSet.insert(samplePt);
        samplePt = new Point2D(0.956, 0.152);
        ptSet.insert(samplePt);
        samplePt = new Point2D(0.536, 0.246);
        ptSet.insert(samplePt);
*/
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
        for (Point2D p : rangeResult) {
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
        private int level;
        private Point2D data;
        private KdPoint left;
        private KdPoint right;

        public KdPoint(Point2D p, int lvl) {
            setData(p);
            setLeft(null);
            setRight(null);
            setLevel(lvl);
        }

        public boolean isLessThan(Point2D that) {
            if (getLevel() % 2 == 0) {
                return this.getData().x() < that.x();
            }
            else {
                return this.getData().y() < that.y();
            }
        }

        public void draw(double minx, double miny, double maxx, double maxy) {
            StdDraw.setPenRadius(0.002);
            if (getLevel() % 2 == 0) {
                // Draw vertically from, use x-coordinate
                StdDraw.setPenColor(255, 0, 0);
                StdDraw.line(getData().x(), miny, getData().x(), maxy);
            }
            else {
                // Draw horizontally from, use y-coordinate
                StdDraw.setPenColor(0, 0, 255);
                StdDraw.line(minx, getData().y(), maxx, getData().y());
            }
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public Point2D getData() {
            return data;
        }

        public void setData(Point2D data) {
            this.data = data;
        }

        public KdPoint getLeft() {
            return left;
        }

        public void setLeft(KdPoint left) {
            this.left = left;
        }

        public KdPoint getRight() {
            return right;
        }

        public void setRight(KdPoint right) {
            this.right = right;
        }
    }
}

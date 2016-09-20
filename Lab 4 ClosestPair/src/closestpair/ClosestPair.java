/* 
* Bradley White
* Lab 4: Closest Pair
* CSCI 232
* April 23, 2016
 */
// NetBeans package
package closestpair;

import java.awt.geom.Point2D;
import java.util.*;

public class ClosestPair {

    // Something
    // Hardcoded set of points sorted by x coordinate
    private static final Point2D[] pointsX = new Point2D[]{new Point2D.Double(2.0, 7.0), new Point2D.Double(4.0, 13.0), new Point2D.Double(5.0, 8.0), new Point2D.Double(10.0, 5.0), new Point2D.Double(14.0, 9.0),
        new Point2D.Double(15.0, 5.0), new Point2D.Double(17.0, 7.0), new Point2D.Double(19.0, 10.0), new Point2D.Double(22.0, 7.0), new Point2D.Double(25.0, 10.0),
        new Point2D.Double(29.0, 14.0), new Point2D.Double(30.0, 2.0)};

    // The two points that are currently the closest pair
    private static Point2D[] currentPoints = new Point2D[2];
    // The closest pair distance
    private static double currentMin = Double.POSITIVE_INFINITY;
    // Total amount of points in the graph
    private static final int amountOfPoints = pointsX.length;
    // ArrayLists to save space during recursion, sorted by X or Y coordinate
    private static List<Point2D.Double> yList = new ArrayList<>(amountOfPoints);
    private static List<Point2D.Double> xList = new ArrayList<>(amountOfPoints);

    public static void main(String[] args) {
        // Call helpers before solving
        printPoints();
        preProcess();
        // Start recursion and solving
        divide(xList, amountOfPoints);
        // Print results
        System.out.format("-------------------------------------\n");
        System.out.format("Final Result: P1:(%.1f,%.1f), P2: (%.1f,%.1f), Distance: %.1f\n", currentPoints[0].getX(), currentPoints[0].getY(), currentPoints[1].getX(), currentPoints[1].getY(), currentMin);
    }
    
    // Recursively divide the problem
    public static double divide(List<Point2D.Double> list, int n) {
        // Print the range of points currently being worked on. Points have the same index in xList and pointsX
        System.out.format("Solving Problem: Point[%d]...Point[%d]\n", xList.indexOf(list.get(0)), xList.indexOf(list.get(n - 1)));
        // Base case 1, a single point in the subproblem
        if (n == 1) {
            System.out.format(" Found Result: Infinity\n");
            return Double.POSITIVE_INFINITY;
        }
        // Base case 2, two points in the subproblem
        if (n == 2) {
            // If only two points the closest pair is within this subproblem
            double distance = list.get(0).distance(list.get(1));
            System.out.format(" Found Result: P1:(%.1f,%.1f), P2: (%.1f,%.1f), Distance: %.1f\n", list.get(0).getX(), list.get(0).getY(), list.get(1).getX(), list.get(1).getY(), distance);
            // If a new closest pair was found, update variables
            if (distance < currentMin) {
                currentPoints[0] = list.get(0);
                currentPoints[1] = list.get(1);
                currentMin = distance;
            }
            return distance;
        }

        // Calculate the midpoint of the list to divide the problem
        int m = (int) (Math.ceil(n / 2) - 1);
        System.out.format(" Dividing at Point[%d]\n", m);

        // Create sublists that only reference the original xList, to avoid wasting time and memory copying arrays
        List<Point2D.Double> leftList = list.subList(0, m + 1);
        List<Point2D.Double> rightList = list.subList(m + 1, n);

        // Recursively call divide on the sublists to create subproblems
        double dleft = divide(leftList, leftList.size());
        double dright = divide(rightList, rightList.size());

        // Print which subproblems are being combined, if statement is to avoid index out of bounds exceptions
        if ((m + 1) < rightList.size()) {
            System.out.format("Combining Problems: Point[%d]...Point[%d] and Point[%d]...Point[%d]\n", xList.indexOf(leftList.get(0)), xList.indexOf(leftList.get(m)), xList.indexOf(rightList.get(0)), xList.indexOf(rightList.get(m + 1)));
        } else {
            System.out.format("Combining Problems: Point[%d]...Point[%d] and Point[%d]...Point[%d]\n", xList.indexOf(leftList.get(0)), xList.indexOf(leftList.get(m)), xList.indexOf(rightList.get(0)), xList.indexOf(rightList.get(m)));
        }

        // Call helper method to check points around cut line
        double dcombine = combine(m, list, currentMin);

        return Math.min(dcombine, currentMin);
    }

    // Check distance between points within the minimum distance of the cut line
    public static double combine(int m, List<Point2D.Double> list, double d) {
        // ArrayList of points within the minimum distance of the cut line, sorted by Y coordinate
        List<Point2D.Double> combineList = new ArrayList<>();

        // Populate the the new list
        for (int i = 0; i < yList.size(); i++) {
            // Make sure the point is within the minimum distance of the cut line
            if (Math.abs(yList.get(i).getX() - yList.get(m).getX()) < d) {
                combineList.add(yList.get(i));
            }
        }

        // Iterate through the new list and check the distance between points
        for (int j = 0; j < (combineList.size() - 2); j++) {
            int k = j + 1;
            // Make sure to check at most six points, stay within the index and within the minimum distance
            while (k <= (j + 6) && k <= (combineList.size() - 1) && (combineList.get(k).getY() - combineList.get(j).getY()) < d) {
                // Check the distance between points
                double distance = combineList.get(j).distance(combineList.get(k));
                // If a new closest pair was found, update variables and print results
                if (distance < currentMin) {
                    System.out.format(" Found Result: P1:(%.1f,%.1f), P2: (%.1f,%.1f), Distance: %.1f\n", combineList.get(j).getX(), combineList.get(j).getY(), combineList.get(k).getX(), combineList.get(k).getY(), distance);
                    currentMin = distance;
                    currentPoints[0] = combineList.get(j);
                    currentPoints[1] = combineList.get(k);
                }
                k++;
            }
        }
        return currentMin;
    }

    // Create ArrayLists based off of pointsX Array
    public static void preProcess() {
        for (Point2D i : pointsX) {
            double x = i.getX();
            double y = i.getY();
            xList.add(new Point2D.Double(x, y));
            yList.add(new Point2D.Double(x, y));
        }

        // Sort yList in ascending Y-Coordinate using comparator
        Collections.sort(yList, new YComp());
    }

    // Print the input points
    public static void printPoints() {
        System.out.println("Input Points:");
        for (Point2D i : pointsX) {
            System.out.format("(%.1f,%.1f)", i.getX(), i.getY());
        }
        System.out.format("\n-------------------------------------\n");
    }

    // Comparator for sorting a list by ascending Y-coordinates
    public static class YComp implements Comparator {

        @Override
        public int compare(Object o1, Object o2) {
            Point2D.Double p1 = (Point2D.Double) o1;
            Point2D.Double p2 = (Point2D.Double) o2;
            Double p1y = p1.y;
            Double p2y = p2.y;
            return p1y.compareTo(p2y);
        }
    }
}

/***
 * source: https://oj.leetcode.com/problemset/algorithms/
 * Author: Liu, Niu <liuniu@tsinghua.org.cn>
 */

package org.kelly.leetcode;

import org.kelly.leetcode.exception.InvalidInputException;

/**
 * Given n non-negative integers a1, a2, ..., an, where each represents a point at coordinate (i, ai). 
 * n vertical lines are drawn such that the two endpoints of line i is at (i, ai) and (i, 0). 
 * Find two lines, which together with x-axis forms a container, such that the container contains the most water.
 *
 * Note: You may not slant the container. 
 *
 * @author liuniu
 */
public class ContainerWithMostWater {
    private static ContainerWithMostWater instance = new ContainerWithMostWater();

    private ContainerWithMostWater() {
    }

    public static ContainerWithMostWater getInstance() {
        return instance;
    }

    /*
     * this is the simple solution to find out the max capacity
     * the logic is simple, but the runtime will be O(n^2),
     * n is the length of input array
     * 
     */
    public int getMaxCapacityWithSimpleSolution(int[] heights) {
        assertInput(heights);

        int maxCapacity = 0;
        for(int i = 0;i < heights.length;i++) {
            for(int j = i + 1;j < heights.length;j++) {
                PointPair pointPair = new PointPair(new Point(i, heights[i]), new Point(j, heights[j]));
                maxCapacity = Math.max(maxCapacity, pointPair.getCapacity());
            }
        }

        return maxCapacity;
    }

    /*
     * this method use optimized algorithm to get the max capacity
     * the runtime should be O(n)
     * n is the length of input array
     */
    public int getMaxCapacityWithQuickSolution(int[] heights) {
        assertInput(heights);

        Point pointLeft = new Point(0, heights[0]);
        Point pointRight = new Point(heights.length - 1, heights[heights.length - 1]);
        int maxCapacity = new PointPair(pointLeft, pointRight).getCapacity();

        while(pointLeft.getX() < pointRight.getX()) {
            if(pointLeft.getY() < pointRight.getY()) {
                pointLeft.moveRight(1);
            }
            else {
                pointRight.moveLeft(1);
            }

            maxCapacity = Math.max(maxCapacity, new PointPair(pointLeft, pointRight).getCapacity());
        }

        return maxCapacity;
    }

    private void assertInput(int[] heights) {
        if(heights == null) {
            throw new InvalidInputException("input heights array cannot be null!");
        }

        if(heights.length == 1) {
            throw new InvalidInputException("input heights array's number should greater than 1");
        }

        for(int height: heights) {
            if(height < 0) {
                throw new InvalidInputException("input height cannot be less than zero!");
            }
        }
    }
}

class Point {
    private int x; // x >= 0
    private int y; // y >= 0

    public Point(int xValue, int yValue) {
        x = xValue;
        y = yValue;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void moveLeft(int step) {
        x -= step;
    }

    public void moveRight(int step) {
        x += step;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }

        if(!(obj instanceof Point)) {
            return false;
        }

        Point point = (Point)obj;
        return (x == point.getX()) && (y == point.getY());
    }
}

class PointPair {
    private Point p1;
    private Point p2;

    public PointPair(Point point1, Point point2) {
        if(point1 == null || point2 == null) {
            throw new InvalidInputException("cannot initialize PointPair with null point!");
        }

        p1 = point1;
        p2 = point2;
    }

    public Point getP1() {
        return p1;
    }

    public Point getP2() {
        return p2;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }

        if(!(obj instanceof PointPair)) {
            return false;
        }

        PointPair pointPair = (PointPair)obj;
        return p1.equals(pointPair.getP1()) && p2.equals(pointPair.getP2());
    }

    public int getCapacity() {
        return Math.abs(p2.getX() - p1.getX()) * Math.min(p1.getY(), p2.getY());
    }
}

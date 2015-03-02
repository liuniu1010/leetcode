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
}

class PointPair {
    private Point p1;
    private Point p2;

    public PointPair(Point point1, Point point2) {
        p1 = point1;
        p2 = point2;
    }

    public Point getP1() {
        return p1;
    }

    public Point getP2() {
        return p2;
    }

    public int getCapacity() {
        return Math.abs(p2.getX() - p1.getX()) * Math.min(p1.getY(), p2.getY());
    }
}

/***
 * source: https://oj.leetcode.com/problemset/algorithms/
 * Author: Liu, Niu <liuniu@tsinghua.org.cn>
 */

package org.kelly.leetcode;

import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

import org.kelly.leetcode.exception.InvalidInputException;

/***
 * Given an array S of n integers, find three integers in S such that the 
 * sum is closest to a given number, target. Return the sum of the three 
 * integers. You may assume that each input would have exactly one solution
 *
 * For example, given array S = {-1 2 1 -4}, and target = 1.
 * The sum that is closest to the target is 2. (-1 + 2 + 1 = 2)
 *
 * @Author Liu, Niu
 */
public class ThreeSumClosest {
    private static ThreeSumClosest instance = new ThreeSumClosest();

    private ThreeSumClosest() {
    }


    public static ThreeSumClosest getInstance() {
        return instance;
    }

    public void assertInput(List<Integer> numbers) {
        if(numbers == null || numbers.size() < 3) {
            throw new InvalidInputException("input numbers' size should be no less than 3!");
        }
    }

    /*
     * this is the simple solution, the running time is O(n^3)
     */
    public Triplet threeSumClosestWithSimpleSolution(List<Integer> numbers, int target) {
        assertInput(numbers);

        Triplet tripletClosest = null;
        int distanceClosest = -1;
        for(int i = 0;i < numbers.size() - 2;i++) {
            for(int j = i + 1;j < numbers.size() - 1;j++) {
                for(int k = j + 1;k < numbers.size();k++) {
                    Triplet triplet = new Triplet(numbers.get(i), numbers.get(j), numbers.get(k));
                    int currentDistance = Math.abs((triplet.sum()) - target);
                    if(currentDistance == 0) {
                        tripletClosest = triplet;
                        distanceClosest = 0;
                        break;
                    } 
                    else if(distanceClosest < 0) {
                        // distanceClosest < 0  means distanceClosest has not been initialized
                        tripletClosest = triplet;
                        distanceClosest = currentDistance;
                    } 
                    else {
                        if(currentDistance < distanceClosest) {
                            tripletClosest = triplet;
                            distanceClosest = currentDistance;
                        }
                    }
                }
            }
        }

        return tripletClosest;
    }

    /*
     * this solution's preferred running time is O(n^2*Log(n))
     */
    public Triplet threeSumClosestWithQuickSolution(List<Integer> numbers, int target) {
        assertInput(numbers);

        // sort the numbers first
        numbers.sort(new Comparator<Integer>() {
            public int compare(Integer number1, Integer number2) {
                return number1.compareTo(number2);
            }
        });

        Triplet tripletClosest = null;
        for(int i = 0;i < numbers.size() - 2;i++) {
            for(int j = i + 1;j < numbers.size() - 1;j++) {
                int leftEdge = j + 1;
                int rightEdge = numbers.size() - 1;

                tripletClosest = getClosestTripletWithFixedNumber1AndNumber2(i, j, target, numbers, leftEdge, rightEdge, tripletClosest);
                int distanceClosest = Math.abs(tripletClosest.sum() - target);
                if(distanceClosest == 0) {
                    break;
                }
            }
        }

        return tripletClosest;
    }

    private Triplet getClosestTripletWithFixedNumber1AndNumber2(int number1, int number2, int target, List<Integer> numbers, int leftEdge, int rightEdge, Triplet previousTripletClosest) {
        if(leftEdge == rightEdge) {
            Triplet currentTriplet = new Triplet(number1, number2, numbers.get(leftEdge));
            if(previousTripletClosest == null) {
                return currentTriplet;
            }
            else {
                int previousDistanceClosest = Math.abs(previousTripletClosest.sum() - target);
                int currentDistance = Math.abs(currentTriplet.sum() - target);

                return (currentDistance < previousDistanceClosest)?currentTriplet:previousTripletClosest;
            }
        }

        int middle = (leftEdge + rightEdge) / 2;
        Triplet currentTriplet = new Triplet(number1, number2, numbers.get(middle));
        int currentDelta = currentTriplet.sum() - target; // note, currentDelta may be less than 0
             
        if(currentDelta == 0) {
            return currentTriplet;
        } 
        else if(previousTripletClosest == null) {
            // it's the first call into this method
            previousTripletClosest = currentTriplet;

            int newLeftEdge;
            int newRightEdge;
            if(currentDelta < 0) {
                newLeftEdge = middle;
                newRightEdge = rightEdge;
            }
            else {
                newLeftEdge = leftEdge;
                newRightEdge = middle;
            }

            return getClosestTripletWithFixedNumber1AndNumber2(number1, number2, target, numbers, newLeftEdge, newRightEdge, previousTripletClosest);
        }
        else {
            int previousDeltaClosest = previousTripletClosest.sum() - target;
            int previousDistanceClosest = Math.abs(previousDeltaClosest);
            int currentDistance = Math.abs(currentDelta);

            int newLeftEdge;
            int newRightEdge;

            if(currentDistance < previousDistanceClosest) {
                previousTripletClosest = currentTriplet;
            }

            if(currentDelta < 0) {
                newLeftEdge = middle;
                newRightEdge = rightEdge;
            }
            else {
                newLeftEdge = leftEdge;
                newRightEdge = middle;
            }

            return getClosestTripletWithFixedNumber1AndNumber2(number1, number2, target, numbers, newLeftEdge, newRightEdge, previousTripletClosest);
        } 
    }
}


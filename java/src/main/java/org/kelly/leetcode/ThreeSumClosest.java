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
        // 3 layers loop to check all possible combinations
        for(int i = 0;i < numbers.size() - 2;i++) {
            for(int j = i + 1;j < numbers.size() - 1;j++) {
                for(int k = j + 1;k < numbers.size();k++) {
                    Triplet currentTriplet = new Triplet(numbers.get(i), numbers.get(j), numbers.get(k));
                    int currentDistance = Math.abs(currentTriplet.sum() - target);
                    if(currentDistance == 0) {
                        tripletClosest = currentTriplet;
                        return tripletClosest;
                    } 
                    else if(tripletClosest == null) {
                        tripletClosest = currentTriplet;
                    } 
                    else {
                        int distanceClosest = Math.abs(tripletClosest.sum() - target);
                        if(currentDistance < distanceClosest) {
                            tripletClosest = currentTriplet;
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
        // 2 layers loop with recursive call to locate the third number's location
        // the preferred running time should be O(n^2*log(n))
        for(int i = 0;i < numbers.size() - 2;i++) {
            if(tripletClosest != null) {
                int currentClosestDistance = Math.abs(tripletClosest.sum() - target);
                int leastDeltaFromNowOn = numbers.get(i) + numbers.get(i + 1) + numbers.get(i + 2) - target;
                if(leastDeltaFromNowOn >= currentClosestDistance) {
                    // because the numbers have been sorted, all the left combination's
                    // Delta will be greater than or equal to leastDeltaFromNowOn
                    // so there is no need to loop with all left indexes of i,j
                    return tripletClosest;
                }
            }

            for(int j = i + 1;j < numbers.size() - 1;j++) {
                if(tripletClosest != null) {
                    int currentClosestDistance = Math.abs(tripletClosest.sum() - target);
                    int leastDeltaFromNowOn = numbers.get(i) + numbers.get(j) + numbers.get(j + 1) - target;
                    if(leastDeltaFromNowOn >= currentClosestDistance) {
                        // because the numbers have been sorted, all the left combination's 
                        // delta will be greater than or equal to leastDeltaFromNowOn
                        // so there is no need to loop with left indexes of j
                        break;
                    }
                }

                int leftEdge = j + 1;
                int rightEdge = numbers.size() - 1;

                tripletClosest = getClosestTripletWithFixedNumber1AndNumber2(numbers.get(i), numbers.get(j), target, numbers, leftEdge, rightEdge, tripletClosest);
                int distanceClosest = Math.abs(tripletClosest.sum() - target);
                if(distanceClosest == 0) {
                    return tripletClosest;
                }
            }
        }

        return tripletClosest;
    }

    private Triplet getClosestTripletWithFixedNumber1AndNumber2(int number1, int number2, int target, List<Integer> numbers, int leftEdge, int rightEdge, Triplet tripletClosest) {
        if(number1 + number2 + numbers.get(leftEdge) - target >= 0
            || number1 + number2 + numbers.get(rightEdge) - target <= 0) {
            // in this case, no need to search recursively in the method.
            // the closest Triplet is at the edge
            // case that leftEdge = rightEdge will also be included here
            Triplet currentTriplet;
            if(number1 + number2 + numbers.get(leftEdge) - target >= 0) {
                currentTriplet = new Triplet(number1, number2, numbers.get(leftEdge));
            }
            else {
                currentTriplet = new Triplet(number1, number2, numbers.get(rightEdge));
            }
            
            if(tripletClosest == null) {
                return currentTriplet;
            }
            else {
                int distanceClosest = Math.abs(tripletClosest.sum() - target);
                int currentDistance = Math.abs(currentTriplet.sum() - target);

                return (currentDistance < distanceClosest)?currentTriplet:tripletClosest;
            }
        }

        int middle = (leftEdge + rightEdge) / 2;
        Triplet currentTriplet = new Triplet(number1, number2, numbers.get(middle));
        int currentDelta = currentTriplet.sum() - target; // note, currentDelta may be less than 0
             
        if(currentDelta == 0) {
            return currentTriplet;
        } 
        else if(tripletClosest == null) {
            // it's the first call into this method
            tripletClosest = currentTriplet;

            int newLeftEdge;
            int newRightEdge;
            if(currentDelta < 0) {
                newLeftEdge = middle + 1;
                newRightEdge = rightEdge;
            }
            else {
                newLeftEdge = leftEdge;
                newRightEdge = middle;
            }

            return getClosestTripletWithFixedNumber1AndNumber2(number1, number2, target, numbers, newLeftEdge, newRightEdge, tripletClosest);
        }
        else {
            int deltaClosest = tripletClosest.sum() - target;
            int distanceClosest = Math.abs(deltaClosest);
            int currentDistance = Math.abs(currentDelta);

            int newLeftEdge;
            int newRightEdge;

            if(currentDistance < distanceClosest) {
                tripletClosest = currentTriplet;
            }

            if(currentDelta < 0) {
                newLeftEdge = middle + 1;
                newRightEdge = rightEdge;
            }
            else {
                newLeftEdge = leftEdge;
                newRightEdge = middle;
            }

            return getClosestTripletWithFixedNumber1AndNumber2(number1, number2, target, numbers, newLeftEdge, newRightEdge, tripletClosest);
        } 
    }
}


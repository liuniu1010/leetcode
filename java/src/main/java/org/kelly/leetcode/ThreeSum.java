/***
 * source: https://oj.leetcode.com/problemset/algorithms/
 * Author: Liu, Niu <liuniu@tsinghua.org.cn>
 */

package org.kelly.leetcode;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Comparator;
import org.kelly.leetcode.util.Triplet;
import org.kelly.leetcode.exception.InvalidInputException;

/***
 * Given an array S of n integers, are there elements a, b, c in S such that a + b + c = 0? Find all unique triplets in the array which gives the sum of zero.
 * Note:
 * Elements in a triplet (a,b,c) must be in non-descending order. (ie, a ≤ b ≤ c)
 * The solution set must not contain duplicate triplets.
 *
 * @Author Liu, Niu
 */
public class ThreeSum {
    private static ThreeSum instance = new ThreeSum();

    private ThreeSum() {
    }


    public static ThreeSum getInstance() {
        return instance;
    }

    public void assertInput(List<Integer> numbers) {
        if(numbers == null || numbers.size() < 3) {
            throw new InvalidInputException("input numbers' size should be no less than 3!");
        }
    }

    /*
     * this is the simple solution with brute force, the preferred running time
     * would be O(n^3), But the implementation is simple.
     * this method will act as comparation with quick solution
     */
    public List<Triplet> threeSumWithSimpleSolution(List<Integer> numbers) {
        assertInput(numbers);

        int num1;
        int num2;
        int num3;
        List<Triplet> triplets = new ArrayList<Triplet>();
        for(int i = 0;i < numbers.size() - 2;i++) {
            num1 = numbers.get(i);
            for(int j = i + 1;j < numbers.size() - 1;j++) {
                num2 = numbers.get(j);
                for(int k = j + 1;k < numbers.size();k++) {
                    num3 = numbers.get(k);

                    if(num1 + num2 + num3 == 0) {
                        Triplet triplet = new Triplet(num1, num2, num3);
                        if(!triplets.contains(triplet)) {
                            triplets.add(triplet);
                        }
                    }
                }
            }
        }

        return triplets; 
    }

    /*
     * this solution's preferred running time is O(n^2)
     */
    public List<Triplet> threeSumWithQuickSolution(List<Integer> numbers) {
        assertInput(numbers);

        // record all numbers into hash map first
        Map<Integer, List<Integer>> numIndexMap = new HashMap<Integer, List<Integer>>();
        for(int index = 0;index < numbers.size();index++) {
            int num = numbers.get(index);
            if(numIndexMap.containsKey(num)) {
                List<Integer> indexes = numIndexMap.get(num);
                indexes.add(index);
            }
            else {
                List<Integer> indexes = new ArrayList<Integer>();
                indexes.add(index);
                numIndexMap.put(num, indexes);
            }
        }

        // begin to search
        int num1;
        int num2;
        int preferredNum3;
        List<Triplet> triplets = new ArrayList<Triplet>();
        for(int i = 0;i < numbers.size() - 1;i++) {
            num1 = numbers.get(i);
            for(int j = i + 1;j < numbers.size();j++) {
                num2 = numbers.get(j);

                preferredNum3 = -(num1 + num2);
                if(numIndexMap.containsKey(preferredNum3)) {
                    // we should check for cases that num3 is equal to num1 or num2
                    List<Integer> indexes = numIndexMap.get(preferredNum3);
                    // try to find at least one element which index is not equal to i or j
                    boolean befound = false;
                    for(int index: indexes) {
                        if(index != i && index != j) {
                            befound = true;
                            break;
                        }
                    }
                    
                    if(befound) {
                        Triplet triplet = new Triplet(num1, num2, preferredNum3);
                        if(!triplets.contains(triplet)) {
                            triplets.add(triplet);
                        }
                    }
                }
            }
        }

        return triplets;
    }
}

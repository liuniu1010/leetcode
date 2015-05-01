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
import org.kelly.leetcode.util.Quadruplet;
import org.kelly.leetcode.util.Doublet;
import org.kelly.leetcode.exception.InvalidInputException;

/***
 * Given an array S of n integers, are there elements a, b, c, and d in S such that a + b + c + d = target? Find all unique quadruplets in the array which gives the sum of target.
 *
 * Note:
 * Elements in a quadruplet (a,b,c,d) must be in non-descending order. (ie, a ≤ b ≤ c ≤ d)
 * The solution set must not contain duplicate quadruplets.
 *
 * @Author Liu, Niu
 */
public class FourSum {
    private static FourSum instance = new FourSum();

    private FourSum() {
    }


    public static FourSum getInstance() {
        return instance;
    }

    private void assertInput(List<Integer> numbers) {
        if(numbers == null || numbers.size() < 4) {
            throw new InvalidInputException("input numbers' size should be no less than 4!");
        }
    }

    /*
     * this is the simple solution with brute force, the preferred running time
     * would be O(n^4), But the implementation is simple.
     * this method will act as comparation with quick solution
     */
    public List<Quadruplet> fourSumWithSimpleSolution(List<Integer> numbers, int target) {
        assertInput(numbers);

        int num1;
        int num2;
        int num3;
        int num4;
        List<Quadruplet> quadruplets = new ArrayList<Quadruplet>();
        for(int i = 0;i < numbers.size() - 3;i++) {
            num1 = numbers.get(i);
            for(int j = i + 1;j < numbers.size() - 2;j++) {
                num2 = numbers.get(j);
                for(int k = j + 1;k < numbers.size() - 1;k++) {
                    num3 = numbers.get(k);
                    for(int l = k + 1;l < numbers.size();l++) {
                        num4 = numbers.get(l);
                        if(num1 + num2 + num3 + num4 == target) {
                            Quadruplet quadruplet = new Quadruplet(num1, num2, num3, num4);
                            if(!quadruplets.contains(quadruplet)) {
                                quadruplets.add(quadruplet);
                            }
                        }
                    }
                }
            }
        }

        return quadruplets; 
    }

    /*
     * this solution's preferred running time is O(n^2)
     */
    public List<Quadruplet> fourSumWithQuickSolution(List<Integer> numbers, int target) {
        assertInput(numbers);

        // record all paired numbers into hash map first
        Map<Integer, List<Doublet>> numIndexMap = new HashMap<Integer, List<Doublet>>();
        for(int index1 = 0;index1 < numbers.size() - 1;index1++) {
            for(int index2 = index1 + 1;index2 < numbers.size();index2++) {
                int num = numbers.get(index1) + numbers.get(index2);
                Doublet doublet = new Doublet(index1, index2);
                if(numIndexMap.containsKey(num)) {
                    List<Doublet> doublets = numIndexMap.get(num);
                    doublets.add(doublet);
                }
                else {
                    List<Doublet> doublets = new ArrayList<Doublet>();
                    doublets.add(doublet);
                }
            }
        }

        // begin to search
        int num1;
        int num2;
        int preferredNum;
        List<Quadruplet> quadruplets = new ArrayList<Quadruplet>();
        for(int i = 0;i < numbers.size() - 1;i++) {
            num1 = numbers.get(i);
            for(int j = i + 1; j < numbers.size();j++) {
                num2 = numbers.get(j);
                preferredNum = target - (num1 + num2);

                if(numIndexMap.containsKey(preferredNum)) {
                    List<Doublet> doublets = numIndexMap.get(preferredNum);
                    Doublet doubletToBeFound = null;
                    for(Doublet doublet: doublets) {
                        if(doublet.getNum1() != i
                            && doublet.getNum2() != i
                            && doublet.getNum1() != j
                            && doublet.getNum2() != j) {
                            doubletToBeFound = doublet;
                            break;
                        }
                    }

                    if(doubletToBeFound != null) {
                        Quadruplet quadruplet = new Quadruplet(i, j, doubletToBeFound.getNum1(), doubletToBeFound.getNum2());
                        if(!quadruplets.contains(quadruplet)) {
                            quadruplets.add(quadruplet);
                        }
                    }
                }
            }
        }

        return quadruplets;
    }
}

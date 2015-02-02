/***
 * source: https://oj.leetcode.com/problemset/algorithms/
 * Author: Liu, Niu <liuniu@tsinghua.org.cn>
 */

package org.kelly.leetcode;

import org.kelly.leetcode.exception.InvalidInputException;
import org.kelly.leetcode.util.SubstringIndex;

/***
 * Given a string S, find the longest palindromic substring in S. You may assume 
 * that the maximum length of S is 1000, and there exists one unique longest 
 * palindromic substring.
 *
 * @author liuniu
 */
public class LongestPalindromic {
    private LongestPalindromic() {
    }

    private static LongestPalindromic instance = new LongestPalindromic();

    public static LongestPalindromic getInstance() {
        return instance;
    }

    /*
     * the preferred running time is O(n^2)
     * n is the length of inputString
     *
     */
    public String getLongestPalindromicSubstring(String inputString) {
        assertInput(inputString);

        Peak currentPeak = null;
        SubstringIndex currentMaxSubstringIndex = new SubstringIndex(0, 0);
        do {
            Peak nextPeak = getNextPeak(inputString, currentPeak);
            if(nextPeak == null) {
                break;
            }
            currentMaxSubstringIndex = getLongestPalindromicSubstringBasedOnPeak(inputString, currentMaxSubstringIndex, nextPeak);
            currentPeak = nextPeak;
        }
        while(true);

        // here we can use String.substring() one time, the running time is O(n), no effect for the whole running time
        return inputString.substring(currentMaxSubstringIndex.getBeginIndex(), currentMaxSubstringIndex.getEndIndex());
    }

    private void assertInput(String inputString) {
        if(inputString == null || inputString.equals("")) {
            throw new InvalidInputException("input string cannot be null or empty!");
        }
    }

    private SubstringIndex getLongestPalindromicSubstringBasedOnPeak(String inputString, SubstringIndex currentMaxSubstringIndex, Peak peak) {
        if(peak instanceof SinglePeak) {
            return getLongestPalindromicSubstringBasedOnSinglePeak(inputString, currentMaxSubstringIndex, (SinglePeak)peak);
        }
        else {
            return getLongestPalindromicSubstringBasedOnDoublePeak(inputString, currentMaxSubstringIndex, (DoublePeak)peak);
        }
    }

    private Peak getNextPeak(String inputString, Peak currentPeak) {
        if(currentPeak == null) {
            SinglePeak resultPeak = new SinglePeak(0);
            return resultPeak;
        }

        if(currentPeak instanceof SinglePeak) {
            SinglePeak singlePeak = (SinglePeak)currentPeak;
            if(singlePeak.getIndex() >= inputString.length() - 1) {
                // it is at the end
                return null;
            }
            else {
                DoublePeak resultPeak = new DoublePeak(singlePeak.getIndex(), singlePeak.getIndex() + 1);
                return resultPeak;
            }
        }
        else {// it is the case that currentPeak instanceof DoublePeak
            DoublePeak doublePeak = (DoublePeak)currentPeak;
            if(doublePeak.getIndex2() >= inputString.length() - 1) {
                // it is at the end
                return null;
            }
            else {
                SinglePeak resultPeak = new SinglePeak(doublePeak.getIndex2());
                return resultPeak;
            }
        }
    }


    private SubstringIndex getLongestPalindromicSubstringBasedOnSinglePeak(String inputString, SubstringIndex currentMaxSubstringIndex, SinglePeak singlePeak) {
        int possibleMaxLength = Math.min(singlePeak.getIndex(), (inputString.length() - 1) - singlePeak.getIndex()) * 2 + 1;
        if(possibleMaxLength <= currentMaxSubstringIndex.length()) {
            // no need to check with this singlePeak
            return currentMaxSubstringIndex;
        }

        int step = 0;
        char leftCh;
        char rightCh;
        do {
            if(singlePeak.getIndex() - step < 0
                || singlePeak.getIndex() + step > inputString.length() - 1) {
                step--;
                break;
            }
            leftCh = inputString.charAt(singlePeak.getIndex() - step);
            rightCh = inputString.charAt(singlePeak.getIndex() + step);

            if(leftCh != rightCh) {
                step--;
                break;
            }

            step++;
        }
        while(true);

        SubstringIndex maxSubstringIndex = new SubstringIndex(singlePeak.getIndex() - step, singlePeak.getIndex() + step + 1);
        return (maxSubstringIndex.length() <= currentMaxSubstringIndex.length())?currentMaxSubstringIndex:maxSubstringIndex;
    }
    
    private SubstringIndex getLongestPalindromicSubstringBasedOnDoublePeak(String inputString, SubstringIndex currentMaxSubstringIndex, DoublePeak doublePeak) {
        int possibleMaxLength = Math.min(doublePeak.getIndex1(), ((inputString.length() - 1) - doublePeak.getIndex2())) * 2 + 2;
        if(possibleMaxLength <= currentMaxSubstringIndex.length()) {
            return currentMaxSubstringIndex;
        }

        if(inputString.charAt(doublePeak.getIndex1()) != inputString.charAt(doublePeak.getIndex2())) {
            return currentMaxSubstringIndex;
        }

        int step = 0;
        char leftCh;
        char rightCh;
        do {
            if(doublePeak.getIndex1() - step < 0
                || doublePeak.getIndex2() + step > inputString.length() - 1) {
                step--;
                break;
            }

            leftCh = inputString.charAt(doublePeak.getIndex1() - step);
            rightCh = inputString.charAt(doublePeak.getIndex2() + step);

            if(leftCh != rightCh) {
                step--;
                break;
            }

            step++;
        }
        while(true);

        SubstringIndex maxSubstringIndex = new SubstringIndex(doublePeak.getIndex1() - step, doublePeak.getIndex2() + step + 1);
        return (maxSubstringIndex.length() <= currentMaxSubstringIndex.length())?currentMaxSubstringIndex:maxSubstringIndex;
    }
}

/*
 * the middle location of a String
 * for case the string's length is odd, the Peak instance
 * should be SinglePeak (only one letter as the middle one)
 * for case the string's length is even, the Peak instance
 * should be DoublePeak (two letters as the middle pair)
 */
interface Peak{
}

class SinglePeak implements Peak {
    private int index;

    public SinglePeak(int idx) {
        this.index = idx;
    }

    public int getIndex() {
        return index;
    }
}

class DoublePeak implements Peak {
    private int index1;
    private int index2;  // infact, it is always the case that index2 == index1 + 1
                        // for us easy to understand, we still define two indexes here

    public DoublePeak(int idx1, int idx2) {
        this.index1 = idx1;
        this.index2 = idx2;
    }

    public int getIndex1() {
        return index1;
    }

    public int getIndex2() {
        return index2;
    }
}


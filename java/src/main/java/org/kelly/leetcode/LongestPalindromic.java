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
            if(singlePeak.index >= inputString.length() - 1) {
                // it is at the end
                return null;
            }
            else {
                DoublePeak resultPeak = new DoublePeak(singlePeak.index, singlePeak.index + 1);
                return resultPeak;
            }
        }
        else {// it is the case that currentPeak instanceof DoublePeak
            DoublePeak doublePeak = (DoublePeak)currentPeak;
            if(doublePeak.index2 >= inputString.length() - 1) {
                // it is at the end
                return null;
            }
            else {
                SinglePeak resultPeak = new SinglePeak(doublePeak.index2);
                return resultPeak;
            }
        }
    }


    private SubstringIndex getLongestPalindromicSubstringBasedOnSinglePeak(String inputString, SubstringIndex currentMaxSubstringIndex, SinglePeak singlePeak) {
        int possibleMaxLength = Math.min(singlePeak.index, (inputString.length() - 1) - singlePeak.index) * 2 + 1;
        if(possibleMaxLength <= currentMaxSubstringIndex.length()) {
            // no need to check with this singlePeak
            return currentMaxSubstringIndex;
        }

        int step = 0;
        char leftCh;
        char rightCh;
        do {
            if(singlePeak.index - step < 0
                || singlePeak.index + step > inputString.length() - 1) {
                step--;
                break;
            }
            leftCh = inputString.charAt(singlePeak.index - step);
            rightCh = inputString.charAt(singlePeak.index + step);

            if(leftCh != rightCh) {
                step--;
                break;
            }

            step++;
        }
        while(true);

        SubstringIndex maxSubstringIndex = new SubstringIndex(singlePeak.index - step, singlePeak.index + step + 1);
        return (maxSubstringIndex.length() <= currentMaxSubstringIndex.length())?currentMaxSubstringIndex:maxSubstringIndex;
    }
    
    private SubstringIndex getLongestPalindromicSubstringBasedOnDoublePeak(String inputString, SubstringIndex currentMaxSubstringIndex, DoublePeak doublePeak) {
        int possibleMaxLength = Math.min(doublePeak.index1, ((inputString.length() - 1) - doublePeak.index2)) * 2 + 2;
        if(possibleMaxLength <= currentMaxSubstringIndex.length()) {
            return currentMaxSubstringIndex;
        }

        if(inputString.charAt(doublePeak.index1) != inputString.charAt(doublePeak.index2)) {
            return currentMaxSubstringIndex;
        }

        int step = 0;
        char leftCh;
        char rightCh;
        do {
            if(doublePeak.index1 - step < 0
                || doublePeak.index2 + step > inputString.length() - 1) {
                step--;
                break;
            }

            leftCh = inputString.charAt(doublePeak.index1 - step);
            rightCh = inputString.charAt(doublePeak.index2 + step);

            if(leftCh != rightCh) {
                step--;
                break;
            }

            step++;
        }
        while(true);

        SubstringIndex maxSubstringIndex = new SubstringIndex(doublePeak.index1 - step, doublePeak.index2 + step + 1);
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
    public int index;

    public SinglePeak(int index) {
        this.index = index;
    }
}

class DoublePeak implements Peak {
    public int index1;
    public int index2;  // infact, it is always the case that index2 == index1 + 1
                        // for us easy to understand, we still define two indexes here

    public DoublePeak(int index1, int index2) {
        this.index1 = index1;
        this.index2 = index2;
    }
}


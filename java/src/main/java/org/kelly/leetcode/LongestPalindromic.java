/***
 * source: https://oj.leetcode.com/problemset/algorithms/
 * Author: Liu, Niu <liuniu@tsinghua.org.cn>
 */

package org.kelly.leetcode;

import org.kelly.leetcode.exception.InvalidInputException;

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

    public String getLongestPalindromicSubstring(String inputString) {
        assertInput(inputString);

        Peak currentPeak = null;
        String currentMaxSubstring = "";
        do {
            Peak nextPeak = getNextPeakWithSimpleSolution(inputString, currentPeak);
            if(nextPeak == null) {
                break;
            }
            currentMaxSubstring = getLongestPalindromicSubstringBasedOnPeak(inputString, currentMaxSubstring, nextPeak);
            currentPeak = nextPeak;
        }
        while(true);

        return currentMaxSubstring;
    }

    private void assertInput(String inputString) {
        if(inputString == null || inputString.equals("")) {
            throw new InvalidInputException("input string cannot be null or empty!");
        }
    }

    private String getLongestPalindromicSubstringBasedOnPeak(String inputString, String currentMaxSubstring, Peak peak) {
        if(peak instanceof SinglePeak) {
            return getLongestPalindromicSubstringBasedOnSinglePeak(inputString, currentMaxSubstring, (SinglePeak)peak);
        }
        else {
            return getLongestPalindromicSubstringBasedOnDoublePeak(inputString, currentMaxSubstring, (DoublePeak)peak);
        }
    }

    private Peak getNextPeakWithSimpleSolution(String inputString, Peak currentPeak) {
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

    private String getLongestPalindromicSubstringBasedOnSinglePeak(String inputString, String currentMaxSubstring, SinglePeak singlePeak) {
        int possibleMaxLength = Math.min(singlePeak.index, (inputString.length() - 1) - singlePeak.index) * 2 + 1;
        if(possibleMaxLength <= currentMaxSubstring.length()) {
            // no need to check with this singlePeak
            return currentMaxSubstring;
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

        String maxSubstring = inputString.substring(singlePeak.index - step, singlePeak.index + step + 1);
        return (maxSubstring.length() <= currentMaxSubstring.length())?currentMaxSubstring:maxSubstring;
    }
    
    private String getLongestPalindromicSubstringBasedOnDoublePeak(String inputString, String currentMaxSubstring, DoublePeak doublePeak) {
        int possibleMaxLength = Math.min(doublePeak.index1, ((inputString.length() - 1) - doublePeak.index2)) * 2 + 2;
        if(possibleMaxLength <= currentMaxSubstring.length()) {
            return currentMaxSubstring;
        }

        if(inputString.charAt(doublePeak.index1) != inputString.charAt(doublePeak.index2)) {
            return currentMaxSubstring;
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

        String maxSubstring = inputString.substring(doublePeak.index1 - step, doublePeak.index2 + step + 1);
        return (maxSubstring.length() <= currentMaxSubstring.length())?currentMaxSubstring:maxSubstring;
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

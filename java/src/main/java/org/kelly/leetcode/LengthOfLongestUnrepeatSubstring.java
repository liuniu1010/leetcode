/***
 * source: https://oj.leetcode.com/problemset/algorithms/
 * Author: Liu, Niu <liuniu@tsinghua.org.cn>
 */

package org.kelly.leetcode;

import java.util.HashMap;
import java.util.Map;

import org.kelly.leetcode.exception.InvalidInputException;
import org.kelly.leetcode.util.SubstringIndex;

/**
 * Given a string, find the length of the longest substring without repeating 
 * characters. For example, the longest substring without repeating letters 
 * for "abcabcbb" is "abc", which the length is 3. For "bbbbb" the longest 
 * substring is "b", with the length of 1.
 * @author liuniu
 *
 */
public class LengthOfLongestUnrepeatSubstring {
    private static LengthOfLongestUnrepeatSubstring instance = new LengthOfLongestUnrepeatSubstring();

    private LengthOfLongestUnrepeatSubstring() {
    }
    
    
    public static LengthOfLongestUnrepeatSubstring getInstance() {
        return instance;
    }

    /*
     * the running time should be O(size of inputString)
     *
     */     
    public String getLongestUnrepeatSubstring(String inputString) {
        assertInput(inputString);

        Map<String, Integer> indexCache = new HashMap<String, Integer>();
        SubstringIndex maxSubstringIndex = new SubstringIndex(0,0);
        int currentBeginIndex = 0;
        for(int index = 0;index < inputString.length();index++) {
            String curChar = inputString.substring(index, index + 1);
            if(indexCache.containsKey(curChar)) {
                /* find the possible repeated char, but we should check index value to make
                 * sure that the index is before or after currentBeginIndex
                 */
                int repeatIndex = indexCache.get(curChar);
                if(repeatIndex < currentBeginIndex) {
                    SubstringIndex currentSubstringIndex = new SubstringIndex(currentBeginIndex, index + 1);
                    if(currentSubstringIndex.length() > maxSubstringIndex.length()) {
                        maxSubstringIndex = currentSubstringIndex;
                    }
                } 
                else {
                    currentBeginIndex = repeatIndex + 1;
                }
            } 
            else {
                SubstringIndex currentSubstringIndex = new SubstringIndex(currentBeginIndex, index + 1);
                if(currentSubstringIndex.length() > maxSubstringIndex.length()) {
                    maxSubstringIndex = currentSubstringIndex;
                }
            }

            /*
             * no matter curChar is in indexCache or not, we always
             * put it again, to replace the old one with current index
             */
            indexCache.put(curChar, index);
        }

        return inputString.substring(maxSubstringIndex.getBeginIndex(), maxSubstringIndex.getEndIndex());
    }

    private void assertInput(String inputString) {
        if(inputString == null || "".equals(inputString)) {
            throw new InvalidInputException("input string should not be empty!");
        } 
    }
}

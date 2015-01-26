package org.kelly.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * Given a string, find the length of the longest substring without repeating 
 * characters. For example, the longest substring without repeating letters 
 * for "abcabcbb" is "abc", which the length is 3. For "bbbbb" the longest 
 * substring is "b", with the length of 1.
 * @author liuniu
 *
 */
public class LengthOfLongestUnrepeatSubstring {
    private LengthOfLongestUnrepeatSubstring() {
    }
    
    private static LengthOfLongestUnrepeatSubstring instance = new LengthOfLongestUnrepeatSubstring();
    
    public static LengthOfLongestUnrepeatSubstring getInstance() {
        return instance;
    }

    /*
     * the running time should be O(size of origString)
     */     
    public String getLongestUnrepeatSubstring(String origString) {
        Map<String, Integer> indexCache = new HashMap<String, Integer>();
        String maxSubstring = "";
        int maxBeginIndex = 0;
        int maxEndIndex = 0;
        int currentBeginIndex = 0;
        for(int index = 0;index < origString.length();index++) {
            String curChar = origString.substring(index, index + 1);
            if(indexCache.containsKey(curChar)) {
                /* find the possible repeated char, but we should check index value to make
                 * sure that the index is before or after currentBeginIndex
                 */
                int repeatIndex = indexCache.get(curChar);
                if(repeatIndex < currentBeginIndex) {
                    String currentSubstring = origString.substring(currentBeginIndex, index + 1);
                    if(currentSubstring.length() > maxSubstring.length()) {
                        maxSubstring = currentSubstring;
                        maxBeginIndex = currentBeginIndex;
                        maxEndIndex = index + 1;
                    }
                } 
                else {
                    currentBeginIndex = repeatIndex + 1;
                }
            } 
            else { // 
                String currentSubstring = origString.substring(currentBeginIndex, index + 1);
                if(currentSubstring.length() > maxSubstring.length()) {
                    maxSubstring = currentSubstring;
                    maxBeginIndex = currentBeginIndex;
                    maxEndIndex = index + 1;
                }
            }

            /*
             * no matter curChar is in indexCache or not, we always
             * put it again, to replace the old one with current index
             */
            indexCache.put(curChar, index);
        }

        return maxSubstring;
    }
}

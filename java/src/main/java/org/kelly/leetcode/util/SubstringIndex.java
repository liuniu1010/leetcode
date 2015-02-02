package org.kelly.leetcode.util;

/***
 * this class is used as a replacement for String.substring() method
 * since jdk7, String.substring() method's complexity is O(n), not O(1)
 * so, in some cases we should avoid using String.substring to make
 * sure that the performance of algorithm will not be effected.
 * we can use this SubstringIndex only to record the beginIndex and endIndex
 * of String
 */
public class SubstringIndex {
    private int beginIndex;
    private int endIndex;

    public SubstringIndex(int beginIdx, int endIdx) {
        beginIndex = beginIdx;
        endIndex = endIdx;
    }

    public int length() {
        return endIndex - beginIndex;
    }

    public int getBeginIndex() {
        return beginIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }
}

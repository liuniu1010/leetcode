/***
 * source: https://oj.leetcode.com/problemset/algorithms/
 * Author: Liu, Niu <liuniu@tsinghua.org.cn>
 */

package org.kelly.leetcode;

import java.util.List;
import java.util.ArrayList;

import org.kelly.leetcode.exception.InvalidInputException;

/**
 * Implement regular expression matching with support for '.' and '*'.
 * '.' Matches any single character.
 * '*' Matches zero or more of the preceding element.
 *
 * @author liuniu
 */
public class RegularExpMatch {
    private final static String CONST_DOT = ".";
    private final static String CONST_ASTERISK = "*";

    private RegularExpMatch() {
    }

    private static RegularExpMatch instance = new RegularExpMatch();
    private static RegularExpMatch getInstance() {
        return instance;
    }

    private void assertInput(String str, String regex) {
        if(str == null || str.isEmpty()) {
            throw new InvalidInputException("input params cannot be empty!");
        }

        if(regex == null || str.isEmpty()) {
            throw new InvalidInputException("input params cannot be empty!");
        }
    }

    public boolean isMatch(String str, String regex) {
        assertInput(str, regex);

        // to be implemented
        return false;
    }

    private List<MyPattern> compile(String regex) {
        List<String> targetPatterns = new ArrayList<String>();
        targetPatterns.add(CONST_DOT);
        targetPatterns.add(CONST_ASTERISK);

        List<MyPattern> myPatterns = new ArrayList<MyPattern>();
        String leftRegex = regex;
        while(true) {
            if(leftRegex.isEmpty()) {
                break;
            }

            MyPattern nextPattern = getNextPattern(targetPatterns, leftRegex);
            myPatterns.add(nextPattern);

            if(nextPattern.length() == leftRegex.length()) {
                break;
            }

            leftRegex = leftRegex.substring(nextPattern.length());
        }

        return myPatterns;
    }

    private MyPattern getNextPattern(List<String> targetPatterns, String leftRegex) {
        int index = indexOf(targetPatterns, leftRegex);
        if(index < 0) {
            return new SimplePattern(leftRegex);
        }
        else if(index == 0) {
            if(leftRegex.startsWith(CONST_DOT)){
                return new DotPattern();
            }
            else { // leftRegex.startsWith(CONST_ASTERISK)
                return new AsteriskPattern();
            }
        }
        else {
            return new SimplePattern(leftRegex.substring(0, index));
        }
    }

    private int indexOf(List<String> toFinds, String str) {
        if(toFinds == null || str == null) {
            return -1;
        }

        int index = -1;
        for(String toFind: toFinds) {
            int currentIndex = str.indexOf(toFind);
            if(currentIndex == 0) {
                index = 0;
                break;
            }
            else if(currentIndex < 0) {
                continue;
            }
            else { // currentIndex > 0
                index = (index < 0)?currentIndex:Math.min(index, currentIndex);
            }
        }

        return index;
    }
}

interface MyPattern {
    public int length();
}

class SimplePattern implements MyPattern {
    private String string;

    public SimplePattern(String str) {
        string = (str == null)?"":str;
    }

    public String getString() {
        return string;
    }

    @Override
    public int length() {
        return string.length();
    }
}

class DotPattern implements MyPattern {
    @Override
    public int length() {
        return 1;
    }
}

class AsteriskPattern implements MyPattern {
    @Override
    public int length() {
        return 1;
    }
}

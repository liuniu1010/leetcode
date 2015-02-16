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

        if(regex.startsWith("*")) {
            throw new InvalidInputException("* cannot be the first letter of regex!");
        }

        if(regex.indexOf("**") >= 0) {
            throw new InvalidInputException("** cannot be in the regex!");
        }

        if(regex.indexOf(".*") >= 0) {
            throw new InvalidInputException(".* cannot be in the regex!");
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
        else if(index == 0) { // it must be startsWith '.', if it is startsWith '*", there must be some logic error in somewhere
            return new DotPattern();
        }
        else {
            if(leftRegex.indexOf(CONST_DOT) == index) { 
                return new SimplePattern(leftRegex.substring(0, index));
            }
            else {// it must be case that leftRegex.indexOf(CONST_ASTERISK) == index)
                if(index == 1) {
                    return new AsteriskPattern(leftRegex.substring(0, 1));
                }
                else {// index > 1
                    return new SimplePattern(leftRegex.substring(0, index - 1));
                }
            }
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

abstract class MyPattern {
    protected final static String CONST_DOT = ".";
    protected final static String CONST_ASTERISK = "*";

    abstract public int length();
}

class SimplePattern extends MyPattern {
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

class DotPattern extends MyPattern {
    @Override
    public int length() {
        return CONST_DOT.length(); 
    }
}

class AsteriskPattern extends MyPattern {
    private String letter;

    public AsteriskPattern(String let) {
        letter = (let == null)?"":let;
    }

    @Override
    public int length() {
        return letter.length() + CONST_ASTERISK.length();
    }
}

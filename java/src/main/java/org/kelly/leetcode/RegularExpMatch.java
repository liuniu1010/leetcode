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
    public static RegularExpMatch getInstance() {
        return instance;
    }

    private void assertInput(String str, String regex) {
        assertInputString(str);
        assertInputRegex(regex);
    }

    private void assertInputString(String str) {
        if(str == null || str.isEmpty()) {
            throw new InvalidInputException("input string cannot be empty!");
        }


        if(str.indexOf("*") >= 0) {
            throw new InvalidInputException("input string cannot contain '*'");
        }
    }

    private void assertInputRegex(String regex) {
        if(regex == null || regex.isEmpty()) {
            throw new InvalidInputException("input regex cannot be empty!");
        }

        if(regex.startsWith("*")) {
            throw new InvalidInputException("* cannot be the first letter of regex!");
        }

        if(regex.indexOf("**") >= 0) {
            throw new InvalidInputException("** cannot be in the regex!");
        }
    }

    public boolean isMatch(String str, String regex) {
        assertInput(str, regex);

        List<MyPattern> myPatterns = compile(regex);
        String leftString = str;
        for(MyPattern myPattern: myPatterns) {
            leftString = myPattern.matchHeadAndCut(leftString);
            if(leftString == null) {
                return false;
            }
        }

        return (leftString.length() == 0)?true:false;
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

            MyPattern nextPattern = getFirstPattern(targetPatterns, leftRegex);
            myPatterns.add(nextPattern);

            if(nextPattern.length() == leftRegex.length()) {
                break;
            }

            leftRegex = leftRegex.substring(nextPattern.length());
        }

        return myPatterns;
    }

    private MyPattern getFirstPattern(List<String> targetPatterns, String regex) {
        int index = indexOf(targetPatterns, regex);
        if(index < 0) {
            return new SimplePattern(regex);
        }
        else if(index == 0) {
            if(regex.startsWith(".")) { 
                return new DotPattern();
            }
            else { // startsWith '*'
                return new AsteriskAnyLetterPattern();
            }
        }
        else {
            if(regex.indexOf(CONST_DOT) == index) { 
                return new SimplePattern(regex.substring(0, index));
            }
            else {// it must be case that regex.indexOf(CONST_ASTERISK) == index)
                if(index == 1) {
                    return new AsteriskPattern(regex.substring(0, 1).charAt(0));
                }
                else {// index > 1
                    return new SimplePattern(regex.substring(0, index - 1));
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

    abstract public String matchHeadAndCut(String str);
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

    @Override
    public String matchHeadAndCut(String str) {
        return (string.startsWith(str))?string.substring(str.length()):null;
    }
}

class DotPattern extends MyPattern {
    @Override
    public int length() {
        return CONST_DOT.length(); 
    }

    @Override
    public String matchHeadAndCut(String str) {
        return (str.isEmpty())?null:str.substring(length());
    }
}

class AsteriskPattern extends MyPattern {
    private char letter;

    public AsteriskPattern(char let) {
        letter = let;
    }

    @Override
    public int length() {
        return CONST_ASTERISK.length() + 1;
    }

    @Override
    public String matchHeadAndCut(String str) {
        if(!str.startsWith(String.valueOf(letter))) {
            return str;
        }

        String head = "";
        while(true) {
            head += String.valueOf(letter);
            if(!str.startsWith(head)) {
                break;
            }
        }

        return str.substring(head.length() - 1);
    }
}

class AsteriskAnyLetterPattern extends MyPattern {
    public AsteriskAnyLetterPattern() {
    }

    @Override 
    public int length() {
        return CONST_ASTERISK.length();
    }

    @Override
    public String matchHeadAndCut(String str) {
        if(str.isEmpty()) {
            return str;
        }

        String head = "";
        String sLetter = str.substring(0, 1);
        while(true) {
            head += sLetter;
            if(!str.startsWith(head)) {
                break;
            }
        }

        return str.substring(head.length() - 1);
    }
}

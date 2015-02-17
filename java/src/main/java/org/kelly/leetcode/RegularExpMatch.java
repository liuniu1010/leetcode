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
    private static final String CONST_DOT = ".";
    private static final String CONST_ASTERISK = "*";

    private static RegularExpMatch instance = new RegularExpMatch();

    private RegularExpMatch() {
    }

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
    }

    private void assertInputRegex(String regex) {
        if(regex == null || regex.isEmpty()) {
            throw new InvalidInputException("input regex cannot be empty!");
        }

        if(regex.indexOf("**") >= 0) {
            throw new InvalidInputException("** cannot be in the regex!");
        }
    }

    /*
     * the preferred run time is O(m+n)
     * m, n is the lenth of input str and regex
     */
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
                // the leftRegex has been used up, no left
                break;
            }

            leftRegex = leftRegex.substring(nextPattern.length());
        }

        return myPatterns;
    }

    private MyPattern getFirstPattern(List<String> targetPatterns, String regex) {
        int index = indexOf(targetPatterns, regex);
        if(index < 0) {
            // no any special letter in the regex, so it should be a normal string
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
                // there has at least 1 normal letter before the first dot, 
                // so generate the normal string as simplePattern first 
                return new SimplePattern(regex.substring(0, index));
            }
            else {// it must be case that regex.indexOf(CONST_ASTERISK) == index)
                if(index == 1) {
                    // there is only one letter before the first asterisk
                    // so this letter plus asterisk will be grouped as the AsteriskPattern
                    return new AsteriskPattern(regex.substring(0, 1).charAt(0));
                }
                else {// index > 1
                    // there has at least 2 letters before the first asterisk
                    // so generate the normal string as simplePattern first, without the nearest one to 
                    // asterisk 
                    return new SimplePattern(regex.substring(0, index - 1));
                }
            }
        }
    }

    /*
     * this is an enhanced version of String.indexOf()
     * which is index of a List<String>, not one string
     * @return    return value >= 0 means at least one String in toFinds was founded out in str
     *            the most left one's index would be returned
     *            return value == -1, means didn't find anything
     */
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
    protected static final String CONST_DOT = ".";
    protected static final String CONST_ASTERISK = "*";

    public abstract int length();

    /*
     * use current pattern to match the input str,
     * no need to match the whole str, only part of str from left beginning is enough
     * then the matched part will be cut, return the left part
     *
     * for example, with SimplePattern("abc")
     * for input str = "abcdefg", the most left part "abc" can be matched, the left side "defg" will be returned
     *
     * another example, with DotPattern
     * for input str = "abcdefg", the most left part "a" can be matched, the left side "bcdefg" will be returned
     * 
     * if the return value is null, that means the input str can not match current Pattern
     * for example, with SimplePattern("abc") 
     * for input str = "dabcdefg", no any most left part can be matched, it will return null
     *
     * this method should be implemented by sub class for different type of match requirement
     */
    public abstract String matchHeadAndCut(String str);
}

/**
 * the simplest pattern which has no any special letter, no any special
 * meaning for each letter
 */
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
        return (str.startsWith(string))?str.substring(this.length()):null;
    }
}

/**
 * matches any single char, only care the first letter of the input str 
 */
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

/**
 * matches zero or more of the preceding letter before '*' 
 */
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
            // zero preceding letter is also matched
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

/**
 * a special type of '*', which has no preceding letter
 * for this type, it match zero or any number of same letter
 * no matter what the letter is
 */
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

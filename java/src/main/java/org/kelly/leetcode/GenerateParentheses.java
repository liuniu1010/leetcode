/***
 * source: https://oj.leetcode.com/problemset/algorithms/
 * Author: Liu, Niu <liuniu@tsinghua.org.cn>
 */

package org.kelly.leetcode;

import java.util.List;
import java.util.ArrayList;
import org.kelly.leetcode.exception.InvalidInputException;

/***
 * Given n pairs of parentheses, write a function to generate all combinations of well-formed parentheses.
 * For example, given n = 3, a solution set is:
 * "((()))", "(()())", "(())()", "()(())", "()()()" 
 *
 * @Author Liu, Niu
 */
public class GenerateParentheses {
    private static GenerateParentheses instance = new GenerateParentheses();

    private final static String PARENTHESE_BEGIN = "(";
    private final static String PARENTHESE_END = ")";

    private GenerateParentheses() {
    }


    public static GenerateParentheses getInstance() {
        return instance;
    }

    private void assertInput(int n) {
        if(n <= 0) {
            throw new InvalidInputException("input n cannot be less than 1");
        }
    }

    /*
     * the basic thinking is
     * there are two pools, the pool1 contains n '(', pool2 contains n ')'
     * each time we can fetch one element from pool1 or pool2 to append to string
     * until no any element in pool1 and pool2
     * but at any time, we must make sure that the pool2's left size is greater of equals to pool1's left size
     */
    public List<String> generateParentheses(int n) {
        assertInput(n);

        int numberOfBeginLeft = n;
        int numberOfEndLeft = n;

        List<String> parentheses = new ArrayList<String>();
        String currentParenthese = "";
        generateParentheses(numberOfBeginLeft, numberOfEndLeft, parentheses, currentParenthese);

        return parentheses;
    }

    private void generateParentheses(int numberOfBeginLeft, int numberOfEndLeft, List<String> parentheses, String currentParenthese) {
        if(numberOfBeginLeft <= 0 && numberOfEndLeft <= 0) {
            parentheses.add(currentParenthese);
            return;
        }

        if(numberOfBeginLeft == numberOfEndLeft) {
            // in this case, numberOfBeginLeft and numberOfEndLeft must be greater than 0
            // we should make sure at any case, numberOfBeginLeft <= numberOfEndLeft
            generateParentheses(numberOfBeginLeft - 1, numberOfEndLeft, parentheses, currentParenthese + PARENTHESE_BEGIN);
            return;
        }
 
        // in current case, it must be numberOfBeginLeft < numberOfEndLeft
        if(numberOfBeginLeft > 0) {
            generateParentheses(numberOfBeginLeft - 1, numberOfEndLeft, parentheses, currentParenthese + PARENTHESE_BEGIN);
        }

        // in current case, it must be numberOfEndLeft > 0
        generateParentheses(numberOfBeginLeft, numberOfEndLeft - 1, parentheses, currentParenthese + PARENTHESE_END);
    }
}


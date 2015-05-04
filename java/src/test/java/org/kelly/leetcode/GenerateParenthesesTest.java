package org.kelly.leetcode;

import java.util.List;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class GenerateParenthesesTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public GenerateParenthesesTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( GenerateParenthesesTest.class );
    }

    public void testGenerateParentheses()
    {
        GenerateParentheses instance = GenerateParentheses.getInstance();
        int n = 4;
        List<String> parentheses = instance.generateParentheses(n);

        System.out.println("parentheses generated:");
        for(String parenthese: parentheses) {
            System.out.println(parenthese);
        }
        System.out.println("when n is " + n + ", total solution number is " + parentheses.size());
    }
}

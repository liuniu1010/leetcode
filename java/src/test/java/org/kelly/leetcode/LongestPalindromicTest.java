package org.kelly.leetcode;


import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class LongestPalindromicTest
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public LongestPalindromicTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( LongestPalindromicTest.class );
    }

    public void testLongestPalindromic() {
        String inputString = "abcd11334422abcdcbaefghijklmn";
        String longestPalindromic = LongestPalindromic.getInstance().getLongestPalindromicSubstring(inputString);

        System.out.println("longest parlindromic = " + longestPalindromic + ", length = " + longestPalindromic.length());
        assertEquals(longestPalindromic, "abcdcba");
    }
}

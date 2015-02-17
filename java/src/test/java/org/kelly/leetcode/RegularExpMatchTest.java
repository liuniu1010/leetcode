package org.kelly.leetcode;


import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.Random;

/**
 * Unit test for simple App.
 */
public class RegularExpMatchTest
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public RegularExpMatchTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( RegularExpMatchTest.class );
    }


    public void testRegularExpMatch()
    {
        boolean isMatch;
        isMatch = RegularExpMatch.getInstance().isMatch("aa", "a");
        assertFalse(isMatch);

        isMatch = RegularExpMatch.getInstance().isMatch("aa", "aa");
        assertTrue(isMatch);

        isMatch = RegularExpMatch.getInstance().isMatch("aaa", "aa");
        assertFalse(isMatch);

        isMatch = RegularExpMatch.getInstance().isMatch("aa", "a*");
        assertTrue(isMatch);
        
        isMatch = RegularExpMatch.getInstance().isMatch("aa", ".*");
        assertTrue(isMatch);

        isMatch = RegularExpMatch.getInstance().isMatch("ab", ".*");
        assertTrue(isMatch);
        
        isMatch = RegularExpMatch.getInstance().isMatch("aab", "c*a*b");
        assertTrue(isMatch);

        System.out.println("regular expression match passed!");
    }
}

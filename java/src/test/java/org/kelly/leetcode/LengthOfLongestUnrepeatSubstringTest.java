package org.kelly.leetcode;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class LengthOfLongestUnrepeatSubstringTest 
    extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public LengthOfLongestUnrepeatSubstringTest( String testName ) {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite( LengthOfLongestUnrepeatSubstringTest.class );
    }
    
    public void testLengthOfSubstring() {
        String origString = "abejklmnpqbbbdefgbbbab12345678cbb";
        String longestSub = LengthOfLongestUnrepeatSubstring.getInstance().getLongestUnrepeatSubstring(origString);

        System.out.println("longestSub = " + longestSub + ", length = " + longestSub.length());
        assertEquals(longestSub, "ab12345678c");
        assertEquals(longestSub.length(), 11);
        
        System.out.println("test LengthOfSubstring passed!");
    }
}

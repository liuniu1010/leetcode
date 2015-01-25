package org.kelly.leetcode;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class TwosumTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public TwosumTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( TwosumTest.class );
    }

    public void testTwosum()
    {
        List<Integer> input = new ArrayList<Integer>();
        input.add(8);
        input.add(7);
        input.add(2);
        input.add(15);
        
        Integer target = 23;
        
        Result result = Twosum.searchTwosum(input, target);
        if(result != null) {
            System.out.println("index1 = " + result.index1);
            System.out.println("index2 = " + result.index2);
        } else {
            System.out.println("cannot find the two indexes!");
        }
        
        assertEquals(result.index1, 0);
        assertEquals(result.index2, 3);
        
        System.out.println("test Twosum passed!");
    }
}

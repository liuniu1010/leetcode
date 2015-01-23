package org.kelly.leetcode;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test
 */
public class MedianOfTwoSortedArraysTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public MedianOfTwoSortedArraysTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( MedianOfTwoSortedArraysTest.class );
    }

    public void testQuickSolution()
    {
        List<Integer> array1 = new ArrayList<Integer>();
        array1.add(1);
        array1.add(2);
        array1.add(3);
        array1.add(4);
        array1.add(5);
        array1.add(6);
        array1.add(7);

        List<Integer> array2 = new ArrayList<Integer>();
        array2.add(3);
        array2.add(4);
        array2.add(5);
        array2.add(6);
        array2.add(7);
//        array2.add(8);
        
        Median median = MedianOfTwoSortedArrays.getInstance().getMedianWithQuickSolution(array1, array2);
        System.out.println("median = " + median);
    }
}

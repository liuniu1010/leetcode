package org.kelly.leetcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        int number = 1;
        Random random = new Random();
        while(number < 1000000) {
            number = number + random.nextInt(10);
            array1.add(number);
        }

        List<Integer> array2 = new ArrayList<Integer>();
        number = 2;
        while(number < 1000000) {
            number = number + random.nextInt(11);
            array2.add(number);
        }
        
        long beginMillis = System.currentTimeMillis();
        Median medianOfSimple = MedianOfTwoSortedArrays.getInstance().getMedianWithSimpleSolution(array1, array2);
        long consumeMillisOfSimple = System.currentTimeMillis() - beginMillis;
        
        beginMillis = System.currentTimeMillis();
        Median medianOfQuick = MedianOfTwoSortedArrays.getInstance().getMedianWithQuickSolution(array1, array2);
        long consumeMillisOfQuick = System.currentTimeMillis() - beginMillis;
        System.out.println("medianSimple = " + medianOfSimple + ", consumeMillis = " + consumeMillisOfSimple);
        System.out.println("medianQuick = " + medianOfQuick + ", consumeMillis = " + consumeMillisOfQuick);
        
        if(medianOfSimple.equals(medianOfQuick)) {
            System.out.println("median of two sorted arrays test passed");
        }
        else {
            throw new RuntimeException("median of two sorted arrays test failed!");
        }
    }
}
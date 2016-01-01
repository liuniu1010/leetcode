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
    extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public MedianOfTwoSortedArraysTest( String testName ) {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite( MedianOfTwoSortedArraysTest.class );
    }

    public void testMediansOfTwoSortedArrays() {
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

        MedianOfTwoSortedArrays instance = MedianOfTwoSortedArrays.getInstance();        
        long beginMillis = System.currentTimeMillis();
        Median medianOfSimple = instance.getMedianWithSimpleSolution(array1, array2);
        long consumeMillisOfSimple = System.currentTimeMillis() - beginMillis;
        
        beginMillis = System.currentTimeMillis();
        Median medianOfQuick = instance.getMedianWithQuickSolution(array1, array2);
        long consumeMillisOfQuick = System.currentTimeMillis() - beginMillis;
        System.out.println("medianSimple = " + medianOfSimple + ", consumeMillis = " + consumeMillisOfSimple);
        System.out.println("medianQuick = " + medianOfQuick + ", consumeMillis = " + consumeMillisOfQuick);
        
        super.assertEquals(medianOfSimple, medianOfQuick);
        
        System.out.println("test MedianOfTwoSortedArrays passed!");
    }
}

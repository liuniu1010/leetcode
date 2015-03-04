package org.kelly.leetcode;


import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.Random;

/**
 * Unit test for simple App.
 */
public class ContainerWithMostWaterTest
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public ContainerWithMostWaterTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( ContainerWithMostWaterTest.class );
    }


    public void testMostWater()
    {
        // generate random points
        int[] heights = new int[10000];
        Random random = new Random();
        for(int i = 0;i < heights.length;i++) {
            heights[i] = random.nextInt(10000);
        }

        ContainerWithMostWater instance = ContainerWithMostWater.getInstance();
        long beginMillis = System.currentTimeMillis();
        int maxCapacityWithSimpleSolution = instance.getMaxCapacityWithSimpleSolution(heights);
        System.out.println("maxCapacityWithSimpleSolution = " + maxCapacityWithSimpleSolution + ", time consume = " + (System.currentTimeMillis() - beginMillis) + " millis");

        beginMillis = System.currentTimeMillis();
        int maxCapacityWithQuickSolution = instance.getMaxCapacityWithQuickSolution(heights);
        System.out.println("maxCapacityWithQuickSolution = " + maxCapacityWithQuickSolution + ", time consume = " + (System.currentTimeMillis() - beginMillis) + " millis");
        assertEquals(maxCapacityWithSimpleSolution, maxCapacityWithQuickSolution);

        System.out.println("test ContainerWithMostWater passed!");
    }
}

package org.kelly.leetcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.kelly.leetcode.util.Triplet;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class ThreeSumClosestTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public ThreeSumClosestTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( ThreeSumClosestTest.class );
    }

    public void testThreesumClosest()
    {
        // generate test numbers randomly
        List<Integer> numbers = new ArrayList<Integer>();
        int minNumber = -10000;
        int maxNumber = 10000;

        int number = minNumber;
        Random random = new Random();
        while(number < maxNumber) {
            number += random.nextInt(2000);
            numbers.add(number);
        }

        int target = random.nextInt(1997);

        ThreeSumClosest instance = ThreeSumClosest.getInstance();

        long beginMillis = System.currentTimeMillis();
        Triplet tripletOfSimple = instance.threeSumClosestWithSimpleSolution(numbers, target);
        System.out.println("time consume with simple solution = " + (System.currentTimeMillis() - beginMillis) + " ms");

        beginMillis = System.currentTimeMillis();
        Triplet tripletOfQuick = instance.threeSumClosestWithQuickSolution(numbers, target);
        System.out.println("time consume with quick solution = " + (System.currentTimeMillis() - beginMillis) + " ms");

        int distanceOfSimple = Math.abs(tripletOfSimple.sum() - target);
        int distanceOfQuick = Math.abs(tripletOfQuick.sum() - target);
        System.out.println("distanceOfSimple = " + distanceOfSimple);
        System.out.println("distanceOfQuick  = " + distanceOfQuick);
        assertEquals(distanceOfSimple, distanceOfQuick);
    }
}

package org.kelly.leetcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.kelly.leetcode.util.Quadruplet;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class FourSumTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public FourSumTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( FourSumTest.class );
    }

    public void testFourSum()
    {
        // generate test numbers randomly
        List<Integer> numbers = new ArrayList<Integer>();
        int minNumber = -500000;
        int maxNumber = 500000;

        int number = minNumber;
        Random random = new Random();
        while(number < maxNumber) {
            number += random.nextInt(20000);
            numbers.add(number);
        }

        FourSum instance = FourSum.getInstance();

        long beginMillis = System.currentTimeMillis();
        List<Quadruplet> quadrupletsOfSimple = instance.fourSumWithSimpleSolution(numbers, 0);
        System.out.println("4 sum, time consume with simple solution = " + (System.currentTimeMillis() - beginMillis) + " ms");

        beginMillis = System.currentTimeMillis();
        List<Quadruplet> quadrupletsOfQuick = instance.fourSumWithQuickSolution(numbers, 0);
        System.out.println("4 sum, time consume with quick solution = " + (System.currentTimeMillis() - beginMillis) + " ms");

        String sQuadrupletsOfSimple = quadruplets2String(quadrupletsOfSimple);
        String sQuadrupletsOfQuick = quadruplets2String(quadrupletsOfQuick);
        System.out.println("quadrupletsOfSimple.size = " + quadrupletsOfSimple.size());
        System.out.println("quadrupletsOfQuick.size =  " + quadrupletsOfQuick.size());

        assertEquals(quadrupletsOfSimple, quadrupletsOfQuick);
    }

    private String quadruplets2String(List<Quadruplet> quadruplets) {
        String str = "";
        for(Quadruplet quadruplet: quadruplets) {
            str += quadruplet.toString();
        }

        return str;
    }
}

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
public class ThreesumTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public ThreesumTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( ThreesumTest.class );
    }

    public void testThreesum()
    {
        // generate test numbers randomly
        List<Integer> numbers = new ArrayList<Integer>();
        int minNumber = -500000;
        int maxNumber = 500000;

        int number = minNumber;
        Random random = new Random();
        while(number < maxNumber) {
            number += random.nextInt(2000);
            numbers.add(number);
        }

        ThreeSum instance = ThreeSum.getInstance();

        long beginMillis = System.currentTimeMillis();
        List<Triplet> tripletsOfSimple = instance.threeSumWithSimpleSolution(numbers);
        System.out.println("time consume with simple solution = " + (System.currentTimeMillis() - beginMillis) + " ms");

        beginMillis = System.currentTimeMillis();
        List<Triplet> tripletsOfQuick = instance.threeSumWithQuickSolution(numbers);
        System.out.println("time consume with quick solution = " + (System.currentTimeMillis() - beginMillis) + " ms");

        String sTripletsOfSimple = triplets2String(tripletsOfSimple);
        String sTripletsOfQuick = triplets2String(tripletsOfQuick);
        System.out.println("tripletsOfSimple size = " + tripletsOfSimple.size());
        System.out.println("tripletsOfQuick size =  " + tripletsOfQuick.size());

        assertEquals(tripletsOfSimple, tripletsOfQuick);
    }

    private String triplets2String(List<Triplet> triplets) {
        String str = "";
        for(Triplet triplet: triplets) {
            str += triplet.toString();
        }

        return str;
    }
}

package org.kelly.leetcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class NQueensTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public NQueensTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( NQueensTest.class );
    }

    public void testNQueens()
    {
        int N = 8;
        List<List<CoordinateTwo>> solvedNQueens = NQueens.getInstance().solveNQueens(N);
        System.out.println("There are " + solvedNQueens.size() + " solutions");
        if(solvedNQueens.size() > 0) {
            System.out.println("Random select one to print here");
            Random random = new Random();
            int randomIndex = random.nextInt(solvedNQueens.size());
            NQueens.getInstance().printNQueen(solvedNQueens.get(randomIndex));
        }
    }
}

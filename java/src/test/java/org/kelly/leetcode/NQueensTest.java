package org.kelly.leetcode;

import java.util.ArrayList;
import java.util.List;

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
        NQueens.getInstance().printNQueens(solvedNQueens);
        System.out.println("There are " + solvedNQueens.size() + " solutions");
        
    }
}

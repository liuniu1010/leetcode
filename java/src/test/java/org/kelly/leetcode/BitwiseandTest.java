package org.kelly.leetcode;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class BitwiseandTest 
    extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public BitwiseandTest( String testName ) {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite( BitwiseandTest.class );
    }

    public void _testBit() {
        int M = 4;
        int N = 8;

        int result = M & N;
        System.out.println("result = " + result);
    }

    public void _test1() {
        for(int i = 0;i < 20;i++) {
            int length = getBitLength(i);
            System.out.println("length of " + i + " is " + length);
        }
    }

    private int getBitLength(int number) {
        return Integer.SIZE-Integer.numberOfLeadingZeros(number);
    }

    private int getMaxEdgeOfBitLength(int bitLength) {
        return 2^bitLength - 1;
    }

    private int getMinEdgeOfBitLength(int bitLength) {
        return 2^(bitLength - 1);
    }

    private Pair getEdgePairOfBitLength(int bitLength) {
        return new Pair(getMinEdgeOfBitLength(bitLength), getMaxEdgeOfBitLength(bitLength));
    }

    private Pair getUpperPairEdgeOfThis(int number) {
        int length = getBitLength(number);
        int maxEdge = getMaxEdgeOfBitLength(length);

        return new Pair(number, maxEdge);
    }

    private Pair getLowerPairEdgeOfThis(int number) {
        int length = getBitLength(number);
        int minEdge = getMinEdgeOfBitLength(length);

        return new Pair(minEdge, number);
    }

    public void test() {
        int result = solution(4, 5);
        System.out.println("result = " + result);
    }

    public int solution(int M, int N) {
        int lengthOfM = getBitLength(M);
        int lengthOfN = getBitLength(N);

        System.out.println("lengthOfM = " + lengthOfM);
        System.out.println("lengthOfN = " + lengthOfN);
        

        if(lengthOfM == lengthOfN) {
            return naiveBitwiseAnd(M, N);
        }
        else if(lengthOfM + 1 == lengthOfN) {
            Pair pairOfM = getUpperPairEdgeOfThis(M);
            Pair pairOfN = getLowerPairEdgeOfThis(N);
            
            int bitwiseLower = naiveBitwiseAnd(pairOfM.minEdge, pairOfN.maxEdge);
            int bitwiseUpper = naiveBitwiseAnd(pairOfN.minEdge, pairOfN.maxEdge);
            return bitwiseLower & bitwiseUpper;
        }
        else {
            Pair pairOfM = getUpperPairEdgeOfThis(M);
            Pair pairOfN = getLowerPairEdgeOfThis(N);
            Pair pairOfNext = getEdgePairOfBitLength(lengthOfM + 1);
            
            int bitwiseLower = naiveBitwiseAnd(pairOfM.minEdge, pairOfN.maxEdge);
            int bitwiseUpper = naiveBitwiseAnd(pairOfN.minEdge, pairOfN.maxEdge);
            int bitwiseMiddle = naiveBitwiseAnd(pairOfNext.minEdge, pairOfNext.maxEdge);

            System.out.println("bitwiseLower = " + bitwiseLower);
            System.out.println("bitwiseMiddle = " + bitwiseMiddle);
            System.out.println("bitwiseUpper = " + bitwiseUpper);

             
            return bitwiseLower & bitwiseMiddle & bitwiseUpper;
        }
    }

/*    private List<Integer> getEdges(int M, int N) {
        List<Integer> edges = new ArrayList<Integer>();
        edges.add(M);
        int edgeOfM = getMaxEdgeOfThis(M);
        edges.add(edgeOfM);
    }
*/
    private int naiveBitwiseAnd(int M, int N) {
        int result = M;
        for(int i = M+1;i < N+1;i++) {
            result = result & i;
        }

        return result;
    }

}

class Pair {
    public int minEdge;
    public int maxEdge;

    public Pair(int inputMinEdge, int inputMaxEdge) {
        minEdge = inputMinEdge;
        maxEdge = inputMaxEdge;
    }
}

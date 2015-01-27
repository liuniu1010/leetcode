package org.kelly.leetcode;


import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.Random;

/**
 * Unit test for simple App.
 */
public class AddTwoNumbersTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AddTwoNumbersTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AddTwoNumbersTest.class );
    }


    public void testAddTwoNumbers()
    {
        Random random = new Random();

        int number1 = random.nextInt(1000000);
        int number2 = random.nextInt(10000);
        ListNode node1 = generateListNodeFromInt(number1);
        ListNode node2 = generateListNodeFromInt(number2);
        
        System.out.println("number1 = " + number1);
        System.out.println("number2 = " + number2);
        System.out.println("node1 = " + node1);
        System.out.println("node2 = " + node2);
        ListNode resultNodeRecursive = AddTwoNumbers.getInstance().addTwoNumbersWithRecursiveSolution(node1, node2);
        ListNode resultNodeLoop = AddTwoNumbers.getInstance().addTwoNumbersWithLoopSolution(node1, node2);

        System.out.println("resultNodeRecursive = " + resultNodeRecursive);
        System.out.println("resultNodeLoop = " + resultNodeLoop);

        int result = number1 + number2;
        ListNode verifyNode = generateListNodeFromInt(result);

        assertEquals(resultNodeRecursive.toString(), verifyNode.toString());
        assertEquals(resultNodeLoop.toString(), verifyNode.toString());

        System.out.println("test Add two numbers passed!");
    }

    private ListNode generateListNodeFromInt(int number) {
        String sNumber = String.valueOf(number);
        ListNode nextNode = null;
        for(int i = 0;i < sNumber.length();i++) {
            String ch = sNumber.substring(i, i + 1);
            ListNode curNode = new ListNode();
            curNode.val = new Integer(ch);

            curNode.next = nextNode;
            nextNode = curNode;

            if(i >= sNumber.length() - 1) {
                return curNode;
            }
        }
        return null;
    }
}

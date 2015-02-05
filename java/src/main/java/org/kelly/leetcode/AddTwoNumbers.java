/***
 * source: https://oj.leetcode.com/problemset/algorithms/
 * Author: Liu, Niu <liuniu@tsinghua.org.cn>
 */

package org.kelly.leetcode;

import org.kelly.leetcode.exception.InvalidInputException;

/***
 * You are given two linked lists representing two non-negative numbers. 
 * The digits are stored in reverse order and each of their nodes contain 
 * a single digit. Add the two numbers and return it as a linked list. 
 * Input: (2 -> 4 -> 3) + (5 -> 6 -> 4) Output: 7 -> 0 -> 8
 * @author liuniu
 *
 */
public class AddTwoNumbers {
    private static AddTwoNumbers instance = new AddTwoNumbers();

    private AddTwoNumbers() {
    }


    public static AddTwoNumbers getInstance() {
        return instance;
    }

    /*
     * the preferred running time should be O(max(l1.size, l2.size))
     */
    public ListNode addTwoNumbersWithRecursiveSolution(ListNode l1, ListNode l2) {
        assertInput(l1);
        assertInput(l2);

        return addTwoNumbersRecursive(l1, l2, 0);
    }

    private ListNode addTwoNumbersRecursive(ListNode l1, ListNode l2, int extra) {
        if(l1 == null && l2 == null && extra == 0) {
            return null;
        }

        int value = (((l1 == null)?0:l1.getVal()) + ((l2 == null)?0:l2.getVal()) + extra) % 10;
        int nextExtra = (((l1 == null)?0:l1.getVal()) + ((l2 == null)?0:l2.getVal()) + extra) / 10;
        ListNode nextNode = addTwoNumbersRecursive((l1 == null)?null:l1.getNext(), (l2 == null)?null:l2.getNext(), nextExtra);
        return new ListNode(value, nextNode);
    }

    /*
     * the preferred running time should be O(max(l1.size, l2.size))
     */
    public ListNode addTwoNumbersWithLoopSolution(ListNode l1, ListNode l2) {
        assertInput(l1);
        assertInput(l2);

        ListNode curNode1 = l1;
        ListNode curNode2 = l2;
        int extra = 0;
        ListNode curResult = null;
        ListNode preResult = null;
        ListNode firstResult = null;
        while((curNode1 != null) || (curNode2 != null) || (extra != 0)) {
            int value = (((curNode1 == null)?0:curNode1.getVal()) + ((curNode2 == null)?0:curNode2.getVal()) + extra) % 10;
            extra = (((curNode1 == null)?0:curNode1.getVal()) + ((curNode2 == null)?0:curNode2.getVal()) + extra) / 10;

            curNode1 = (curNode1 == null)?null:curNode1.getNext();
            curNode2 = (curNode2 == null)?null:curNode2.getNext();
            curResult = new ListNode(value);
            
            if(preResult != null) {
                preResult.setNext(curResult);
            } 
            else{
                firstResult = curResult;
            }

            preResult = curResult;
        }

        return firstResult;
    }

    private void assertInput(ListNode ld) {
        if(ld == null) {
            throw new InvalidInputException("input ListNode can not be null!");
        }
    }
}

/**
 * for convenience, all variables in this class are defined as public
 */
class ListNode {
    private int val;
    private ListNode next;

    public ListNode() {
        val = 0;
        next = null;
    }

    public ListNode(int x) {
        val = x;
        next = null;
    }

    public ListNode(int x, ListNode nextNode) {
        val = x;
        next = nextNode;
    }

    public int getVal() {
        return val;
    }

    public ListNode getNext() {
        return next;
    }

    public void setNext(ListNode nextNode) {
        next = nextNode;
    }

    @Override
    public String toString() {
        return String.valueOf(val) + "->" + ((next == null)?"":next.toString());
    }
}

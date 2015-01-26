package org.kelly.leetcode;

import java.util.List;
import java.util.ArrayList;

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
    private AddTwoNumbers() {
    }

    private static AddTwoNumbers instance = new AddTwoNumbers();

    public static AddTwoNumbers getInstance() {
        return instance;
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        assertInput(l1);
        assertInput(l2);

        List<Integer> list1 = generateListFromListNode(l1);
        List<Integer> list2 = generateListFromListNode(l2);

        List<Integer> resultList = new ArrayList<Integer>();
        int maxLengthOfList = Math.max(list1.size(), list2.size());
        int extra = 0;
        for(int i = 0;i < maxLengthOfList;i++) {
            int value1 = (i >= list1.size())?0:list1.get(i);
            int value2 = (i >= list2.size())?0:list2.get(i);
            
            int resultSingleDigit = (value1 + value2 + extra) % 10;
            extra = (value1 + value2 + extra) / 10;

            resultList.add(resultSingleDigit);
        }
        
        if(extra > 0) {
            resultList.add(extra);
        }

        return generateListNodeFromList(resultList);
    }

    private List<Integer> generateListFromListNode(ListNode ld) {
        List<Integer> list = new ArrayList<Integer>();
        list.add(0, ld.val);
        while(ld.next != null) {
            list.add(ld.next.val);
            ld = ld.next;
        }

        return list;
    }

    private ListNode generateListNodeFromList(List<Integer> list) {
        ListNode nextNode = null;
        ListNode resultNode = null;
        for(int i = 0; i < list.size(); i++) {
            ListNode ld = new ListNode(list.get(i));
            ld.next = nextNode;
            nextNode = ld;

            if(i >= list.size() - 1) {
                resultNode = ld;
                break;
            }
        }
        
        return resultNode;
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
    public int val;
    public ListNode next;
    public ListNode(int x) {
        val = x;
        next = null;
    }
}

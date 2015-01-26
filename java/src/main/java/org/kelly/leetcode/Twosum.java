package org.kelly.leetcode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kelly.leetcode.exception.InvalidInputException;

/**
 * Given an array of integers, find two numbers such that they add up to a specific target number.
 * The function twoSum should return indices of the two numbers such that they add up to the target, 
 * where index1 must be less than index2. Please note that your returned answers (both index1 and index2) are zero-based.
 * You may assume that each input would have exactly one solution.
 * Input: numbers={2, 7, 11, 15}, target=9
 * Output: index1=0, index2=1
 *
 * @author liuniu
 */
public class Twosum {
    /**
     * search the two indexes, the running time should be O(size of input)
     * @param input
     * @param target
     * @return          if the two indexes were found out, return the related Result instance,
     *                  else return null
     */
    public static Result searchTwosum(List<Integer> input, Integer target) {
        assertInput(input, target);
        
        Map<Integer, Integer> inputCache = new HashMap<Integer, Integer>();
        
        /***
         * first loop through the input, record the value and index into cache
         * to make sure the overall loop times is O(input.size), not O(input.size^2)
         */
        for(int i = 0;i < input.size();i++) {
            inputCache.put(input.get(i), i);
        }
        
        for(int i = 0;i < input.size();i++) {
            int currentValue = input.get(i);
            int searchValue = target - currentValue;
            if(inputCache.containsKey(searchValue)) {
                Result result = new Result();
                result.index1 = i;
                result.index2 = inputCache.get(searchValue);
                return result;
            }
        }
        
        return null;
    }
    
    private static void assertInput(List<Integer> input, Integer target) {
        if(input == null) {
            throw new InvalidInputException("input should not be null!");
        }
        else if(input.size() < 2) {
            throw new InvalidInputException("input length should be greater or equal than 2");
        }
        else if(target == null) {
            throw new InvalidInputException("target should not be null!");
        }
    }
}

class Result {
    public Result() {
        index1 = 0;
        index2 = 0;
    }
    
    public int index1;
    public int index2;
}

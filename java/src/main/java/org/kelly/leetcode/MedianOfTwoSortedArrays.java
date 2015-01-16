package org.kelly.leetcode;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.kelly.leetcode.exception.InvalidInputException;

/**
 * There are two sorted arrays A and B of size m and n respectively. 
 * Find the median of the two sorted arrays. The overall run time 
 * complexity should be O(log (m+n)).
 * 
 * @author liuniu
 *
 */
public class MedianOfTwoSortedArrays {
    
    /*
     * this is the simple solution to get the median
     * the logic is simple, but the runtime will be O((m+n)log(m+n))
     * since the logic is simple, we can know for sure the result is right
     * so we can use this method's result to verify the results of quick solution
     * assume the input arrays are sorted ascending
     */
    public Median getMedianWithSimpleSolution(List<Integer> sortedArray1, List<Integer> sortedArray2) {
        assertInput(sortedArray1, sortedArray2);
        
        List<Integer> mergedArray = new ArrayList<Integer>();
        mergedArray.addAll(sortedArray1);
        mergedArray.addAll(sortedArray2);
        Comparator<Integer> comparator = new Comparator<Integer>() {

            public int compare(Integer number1, Integer number2) {
                return number1.compareTo(number2);
            }
        };        
        mergedArray.sort(comparator);
        
        return getMedian(mergedArray);
    }
    
    /*
     * this is the quick solution to get median
     * the logic is a bit complicated, but the runtime
     */
    public Median getMedianWithQuickSolution(List<Integer> sortedArray1, List<Integer> sortedArray2) {
        assertInput(sortedArray1, sortedArray2);
        
        // wait to be completed...
        return null;
    }
    
    private void assertInput(List<Integer> sortedArray1, List<Integer> sortedArray2) {
        assertInput(sortedArray1);
        assertInput(sortedArray2);
    }
    
    private void assertInput(List<Integer> sortedArray) {
        if(sortedArray == null || sortedArray.isEmpty()) {
            throw new InvalidInputException("input array should not be empty!");
        }
    }
    
    private boolean isEven(int number) {
        return (number % 2) == 1;
    }
         
    /*
     * get Median of one array
     */
    private Median getMedian(List<Integer> sortedArray) {
        if(isEven(sortedArray.size())) {
            DoubleMedian median = new DoubleMedian();
            median.index1 = (sortedArray.size() / 2) - 1;
            median.index2 = median.index1 + 1;
            median.value1 = sortedArray.get(median.index1);
            median.value2 = sortedArray.get(median.index2);
            
            return median;
        } else {
            SingleMedian median = new SingleMedian();
            median.index = sortedArray.size() / 2;
            median.value = sortedArray.get(median.index);
            
            return median;
        }
    }
} 

/*
 * 
 * Middle number of a sorted list of numbers
 * this is just a interface implemented by SingleMedian or DoubleMedian
 * in case that the size of list numbers are even, the median instance 
 * should be DoubleMedian.
 * in case that the size of list numbers are odd, the median instance 
 * should be SingleMedian  
 *
 */
interface Median {
}

class SingleMedian implements Median {
    public int index;
    public int value;
}

class DoubleMedian implements Median {
    public int index1;
    public int value1;
    
    public int index2;
    public int value2;
}
/***
 * source: https://oj.leetcode.com/problemset/algorithms/
 * Author: Liu, Niu <liuniu@tsinghua.org.cn>
 */

package org.kelly.leetcode;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;
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
    private static MedianOfTwoSortedArrays instance = new MedianOfTwoSortedArrays();

    private MedianOfTwoSortedArrays() {
    }


    public static MedianOfTwoSortedArrays getInstance() {
        return instance;
    }
    
    /*
     * this is the simple solution to get the median
     * the logic is simple, but the runtime will be O((m+n)log(m+n))
     * since the logic is simple, we can know for sure the result is right
     * so we can use this method's result to verify with the results of quick solution
     * assume the input arrays are sorted ascending
     */
    public Median getMedianWithSimpleSolution(List<Integer> sortedArray1, List<Integer> sortedArray2) {
        assertInput(sortedArray1, sortedArray2);
        
        List<Integer> mergedArray = new ArrayList<Integer>();
        mergedArray.addAll(sortedArray1);
        mergedArray.addAll(sortedArray2);
        
        return getMedianWithUnsortedArray(mergedArray);
    }
    
    /*
     * this is the quick solution to get median
     * the logic is some more complicated
     * the running time should be O(log(min(m,n)), m and n represent 
     * size of sortedArray1 and sortedArray2
     */
    public Median getMedianWithQuickSolution(List<Integer> sortedArray1, List<Integer> sortedArray2) {
        this.assertInput(sortedArray1, sortedArray2);
        
        /*
         * prepare to call private method getMedian(List<Integer>, List<Integer>)
         * we should make sure the first argument1's size is less than or equals
         * to argument2's size. this is the assumption of the called method
         * the assumption can simplify logics of the algorithm
         */
        if(sortedArray1.size() <= sortedArray2.size()) {
            return getMedian(sortedArray1, sortedArray2);
        }
        else {
            return getMedian(sortedArray2, sortedArray1);
        }
    }
    
    /*
     * the basic assumption is that length of sortedArrayLeft is less than or equal to sortedArrayRight.
     */
    private Median getMedian(List<Integer> sortedArrayLeft, List<Integer> sortedArrayRight) {
        Median medianLeft = getMedian(sortedArrayLeft);
        Median medianRight = getMedian(sortedArrayRight);
                
        if((medianLeft instanceof SingleMedian) && (medianRight instanceof SingleMedian)) {
            return getMedian(sortedArrayLeft, sortedArrayRight, (SingleMedian)medianLeft, (SingleMedian)medianRight);
        } 
        else if((medianLeft instanceof SingleMedian) && (medianRight instanceof DoubleMedian)) {
            return getMedian(sortedArrayLeft, sortedArrayRight, (SingleMedian)medianLeft, (DoubleMedian)medianRight);
        }
        else if((medianLeft instanceof DoubleMedian) && (medianRight instanceof SingleMedian)) {
            return getMedian(sortedArrayLeft, sortedArrayRight, (DoubleMedian)medianLeft, (SingleMedian)medianRight);
        }
        else { // it must be case that medianLeft and medianRight are all DoubleMedian
            return getMedian(sortedArrayLeft, sortedArrayRight, (DoubleMedian)medianLeft, (DoubleMedian)medianRight);
        }
    }
    
    private Median getMedian(List<Integer> sortedArrayLeft, List<Integer> sortedArrayRight, SingleMedian medianLeft, SingleMedian medianRight) {
        int valueOfMedianLeft = medianLeft.getValue();
        int valueOfMedianRight = medianRight.getValue();

        if(valueOfMedianLeft == valueOfMedianRight) {
            return new DoubleMedian(-1, valueOfMedianLeft, -1, valueOfMedianRight);
        }
        
        if(sortedArrayLeft.size() == 1 && sortedArrayRight.size() == 1) {
            return new DoubleMedian(-1, Math.min(valueOfMedianLeft, valueOfMedianRight), -1, Math.max(valueOfMedianLeft, valueOfMedianRight));
        }

        if(sortedArrayLeft.size() == 1) { // here, the sortedArrayRight.size must be >= 3
            int valueBelowMedianRight = sortedArrayRight.get(medianRight.getIndex() - 1);
            int valueAboveMedianRight = sortedArrayRight.get(medianRight.getIndex() + 1);
            List<Integer> limitSizedList = new ArrayList<Integer>(); // we will sort this list with size = 4
            limitSizedList.add(valueOfMedianLeft);
            limitSizedList.add(valueBelowMedianRight);
            limitSizedList.add(valueOfMedianRight);
            limitSizedList.add(valueAboveMedianRight);

            return getMedianWithUnsortedArray(limitSizedList);
        }
       
        // sortedArrayLeft.size >= 3 
        if(valueOfMedianLeft < valueOfMedianRight) {
            List<Integer> childSortedArrayLeft = sortedArrayLeft.subList(medianLeft.getIndex(), sortedArrayLeft.size());
            List<Integer> childSortedArrayRight = sortedArrayRight.subList(0, sortedArrayRight.size() - medianLeft.getIndex());
            
            return getMedian(childSortedArrayLeft, childSortedArrayRight);
        }
        else { // valueOfMedianLeft > valueOfMedianRight
            List<Integer> childSortedArrayLeft = sortedArrayLeft.subList(0, medianLeft.getIndex() + 1);
            List<Integer> childSortedArrayRight = sortedArrayRight.subList(medianLeft.getIndex(), sortedArrayRight.size());
            
            return getMedian(childSortedArrayLeft, childSortedArrayRight);
        }
    }
    
    private Median getMedian(List<Integer> sortedArrayLeft, List<Integer> sortedArrayRight, SingleMedian medianLeft, DoubleMedian medianRight) {
        int valueOfMedianLeft = medianLeft.getValue();
        int valueBelowMedianRight = medianRight.getValue1();
        int valueAboveMedianRight = medianRight.getValue2();

        if(valueOfMedianLeft >= valueBelowMedianRight && valueOfMedianLeft <= valueAboveMedianRight) {
            return new SingleMedian(-1, valueOfMedianLeft);
        }
         
        if(sortedArrayLeft.size() == 1) {
            List<Integer> limitSizedList = new ArrayList<Integer>(); // we will sort the list with size = 3
            limitSizedList.add(valueOfMedianLeft);
            limitSizedList.add(valueBelowMedianRight);
            limitSizedList.add(valueAboveMedianRight);

            return getMedianWithUnsortedArray(limitSizedList);
        }

        if(valueOfMedianLeft < valueBelowMedianRight) {
            List<Integer> childSortedArrayLeft = sortedArrayLeft.subList(medianLeft.getIndex(), sortedArrayLeft.size());
            List<Integer> childSortedArrayRight = sortedArrayRight.subList(0, sortedArrayRight.size() - medianLeft.getIndex());

            return getMedian(childSortedArrayLeft, childSortedArrayRight);
        }
        else { // it must be the case that valueOfMedianLeft > valueOfAboveMedianRight
            List<Integer> childSortedArrayLeft = sortedArrayLeft.subList(0, medianLeft.getIndex() + 1);
            List<Integer> childSortedArrayRight = sortedArrayRight.subList(medianLeft.getIndex(), sortedArrayRight.size());

            return getMedian(childSortedArrayLeft, childSortedArrayRight);
        }
    }
     
    private Median getMedian(List<Integer> sortedArrayLeft, List<Integer> sortedArrayRight, DoubleMedian medianLeft, SingleMedian medianRight) {
        int valueBelowMedianLeft = medianLeft.getValue1();
        int valueAboveMedianLeft = medianLeft.getValue2();
        int valueOfMedianRight = medianRight.getValue();
        int valueBelowMedianRight = sortedArrayRight.get(medianRight.getIndex() - 1); // because sortedArrayLeft.size <= sortedArrayRight.size, we can know for 
                                                                                 // sure that sortedArrayRight.size >= 3 in this case, that means
                                                                                 // medianRight.index >= 1 at least
        int valueAboveMedianRight = sortedArrayRight.get(medianRight.getIndex() + 1);

        if(valueBelowMedianLeft <= valueOfMedianRight && valueAboveMedianLeft >= valueOfMedianRight) {
            return new SingleMedian(-1, valueOfMedianRight);
        }

        if(sortedArrayLeft.size() == 2) {
            List<Integer> limitSizedList = new ArrayList<Integer>();  // we will sort the list with size = 5
            limitSizedList.add(valueBelowMedianLeft);
            limitSizedList.add(valueAboveMedianLeft);
            limitSizedList.add(valueBelowMedianRight);
            limitSizedList.add(valueOfMedianRight);
            limitSizedList.add(valueAboveMedianRight);

            return getMedianWithUnsortedArray(limitSizedList);
        }

        if(valueAboveMedianLeft < valueOfMedianRight) {
            List<Integer> childSortedArrayLeft = sortedArrayLeft.subList(medianLeft.getIndex2(), sortedArrayLeft.size());
            List<Integer> childSortedArrayRight = sortedArrayRight.subList(0, sortedArrayRight.size() - medianLeft.getIndex2());

            return getMedian(childSortedArrayLeft, childSortedArrayRight);
        }
        else { // it must be case that valueBelowMedianLeft > valueOfMedianRight
            List<Integer> childSortedArrayLeft = sortedArrayLeft.subList(0, medianLeft.getIndex1() + 1);
            List<Integer> childSortedArrayRight = sortedArrayRight.subList(medianLeft.getIndex2(), sortedArrayRight.size());

            return getMedian(childSortedArrayLeft, childSortedArrayRight);
        }
    }
    
    private Median getMedian(List<Integer> sortedArrayLeft, List<Integer> sortedArrayRight, DoubleMedian medianLeft, DoubleMedian medianRight) {
        int valueBelowMedianLeft = medianLeft.getValue1();
        int valueAboveMedianLeft = medianLeft.getValue2();
        int valueBelowMedianRight = medianRight.getValue1();
        int valueAboveMedianRight = medianRight.getValue2();

        if((!(valueAboveMedianLeft <= valueBelowMedianRight) && !(valueBelowMedianLeft >= valueAboveMedianRight))
             || (sortedArrayLeft.size() == 2 && sortedArrayRight.size() == 2)) {
            List<Integer> limitSizedList = new ArrayList<Integer>(); // we will sort this list with size = 4
            limitSizedList.add(valueBelowMedianLeft);
            limitSizedList.add(valueAboveMedianLeft);
            limitSizedList.add(valueBelowMedianRight);
            limitSizedList.add(valueAboveMedianRight);

            return getMedianWithUnsortedArray(limitSizedList);
        }
        

        if(sortedArrayLeft.size() == 2) { // it must be case that sortedArrayRight.size >= 4
            if(valueAboveMedianLeft <= valueBelowMedianRight) {
                return new DoubleMedian(-1, Math.max(valueAboveMedianLeft, sortedArrayRight.get(medianRight.getIndex1() - 1)), -1, valueBelowMedianRight);
            }
            else { // it must be case that valueBelowMedianLeft >= valueAboveMedianRight
                return new DoubleMedian(-1, valueAboveMedianRight, -1, Math.min(valueBelowMedianLeft, sortedArrayRight.get(medianRight.getIndex2() + 1)));
            }
        }
        
        if(valueAboveMedianLeft <= valueBelowMedianRight) {
            List<Integer> childSortedArrayLeft = sortedArrayLeft.subList(medianLeft.getIndex2(), sortedArrayLeft.size());
            List<Integer> childSortedArrayRight = sortedArrayRight.subList(0, sortedArrayRight.size() - medianLeft.getIndex2());

            return getMedian(childSortedArrayLeft, childSortedArrayRight);
        }
        else { // it must be case that valueBelowMedianLeft >= valueAboveMedianRight
            List<Integer> childSortedArrayLeft = sortedArrayLeft.subList(0, medianLeft.getIndex1() + 1);
            List<Integer> childSortedArrayRight = sortedArrayRight.subList(medianLeft.getIndex2(), sortedArrayRight.size());

            return getMedian(childSortedArrayLeft, childSortedArrayRight);
        }
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
        return (number % 2) == 0;
    }
         
    /*
     * get Median of one array
     */
    private Median getMedian(List<Integer> sortedArray) {
        if(isEven(sortedArray.size())) {
            return new DoubleMedian((sortedArray.size() / 2) - 1, sortedArray.get((sortedArray.size() / 2) - 1), sortedArray.size() / 2, sortedArray.get(sortedArray.size() / 2));
        } else {
            return new SingleMedian(sortedArray.size() / 2, sortedArray.get(sortedArray.size() / 2));
        }
    }

    /*
     * this method use sort. it means that the runing time should be O(size*log(size))
     * but for the quick solution method. it only use this method for limit sized such as 3,4 or 5.
     */
    private Median getMedianWithUnsortedArray(List<Integer> array) {
        Collections.sort(array, new Comparator<Integer>() {
            public int compare(Integer number1, Integer number2) {
                return number1.compareTo(number2);
            }
        });

        // now array is soted
        return getMedian(array);
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
    private int index;
    private int value;

    public SingleMedian(int idx, int val) {
        index = idx;
        value = val;
    }

    public int getIndex() {
        return index;
    }

    public int getValue() {
        return value;
    }
   
    @Override 
    public String toString() {
        return String.valueOf(value);
    }
    
    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        
        if(!(obj instanceof SingleMedian)) {
            return false;
        }
        
        return value == ((SingleMedian)obj).getValue(); // no need to care the index
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}

class DoubleMedian implements Median {
    private int index1;
    private int value1;
    
    private int index2;
    private int value2;

    public DoubleMedian(int idx1, int val1, int idx2, int val2) {
        index1 = idx1;
        value1 = val1;
        index2 = idx2;
        value2 = val2;
    }

    public int getIndex1() {
        return index1;
    }

    public int getValue1() {
        return value1;
    }

    public int getIndex2() {
        return index2;
    }

    public int getValue2() {
        return value2;
    }
    
    @Override
    public String toString() {
        return value1 + ", " + value2;
    }
    
    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        
        if(!(obj instanceof DoubleMedian)) {
            return false;
        }
        
        return (value1 == ((DoubleMedian)obj).getValue1()) && (value2 == ((DoubleMedian)obj).getValue2());
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}

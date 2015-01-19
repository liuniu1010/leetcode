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
     * the logic is a bit complicated
     */
    public Median getMedianWithQuickSolution(List<Integer> sortedArray1, List<Integer> sortedArray2) {
        this.assertInput(sortedArray1, sortedArray2);
        
        /*
         * prepare to call private method getMedian(List<Integer>, List<Integer>)
         * we should make sure the first argument1's size is less than or equals
         * to argument2's size. this is the assumption of the called method
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
        else {
            return getMedian(sortedArrayLeft, sortedArrayRight, (DoubleMedian)medianLeft, (DoubleMedian)medianRight);
        }
    }
    
    private Median getMedian(List<Integer> sortedArrayLeft, List<Integer> sortedArrayRight, SingleMedian medianLeft, SingleMedian medianRight) {
        if(medianLeft.equals(medianRight)) {
            DoubleMedian resultMedian = new DoubleMedian();
            resultMedian.value1 = medianLeft.value;
            resultMedian.value2 = medianLeft.value;
            
            return resultMedian;
        }
        
        if(sortedArrayLeft.size() == 1) {
            /*
             *  the sortedArrayRight.size must greater than or equals to 3
             *  or else, the medianLeft would be equal to medianRight
             */
            int valueOfMedianLeft = sortedArrayLeft.get(0);
            
            int valueAboveMedianRight = sortedArrayRight.get(medianRight.index + 1);
            int valueOfMedianRight = sortedArrayRight.get(medianRight.index);
            int valueBelowMedianRight = sortedArrayRight.get(medianRight.index - 1);
            
            if(valueOfMedianLeft <= valueBelowMedianRight) {
                DoubleMedian resultMedian = new DoubleMedian();
                resultMedian.value1 = valueBelowMedianRight;
                resultMedian.value2 = valueOfMedianRight;
                
                return resultMedian;
            }
            else if(valueOfMedianLeft > valueBelowMedianRight && valueOfMedianLeft < valueOfMedianRight) {
                DoubleMedian resultMedian = new DoubleMedian();
                resultMedian.value1 = valueOfMedianLeft;
                resultMedian.value2 = valueOfMedianRight;
                
                return resultMedian;
            }
            else if(valueOfMedianLeft > valueOfMedianRight && valueOfMedianLeft < valueAboveMedianRight) {
                DoubleMedian resultMedian = new DoubleMedian();
                resultMedian.value1 = valueOfMedianRight;
                resultMedian.value2 = valueOfMedianLeft;
                
                return resultMedian;
            }
            else { // it must be this case that valueOfMedianLeft >= valueAboveMedianRight
                DoubleMedian resultMedian = new DoubleMedian();
                resultMedian.value1 = valueOfMedianRight;
                resultMedian.value2 = valueAboveMedianRight;
                
                return resultMedian;
            }
        }
        
        if(medianLeft.value < medianRight.value) {
            List<Integer> childSortedArrayLeft = sortedArrayLeft.subList(medianLeft.index, sortedArrayLeft.size() - 1);
            List<Integer> childSortedArrayRight = sortedArrayRight.subList(0, (sortedArrayRight.size() - medianLeft.index) - 1);
            
            return getMedian(childSortedArrayLeft, childSortedArrayRight);
        }
        else {
            List<Integer> childSortedArrayLeft = sortedArrayLeft.subList(0, medianLeft.index);
            List<Integer> childSortedArrayRight = sortedArrayRight.subList((sortedArrayRight.size() - medianLeft.index) - 1, sortedArrayRight.size() - 1);
            
            return getMedian(childSortedArrayLeft, childSortedArrayRight);
        }
    }
    
    private Median getMedian(List<Integer> sortedArrayLeft, List<Integer> sortedArrayRight, SingleMedian medianLeft, DoubleMedian medianRight) {
        // to be implemented
        return null;
    }
    
    private Median getMedian(List<Integer> sortedArrayLeft, List<Integer> sortedArrayRight, DoubleMedian medianLeft, SingleMedian medianRight) {
        // to be implemented
        return null;
    }
    
    private Median getMedian(List<Integer> sortedArrayLeft, List<Integer> sortedArrayRight, DoubleMedian medianLeft, DoubleMedian medianRight) {
        if(medianLeft.equals(medianRight)) {
            DoubleMedian resultMedian = new DoubleMedian();
            resultMedian.value1 = medianLeft.value1;
            resultMedian.value2 = medianLeft.value2;
            
            return resultMedian;
        }
        
        // to be implemented
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
        return (number % 2) == 0;
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
    
    public String toString() {
        return String.valueOf(value);
    }
    
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        
        if(!(obj instanceof SingleMedian)) {
            return false;
        }
        
        return (value == ((SingleMedian)obj).value); // no need to care the index
    }
}

class DoubleMedian implements Median {
    public int index1;
    public int value1;
    
    public int index2;
    public int value2;
    
    public String toString() {
        return String.valueOf(value1) + ", " + String.valueOf(value2);
    }
    
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        
        if(!(obj instanceof DoubleMedian)) {
            return false;
        }
        
        return (value1 == ((DoubleMedian)obj).value1) && (value2 == ((DoubleMedian)obj).value2);
    }
}
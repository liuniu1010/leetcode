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
    private MedianOfTwoSortedArrays() {
    }

    private static MedianOfTwoSortedArrays instance = new MedianOfTwoSortedArrays();

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
     * the logic is a bit complicated
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
        int valueOfMedianLeft = medianLeft.value;
        int valueOfMedianRight = medianRight.value;

        if(valueOfMedianLeft == valueOfMedianRight) {
            DoubleMedian resultMedian = new DoubleMedian();
            resultMedian.value1 = valueOfMedianLeft;
            resultMedian.value2 = valueOfMedianRight;
            
            return resultMedian;
        }
        
        if(sortedArrayLeft.size() == 1 && sortedArrayRight.size() == 1) {
            DoubleMedian resultMedian = new DoubleMedian();
            resultMedian.value1 = Math.min(valueOfMedianLeft, valueOfMedianRight);
            resultMedian.value2 = Math.max(valueOfMedianLeft, valueOfMedianRight);

            return resultMedian;
        }

        if(sortedArrayLeft.size() == 1) { // here, the sortedArrayRight.size must be >= 3
            int valueBelowMedianRight = sortedArrayRight.get(medianRight.index - 1);
            int valueAboveMedianRight = sortedArrayRight.get(medianRight.index + 1);
            List<Integer> limitSizedList = new ArrayList<Integer>(); // we will sort this list with size = 4
            limitSizedList.add(valueOfMedianLeft);
            limitSizedList.add(valueBelowMedianRight);
            limitSizedList.add(valueOfMedianRight);
            limitSizedList.add(valueAboveMedianRight);

            return getMedianWithUnsortedArray(limitSizedList);
        }
       
        // sortedArrayLeft.size >= 3 
        if(valueOfMedianLeft < valueOfMedianRight) {
            List<Integer> childSortedArrayLeft = sortedArrayLeft.subList(medianLeft.index, sortedArrayLeft.size());
            List<Integer> childSortedArrayRight = sortedArrayRight.subList(0, sortedArrayRight.size() - medianLeft.index);
            
            return getMedian(childSortedArrayLeft, childSortedArrayRight);
        }
        else { // valueOfMedianLeft > valueOfMedianRight
            List<Integer> childSortedArrayLeft = sortedArrayLeft.subList(0, medianLeft.index + 1);
            List<Integer> childSortedArrayRight = sortedArrayRight.subList(medianLeft.index, sortedArrayRight.size());
            
            return getMedian(childSortedArrayLeft, childSortedArrayRight);
        }
    }
    
    private Median getMedian(List<Integer> sortedArrayLeft, List<Integer> sortedArrayRight, SingleMedian medianLeft, DoubleMedian medianRight) {
        int valueOfMedianLeft = medianLeft.value;
        int valueBelowMedianRight = medianRight.value1;
        int valueAboveMedianRight = medianRight.value2;

        if(valueOfMedianLeft >= valueBelowMedianRight && valueOfMedianLeft <= valueAboveMedianRight) {
            SingleMedian resultMedian = new SingleMedian();
            resultMedian.value = valueOfMedianLeft;

            return resultMedian;
        }
         
        if(sortedArrayLeft.size() == 1) {
            List<Integer> limitSizedList = new ArrayList<Integer>(); // we will sort the list with size = 3
            limitSizedList.add(valueOfMedianLeft);
            limitSizedList.add(valueBelowMedianRight);
            limitSizedList.add(valueAboveMedianRight);

            return getMedianWithUnsortedArray(limitSizedList);
        }

        if(valueOfMedianLeft < valueBelowMedianRight) {
            List<Integer> childSortedArrayLeft = sortedArrayLeft.subList(medianLeft.index, sortedArrayLeft.size());
            List<Integer> childSortedArrayRight = sortedArrayRight.subList(0, sortedArrayRight.size() - medianLeft.index);

            return getMedian(childSortedArrayLeft, childSortedArrayRight);
        }
        else { // it must be the case that valueOfMedianLeft > valueOfAboveMedianRight
            List<Integer> childSortedArrayLeft = sortedArrayLeft.subList(0, medianLeft.index + 1);
            List<Integer> childSortedArrayRight = sortedArrayRight.subList(medianLeft.index, sortedArrayRight.size());

            return getMedian(childSortedArrayLeft, childSortedArrayRight);
        }
    }
     
    private Median getMedian(List<Integer> sortedArrayLeft, List<Integer> sortedArrayRight, DoubleMedian medianLeft, SingleMedian medianRight) {
        int valueBelowMedianLeft = medianLeft.value1;
        int valueAboveMedianLeft = medianLeft.value2;
        int valueOfMedianRight = medianRight.value;
        int valueBelowMedianRight = sortedArrayRight.get(medianRight.index - 1); // because sortedArrayLeft.size <= sortedArrayRight.size, we can know for 
                                                                                 // sure that sortedArrayRight.size >= 3 in this case, that means
                                                                                 // medianRight.index >= 1 at least
        int valueAboveMedianRight = sortedArrayRight.get(medianRight.index + 1);

        if(valueBelowMedianLeft <= valueOfMedianRight && valueAboveMedianLeft >= valueOfMedianRight) {
            SingleMedian resultMedian = new SingleMedian();
            resultMedian.value = valueOfMedianRight;

            return resultMedian;
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
            List<Integer> childSortedArrayLeft = sortedArrayLeft.subList(medianLeft.index2, sortedArrayLeft.size());
            List<Integer> childSortedArrayRight = sortedArrayRight.subList(0, sortedArrayRight.size() - medianLeft.index2);

            return getMedian(childSortedArrayLeft, childSortedArrayRight);
        }
        else { // it must be case that valueBelowMedianLeft > valueOfMedianRight
            List<Integer> childSortedArrayLeft = sortedArrayLeft.subList(0, medianLeft.index1 + 1);
            List<Integer> childSortedArrayRight = sortedArrayRight.subList(medianLeft.index2, sortedArrayRight.size());

            return getMedian(childSortedArrayLeft, childSortedArrayRight);
        }
    }
    
    private Median getMedian(List<Integer> sortedArrayLeft, List<Integer> sortedArrayRight, DoubleMedian medianLeft, DoubleMedian medianRight) {
        int valueBelowMedianLeft = medianLeft.value1;
        int valueAboveMedianLeft = medianLeft.value2;
        int valueBelowMedianRight = medianRight.value1;
        int valueAboveMedianRight = medianRight.value2;

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
                DoubleMedian resultMedian = new DoubleMedian();
                resultMedian.value1 = Math.max(valueAboveMedianLeft, sortedArrayRight.get(medianRight.index1 - 1));
                resultMedian.value2 = valueBelowMedianRight;

                return resultMedian;
            }
            else { // it must be case that valueBelowMedianLeft >= valueAboveMedianRight
                DoubleMedian resultMedian = new DoubleMedian();
                resultMedian.value1 = valueAboveMedianRight;
                resultMedian.value2 = Math.min(valueBelowMedianLeft, sortedArrayRight.get(medianRight.index2 + 1));

                return resultMedian;
            }
        }
        
        if(valueAboveMedianLeft <= valueBelowMedianRight) {
            List<Integer> childSortedArrayLeft = sortedArrayLeft.subList(medianLeft.index2, sortedArrayLeft.size());
            List<Integer> childSortedArrayRight = sortedArrayRight.subList(0, sortedArrayRight.size() - medianLeft.index2);

            return getMedian(childSortedArrayLeft, childSortedArrayRight);
        }
        else { // it must be case that valueBelowMedianLeft >= valueAboveMedianRight
            List<Integer> childSortedArrayLeft = sortedArrayLeft.subList(0, medianLeft.index1 + 1);
            List<Integer> childSortedArrayRight = sortedArrayRight.subList(medianLeft.index2, sortedArrayRight.size());

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

    /*
     * this method use sort. it means that the runing time should be O(size*log(size))
     * but for the quick solution method. it only use this method for limit sized such as 3,4 or 5.
     */
    private Median getMedianWithUnsortedArray(List<Integer> array) {
        array.sort(new Comparator<Integer>() {
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
    public int index;
    public int value;
   
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
        
        return (value == ((SingleMedian)obj).value); // no need to care the index
    }
}

class DoubleMedian implements Median {
    public int index1;
    public int value1;
    
    public int index2;
    public int value2;
    
    @Override
    public String toString() {
        return String.valueOf(value1) + ", " + String.valueOf(value2);
    }
    
    @Override
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

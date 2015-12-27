package org.kelly.leetcode.util;

import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;

public class Doublet {
    private int num1;
    private int num2;

    public Doublet(int number1, int number2) {
        // the input numbers might not be sorted, so 
        // here we sorted it first
        List<Integer> array = new ArrayList<Integer>();
        array.add(number1);
        array.add(number2);

        Collections.sort(array, new Comparator<Integer>() {
            public int compare(Integer number1, Integer number2) {
                return number1.compareTo(number2);
            }
        });

        // then set the sorted numbers to member variables to make
        // sure that num1 <= num2 <= num3
        num1 = array.get(0);
        num2 = array.get(1);
    }

    public int getNum1() {
        return num1;
    }

    public int getNum2() {
        return num2;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }

        if(!(obj instanceof Doublet)) {
            return false;
        }

        Doublet doublet = (Doublet)obj;
        return (num1 == doublet.getNum1()) && (num2 == doublet.getNum2());
    }

    public int sum() {
        return num1 + num2;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "[" + num1 + ", " + num2 + "]";
    }
}

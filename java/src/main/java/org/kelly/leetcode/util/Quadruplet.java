package org.kelly.leetcode.util;

import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;

public class Quadruplet implements Comparable<Quadruplet>{
    private int num1;
    private int num2;
    private int num3;
    private int num4;

    public Quadruplet(int number1, int number2, int number3, int number4) {
        // the input numbers might not be sorted, so 
        // here we sorted it first
        List<Integer> array = new ArrayList<Integer>();
        array.add(number1);
        array.add(number2);
        array.add(number3);
        array.add(number4);

        Collections.sort(array, new Comparator<Integer>() {
            public int compare(Integer number1, Integer number2) {
                return number1.compareTo(number2);
            }
        });

        // then set the sorted numbers to member variables to make
        // sure that num1 <= num2 <= num3 <= num4
        num1 = array.get(0);
        num2 = array.get(1);
        num3 = array.get(2);
        num4 = array.get(3);
    }

    public int getNum1() {
        return num1;
    }

    public int getNum2() {
        return num2;
    }

    public int getNum3() {
        return num3;
    }

    public int getNum4() {
        return num4;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }

        if(!(obj instanceof Quadruplet)) {
            return false;
        }

        Quadruplet quadruplet = (Quadruplet)obj;
        return (num1 == quadruplet.getNum1()) && (num2 == quadruplet.getNum2()) && (num3 == quadruplet.getNum3()) && (num4 == quadruplet.getNum4());
    }

    public int sum() {
        return num1 + num2 + num3 + num4;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "[" + num1 + ", " + num2 + ", " + num3 + ", " + num4 + "]";
    }

    public int compareTo(Quadruplet quadruplet) {
        int compareOfnum = Integer.valueOf(num1).compareTo(quadruplet.getNum1());
        if(compareOfnum != 0) {
            return compareOfnum;
        }
        
        compareOfnum = Integer.valueOf(num2).compareTo(quadruplet.getNum2());
        if(compareOfnum != 0) {
            return compareOfnum;
        }
        
        compareOfnum = Integer.valueOf(num3).compareTo(quadruplet.getNum3());
        if(compareOfnum != 0) {
            return compareOfnum;
        }
        
        return Integer.valueOf(num4).compareTo(quadruplet.getNum4());
    }
}

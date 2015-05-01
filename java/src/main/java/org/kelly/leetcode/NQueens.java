/***
 * source: https://oj.leetcode.com/problemset/algorithms/
 * Author: Liu, Niu <liuniu@tsinghua.org.cn>
 */

package org.kelly.leetcode;

import java.util.List;
import java.util.ArrayList;

import org.kelly.leetcode.exception.InvalidInputException;

/***
 * Given an integer n, return all distinct solutions to the n-queens puzzle.
 * Each solution contains a distinct board configuration of the n-queens' placement, 
 * where 'Q' and '+' both indicate a queen and an empty space respectively.
 *
 * @Author Liu, Niu
 */
public class NQueens {
    private static NQueens instance = new NQueens();

    private NQueens() {
    }


    public static NQueens getInstance() {
        return instance;
    }

    private void assertInput(int N) {
        if(N <= 0) {
            throw new InvalidInputException("input N cannot be less than or equals to zero!");
        }
    }

    private boolean isConflict(CoordinateTwo coord1, CoordinateTwo coord2) {
        if(coord1.getX() == coord2.getX()) {
            return true;
        }

        if(coord1.getY() == coord2.getY()) {
            return true;
        }

        if(Math.abs(coord1.getX() - coord2.getX()) == Math.abs(coord1.getY() - coord2.getY())) {
            return true;
        }

        return false;
    }

    private boolean isConflict(CoordinateTwo newCoord, List<CoordinateTwo>coords) {
        for(CoordinateTwo coord: coords) {
            if(isConflict(coord, newCoord)) {
                return true;
            }
        }

        return false;
    }

    /***
     * we can use backtracking algorithm to solve the NQueens problem
     * the running time should be O(N^N)
     */
    public List<List<CoordinateTwo>> solveNQueens(int N) {
        assertInput(N);

        List<List<CoordinateTwo>> solvedResults = new ArrayList<List<CoordinateTwo>>();
        List<CoordinateTwo> partialResult = new ArrayList<CoordinateTwo>();
        solveNQueen(N, solvedResults, partialResult);

        return solvedResults;
    }

    /***
     * recursively call this method to backtracking solve NQueens
     */
    private void solveNQueen(int N, List<List<CoordinateTwo>> solvedResults, List<CoordinateTwo> partialResult) {
        int partialSize = partialResult.size();
        if(partialSize < N) {
            for(int i = 0;i < N;i++) {
                CoordinateTwo newCoordinate = new CoordinateTwo(i, partialSize);
                if(isConflict(newCoordinate, partialResult)) {
                   continue; 
                }
                partialResult.add(newCoordinate);
                solveNQueen(N, solvedResults, partialResult);
                partialResult.remove(newCoordinate);
            }
            return;
        }

        List<CoordinateTwo> cloneResult = new ArrayList<CoordinateTwo>();
        cloneResult.addAll(partialResult);
        solvedResults.add(cloneResult);
    }

    public void printNQueens(List<List<CoordinateTwo>> solvedResults) {
        System.out.println("\n");
        for(List<CoordinateTwo> solvedResult: solvedResults) {
            printNQueen(solvedResult);
        }
    }

    public void printNQueen(List<CoordinateTwo> solvedResult) {
        System.out.println("****************************************");
        for(CoordinateTwo coord: solvedResult) {
            String line = "";
            for(int i = 0;i < solvedResult.size();i++) {
                if(coord.getX() == i) {
                    line += " Q ";
                }
                else {
                    line += " + ";
                }
            }
            System.out.println(line);
        }
    }
}

// 2-dimension coordinate
class CoordinateTwo {
    private int x;
    private int y;

    public int getX() {
        return x;
    }

    public void setX(int xValue) {
        x = xValue;
    }

    public int getY() {
        return y;
    }

    public void setY(int yValue) {
        y = yValue;
    }

    public CoordinateTwo() {
        x = -1;
        y = -1;
    }

    public CoordinateTwo(int xValue, int yValue) {
        x = xValue;
        y = yValue;
    }
}

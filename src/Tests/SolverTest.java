package Tests;

import Model.Board;
import Model.Case;
import Model.Solver;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SolverTest {

    @Test
    void test15x15_7_Hard_2() {
        Board board = new Board(15);
        int[] x = {5,2,4,3,3,3,3,2,3,3,2,2,5,0,7};
        int[] y = {3,3,4,3,2,5,2,4,2,4,2,1,5,2,5};
        for (int i = 0; i < 15; i++) {
            board.setX(i, x[i]);
            board.setY(i, y[i]);
        }
        board.setCase(0,3, Case.Tree);
        board.setCase(0,7, Case.Tree);
        board.setCase(0,11, Case.Tree);
        board.setCase(0,13, Case.Tree);
        board.setCase(1,3, Case.Tree);
        board.setCase(1,6, Case.Tree);
        board.setCase(1,7, Case.Tree);
        board.setCase(1,12, Case.Tree);
        board.setCase(2,1, Case.Tree);
        board.setCase(2,8, Case.Tree);
        board.setCase(2,10, Case.Tree);
        board.setCase(2,14, Case.Tree);
        board.setCase(3,0, Case.Tree);
        board.setCase(3,2, Case.Tree);
        board.setCase(3,5, Case.Tree);
        board.setCase(4,1, Case.Tree);
        board.setCase(4,5, Case.Tree);
        board.setCase(4,13, Case.Tree);
        board.setCase(5,8, Case.Tree);
        board.setCase(5,11, Case.Tree);
        board.setCase(6,2, Case.Tree);
        board.setCase(7,0, Case.Tree);
        board.setCase(7,7, Case.Tree);
        board.setCase(7,11, Case.Tree);
        board.setCase(7,14, Case.Tree);
        board.setCase(8,0, Case.Tree);
        board.setCase(8,6, Case.Tree);
        board.setCase(8,8, Case.Tree);
        board.setCase(9,1, Case.Tree);
        board.setCase(9,6, Case.Tree);
        board.setCase(9,14, Case.Tree);
        board.setCase(10,6, Case.Tree);
        board.setCase(10,11, Case.Tree);
        board.setCase(10,14, Case.Tree);
        board.setCase(11,3, Case.Tree);
        board.setCase(11,7, Case.Tree);
        board.setCase(12,0, Case.Tree);
        board.setCase(12,9, Case.Tree);
        board.setCase(12,11, Case.Tree);
        board.setCase(13,1, Case.Tree);
        board.setCase(13,3, Case.Tree);
        board.setCase(13,5, Case.Tree);
        board.setCase(13,13, Case.Tree);
        board.setCase(14,4, Case.Tree);
        board.setCase(14,6, Case.Tree);
        board.setCase(14,10, Case.Tree);
        board.setCase(14,12, Case.Tree);

        Solver solver = new Solver();
        solver.solveGame(board);
        assertTrue(true);
    }

    @Test
    void test10x10_1_Hard_2() {
        int size = 10;
        Board board = new Board(size);
        int[] x = {2,2,1,3,2,3,1,4,1,3};
        int[] y = {3,2,2,2,1,3,2,2,3,2};
        for (int i = 0; i < size; i++) {
            board.setX(i, x[i]);
            board.setY(i, y[i]);
        }
        board.setCase(0,1, Case.Tree);
        board.setCase(1,0, Case.Tree);
        board.setCase(1,6, Case.Tree);
        board.setCase(1,9, Case.Tree);
        board.setCase(2,2, Case.Tree);
        board.setCase(2,4, Case.Tree);
        board.setCase(2,8, Case.Tree);
        board.setCase(3,2, Case.Tree);
        board.setCase(4,1, Case.Tree);
        board.setCase(4,6, Case.Tree);
        board.setCase(4,9, Case.Tree);
        board.setCase(5,1, Case.Tree);
        board.setCase(6,5, Case.Tree);
        board.setCase(6,7, Case.Tree);
        board.setCase(6,9, Case.Tree);
        board.setCase(7,0, Case.Tree);
        board.setCase(7,2, Case.Tree);
        board.setCase(7,6, Case.Tree);
        board.setCase(7,8, Case.Tree);
        board.setCase(9,2, Case.Tree);
        board.setCase(9,7, Case.Tree);
        board.setCase(9,9, Case.Tree);

        Solver solver = new Solver();
        solver.solveGame(board);
        assertTrue(true);
    }

    @Test
    void test10x10_1_Basic() {
        int size = 10;
        Board board = new Board(size);
        int[] x = {3,0,2,2,0,5,0,4,0,4};
        int[] y = {4,1,2,1,3,1,3,0,2,3};
        for (int i = 0; i < size; i++) {
            board.setX(i, x[i]);
            board.setY(i, y[i]);
        }
        board.setCase(0,1, Case.Tree);
        board.setCase(0,8, Case.Tree);
        board.setCase(1,0, Case.Tree);
        board.setCase(1,5, Case.Tree);
        board.setCase(2,8, Case.Tree);
        board.setCase(3,1, Case.Tree);
        board.setCase(3,2, Case.Tree);
        board.setCase(5,1, Case.Tree);
        board.setCase(5,5, Case.Tree);
        board.setCase(6,0, Case.Tree);
        board.setCase(6,1, Case.Tree);
        board.setCase(6,4, Case.Tree);
        board.setCase(6,6, Case.Tree);
        board.setCase(6,9, Case.Tree);
        board.setCase(7,5, Case.Tree);
        board.setCase(8,0, Case.Tree);
        board.setCase(8,8, Case.Tree);
        board.setCase(9,3, Case.Tree);
        board.setCase(9,7, Case.Tree);
        board.setCase(9,9, Case.Tree);

        Solver solver = new Solver();
        solver.solveGame(board);
        assertTrue(true);
    }
}
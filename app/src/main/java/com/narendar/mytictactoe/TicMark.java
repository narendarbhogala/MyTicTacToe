package com.narendar.mytictactoe;

public class TicMark {

    public final int a, b;

    public TicMark(int x, int y) {
        if (0 <= x && x <= 2 && 0 <= y && y <= 2) {
            a = x;
            b = y;
        } else {
            throw new UnsupportedOperationException("x and y must lie between 0 and 2 (inclusive)");
        }
    }

    @Override
    public String toString() {
        return "(" + a + ", " + b + ")";
    }
}

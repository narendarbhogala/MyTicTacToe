package com.narendar.mytictactoe;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TicTacToeGame {

    private static final String TAG = TicTacToeGame.class.getName();

    public static final int USER_GAME = 1;
    public static final int SYSTEM_GAME = 2;

    private int[][] mBoard = new int[3][3];

    private List<ScoresBoard> rootsChildrenScores;

    public boolean isOver() {
        return (isUserWon() || isSystemWon() || getAvailableStates().isEmpty());
    }

    public int getBoardItem(TicMark ticMark) {
        return mBoard[ticMark.a][ticMark.b];
    }

    //TO get to know User has won it or  not.
    public boolean isUserWon() {
        //Log.i(TAG,"isUserWon()");
        if ((mBoard[0][0] == mBoard[1][1] && mBoard[0][0] == mBoard[2][2] && mBoard[0][0] == USER_GAME)
                || (mBoard[0][2] == mBoard[1][1] && mBoard[0][2] == mBoard[2][0] && mBoard[0][2] == USER_GAME)) {
            return true;
        }
        for (int i = 0; i < 3; ++i) {
            if (((mBoard[i][0] == mBoard[i][1] && mBoard[i][0] == mBoard[i][2] && mBoard[i][0] == USER_GAME)
                    || (mBoard[0][i] == mBoard[1][i] && mBoard[0][i] == mBoard[2][i] && mBoard[0][i] == USER_GAME))) {
                return true;
            }
        }
        return false;
    }


    //TO get to know System has won it or  not.
    public boolean isSystemWon() {
      //  Log.i(TAG,"isSystemWon()");
        if ((mBoard[0][0] == mBoard[1][1] && mBoard[0][0] == mBoard[2][2] && mBoard[0][0] == SYSTEM_GAME) ||
                (mBoard[0][2] == mBoard[1][1] && mBoard[0][2] == mBoard[2][0] && mBoard[0][2] == SYSTEM_GAME)) {
            return true;
        }
        for (int i = 0; i < 3; ++i) {
            if ((mBoard[i][0] == mBoard[i][1] && mBoard[i][0] == mBoard[i][2] && mBoard[i][0] == SYSTEM_GAME)
                    || (mBoard[0][i] == mBoard[1][i] && mBoard[0][i] == mBoard[2][i] && mBoard[0][i] == SYSTEM_GAME)) {
                return true;
            }
        }

        return false;
    }

    //TO get the available state from current game.
    private List<TicMark> getAvailableStates() {
        List<TicMark> availableTicMarks = new ArrayList<>();
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                if (mBoard[i][j] == 0) {
                    availableTicMarks.add(new TicMark(i, j));
                }
            }
        }
        return availableTicMarks;
    }

    public TicTacToeGame(int firstPlayer) {
        if (firstPlayer == USER_GAME) {
            Random mRandom = new Random();
            placeMove(new TicMark(mRandom.nextInt(3), mRandom.nextInt(3)), USER_GAME);
        }
    }

    public void play(TicMark ticMark) {
        placeMove(ticMark, SYSTEM_GAME);
        if (!isOver()) {
            playPC();
        }
    }

    private void placeMove(TicMark ticMark, int player) {
        if (mBoard[ticMark.a][ticMark.b] == 0) {
            mBoard[ticMark.a][ticMark.b] = player;
        } else {
            throw new UnsupportedOperationException("Illegal move");
        }
    }

    private TicMark returnBestMove() {
        int MAX = -100000;
        int best = -1;

        for (int i = 0; i < rootsChildrenScores.size(); ++i) {
            if (MAX < rootsChildrenScores.get(i).score) {
                MAX = rootsChildrenScores.get(i).score;
                best = i;
            }
        }

        return rootsChildrenScores.get(best).ticMark;
    }

    private int returnMin(List<Integer> list) {
        int min = Integer.MAX_VALUE;
        int index = -1;
        for (int i = 0; i < list.size(); ++i) {
            if (list.get(i) < min) {
                min = list.get(i);
                index = i;
            }
        }
        return list.get(index);
    }

    private int returnMax(List<Integer> list) {
        int max = Integer.MIN_VALUE;
        int index = -1;
        for (int i = 0; i < list.size(); ++i) {
            if (list.get(i) > max) {
                max = list.get(i);
                index = i;
            }
        }
        return list.get(index);
    }

    private void playPC() {
        rootsChildrenScores = new ArrayList<>();
        minimax(0, USER_GAME);
        placeMove(returnBestMove(), USER_GAME);
    }

    private int minimax(int depth, int turn) {

        if (isUserWon()) return +1;
        if (isSystemWon()) return -1;

        List<TicMark> pointsAvailable = getAvailableStates();
        if (pointsAvailable.isEmpty()) return 0;

        List<Integer> scores = new ArrayList<>();

        for (int i = 0; i < pointsAvailable.size(); ++i) {
            TicMark ticMark = pointsAvailable.get(i);

            if (turn == USER_GAME) {
                placeMove(ticMark, USER_GAME);
                int currentScore = minimax(depth + 1, SYSTEM_GAME);
                scores.add(currentScore);

                if (depth == 0)
                    rootsChildrenScores.add(new ScoresBoard(currentScore, ticMark));

            } else if (turn == SYSTEM_GAME) {
                placeMove(ticMark, SYSTEM_GAME);
                scores.add(minimax(depth + 1, USER_GAME));
            }
            mBoard[ticMark.a][ticMark.b] = 0;
        }
        return turn == 1 ? returnMax(scores) : returnMin(scores);
    }

    public int[][] getmBoard() {
        return mBoard;
    }

    public static TicMark posToPoint(int pos) {
        return new TicMark(pos / 3, pos - 3 * (pos / 3));
    }
}

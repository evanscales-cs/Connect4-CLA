package matrix;

import java.util.Arrays;

import constants.Const;

public class Board {
    
    public final Character[][] board = new Character[Const.BOARD_DIM][Const.BOARD_DIM];
    public final int[] piecesInColumn = new int[Const.BOARD_DIM];

    public Board() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] =  '0';
            }
        }
        Arrays.fill(piecesInColumn, 0);
    }

    public Board(final Board copyBoard) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = copyBoard.board[i][j];
            }
        }
    }

    /**
     * Finds the lowest playable row for a given game column
     * @param column - column searching for a playable row in
     * @return row index [between 0 and board.length - 1] if playable, else
     * returns -1 if all rows are filled.
     */
    public int getPlayableRow(final int column) {
        if (piecesInColumn[column] != 7 ) {
            return piecesInColumn[column]++;
        }
        return -1;
    }

    public boolean playColumn(final int column, final char color) {
        int rowPlayed = getPlayableRow(column);

        if (rowPlayed != -1) {
            board[rowPlayed][column] = color;
            return true;
        }
        return false;
    }

    /**
     * row
     *  6   |0|0|0|0|0|0|0|
     *  5   |0|0|0|0|0|0|0|
     *  4   |0|0|0|0|0|0|0|
     *  3   |0|0|0|0|0|0|0|
     *  2   |0|0|0|0|0|0|0|
     *  1   |0|0|0|0|0|0|0|
     *  0   |0|0|0|0|0|0|0|
     *      ---------------
     * col  -0-1-2-3-4-5-6
     */

    public String toString() {
        String visualBoard = "";
        for (int i = 0; i < Const.BOARD_DIM; i++) {
            visualBoard += "-" + (i+1);
        }
        visualBoard += "-\n";

        for (int i = board.length-1; i >= 0; i--) {
            for (Character c : board[i]) {

                visualBoard += "|" + c;
            }
            visualBoard += "|\n";
        }

        for (int i = 0; i < Const.BOARD_DIM * 2 + 1; i++) {
            visualBoard +="-";
        }

        return visualBoard;
    }
}



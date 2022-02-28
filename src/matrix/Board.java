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

    public boolean checkWin() {

        // Vertical Check
        for (int col = 0; col < Const.BOARD_DIM; col++) {
            int matchCount = 1;
            for (int row = 0; row < Const.BOARD_DIM - 1; row++) {
                if (board[row][col] == '0') {
                    break;
                }
                if (board[row][col] == board[row+1][col]) {
                    matchCount++;
                }
                else {
                    matchCount = 1;
                }

                if (matchCount == Const.TO_WIN) {
                    return true;
                }
            }
        }

        // Horizontal Check
        for (int row = 0; row < Const.BOARD_DIM; row++) {
            int matchCount = 1;
            for (int col = 0; col < Const.BOARD_DIM-1; col++) {
                if (board[row][col] == '0' || board[row][col] != board[row][col+1]) {
                    matchCount = 1;
                    continue;
                }
                else if (board[row][col] == board[row][col+1]) {
                    matchCount++;
                }
                
                if (Const.TO_WIN - matchCount > Const.BOARD_DIM - col - 1) {
                    break;
                }

                if (matchCount == Const.TO_WIN) {
                    return true;
                }
            }
        }
        
        // Descending Diagonal
        for (int rowStart = Const.TO_WIN - 1; rowStart < Const.BOARD_DIM; rowStart++) {
            int matchCount = 1;
            int col = 0;
            for (int row = rowStart; row > 0 && col < Const.BOARD_DIM-1; col++, row--) {
                if (board[row][col] != '0' && board[row][col] == board[row-1][col+1]) {
                    matchCount++;
                    if (matchCount == Const.TO_WIN) {
                        return true;
                    }
                }
                else {
                    matchCount = 1;
                }
            }
        }

        for (int colStart = 1; colStart <= Const.BOARD_DIM - Const.TO_WIN; colStart++) {
            int matchCount = 1;
            int row = 0;
            for (int col = colStart; row > 0 && col < Const.BOARD_DIM-1; col++, row--) {
                if (board[row][col] != '0' && board[row][col] == board[row-1][col+1]) {
                    matchCount++;
                    if (matchCount == Const.TO_WIN) {
                        return true;
                    }
                }
                else {
                    matchCount = 1;
                }
            }
        }

        // Ascending Diagonal
        for (int rowStart = 0; rowStart <= Const.BOARD_DIM - Const.TO_WIN; rowStart++) {
            int matchCount = 1;
            int col = 0;
            for (int row = rowStart; row < Const.BOARD_DIM-1 && col < Const.BOARD_DIM-1; row++, col++) {
                if (board[row][col] != '0' && board[row][col] == board[row+1][col+1]) {
                    matchCount++;
                    if (matchCount == Const.TO_WIN) {
                        return true;
                    }
                }
                else {
                    matchCount = 1;
                }
            }
        }

        for (int colStart = 1; colStart <= Const.BOARD_DIM - Const.TO_WIN; colStart++) {
            int matchCount = 1;
            int row = 0;
            for (int col = colStart; row < Const.BOARD_DIM-1 && col < Const.BOARD_DIM-1; row++, col++) {
                if (board[row][col] != '0' && board[row][col] == board[row+1][col+1]) {
                    matchCount++;
                    if (matchCount == Const.TO_WIN) {
                        return true;
                    }
                }
                else {
                    matchCount = 1;
                }
            }
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

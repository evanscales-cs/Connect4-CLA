package matrix;

import java.util.Arrays;

import constants.Const;

public class Board {

    public final Character[][] board = new Character[Const.BOARD_DIM][Const.BOARD_DIM];
    public final int[] piecesInColumn = new int[Const.BOARD_DIM];

    /**
     * Creates a new game board containing all '0' characters to indicate an
     * empty board.
     */
    public Board() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = '0';
            }
        }
        Arrays.fill(piecesInColumn, 0);
    }

    /**
     * Creates a copy of a game board from an excisting game board object
     * @param copyBoard - Game board that will be copied to the new Board object
     */
    public Board(final Board copyBoard) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = copyBoard.board[i][j];
            }
        }
    }

    /**
     * Finds the lowest playable row for a given game column
     * 
     * @param column - column searching for a playable row in
     * @return row index [between 0 and board.length - 1] if playable, else
     *         returns -1 if all rows are filled.
     */
    public int getPlayableRow(final int column) {
        if (piecesInColumn[column] != 7) {
            return piecesInColumn[column]++;
        }
        return -1;
    }

    public boolean playColumn(final int column, final char color) {
        int rowPlayed = getPlayableRow(column);

        if (rowPlayed != -1) {
            board[rowPlayed][column] = color;
            checkWin(rowPlayed, column);
            return true;
        }
        return false;
    }

    public boolean playPiece(final int row, final int column, final char color) {
        board[row][column] = color;
        return checkWin(row, column);
    }

    /**
     * Checks if the max number of pieces connected in a row to the last piece
     * placed meets or exceeds the number in a row needed to win
     * 
     * @param row - row indes of the last placed game piece
     * @param col - column index of the last placed game piece
     * @return true if the number of pieces conneced in a row meets or exceeds
     *         the number needed to win, otherwise returns false
     */
    public boolean checkWin(final int row, final int col) {
        int connected = checkPiecesConnected(row, col);
        if (connected >= Const.TO_WIN) {
            return true;
        }
        return false;
    }

    /**
     * Iteratively finds the maximum number of same-color pieces connected in a 
     * single direction. These pieces will all be connected to the last piece 
     * placed on the board.
     * 
     * @param row row index of the last placed game piece
     * @param col column index of the last placed game piece
     * @return maxiumn number of same-color pieces connected to the placed piece
     *         in one particular direction.
     */
    public int checkPiecesConnected(final int row, final int col) {

        char currColor = board[row][col];

        boolean down = true, left = true, right = true;
        boolean rightAscend = true, leftAscend = true, leftDescend = true, rightDescend = true;

        int diagAscendCount = 1;
        int diagDescendCount = 1;
        int horizCount = 1;
        int vertCount = 1;

        for (int i = 1 ; i < Const.TO_WIN; i++) {

            // Vertical Check
            if (down && row - i >= 0 ) {
                if (currColor == board[row - i][col]) {
                    vertCount++;
                    if (vertCount >= Const.TO_WIN) {
                        return vertCount;
                    }
                }
                else {
                    down = false;
                }
            }

            // Left Horizontal Check
            if (left && col - i >= 0) {
                if (currColor == board[row][col - i]) {
                    horizCount++;
                    if (horizCount == Const.TO_WIN) {
                        return horizCount;
                    }
                }
                else left = false;
            }

            // Right Horizontal Check
            if (right && col + i < Const.BOARD_DIM) {
                if (currColor == board[row][col + i]) {
                    horizCount++;
                    if (horizCount == Const.TO_WIN) {
                        return horizCount;
                    }
                }
                else right = false;
            }

            // Left Descend Check
            if (leftDescend && row + i < Const.BOARD_DIM && col - i >= 0) {
                if (currColor == board[row + i][col - i]) {
                    diagDescendCount++;
                    if(diagDescendCount == Const.TO_WIN) { 
                        return diagDescendCount;
                    }
                }
                else leftDescend = false;
            }

            // Right Descend Check
            if (rightDescend && row - i >= 0 && col + i < Const.BOARD_DIM) {
                if (currColor == board[row - i][col + i]) {
                    diagDescendCount++;
                    if(diagDescendCount == Const.TO_WIN) { 
                        return diagDescendCount;
                    }
                }
                else rightDescend = false;
            }

            // Right Ascend Check
            if (rightAscend && col + i < Const.BOARD_DIM && row + i < Const.BOARD_DIM) {
                if (currColor == board[col + i][row + i]) {
                    diagAscendCount++;
                    if(diagAscendCount == Const.TO_WIN) {
                        return diagAscendCount;
                    }
                }
                else rightAscend = false;
            }

            // Left Ascend Check  
            if (leftAscend && col - i >= 0 && row - i >= 0) {
                if (currColor == board[col - i][row - i]) {
                    diagAscendCount++;
                    if(diagAscendCount == Const.TO_WIN) {
                        return diagAscendCount;
                    }
                }
                else leftAscend = false;
            }
        }

        return 0;
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
     *  col -0-1-2-3-4-5-6-
     * 
     * Creates a string represenation of the game board 2D array. This board
     * has its (0, 0) origin in the bottom left corner.
     * 
     * @return a string representation of the game board and all pieces existing
     *         on it.
     */
    public String toString() {
        String visualBoard = "";
        for (int i = 0; i < Const.BOARD_DIM; i++) {
            visualBoard += "-" + (i + 1);
        }
        visualBoard += "-\n";

        for (int i = board.length - 1; i >= 0; i--) {
            for (Character c : board[i]) {

                visualBoard += "|" + c;
            }
            visualBoard += "|\n";
        }

        for (int i = 0; i < Const.BOARD_DIM * 2 + 1; i++) {
            visualBoard += "-";
        }

        return visualBoard;
    }
}

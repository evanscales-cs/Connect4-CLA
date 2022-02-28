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
        int connected = checkPiecesConnected(row, col, 0, 0);
        if (connected >= Const.TO_WIN) {
            return true;
        }
        return false;
    }

    /**
     * -4 1 2
     * -3 X 3
     * -2-1 4
     * Finds the maximum number of same-color pieces connected in a single
     * direction. these pieces will all be connected to the last piece placed
     * on the board.
     * 
     * @param row row index of the last placed game piece
     * @param col column index of the last placed game piece
     * @param dir direction of the recursive checks
     * @return maxiumn number of same-color pieces connected to the placed piece
     *         in one particular direction.
     */
    public int checkPiecesConnected(final int row, final int col, final int dir,
                                    final int matchLen) {
        if (dir == 0) {
            int diagAscendCount = 1;
            int diagDescendCount = 1;
            int horizCount = 1;
            int vertCount = 1;

            // Vertical Branch
            if (row >= Const.TO_WIN - 1 && board[row][col] == board[row - 1][col]) {
                int connected = checkPiecesConnected(row - 1, col, -1, matchLen + 1);
                vertCount += connected;
            }

            // Ascending Diagonal Branch
            if (row < Const.BOARD_DIM - 1 && col < Const.BOARD_DIM - 1
                    && board[row][col] == board[row + 1][col + 1]) {
                diagAscendCount += checkPiecesConnected(row + 1, col + 1, 2, matchLen + 1);
            }

            if (row > 0 && col > 0 && board[row][col] == board[row - 1][col - 1]) {
                diagAscendCount += checkPiecesConnected(row - 1, col - 1, -2, matchLen + 1);
            }

            // Horizontal Branch
            if (col < Const.BOARD_DIM - 1 && board[row][col] == board[row][col + 1]) {
                horizCount += checkPiecesConnected(row, col + 1, 3, matchLen + 1);
            }

            if (col > 0 && board[row][col] == board[row][col - 1]) {
                horizCount += checkPiecesConnected(row, col - 1, -3, matchLen + 1);
            }

            // Descending Diagonal Branch
            if (row < Const.BOARD_DIM - 1 && col > 0
                    && board[row][col] == board[row + 1][col - 1]) {
                diagDescendCount += checkPiecesConnected(row + 1, col - 1, 4, matchLen + 1);
            }

            if (row > 0 && col < Const.BOARD_DIM - 1
                    && board[row][col] == board[row - 1][col + 1]) {
                diagDescendCount += checkPiecesConnected(row - 1, col + 1, -4, matchLen + 1);
            }

            return Math.max(Math.max(vertCount, horizCount),
                    Math.max(diagAscendCount, diagDescendCount));
        }

        // Vertical Branch
        if (dir == -1 && row > 0 && board[row][col] == board[row - 1][col]) {
            return checkPiecesConnected(row - 1, col, -1, matchLen + 1);
        }

        // Ascending Diagonal Branch
        if (dir == 2 && row < Const.BOARD_DIM - 1 && col < Const.BOARD_DIM - 1
                && board[row][col] == board[row + 1][col + 1]) {
            return checkPiecesConnected(row + 1, col + 1, 2, matchLen + 1);
        }

        if (dir == -2 && row > 0 && col > 0 && board[row][col] == board[row - 1][col - 1]) {
            return checkPiecesConnected(row - 1, col - 1, -2, matchLen + 1);
        }

        // Horizontal Branch
        if (dir == 3 && col < Const.BOARD_DIM - 1 && board[row][col] == board[row][col + 1]) {
            return checkPiecesConnected(row, col + 1, 3, matchLen + 1);
        }

        if (dir == -3 && col > 0 && board[row][col] == board[row][col - 1]) {
            return checkPiecesConnected(row, col - 1, -3, matchLen + 1);
        }

        // Descending Diagonal Branch
        if (dir == 4 && row < Const.BOARD_DIM - 1 && col > 0
                && board[row][col] == board[row + 1][col - 1]) {
            return checkPiecesConnected(row + 1, col - 1, 4, matchLen + 1);
        }

        if (dir == -4 && row > 0 && col < Const.BOARD_DIM - 1
                && board[row][col] == board[row - 1][col + 1]) {
            return checkPiecesConnected(row - 1, col + 1, -4, matchLen + 1);
        }

        return matchLen;
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

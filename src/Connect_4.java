import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import constants.Const;
import matrix.Board;

public class Connect_4 {
    public static void main(final String[] args) {
        Board b = new Board();
        Scanner userInput = new Scanner(System.in);

        int currPlayer = 0;
        int playerColumnMove = 0;
        boolean fullColumnMove = false;
        boolean errorMove = false;
        boolean invalidCol = false;
        boolean gameOver = false;
        String errRead = "";

        while (true) {
            System.out.println(b.toString());

            if (fullColumnMove) {
                System.out.printf("Column %d is filled\n", playerColumnMove);
                fullColumnMove = false;
            }
            if (errorMove) {
                System.out.printf("%s is not a valid column number\n", errRead);
                errorMove = false;
            }
            if (invalidCol) {
                System.out.printf("%d is not a valid column number\n", playerColumnMove);
                invalidCol = false;
            }
            if (gameOver) {
                System.out.println("Player " + Const.PLAYERS[currPlayer] + " Wins!");
                break;
            }

            System.out.println("Player " + Const.PLAYERS[currPlayer] +
                    " pick a column!");

            try {
                playerColumnMove = userInput.nextInt();
                int rowPlayed = -1;
                if (playerColumnMove > Const.BOARD_DIM || playerColumnMove <= 0) {
                    invalidCol = true;
                }
                // A Playable row is found and used
                else if ((rowPlayed = b.getPlayableRow(playerColumnMove - 1)) != -1) {
                    gameOver = b.playPiece(rowPlayed, playerColumnMove - 1,
                            Const.PLAYER_SYMBOLS[currPlayer]);
                    if (gameOver) {
                        continue;
                    }
                    currPlayer = (currPlayer + 1) % Const.PLAYERS.length;
                }
                // If a playable row is unavailable an error msg is shown
                else {
                    fullColumnMove = true;
                }
            }

            catch (InputMismatchException e) {
                errRead = userInput.nextLine();
                errorMove = true;
            } catch (NoSuchElementException e) {
                System.out.println("CLOSING...");
                break;
            }
        }
        userInput.close();
    }
}

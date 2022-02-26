import java.io.EOFException;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import constants.Const;
import matrix.Board;

public class Connect_4 {
    public static void main(String[] args) {
        Board b = new Board();

        Scanner userInput = new Scanner(System.in);

        int currPlayer = 0;
        int playerMove = 0;
        boolean fullColumnMove = false;
        boolean errorMove = false;
        boolean invalidCol = false;

        String errRead = "";
        while(true) {
            System.out.println(b.toString());

            if (fullColumnMove) {
                System.out.printf("Column %d is filled\n", playerMove);
                fullColumnMove = false;
            }
            if (errorMove) {
                System.out.printf("%s is not a valid column number\n", errRead);
                errorMove = false;
            }
            if (invalidCol) {
                System.out.printf("%d is not a valid column number\n", playerMove);
                invalidCol = false;
            }

            System.out.println("Player " + Const.PLAYERS[currPlayer] +
                                " pick a column!");
            
            try {
                playerMove = userInput.nextInt();

                if (playerMove > 7 || playerMove <= 0) {
                    invalidCol = true;
                    errRead = "";
                }
                else if (b.playColumn(playerMove - 1, Const.PLAYER_SYMBOLS[currPlayer])) {
                    currPlayer = (currPlayer + 1) % Const.PLAYERS.length;
                }
                else {
                    fullColumnMove = true;
                }
            }

            catch (InputMismatchException e) {
                errRead = userInput.nextLine();
                errorMove = true;
            }
            catch (NoSuchElementException e) {
                System.out.println("CLOSING...");
                break;
            }

        }
        userInput.close();

    }
}

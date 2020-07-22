package tictactoe;

/**
 * @author DavidHurst
 */
public class Board implements Cloneable {

    private final char[][] board;
    private char winningMark;
    private final int BOARD_WIDTH = 3;
    private boolean crossTurn, gameOver;

    /**
     * Construct the game board.
     */
    public Board() {
        board = new char[BOARD_WIDTH][BOARD_WIDTH];
        crossTurn = true;
        gameOver = false;
        winningMark = ' ';
        initialiseBoard();
    }

    /**
     * Assign all board positions to be empty and add all positions to the
     * available moves set.
     */
    private void initialiseBoard() {
        for (int row = 0; row < BOARD_WIDTH; row++) {
            for (int col = 0; col < BOARD_WIDTH; col++) {
                board[row][col] = ' ';
            }
        }
    }

    /**
     * Attempt to place a mark on the given coordinate, placed alternating marks
     * and check if move has won the game.
     *
     * @param row row coordinate to try to place mark
     * @param col column coordinate to try place the mark
     * @return true if mark was placed successfully
     */
    public boolean placeMark(int row, int col) {
        if (row < 0 || row >= BOARD_WIDTH || col < 0 || col >= BOARD_WIDTH
                || board[row][col] != ' ' || gameOver) {
            return false;
        }
        board[row][col] = crossTurn ? 'X' : 'O';
        checkWin(row, col);
        togglePlayer();
        return true;
    }

    /**
     * Checks row and column mark was placed in for game win, if no win there,
     * checks diagonals.
     *
     * @param row row coordinate to check
     * @param col column coordinate to check
     */
    private void checkWin(int row, int col) {
        int checkSum = 0;
        // Check row for winner.
        for (int c = 0; c < BOARD_WIDTH; c++) {
            checkSum += board[row][c];
        }
        if (calcWinner(checkSum) != ' ') {
            winningMark = calcWinner(checkSum);
            System.out.println(winningMark + " wins on row " + row);
            return;
        }

        // Check column for winner.
        checkSum = 0;
        for (int r = 0; r < BOARD_WIDTH; r++) {
            checkSum += board[r][col];
        }
        if (calcWinner(checkSum) != ' ') {
            winningMark = calcWinner(checkSum);
            System.out.println(winningMark + " wins on column " + col);
            return;
        }

        // Check diagonals for winner.
        checkSum = 0;
        if (row == col) { // Top-left to bottom-right diagonal.
            for (int index = 0; index < BOARD_WIDTH; index++) {
                checkSum += board[index][index];
            }
        } else { // Top-right to bottom-left diagonal.
            int indexMax = BOARD_WIDTH - 1;
            for (int index = 0; index <= indexMax; index++) {
                checkSum += board[index][indexMax - index];
            }
        }
        if (calcWinner(checkSum) != ' ') {
            winningMark = calcWinner(checkSum);
            System.out.println(winningMark + " wins on the diagonal");
        }
    }

    /**
     * Compares summed value of row/column/diagonal to value required for a win.
     *
     * @param checkSum summed value to check
     * @return mark that won or blank space if no win calculated
     */
    private char calcWinner(int checkSum) {
        int Xwin = 'X' * BOARD_WIDTH;
        int Owin = 'O' * BOARD_WIDTH;
        if (checkSum == Xwin) {
            gameOver = true;
            return 'X';
        } else if (checkSum == Owin) {
            gameOver = true;
            return 'O';
        }
        return ' ';
    }

    /**
     * Toggles which player's turn it is (nought or cross).
     */
    private void togglePlayer() {
        crossTurn = !crossTurn;
    }

    @Override
    public String toString() {
        StringBuilder strBldr = new StringBuilder();
        for (char[] row : board) {
            for (char ch : row) {
                strBldr.append(ch).append(' ');
            }
            strBldr.append("\n");
        }
        return strBldr.toString();
    }

    public boolean isCrossTurn() {
        return crossTurn;
    }

    public char[][] getBoard() {
        return board;
    }

    public int getBOARD_WIDTH() {
        return BOARD_WIDTH;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public char getWinningMark() {
        return winningMark;
    }
}

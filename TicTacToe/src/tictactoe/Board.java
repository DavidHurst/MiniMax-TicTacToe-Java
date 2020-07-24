package tictactoe;

/**
 * @author DavidHurst
 */
public class Board implements Cloneable {

    private final char[][] board;
    private char winningMark;
    private final int BOARD_WIDTH = 3;
    private boolean crossTurn, gameOver;

    public Board() {
        board = new char[BOARD_WIDTH][BOARD_WIDTH];
        crossTurn = true;
        gameOver = false;
        winningMark = ' ';
        initialiseBoard();
    }

    private void initialiseBoard() {
        for (int row = 0; row < BOARD_WIDTH; row++) {
            for (int col = 0; col < BOARD_WIDTH; col++) {
                board[row][col] = ' ';
            }
        }
    }

    public boolean placeMark(int row, int col) {
        if (row < 0 || row >= BOARD_WIDTH || col < 0 || col >= BOARD_WIDTH
                || board[row][col] != ' ' || gameOver) {
            return false;
        }

        board[row][col] = crossTurn ? 'X' : 'O';
        togglePlayer();
        checkWin(row, col);
        return true;
    }

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

        // Top-left to bottom-right diagonal.
        checkSum = 0;
        for (int index = 0; index < BOARD_WIDTH; index++) {
            checkSum += board[index][index];
        }
        if (calcWinner(checkSum) != ' ') {
            winningMark = calcWinner(checkSum);
            System.out.println(winningMark + " wins on the diagonal");
            return;
        }
        
        // Top-right to bottom-left diagonal.
        checkSum = 0;
        int indexMax = BOARD_WIDTH - 1;
        for (int index = 0; index <= indexMax; index++) {
            checkSum += board[index][indexMax - index];
        }
        if (calcWinner(checkSum) != ' ') {
            winningMark = calcWinner(checkSum);
            System.out.println(winningMark + " wins on the diagonal");
            return;
        }

        if (!areMovesLeft()) {
            gameOver = true;
            System.out.println("Tie!");
        }
    }

    // Refactor to winner found, return true if winner found and use return to 
    // exit checkWin() and return the winningMark. Can maybe return where win 
    // was for CSS animation.
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

    public boolean areMovesLeft() {
        for (int row = 0; row < BOARD_WIDTH; row++) {
            for (int col = 0; col < BOARD_WIDTH; col++) {
                if (board[row][col] == ' ') {
                    return true;
                }
            }
        }
        return false;
    }

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

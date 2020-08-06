package Game;

/**
 * @author DavidHurst
 */
public class Board {

    private final Tile[][] board;
    private char winningMark;
    private final int BOARD_WIDTH = 3;
    private boolean crossTurn, gameOver;

    public Board() {
        board = new Tile[BOARD_WIDTH][BOARD_WIDTH];
        crossTurn = true;
        gameOver = false;
        winningMark = ' ';
        initialiseBoard();
    }

    private void initialiseBoard() {
        for (int row = 0; row < BOARD_WIDTH; row++) {
            for (int col = 0; col < BOARD_WIDTH; col++) {
                board[row][col] = new Tile(' ');
            }
        }
    }

    /**
     * Attempt to mark tile at the given coordinates if they are valid and it is 
     * possible to do so, toggle the player and check if the placing the mark 
     * has resulted in a win.
     * @param row Row coordinate to attempt to mark
     * @param col Column coordinate to attempt to mark
     * @return true if mark was placed successfully
     */
    public boolean placeMark(int row, int col) {
        if (row < 0 || row >= BOARD_WIDTH || col < 0 || col >= BOARD_WIDTH
                || isTileMarked(row, col) || gameOver) { 
            return false;
        }
        board[row][col].setMark(crossTurn ? 'X' : 'O');
        togglePlayer();
        checkWin(row, col);
        return true;
    }

    /**
     * Check row and column provided and diagonals for win.
     * @param row Row to check
     * @param col Column to check
     */
    private void checkWin(int row, int col) {
        int rowSum = 0;
        // Check row for winner.
        for (int c = 0; c < BOARD_WIDTH; c++) {
            rowSum += getMarkAt(row, c);
        }
        if (calcWinner(rowSum) != ' ') {
            System.out.println(winningMark + " wins on row " + row);
            return;
        }

        // Check column for winner.
        rowSum = 0;
        for (int r = 0; r < BOARD_WIDTH; r++) {
            rowSum += getMarkAt(r, col);
        }
        if (calcWinner(rowSum) != ' ') {
            System.out.println(winningMark + " wins on column " + col);
            return;
        }

        // Top-left to bottom-right diagonal.
        rowSum = 0;
        for (int i = 0; i < BOARD_WIDTH; i++) {
            rowSum += getMarkAt(i, i);
        }
        if (calcWinner(rowSum) != ' ') {
            System.out.println(winningMark + " wins on the top-left to "
                    + "bottom-right diagonal");
            return;
        }
        
        // Top-right to bottom-left diagonal.
        rowSum = 0;
        int indexMax = BOARD_WIDTH - 1;
        for (int i = 0; i <= indexMax; i++) {
            rowSum += getMarkAt(i, indexMax - i);
        }
        if (calcWinner(rowSum) != ' ') {
            System.out.println(winningMark + " wins on the top-right to "
                    + "bottom-left diagonal.");
            return;
        }

        if (!anyMovesAvailable()) {
            gameOver = true;
            System.out.println("Tie!");
        }
    }

    /**
     * Calculates if provided ASCII sum equates to a win for X or O.
     * @param rowSum Sum of characters' ASCII values in a row
     * @return char indicating which player won or a space character if neither
     */
    private char calcWinner(int rowSum) {
        int Xwin = 'X' * BOARD_WIDTH;
        int Owin = 'O' * BOARD_WIDTH;
        if (rowSum == Xwin) {
            gameOver = true;
            winningMark = 'X';
            return 'X';
        } else if (rowSum == Owin) {
            gameOver = true;
            winningMark = 'O';
            return 'O';
        }
        return ' ';
    }
    
    private void togglePlayer() {
        crossTurn = !crossTurn;
    }

    public boolean anyMovesAvailable() {
        for (int row = 0; row < BOARD_WIDTH; row++) {
            for (int col = 0; col < BOARD_WIDTH; col++) {
                if (!isTileMarked(row, col)) {
                    return true;
                }
            }
        }
        return false;
    }

    public char getMarkAt(int row, int column) {
        return board[row][column].getMark();
    }
    
    public boolean isTileMarked(int row, int column) {
        return board[row][column].isMarked();
    }
    
    public void setMarkAt(int row, int column, char newMark) {
        board[row][column].setMark(newMark);
    }

    @Override
    public String toString() {
        StringBuilder strBldr = new StringBuilder();
        for (Tile[] row : board) {
            for (Tile tile : row) {
                strBldr.append(tile.getMark()).append(' ');
            }
            strBldr.append("\n");
        }
        return strBldr.toString();
    }

    public boolean isCrossTurn() {
        return crossTurn;
    }

    public int getWidth() {
        return BOARD_WIDTH;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public char getWinningMark() {
        return winningMark;
    }
}

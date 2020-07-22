package tictactoe;

/**
 * @author DavidHurst
 */
public class MiniMax {

    /**
     * Utility class so no instantiation required.
     */
    private MiniMax() {
    }

    /**
     *
     * @param board
     * @param depth
     * @param maximisingPlayer
     * @return
     */
    public static int miniMax(Board board, int depth, boolean maximisingPlayer) {
        int boardVal = evaluateBoard(board);

        // Terminating node reached.
        if (Math.abs(boardVal) == 10) {
            return boardVal;
        }
        if (board.isGameOver()) {
            return 0;
        }

        // Maximising player, find the maximum attainable value.
        if (maximisingPlayer) {
            int best = Integer.MIN_VALUE;
            for (int row = 0; row < board.getBOARD_WIDTH(); row++) {
                for (int col = 0; col < board.getBOARD_WIDTH(); col++) {
                    if (board.getBoard()[row][col] == ' ') {
                        board.getBoard()[row][col] = 'X';
                        best = Math.max(best, miniMax(board, depth + 1,
                                false));
                        board.getBoard()[row][col] = ' ';
                    }
                }
            }
            return best;
            // Minimising player, find the minimum attainable value;
        } else {
            int best = Integer.MAX_VALUE;
            for (int row = 0; row < board.getBOARD_WIDTH(); row++) {
                for (int col = 0; col < board.getBOARD_WIDTH(); col++) {
                    if (board.getBoard()[row][col] == ' ') {
                        board.getBoard()[row][col] = 'O';
                        best = Math.min(best, miniMax(board, depth + 1,
                                true));
                        board.getBoard()[row][col] = ' ';
                    }
                }
            }
            return best;
        }
    }

    public static int[] getBestMove(Board board) throws CloneNotSupportedException {
        int[] bestMove = new int[]{-1, -1};
        int bestValue = Integer.MIN_VALUE;

        for (int row = 0; row < board.getBOARD_WIDTH(); row++) {
            for (int col = 0; col < board.getBOARD_WIDTH(); col++) {
                if (board.getBoard()[row][col] == ' ') {
                    board.getBoard()[row][col] = 'X';
                    int moveValue = miniMax(board, 0, false);
                    board.getBoard()[row][col] = ' ';
                    if (moveValue > bestValue) {
                        bestMove[0] = row;
                        bestMove[1] = col;
                        bestValue = moveValue;
                    }
                }
            }
        }
        return bestMove;
    }

    private static int evaluateBoard(Board board) {
        int bWidth = board.getBOARD_WIDTH();
        int Xwin = 'X' * bWidth;
        int Owin = 'O' * bWidth;
        int checkSum = 0;
        
        // Check rows for winner.
        for (int row = 0; row < bWidth; row++) {
            for (int col = 0; col < bWidth; col++) {
                checkSum += board.getBoard()[row][col];
            }
            if (checkSum == Xwin) {
                return 10;
            } else if (checkSum == Owin) {
                return -10;
            }
            checkSum = 0;
        }

        // Check columns for winner.
        checkSum = 0;
        for (int col = 0; col < bWidth; col++) {
            for (int row = 0; row < bWidth; row++) {
                checkSum += board.getBoard()[row][row];
            }
            if (checkSum == Xwin) {
                return 10;
            } else if (checkSum == Owin) {
                return -10;
            }
            checkSum = 0;
        }

        // Check diagonals.
        checkSum = 0;
        // Top-left to bottom-right diagonal.
        for (int i = 0; i < bWidth; i++) {
            checkSum += board.getBoard()[i][i];
        }
        if (checkSum == Xwin) {
            return 10;
        } else if (checkSum == Owin) {
            return -10;
        }
        
        checkSum = 0;
        // Top-right to bottom-left diagonal.
        int indexMax = bWidth - 1;
        for (int index = 0; index <= indexMax; index++) {
            checkSum += board.getBoard()[index][indexMax - index];
        }
        if (checkSum == Xwin) {
            return 10;
        } else if (checkSum == Owin) {
            return -10;
        }
        
        return 0;
    }
}

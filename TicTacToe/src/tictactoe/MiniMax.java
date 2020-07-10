package tictactoe;

/**
 * @author DavidHurst
 */
public class MiniMax {

    private static final int maxDepth = 8; // Maximum search depth.

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
        
        // Terminating node reached or game over, return board value.
        if (depth == maxDepth || board.isGameOver()) {
            return boardVal;
        }

        // Maximising player, find the maximum attainable value.
        if (maximisingPlayer) {
            int best = Integer.MIN_VALUE;
            for (int row = 0; row < board.getBOARD_WIDTH(); row++) {
                for (int col = 0; col < board.getBOARD_WIDTH(); col++) {
                    if (board.getBoard()[row][col] == ' ') {
                        board.placeMark(row, col);
                        best = Math.max(best, miniMax(board, depth + 1,
                                !maximisingPlayer));
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
                        board.placeMark(row, col);
                        best = Math.min(best, miniMax(board, depth + 1,
                                !maximisingPlayer));
                        board.getBoard()[row][col] = ' ';
                    }
                }
            }
            return best;
        }
    }

    public static int[] getBestMove(Board board) {
        int[] bestMove = new int[]{-1, -1};
        int bestValue = Integer.MIN_VALUE;

        for (int row = 0; row < board.getBOARD_WIDTH(); row++) {
            for (int col = 0; col < board.getBOARD_WIDTH(); col++) {
                if (board.getBoard()[row][col] == ' ') {
                    board.placeMark(row, col);
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
        if (board.isGameOver() && board.getWinningMark() == 'X') {
            return 10;
        } else if (board.isGameOver() && board.getWinningMark() == 'O') {
            return -10;
        }
        return 0;
    }
}

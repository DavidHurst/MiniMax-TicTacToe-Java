package tictactoe;

/**
 * @author DavidHurst
 */
public class MiniMax {

    private MiniMax() {
    }

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
                        best = Math.max(best, miniMax(board, depth + 1, false));
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
                        best = Math.min(best, miniMax(board, depth + 1, true));
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
        if (board.getWinningMark() == 'X') {
            return 10;
        } else if (board.getWinningMark() == 'O') {
            return -10;
        }
        return 0;
    }

}

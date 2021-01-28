package ai;

import game.Board;

import static game.Mark.*;

/**
 * The MiniMax algorithm in its most basic form.
 *
 * @author DavidHurst
 */
public class MiniMax {

    private static final int MAX_DEPTH = 6;

    private MiniMax() {
    }

    /**
     * Play moves on the board alternating between playing as X and O analysing 
     * the board each time to return the value of the highest value move for the
     * X player. Return the highest value move when a terminal node or the 
     * maximum search depth is reached.
     * @param board Board to play on and evaluate
     * @param depth The maximum depth of the game tree to search to
     * @param isMax Maximising or minimising player 
     * @return Value of the board 
     */
    public static int miniMax(Board board, int depth, boolean isMax) {
        int boardVal = evaluateBoard(board);

        // Terminal node (win/lose/draw) or max depth reached.
        if (Math.abs(boardVal) == 10 || depth == 0 
                || !board.anyMovesAvailable()) {
            return boardVal;
        }

        // Maximising player, find the maximum attainable value.
        if (isMax) {
            int highestVal = Integer.MIN_VALUE;
            for (int row = 0; row < board.getWidth(); row++) {
                for (int col = 0; col < board.getWidth(); col++) {
                    if (!board.isTileMarked(row, col)) {
                        board.setMarkAt(row, col, X);
                        highestVal = Math.max(highestVal, miniMax(board,
                                depth - 1, false));
                        board.setMarkAt(row, col, BLANK);
                    }
                }
            }
            return highestVal;
            // Minimising player, find the minimum attainable value;
        } else {
            int lowestVal = Integer.MAX_VALUE;
            for (int row = 0; row < board.getWidth(); row++) {
                for (int col = 0; col < board.getWidth(); col++) {
                    if (!board.isTileMarked(row, col)) {
                        board.setMarkAt(row, col, O);
                        lowestVal = Math.min(lowestVal, miniMax(board,
                                depth - 1, true));
                        board.setMarkAt(row, col, BLANK);
                    }
                }
            }
            return lowestVal;
        }
    }

    /**
     * Evaluate every legal move on the board and return the best one.
     * @param board Board to evaluate
     * @return Coordinates of best move
     */
    public static int[] getBestMove(Board board) {
        int[] bestMove = new int[]{-1, -1};
        int bestValue = Integer.MIN_VALUE;

        for (int row = 0; row < board.getWidth(); row++) {
            for (int col = 0; col < board.getWidth(); col++) {
                if (!board.isTileMarked(row, col)) {
                    board.setMarkAt(row, col, X);
                    int moveValue = miniMax(board, MAX_DEPTH, false);
                    board.setMarkAt(row, col, BLANK);
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

    /**
     * Evaluate the given board from the perspective of the X player, return 
     * 10 if a winning board configuration is found, -10 for a losing one and 0 
     * for a draw.
     * @param board Board to evaluate
     * @return value of the board
     */
    private static int evaluateBoard(Board board) {
        int rowSum = 0;
        int bWidth = board.getWidth();
        int Xwin = X.getMark() * bWidth;
        int Owin = O.getMark() * bWidth;

        // Check rows for winner.
        for (int row = 0; row < bWidth; row++) {
            for (int col = 0; col < bWidth; col++) {
                rowSum += board.getMarkAt(row, col).getMark();
            }
            if (rowSum == Xwin) {
                return 10;
            } else if (rowSum == Owin) {
                return -10;
            }
            rowSum = 0;
        }

        // Check columns for winner.
        rowSum = 0;
        for (int col = 0; col < bWidth; col++) {
            for (int row = 0; row < bWidth; row++) {
                rowSum += board.getMarkAt(row, col).getMark();
            }
            if (rowSum == Xwin) {
                return 10;
            } else if (rowSum == Owin) {
                return -10;
            }
            rowSum = 0;
        }

        // Check diagonals for winner.
        // Top-left to bottom-right diagonal.
        rowSum = 0;
        for (int i = 0; i < bWidth; i++) {
            rowSum += board.getMarkAt(i, i).getMark();
        }
        if (rowSum == Xwin) {
            return 10;
        } else if (rowSum == Owin) {
            return -10;
        }

        // Top-right to bottom-left diagonal.
        rowSum = 0;
        int indexMax = bWidth - 1;
        for (int i = 0; i <= indexMax; i++) {
            rowSum += board.getMarkAt(i, indexMax - i).getMark();
        }
        if (rowSum == Xwin) {
            return 10;
        } else if (rowSum == Owin) {
            return -10;
        }

        return 0;
    }

}

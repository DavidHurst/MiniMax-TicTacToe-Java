
package AI;

import Game.Board;

/**
 *
 * @author 198780
 */
public class MiniMaxAlphaBeta {
    
    private static final int MAX_DEPTH = 12;
    
    private MiniMaxAlphaBeta() {
    }

    public static int miniMax(Board board, int depth, int alpha, int beta, 
            boolean isMax) {
        int score = evaluateBoard(board);
        
        // Terminating node (win/lose board configuration) or max depth reached.
        if (Math.abs(score) == 10 || depth == 0) {
            return score;
        }
        if (!board.anyMovesAvailable()) {
            return 0;
        }

        // Maximising player, find the maximum attainable value.
        if (isMax) {
            int highestVal = Integer.MIN_VALUE;
            for (int row = 0; row < board.getWidth(); row++) {
                for (int col = 0; col < board.getWidth(); col++) {
                    if (!board.isTileMarked(row, col)) {
                        board.setMarkAt(row, col, 'X');
                        highestVal = Math.max(highestVal, miniMax(board, 
                                depth - 1, alpha, beta, false));
                        board.setMarkAt(row, col, ' ');
                        alpha = Math.max(alpha, highestVal);
                        if (alpha >= beta) {
                            return highestVal;
                        }
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
                        board.setMarkAt(row, col, 'O');
                        lowestVal = Math.min(lowestVal, miniMax(board, 
                                depth - 1, alpha, beta, true));
                        board.setMarkAt(row, col, ' ');
                        beta = Math.min(beta, lowestVal);
                        if(beta <= alpha) {
                            return lowestVal;
                        }
                    }
                }
            }
            return lowestVal;
        }
    }

    public static int[] getBestMove(Board board) {
        int[] bestMove = new int[]{-1, -1};
        int bestValue = Integer.MIN_VALUE;
        
        for (int row = 0; row < board.getWidth(); row++) {
            for (int col = 0; col < board.getWidth(); col++) {
                if (!board.isTileMarked(row, col)) {
                    board.setMarkAt(row, col, 'X');
                    int moveValue = miniMax(board, MAX_DEPTH, Integer.MIN_VALUE, 
                            Integer.MAX_VALUE, false);
                    board.setMarkAt(row, col, ' ');
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
        int checkSum = 0;
        int bWidth = board.getWidth();
        int Xwin = 'X' * bWidth;
        int Owin = 'O' * bWidth;

        // Check rows for winner.
        for (int row = 0; row < bWidth; row++) {
            for (int col = 0; col < bWidth; col++) {
                checkSum += board.getMarkAt(row, col);
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
                checkSum += board.getMarkAt(row, col);
            }
            if (checkSum == Xwin) {
                return 10;
            } else if (checkSum == Owin) {
                return -10;
            }
            checkSum = 0;
        }

        // Check diagonals for winner.
        // Top-left to bottom-right diagonal.
        checkSum = 0;
        for (int i = 0; i < bWidth; i++) {
            checkSum += board.getMarkAt(i, i);
        }
        if (checkSum == Xwin) {
            return 10;
        } else if (checkSum == Owin) {
            return -10;
        }

        // Top-right to bottom-left diagonal.
        checkSum = 0;
        int indexMax = bWidth - 1;
        for (int i = 0; i <= indexMax; i++) {
            checkSum += board.getMarkAt(i, indexMax - i);
        }
        if (checkSum == Xwin) {
            return 10;
        } else if (checkSum == Owin) {
            return -10;
        }
        
        return 0;
    }
}

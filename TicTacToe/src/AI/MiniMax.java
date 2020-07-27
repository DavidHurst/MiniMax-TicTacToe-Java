package AI;

import Game.Board;

/**
 * @author DavidHurst
 */
public class MiniMax {
    
    private static int MAX_DEPTH = 6;

    private MiniMax() {
    }

    public static int miniMax(Board board, int depth, boolean maximisingPlayer) {
        int score = evaluateBoard(board);
        
        // Terminating node (win/lose board configuration) or max depth reached.
        if (Math.abs(score) == 10 || depth >= MAX_DEPTH) {
            return score;
        }
        if (!board.areMovesLeft()) {
            return 0;
        }

        // Maximising player, find the maximum attainable value.
        if (maximisingPlayer) {
            int best = -1000;
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
            int best = 1000;
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
        int bestValue = -1000;
        
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

    private static int evaluateBoard(Board b) {
        int checkSum = 0;
        int bWidth = b.getBOARD_WIDTH();
        int Xwin = 'X' * bWidth;
        int Owin = 'O' * bWidth;

        // Check rows for winner.
        for (int row = 0; row < bWidth; row++) {
            for (int col = 0; col < bWidth; col++) {
                checkSum += b.getBoard()[row][col];
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
                checkSum += b.getBoard()[row][col];
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
        for (int index = 0; index < bWidth; index++) {
            checkSum += b.getBoard()[index][index];
        }
        if (checkSum == Xwin) {
            return 10;
        } else if (checkSum == Owin) {
            return -10;
        }

        // Top-right to bottom-left diagonal.
        checkSum = 0;
        int indexMax = bWidth - 1;
        for (int index = 0; index <= indexMax; index++) {
            checkSum += b.getBoard()[index][indexMax - index];
        }
        if (checkSum == Xwin) {
            return 10;
        } else if (checkSum == Owin) {
            return -10;
        }
        
        return 0;
    }

}

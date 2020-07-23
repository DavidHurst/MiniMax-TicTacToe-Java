package tictactoe;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 * @author DavidHurst
 */
public class TicTacToe extends Application {

    static GridPane gameBoard;
    static Board board;
    Button newGame;
    AnimationTimer gameTimer;

    public final static class Tile extends Button {

        private final int row;
        private final int col;
        private char mark;

        public Tile(int newRow, int newCol, char newMark) {
            row = newRow;
            col = newCol;
            mark = newMark;
            initialiseTile();
        }

        private void initialiseTile() {
            this.setOnMouseClicked(e -> {
                if (!board.isCrossTurn()) {
                    board.placeMark(row, col);
                    this.update();
                }
            });
            this.setStyle("-fx-font-size:70");
            this.setTextAlignment(TextAlignment.CENTER);
            this.setPrefSize(200.0, 200.0);
            this.setText("" + mark);
        }

        public void update() {
            this.mark = board.getBoard()[row][col];
            this.setText("" + mark);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        newGame = new Button("New Game");

        root.setCenter(intialiseGame());
        root.setTop(newGame);

        newGame.setOnMouseClicked(e -> {
            root.setCenter(intialiseGame());
            gameTimer.start();
        });

        Scene scene = new Scene(root);
        primaryStage.setTitle("Tic Tac Toe");
        primaryStage.setScene(scene);

        gameTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (board.isGameOver()) {
                    this.stop();
                    if (board.getWinningMark() == ' ') {
                        System.out.println("Tie!");
                    }
                } else {
                    if (board.isCrossTurn()) {
                        playAI();
                    }
                }
            }
        };
        gameTimer.start();

        primaryStage.show();
    }

    private static GridPane intialiseGame() {
        gameBoard = new GridPane();
        gameBoard.setAlignment(Pos.CENTER);
        gameBoard.setDisable(false);
        board = new Board();
        for (int row = 0; row < board.getBOARD_WIDTH(); row++) {
            for (int col = 0; col < board.getBOARD_WIDTH(); col++) {
                Tile tile = new Tile(row, col, board.getBoard()[row][col]);
                GridPane.setConstraints(tile, col, row);
                gameBoard.getChildren().add(tile);
            }
        }
        return gameBoard;
    }

    private static void playAI() {
        int[] move = MiniMax.getBestMove(board);
        int row = move[0];
        int col = move[1];
        board.placeMark(row, col);
        for (Node child : gameBoard.getChildren()) {
            if (gameBoard.getRowIndex(child) == row
                    && gameBoard.getColumnIndex(child) == col) {
                Tile t = (Tile) child;
                t.update();
            }
        }
        System.out.println("MiniMax placed mark at " + row + col);
    }

}

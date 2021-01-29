package game;

import ai.MiniMaxCombined;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 * @author DavidHurst
 */
public class TicTacToe extends Application {

    private static GridPane gameBoard;
    private static Board board;
    private AnimationTimer gameTimer;
    private MenuBar menuBar;
    private Menu gameMenu;
    private MenuItem newGameOption;
    private BorderPane root;

    /**
     * Objects of this class visually represent the tiles in the game and handle
     * user input.
     */
    public final static class Tile extends Button {

        private final int row;
        private final int col;
        private Mark mark;

        public Tile(int initRow, int initCol, Mark initMark) {
            row = initRow;
            col = initCol;
            mark = initMark;
            initialiseTile();
        }

        private void initialiseTile() {
            this.setOnMouseClicked(e -> {
                if (!board.isCrossTurn()) {
                    board.placeMark(this.row, this.col);
                    this.update();
                }
            });
            this.setStyle("-fx-font-size:70");
            this.setTextAlignment(TextAlignment.CENTER);
            this.setMinSize(150.0, 150.0);
            this.setText("" + this.mark);
        }

        /**
         * Retrieves state of tile from board which has the corresponding row 
         * and column coordinate and updates this object's text field with it.
         */
        public void update() {
            this.mark = board.getMarkAt(this.row, this.col);
            this.setText("" + mark);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        root = new BorderPane();

        root.setCenter(generateGUI());
        root.setTop(initialiseMenu());

        Scene scene = new Scene(root);
        primaryStage.setTitle("Tic Tac Toe");
        primaryStage.setScene(scene);

        runGameLoop();

        primaryStage.show();
    }

    /**
     * Fills and returns a GridPane with tiles, this GridPane is representative
     * of the game board.
     * @return the GridPane
     */
    private static GridPane generateGUI() {
        gameBoard = new GridPane();
        board = new Board();
        gameBoard.setAlignment(Pos.CENTER);
        
        for (int row = 0; row < board.getWidth(); row++) {
            for (int col = 0; col < board.getWidth(); col++) {
                Tile tile = new Tile(row, col, board.getMarkAt(row, col));
                GridPane.setConstraints(tile, col, row);
                gameBoard.getChildren().add(tile);
            }
        }
        return gameBoard;
    }

    private MenuBar initialiseMenu() {
        menuBar = new MenuBar();
        gameMenu = new Menu("game");
        newGameOption = new MenuItem("New Game");

        gameMenu.getItems().add(newGameOption);
        menuBar.getMenus().add(gameMenu);
        newGameOption.setOnAction(e -> {
            resetGame();
        });
        return menuBar;
    }

    /**
     * Runs the main game loop which is responsible for playing the AI's turn 
     * as long as the game is still ongoing.
     */
    private void runGameLoop() {
        gameTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (board.isGameOver()) {
                    endGame();
                } else {
                    if (board.isCrossTurn()) {
                        playAI();
                    }
                }
            }
        };
        gameTimer.start();
    }

    /**
     * Analyses the current state of the board and plays the move best for the X
     * player. Updates the tile it places a mark on also.
     */
    private static void playAI() {
        int[] move = MiniMaxCombined.getBestMove(board);
        int row = move[0];
        int col = move[1];
        board.placeMark(row, col);
        for (Node child : gameBoard.getChildren()) {
            if (GridPane.getRowIndex(child) == row
                    && GridPane.getColumnIndex(child) == col) {
                Tile t = (Tile) child;
                t.update();
                return;
            }
        }
    }

    private void resetGame() {
        root.setCenter(generateGUI());
        runGameLoop();
    }

    /**
     * Stops the game loop and displays the result of the game.
     */
    private void endGame() {
        gameTimer.stop();
        Alert gameOverAlert = new Alert(AlertType.INFORMATION, "",
                new ButtonType("New Game"));
        Mark winner = board.getWinningMark();

        gameOverAlert.setTitle("Game Over");
        gameOverAlert.setHeaderText(null);
        if (winner == Mark.BLANK) {
            gameOverAlert.setContentText("Draw!");
        } else {
            gameOverAlert.setContentText(winner + " wins!");
        }
        gameOverAlert.setOnHidden(e -> {
            gameOverAlert.close();
            resetGame();
        });
        gameOverAlert.show();
    }
}

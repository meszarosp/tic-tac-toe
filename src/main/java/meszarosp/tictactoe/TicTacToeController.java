package meszarosp.tictactoe;

import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import meszarosp.tictactoe.model.Board;
import meszarosp.tictactoe.model.Mark;
import meszarosp.tictactoe.model.Opponent;
import meszarosp.tictactoe.view.BoardView;


public class TicTacToeController {

    private Board board;
    private BoardView boardView;
    private Label resultLabel;

    private Opponent opponent;

    private boolean playerStarts = true;

    private int win = 0;
    private int draw = 0;
    private int loss = 0;

    private Thread opponentThread = new Thread();

    public TicTacToeController(Board board, BoardView boardView, Label resultLabel, double difficulty) {
        this.board = board;
        this.boardView = boardView;
        this.resultLabel = resultLabel;
        refreshResultLabel();

        opponent = new Opponent(difficulty);

        boardView.getGrid().getChildren().forEach(node -> ((Button) node).setOnAction(
                event -> {
                    if (opponentThread.isAlive())
                        return;
                    Button button = (Button) event.getSource();
                    int buttonId = Integer.parseInt(button.getId());
                    if (board.getMark(buttonId) != Mark.Empty)
                        return;
                    board.setMark(buttonId, Mark.X);
                    button.setText(board.getMark(Integer.parseInt(button.getId())).toString());
                    Mark whoIsWinning = board.whoIsWinning();
                    if (board.isFull() || whoIsWinning != Mark.Empty) {
                        gameEnded(whoIsWinning);
                        return;
                    }
                    opponentMove();
                    event.consume();
                }
        ));
    }

    private void opponentMove(){
        Task<Integer> opponentMoveTask = new Task<Integer>() {
            @Override protected Integer call() throws Exception {
                opponent.makeNextMove(board);
                return 0;
            }
        };
        opponentMoveTask.setOnSucceeded(t -> {
            boardView.refresh();
            Mark whoIsWinning = board.whoIsWinning();
            if (board.isFull() || whoIsWinning != Mark.Empty)
                gameEnded(whoIsWinning);
        });
        opponentThread = new Thread(opponentMoveTask);
        opponentThread.setDaemon(true);
        opponentThread.start();
    }

    private void refreshResultLabel(){
        resultLabel.setText(String.format("Win: %s Draw: %s Loss: %s", win, draw, loss));
    }

    private void startNewGame(){
        board.clear();
        refreshResultLabel();

        playerStarts = !playerStarts;
        if (!playerStarts)
            opponentMove();
        boardView.refresh();
    }

    private void gameEnded(Mark whoIsWinning){
        Alert alert = new Alert(Alert.AlertType.NONE);
        switch (whoIsWinning) {
            case Empty:
                alert.setContentText("The game ended in a draw!");
                draw++;
                break;
            case X:
                alert.setContentText("You have won!");
                win++;
                break;
            case O:
                alert.setContentText("You have lost!");
                loss++;
                break;
        }
        alert.setTitle("Game ended");
        alert.getButtonTypes().add(ButtonType.OK);
        alert.show();

        alert.setOnCloseRequest(event -> {
            startNewGame();
        });
    }
}
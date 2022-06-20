package meszarosp.tictactoe;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import meszarosp.tictactoe.model.Board;
import meszarosp.tictactoe.view.BoardView;

import java.util.List;
import java.util.Optional;

public class TicTacToeApplication extends Application {

    private Board board = new Board();
    private BoardView boardView = new BoardView(board);
    private Label resultLabel = new Label();

    @Override
    public void start(Stage stage){
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(boardView.getGrid());
        borderPane.setBottom(resultLabel);
        resultLabel.setMaxWidth(Double.MAX_VALUE);
        resultLabel.setAlignment(Pos.CENTER);
        resultLabel.setStyle("-fx-font-size: 20pt");
        Scene scene = new Scene(borderPane, 400, 400);
        stage.setTitle("Tic tac toe");
        stage.setScene(scene);
        stage.show();
        stage.requestFocus();
        double difficulty = askDifficulty();
        TicTacToeController controller = new TicTacToeController(board, boardView, resultLabel, difficulty);

    }

    private double askDifficulty(){
        List<String> choices = List.of("Very Easy", "Easy", "Medium", "Hard", "Very Hard");
        ChoiceDialog<String> dialog = new ChoiceDialog<>("Very Hard", choices);
        dialog.setTitle("Difficulty");
        dialog.setHeaderText("Choose your difficulty");

        Optional<String> result = dialog.showAndWait();
        double difficulty = 1.0;
        if (result.isPresent())
            difficulty = 1.0*(choices.indexOf(result.get())+1)/choices.size();
        return difficulty;
    }

    public static void main(String[] args) {
        launch();
    }
}
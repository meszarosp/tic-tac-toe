package meszarosp.tictactoe.view;

import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import meszarosp.tictactoe.model.Board;

public class BoardView{

    private Board board;

    private GridPane grid = new GridPane();

    public BoardView(Board board) {
        this.board = board;
        initGrid();
    }

    private void initGrid(){
        for (int row = 0; row < board.getSize(); row++) {
            RowConstraints rc = new RowConstraints();
            rc.setVgrow(Priority.ALWAYS);
            rc.setFillHeight(true);
            grid.getRowConstraints().add(rc);
        }
        for (int col = 0; col < board.getSize(); col++){
            ColumnConstraints cc = new ColumnConstraints();
            cc.setHgrow(Priority.ALWAYS);
            cc.setFillWidth(true);
            grid.getColumnConstraints().add(cc);
        }

        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {
                int id = board.getSize() * row + col;
                Button button = new Button("");
                button.setId(String.valueOf(id));
                button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                button.setStyle("-fx-font-family: 'monospaced';\n -fx-font-size: 40pt");
                grid.add(button, col, row);
            }
        }
    }

    public GridPane getGrid() {
        return grid;
    }

    public void refresh(){
        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < 3; col++) {
                Button button = (Button)grid.getChildren().get(row*board.getSize()+col);
                button.setText(board.getMark(row*board.getSize()+col).toString());
            }
        }
    }
}

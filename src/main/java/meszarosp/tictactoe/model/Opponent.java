package meszarosp.tictactoe.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Opponent {

    private int[] moves;
    private double difficulty;

    private Random random = new Random();

    public Opponent(double difficulty) {
        this.difficulty = difficulty;
    }

    public Opponent(){
        difficulty = 1.0;
    }

    public void makeNextMove(Board board) {
        int size = board.getSize();
        moves = new int[size * size];
        Arrays.fill(moves, Integer.MAX_VALUE);
        minimax(board, true, Mark.O);

        int minValue = Arrays.stream(moves).min().getAsInt();
        List<Integer> optimalMoves = new ArrayList<>(size);
        List<Integer> possibleMoves = new ArrayList<>(size);
        for (int i = 0; i < size*size; i++) {
            if (moves[i] == minValue)
                optimalMoves.add(i);
           if (board.getMark(i) == Mark.Empty)
                possibleMoves.add(i);
        }

        if (random.nextDouble() < difficulty)
            board.setMark(optimalMoves.get(random.nextInt(optimalMoves.size())), Mark.O);
        else
            board.setMark(possibleMoves.get(random.nextInt(possibleMoves.size())), Mark.O);
    }

    private int minimax(Board board, boolean nextMove, Mark mark) {
        Mark whoIsWinning = board.whoIsWinning();
        if (board.isFull() || whoIsWinning != Mark.Empty)
            return whoIsWinning.value;

        if (mark == Mark.X)
            return maximizing(board, nextMove, mark);
        else
            return minimizing(board, nextMove, mark);
    }

    private int maximizing(Board board, boolean nextMove, Mark mark) {
        int max = Integer.MIN_VALUE;
        int size = board.getSize();
        for (int i = 0; i < size * size; i++) {
            if (board.getMark(i) == Mark.Empty) {
                board.setMark(i, mark);
                int value = minimax(board, false, Mark.O);
                if (nextMove)
                    moves[i] = value;
                board.setMark(i, Mark.Empty);
                if (value > max)
                    max = value;
            }
        }
        return max;
    }

    private int minimizing(Board board, boolean nextMove, Mark mark) {
        int min = Integer.MAX_VALUE;
        int size = board.getSize();
        for (int i = 0; i < size * size; i++) {
            if (board.getMark(i) == Mark.Empty) {
                board.setMark(i, mark);
                int value = minimax(board, false, Mark.X);
                if (nextMove)
                    moves[i] = value;
                board.setMark(i, Mark.Empty);
                if (value < min)
                    min = value;
            }
        }
        return min;
    }
}

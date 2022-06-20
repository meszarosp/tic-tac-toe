package meszarosp.tictactoe.model;

import java.util.Arrays;

import static meszarosp.tictactoe.model.Mark.*;

public class Board {

    public static final int DEFAULT_SIZE = 3;

    private Mark[] marks;

    private int size;

    public int getSize() {
        return size;
    }

    public Board(int size) {
        this.size = size;
        marks = new Mark[size*size];
        clear();
    }

    public Board(){
        this(DEFAULT_SIZE);
    }

    public void setMark(int row, int col, Mark mark){
        marks[size*row+col] = mark;
    }

    public void setMark(int id, Mark mark){
        marks[id] = mark;
    }

    public Mark getMark(int id){
        return marks[id];
    }

    public boolean isFull(){
        return Arrays.stream(marks).filter(m -> m != Empty).count() == (long) size *size;
    }

    public void clear(){
        for (int i = 0; i < size*size; i++)
            marks[i] = Empty;
    }

    public Mark whoIsWinning(){
        //check rows
        for (int row = 0; row < size; row++) {
            int sum = 0;
            for (int col = 0; col < size; col++)
                sum += marks[size*row + col].value;
            if (sum == size)
                return X;
            else if (sum == -size)
                return O;
        }
        //check columns
        for (int col = 0; col < size; col++) {
            int sum = 0;
            for (int row = 0; row < size; row++)
                sum += marks[size*row + col].value;
            if (sum == size)
                return X;
            else if (sum == -size)
                return O;
        }

        //check diagonals
        int sum = 0;
        for (int i = 0; i < size; i++)
            sum += marks[size*i + i].value;
        if (sum == size)
            return X;
        else if (sum == -size)
            return O;

        sum = 0;
        for (int i = 0; i < size; i++)
            sum += marks[size*(size-1-i) + i].value;
        if (sum == size)
            return X;
        else if (sum == -size)
            return O;

        return Empty;
    }

}

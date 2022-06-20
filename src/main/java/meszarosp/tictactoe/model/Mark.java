package meszarosp.tictactoe.model;

public enum Mark {
    Empty(0){
        @Override
        public String toString() {
            return " ";
        }
    },
    X(1),
    O(-1);

    public int value;

    Mark(int value) {
        this.value = value;
    }
}

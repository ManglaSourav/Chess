package com.chess.engine;

/**
 * Created by Sourav Mangla on Jan, 2018
 */
//this is useful for both player and for pieces
public enum Alliance {

    WHITE{  //
        @Override
        public int getdirection() {  //pawn can move only one side so we use direction means white pawn moves in negative direction
            return -1;
        }

        @Override
        public boolean isWhite() {
            return true;
        }

        @Override
        public boolean isBlack() {
            return false;
        }
    },
    BLACK{
        @Override
        public int getdirection() { // black pawn moves in postive diection of board
            return 1;
        }

        @Override
        public boolean isWhite() {
            return false;
        }

        @Override
        public boolean isBlack() {
            return true;
        }
    };
    public abstract int getdirection();
    public abstract boolean isWhite();
    public abstract boolean isBlack();

}

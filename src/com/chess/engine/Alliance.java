package com.chess.engine;

/**
 * Created by Sourav Mangla on Jan, 2018
 */
//thsi is useful for both player and for pieces
public enum Alliance {
    WHITE{
        @Override
        public int getDirectio() {
            return -1;
        }
    },
    BLACK{
        @Override
        public int getDirectio() {
            return 1;
        }
    };
    public abstract int getDirectio();

}

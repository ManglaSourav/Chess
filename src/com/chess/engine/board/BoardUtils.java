package com.chess.engine.board;

/**
 * Created by Sourav Mangla on Jan, 2018
 */
public class BoardUtils {

    public static final boolean[] FIRST_COLUMN = initColumn(0);
    public static final boolean[] SECOND_COLUMN = initColumn(2);
    public static final boolean[] SEVENTH_COLUMN = initColumn(6);
    public static final boolean[] EIGHTH_COLUMN = initColumn(7);

    public static final boolean[] SECOND_ROW = null;
    public static final boolean[] SEVENTH_ROW = null;





    public static final int NUM_TILES = 64;
    public static final int NUM_TILES_PER_ROW = 8;

    private BoardUtils() //dont allow to make object of this class;
    {
        throw new RuntimeException("you cannot intantiate me!");
    }

    private static boolean[] initColumn(int columnNumber) { // true for every column (for colNo. = 0 --> columns 0th,8th,16th,24th,32th,40th,48th,56th,64th are true)
        final boolean[] column = new boolean[64];
        do{
            column[columnNumber] = true;
            columnNumber += NUM_TILES_PER_ROW;
        }while(columnNumber < NUM_TILES);
        return column;
    }

    public static boolean isValidTileCoordinate(int coordinate) {
        return coordinate >= 0 && coordinate <NUM_TILES; //check  move will not go out of bound from chess

    }

}

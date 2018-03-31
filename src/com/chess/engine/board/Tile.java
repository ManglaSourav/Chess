package com.chess.engine.board;

import com.chess.engine.pieces.Piece;
import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sourav Mangla on 21-Aug-17.
 */

public abstract class Tile {  //64 tiles in chess  no class can instantiate this class

    protected final int tileCoordinate; //tile no.

    private static final Map<Integer, EmptyTile> EMPTY_TILES_CACHE = createAllPossibleEmptyTiles();

    private Tile(final int tileCoordinate) {

        this.tileCoordinate = tileCoordinate;
    }

    public abstract boolean isTileOccupied();

    public abstract Piece getPiece();

    private static  Map<Integer, EmptyTile> createAllPossibleEmptyTiles(){

        final Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();

        for (int i=0; i<BoardUtils.NUM_TILES; i++){ //for val i : [0,64){

            emptyTileMap.put(i,new EmptyTile(i));
        }
        //also we use in-build function --> return Collections.unmodifiableMap(emptyTileMap)
        return ImmutableMap.copyOf(emptyTileMap);//class belongs to guava lib
    }

    public static Tile createTile(final int tileCoordinate,final Piece piece){ //ony method by which we can make Tile

        return piece != null ? new OccupiedTile(tileCoordinate,piece) : EMPTY_TILES_CACHE.get(tileCoordinate);

    }

    public int getTileCoordinate() {
        return tileCoordinate;
    }


    //this class define tile is not occupied by piece
    public static final class EmptyTile extends Tile{   //innner child class

        private EmptyTile(final int tileCoordinate) {
            super(tileCoordinate);
        }

        @Override
        public String toString() {
            return "-";
        }

        @Override
        public boolean isTileOccupied() {
            return false;
        }

        @Override
        public Piece getPiece() {
            return null;
        }
    }

    //this class define the tile is occupied by  piece(Like king,queen,knights,rook,etc)
    public static final class OccupiedTile extends Tile{  //inner child class

        private final Piece pieceOnTile;

        private OccupiedTile(int tileCoordinate,final Piece pieceOnTile) {
            super(tileCoordinate);
            this.pieceOnTile = pieceOnTile;
        }

        @Override
        public String toString() {
            return getPiece().getPieceAlliance().isBlack() ? getPiece().toString().toLowerCase() :
                    getPiece().toString();
        }

        @Override
        public boolean isTileOccupied() {
            return true;
        }


        @Override
        public Piece getPiece() {
            return this.pieceOnTile;
        }

    }

}

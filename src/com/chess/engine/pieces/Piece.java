package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

import java.util.Collection;

/**
 * Created by Sourav Mangla on 21-Aug-17.
 */

public  abstract  class Piece {

    protected final PieceType pieceType;

    protected  final  int piecePostion; // a tile no is occupied by a piece

    protected final Alliance pieceAlliance;     //piece either white or black color

    protected final boolean isFirstMove;

    public PieceType getPieceType() {
        return this.pieceType;
    }

    Piece(final PieceType pieceType, final int piecePostion, final Alliance pieceAlliance){
        this.pieceType = pieceType;
        this.piecePostion = piecePostion;
        this.pieceAlliance = pieceAlliance;
        this.isFirstMove = false;
    }
    public int getPiecePostion(){
        return this.piecePostion;
    }

    public Alliance getPieceAlliance() {
        return pieceAlliance;
    }
    public boolean isFirstMove(){
        return this.isFirstMove;
    }


    // return a legal move of list for a piece like for king , queen , pawn, etc

    public abstract Collection<Move> calculateLegalMoves(final Board board);

    public enum PieceType{

        PAWN("P") {
            @Override
            public boolean isKing() {
                return false;
            }
        },
        KNIGHT("N") {
            @Override
            public boolean isKing() {
                return false;
            }
        },
        BISHOP("B") {
            @Override
            public boolean isKing() {
                return false;
            }
        },
        ROOK("R") {
            @Override
            public boolean isKing() {
                return false;
            }
        },
        QUEEN("Q") {
            @Override
            public boolean isKing() {
                return false;
            }
        },
        KING("K") {
            @Override
            public boolean isKing() {
                return true;
            }
        };

        private String pieceName;

        PieceType(final String pieceName){
            this.pieceName = pieceName;
        }

        @Override
        public String toString(){
            return this.pieceName;
        }


        public abstract boolean isKing();
    }


}

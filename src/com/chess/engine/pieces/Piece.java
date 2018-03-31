package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

import java.util.Collection;
import java.util.Objects;

/**
 * Created by Sourav Mangla on 21-Aug-17.
 */

public  abstract  class Piece {

    protected final PieceType pieceType;

    protected  final  int piecePostion; // a tile no is occupied by a piece

    protected final Alliance pieceAlliance;     //piece either white or black color

    protected final boolean isFirstMove;

    private final int cachedHashCode;

    public PieceType getPieceType() {
        return this.pieceType;
    }

    Piece(final PieceType pieceType, final int piecePostion, final Alliance pieceAlliance){
        this.pieceType = pieceType;
        this.piecePostion = piecePostion;
        this.pieceAlliance = pieceAlliance;
        this.isFirstMove = false;
        this.cachedHashCode = computeHashCode();
    }

    private int computeHashCode() {
        return Objects.hash(getPieceType(), getPiecePostion(), getPieceAlliance(), isFirstMove());
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

    @Override
    public boolean equals(final Object other) {
        if (this == other)
            return true;
        if (!(other instanceof Piece))
            return false;
        Piece piece = (Piece) other;
        return getPiecePostion() == piece.getPiecePostion() &&
                isFirstMove() == piece.isFirstMove() &&
                getPieceType() == piece.getPieceType() &&
                getPieceAlliance() == piece.getPieceAlliance();
    }

    @Override
    public int hashCode() {
        return this.cachedHashCode;
    }

    // return a legal move of list for a piece like for king , queen , pawn, etc
    public abstract Collection<Move> calculateLegalMoves(final Board board);

    public abstract Piece movePiece(Move move);

    public enum PieceType{

        PAWN("P") {
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        },
        KNIGHT("N") {
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        },
        BISHOP("B") {
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        },
        ROOK("R") {
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return true;
            }
        },
        QUEEN("Q") {
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        },
        KING("K") {
            @Override
            public boolean isKing() {
                return true;
            }

            @Override
            public boolean isRook() {
                return false;
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
        public abstract  boolean isRook();
    }
}

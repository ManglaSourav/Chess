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

    public int getPieceValue() {
        return this.pieceType.getPieceValue();
    }

    Piece(final PieceType pieceType, final int piecePostion, final Alliance pieceAlliance, final boolean isFirstMove){
        this.pieceType = pieceType;
        this.piecePostion = piecePostion;
        this.pieceAlliance = pieceAlliance;
        this.isFirstMove = isFirstMove;
        this.cachedHashCode = computeHashCode();
    }

    private int computeHashCode() {
        return Objects.hash(getPieceType(), getPiecePosition(), getPieceAlliance(), isFirstMove());
    }

    public int getPiecePosition(){
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
        return getPiecePosition() == piece.getPiecePosition() &&
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

        PAWN(100, "P") {
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }

            @Override
            public boolean isPawn() {
                return true;
            }

            @Override
            public boolean isBishop() {
                return false;
            }
        },
        KNIGHT(320, "N") {
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }

            @Override
            public boolean isPawn() {
                return false;
            }

            @Override
            public boolean isBishop() {
                return false;
            }
        },
        BISHOP(350, "B") {
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }

            @Override
            public boolean isPawn() {
                return false;
            }

            @Override
            public boolean isBishop() {
                return true;
            }
        },
        ROOK(500, "R") {
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return true;
            }

            @Override
            public boolean isPawn() {
                return false;
            }

            @Override
            public boolean isBishop() {
                return false;
            }
        },
        QUEEN(900, "Q") {
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }

            @Override
            public boolean isPawn() {
                return false;
            }

            @Override
            public boolean isBishop() {
                return false;
            }
        },
        KING(1000, "K") {
            @Override
            public boolean isKing() {
                return true;
            }

            @Override
            public boolean isRook() {
                return false;
            }

            @Override
            public boolean isPawn() {
                return false;
            }

            @Override
            public boolean isBishop() {
                return false;
            }
        };

        private String pieceName;
        private int pieceValue;

        PieceType( final int pieceValue, final String pieceName){
            this.pieceName = pieceName;
            this.pieceValue = pieceValue;
        }

        @Override
        public String toString(){
            return this.pieceName;
        }

        public int getPieceValue(){
            return this.pieceValue;
        }
        public abstract boolean isKing();
        public abstract  boolean isRook();
        public abstract boolean isPawn();
        public abstract boolean isBishop();
    }
}

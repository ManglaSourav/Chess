package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

import java.util.Collection;

/**
 * Created by Sourav Mangla on 21-Aug-17.
 */

public  abstract  class Piece {

    protected  final  int piecePostion; // a tile no is occupied by a piece

    protected final Alliance pieceAlliance     //piece either white or black color

    Piece(final int piecePostion,final Alliance pieceAlliance){

        this.piecePostion = piecePostion;
        this.pieceAlliance = pieceAlliance;
    }

    public Alliance getPieceAlliance() {
        return pieceAlliance;
    }

    // return a legal move of list for a piece like for king , queen , pawn, etc

    public abstract Collection<Move> calculateLegalMoves(final Board board);

}

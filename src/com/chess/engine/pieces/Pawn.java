package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Sourav Mangla on Jan, 2018
 */
public class Pawn extends Piece{

    private final static int[] CANDIDATE_MOVE_COORDINATE = {8};

    Pawn(int piecePostion, Alliance pieceAlliance) {
        super(piecePostion, pieceAlliance);
    }

    @Override
    public Collection<Move> calculateLegalMoves(Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        for(final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATE ) {
            int candidateDestinationCoordinate = this.piecePostion +(this.getPieceAlliance() * currentCandidateOffset);

            if(!BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)){
                continue;
            }
            if( currentCandidateOffset == 8 && !board.getTile(candidateDestinationCoordinate).isTileOccupied()){
                legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate))
            }

        }

        return ImmutableList.copyOf(legalMoves);
    }


}

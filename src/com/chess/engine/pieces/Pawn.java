package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.chess.engine.board.Move.*;

/**
 * Created by Sourav Mangla on Jan, 2018
 */
public class Pawn extends Piece{

    private final static int[] CANDIDATE_MOVE_COORDINATE = {8,16,7,9};

    public Pawn(final Alliance pieceAlliance, final int piecePostion) {
        super(PieceType.PAWN, piecePostion, pieceAlliance, true);
    }

    public Pawn(final Alliance pieceAlliance, final int piecePostion, final boolean isFirstMove) {
        super(PieceType.PAWN, piecePostion, pieceAlliance, isFirstMove);
    }

    @Override
    public Collection<Move> calculateLegalMoves(Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        for(final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATE ) {

            int candidateDestinationCoordinate = this.piecePostion +(this.pieceAlliance.getdirection() * currentCandidateOffset); //

            if(!BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)){
                continue;
            }

            if( currentCandidateOffset == 8 && !board.getTile(candidateDestinationCoordinate).isTileOccupied()){ // this handles the pawn non attacking move
                legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));

            }else if (currentCandidateOffset == 16 && this.isFirstMove() &&  // this handles the pawn jumps(2 cell jump )
                    ((BoardUtils.SEVENTH_RANK[this.piecePostion] && this.getPieceAlliance().isBlack()) ||
                     (BoardUtils.SECOND_RANK[this.piecePostion] && this.getPieceAlliance().isWhite()))) {

                final int behindCandidateDestinationCoordinate = this.piecePostion + (this.pieceAlliance.getdirection() * 8);
                if (!board.getTile(behindCandidateDestinationCoordinate).isTileOccupied() &&
                    !board.getTile(candidateDestinationCoordinate).isTileOccupied()){
                    legalMoves.add(new PawnJump(board, this, candidateDestinationCoordinate));
                }

            }else if (currentCandidateOffset == 7 &&
                     !((BoardUtils.EIGHTH_COLUMN[this.piecePostion] && this.pieceAlliance.isWhite()  ||
                      (BoardUtils.FIRST_COLUMN[this.piecePostion] && this.pieceAlliance.isBlack())))) {
                if (board.getTile(candidateDestinationCoordinate).isTileOccupied()){
                    final Piece   pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
                    if (this.pieceAlliance != pieceOnCandidate.getPieceAlliance()){
                        //TODO more here
                        legalMoves.add(new PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate));
                    }
                }


            }else if(currentCandidateOffset == 9 &&
                    !((BoardUtils.FIRST_COLUMN[this.piecePostion] && this.pieceAlliance.isWhite()  ||
                     (BoardUtils.EIGHTH_COLUMN[this.piecePostion] && this.pieceAlliance.isBlack())))){

                if (board.getTile(candidateDestinationCoordinate).isTileOccupied()){
                    final Piece   pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
                    if (this.pieceAlliance != pieceOnCandidate.getPieceAlliance()){
                        //TODO more here
                        legalMoves.add(new PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate));
                    }
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }
    @Override
    public Pawn movePiece(final Move move) {

        return new Pawn(move.getMovedPiece().getPieceAlliance(), move.getDestinationCoordinate());
    }
    @Override
    public String toString(){
        return PieceType.PAWN.toString();

    }
}

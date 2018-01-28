package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Move;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move.*;
import com.chess.engine.board.Tile;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Sourav Mangla on Jan, 2018
 */
public class Knight extends Piece{
    private final static int[] CANDIDATE_MOVE_COORDINATES = {-17,-15,10,-6,6,10,15,17}; // no. possible position offset for knight

    public Knight(int piecePostion, Alliance pieceAlliance) {
        super(piecePostion, pieceAlliance);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {


        final List<Move> legalMoves = new ArrayList<>();

        for(final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATES){

            final int candidateDestinationCoordinate = this.piecePostion + currentCandidateOffset; // add current postion of knight + offset(one by one check possible all moves)

            if(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)){ // check  a valid move or not

                if(isFirstColumnExclusion(this.piecePostion, currentCandidateOffset) || //check Exception moves if a knight occur at First Column
                        isSecondColumnExclusion(this.piecePostion, currentCandidateOffset) || //check Exception moves if a knight occur at Second Column
                        isSeventhColumnExclusion(this.piecePostion, currentCandidateOffset)|| //check Exception moves if a knight occur at Seventh Column
                        isEightHColumnExclusion(this.piecePostion, currentCandidateOffset) ){  //check Exception moves if a knight occur at Eight Column
                    continue; // Exception move occur so we continue the loop for next offset
                }

                final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
                if(!candidateDestinationTile.isTileOccupied()){  //if tile is not occupied than it is a legal Move
                    legalMoves.add(new MajorMove(board,this,candidateDestinationCoordinate)); // not attacking move

                }else{ // if tile is occupied than get piece from that tile and check for is color(Alliances).if it has different color than we can captures the piece of opponent
                    final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                    final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();
                    if (this.pieceAlliance != pieceAlliance){ // if our piece has different color than oppnent, its a legal move
                        legalMoves.add(new AttackMove(board,this,candidateDestinationCoordinate,pieceAtDestination));//attacking move
                    }
                }
            }

        }

        return ImmutableList.copyOf(legalMoves);
    }

    private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset){ //check unvalid(Exception) moves for first column of board

        return BoardUtils.FIRST_COLUMN[currentPosition] && ((candidateOffset = -17) || (candidateOffset = -10) ||(candidateOffset = 6) ||(candidateOffset = 15))

    }
    private static boolean isSecondColumnExclusion(final int currentPosition, final int candidateOffset){ //check unvalid moves for Second column of board

        return BoardUtils.SECOND_COLUMN[currentPosition] && ((candidateOffset = -10) || (candidateOffset = 6))

    }
    private static boolean isSeventhColumnExclusion(final int currentPosition, final int candidateOffset){ //check unvalid moves for Seventh column of board

        return BoardUtils.SEVENTH_COLUMN[currentPosition] && ((candidateOffset = 10) || (candidateOffset = -6))

    }
    private static boolean isEightHColumnExclusion(final int currentPosition, final int candidateOffset){ //check unvalid moves for Eighth column of board

        return BoardUtils.EIGHTH_COLUMN[currentPosition] && ((candidateOffset = 17) || (candidateOffset = 10) ||(candidateOffset = -6) ||(candidateOffset = -15))

    }


}

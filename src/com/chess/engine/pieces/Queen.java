package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.MajorAttackMove;
import com.chess.engine.board.Tile;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Sourav Mangla on Jan, 2018
 */
public class Queen extends  Piece{

    private final static int[] CANDIDATE_MOVE_VECTOR_COORDINATE = {-9, -8 -7, -1, 1, 7, 8, 9};

    public Queen(final Alliance pieceAlliance, final int piecePostion) {
        super(PieceType.QUEEN, piecePostion, pieceAlliance, true);
    }


    public Queen(final Alliance pieceAlliance, final int piecePostion, final boolean isFirstMove) {
        super(PieceType.QUEEN, piecePostion, pieceAlliance, isFirstMove);
    }

    @Override
    public Collection<Move> calculateLegalMoves(Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        for(final int candidateCoordinateOffset : CANDIDATE_MOVE_VECTOR_COORDINATE ){
            int candidateDestinationCoordinate = this.piecePostion;

            while(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)){

                if( isFirstColumnExclusion(candidateDestinationCoordinate,candidateCoordinateOffset) ||
                        isEightColumnExclusion(candidateDestinationCoordinate, candidateCoordinateOffset )){
                    break;
                }

                candidateDestinationCoordinate += candidateCoordinateOffset;

                if(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)){ //after addition coordinate does not go out of bound

                    final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);

                    if(!candidateDestinationTile.isTileOccupied()){  //if tile is not occupied than it is a legal Move

                        legalMoves.add(new Move.MajorMove(board,this,candidateDestinationCoordinate)); // not attacking move

                    }else { // if tile is occupied than get piece from that tile and check for is color(Alliances).if it has different color than we can captures the piece of opponent
                        final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                        final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();
                        if (this.pieceAlliance != pieceAlliance) { // if our piece has different color than oppnent, its a legal move
                            legalMoves.add(new MajorAttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination));//attacking move
                        }
                        break; // if any piece occur in between any vector we can't go further for this vector
                    }

                }
            }
        }

        return ImmutableList.copyOf(legalMoves);
    }

    @Override
    public Queen movePiece(final Move move) {

        return new Queen(move.getMovedPiece().getPieceAlliance(), move.getDestinationCoordinate());
    }

    @Override
    public String toString(){
        return PieceType.QUEEN.toString();
    }
    private static boolean isFirstColumnExclusion(final int currentPostion, final int candidateOffset){

        return BoardUtils.FIRST_COLUMN[currentPostion] && (candidateOffset == -9 || candidateOffset == -1 || candidateOffset == 7);
    }
    private static boolean isEightColumnExclusion(final int currentPostion, final int candidateOffset){

        return BoardUtils.EIGHTH_COLUMN[currentPostion] && (candidateOffset == -7 || candidateOffset == 1 || candidateOffset == 9);
    }
}

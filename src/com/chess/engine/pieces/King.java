package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.AttackMove;
import com.chess.engine.board.Move.MajorMove;
import com.chess.engine.board.Tile;
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Sourav Mangla on Jan, 2018
 */
public class King extends Piece{

    private final static int[] CANDIATE_MOVE_COORDINATE = {-9, -8, -7, -1, 1, 7, 8, 9};

    public King(final  Alliance pieceAlliance, final int piecePostion) {
        super(PieceType.KING, piecePostion, pieceAlliance);
    }

    @Override
    public Collection<Move> calculateLegalMoves(Board board) {

        final List<Move> legalMoves = new ArrayList<>();


        for(int currrentCandidateOffset : CANDIATE_MOVE_COORDINATE){

            final int candidateDestinationCoordinate = this.piecePostion + currrentCandidateOffset;

            if(isFirstColumnExclusion(this.piecePostion,currrentCandidateOffset) ||
                    isEigthColumnExclusion(this.piecePostion,currrentCandidateOffset)){
                continue;
            }

            if(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)){
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

    @Override
    public King movePiece(final Move move) {

        return new King(move.getMovedPiece().getPieceAlliance(), move.getDestinationCoordinate());
    }

    @Override
    public String toString(){
        return PieceType.KING.toString();
    }

    private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset){ //check unvalid(Exception) moves for first column of board

        return BoardUtils.FIRST_COLUMN[currentPosition] && ((candidateOffset == -9) || (candidateOffset == -1) ||(candidateOffset == 7));

    }
    private static boolean isEigthColumnExclusion(final int currentPosition, final int candidateOffset){ //check unvalid moves for Second column of board

        return BoardUtils.EIGHTH_COLUMN[currentPosition] && ((candidateOffset == -7 || (candidateOffset == 1) || (candidateOffset == 9)));

    }
}

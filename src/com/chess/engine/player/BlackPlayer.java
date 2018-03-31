package com.chess.engine.player;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Rook;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.chess.engine.board.Move.*;

/**
 * Created by Sourav Mangla on Feb, 2018
 */
public class BlackPlayer extends Player {
    public BlackPlayer(final Board board,
                       final Collection<Move> whiteStandardLegalMoves,
                       final Collection<Move> blackStandardLegalMoves) {
        super(board,blackStandardLegalMoves,whiteStandardLegalMoves);
    }

    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getBlackPieces();
    }

    @Override
    public Alliance getAlliance() {
        return Alliance.BLACK;
    }

    @Override
    public Player getOpponent() {
        return this.board.whitePlayer();
    }
    @Override
    protected Collection<Move> calculateKingCastles(final Collection<Move> playerLegals,
                                                    final Collection<Move> opponentLegals) {
        final List<Move> kingCastles = new ArrayList<>();
        if(this.playerKing.isFirstMove() && !this.isInCheck()){ //checking king does not play any move and it is not in check
            //black king side castle
            if(!this.board.getTile(5).isTileOccupied() && //checking between rook and king we have empty tiles to perform castling
                    !this.board.getTile(6).isTileOccupied()){
                final Tile rookTile = this.board.getTile(7);
                if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()){ //checking rook does not play any move
                    if(Player.calculateAttacksOnTile(5, opponentLegals).isEmpty() && // check we dont any attack after castling so opponent dont have any legal move on tile 5 and 6
                            Player.calculateAttacksOnTile(6, opponentLegals).isEmpty() &&
                            rookTile.getPiece().getPieceType().isRook()){
                        kingCastles.add(new KingSideCastleMove(this.board,
                                                                    this.playerKing,
                                                   6,
                                                                     (Rook)rookTile.getPiece(),
                                                                     rookTile.getTileCoordinate(),
                                                   5)); // castlemove towards opposite side of queen
                    }
                }
            }
            if(!this.board.getTile(1).isTileOccupied() &&  //checking we dont have anything in between rook and king
                    !this.board.getTile(2).isTileOccupied() &&
                    !this.board.getTile(3).isTileOccupied()){

                final Tile rookTile = this.board.getTile(0);
                if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()){ //checking its rook's first move

                    kingCastles.add(new QueenSideCastleMove(this.board,
                                                                 this.playerKing,
                                               2,
                                                                (Rook)rookTile.getPiece(),
                                                                 rookTile.getTileCoordinate(),
                                               3));//castle move towards queen side
                }
            }
        }
        return ImmutableList.copyOf(kingCastles);
    }
}

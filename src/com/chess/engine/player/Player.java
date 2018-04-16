package com.chess.engine.player;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Piece;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Sourav Mangla on Feb, 2018
 */
public abstract class Player {

    protected final Board board;
    protected final King playerKing;
    protected final Collection<Move> legalMoves;
    private final boolean isInCheck;

    public Player(Board board,
                  Collection<Move> legalMoves,
                  Collection<Move> opponentMoves) {
        this.board = board;
        this.playerKing = establishKing();
        this.legalMoves =ImmutableList.copyOf(Iterables.concat(legalMoves, calculateKingCastles(legalMoves, opponentMoves)));
        this.isInCheck = !Player.calculateAttacksOnTile(this.playerKing.getPiecePosition(), opponentMoves).isEmpty();

    }

    public Collection<Move> getLegalMoves() {
        return legalMoves;
    }

    protected static Collection<Move> calculateAttacksOnTile(int piecePostion, Collection<Move> moves) {

        final List<Move> attackMoves = new ArrayList<>();

        for (final Move move : moves){
            if (piecePostion == move.getDestinationCoordinate()){
                attackMoves.add(move);
            }
        }
        return ImmutableList.copyOf(attackMoves);
    }

    private King establishKing() { //this method ensure that there is king on board for a player he is in legal state in the game

        for (final Piece piece : getActivePieces()){
            if (piece.getPieceType().isKing()){
                return ( King )piece;
            }
        }
        throw new RuntimeException("should not reach here! Not a valid Board");// if king is not found  thats is not a valid chess board
    }

    public boolean isMoveLegal(final Move move){

        return this.legalMoves.contains(move);
    }

    public boolean isInCheck(){
        return false;
    }

    public boolean isInCheckMate(){
        return this.isInCheck && !hasEscapeMoves();
    }

    protected boolean hasEscapeMoves(){
        for (final Move move : this.legalMoves){
            final MoveTransition transition = makeMove(move);
            if(transition.getMoveStatus().isDone()){
                return true;
            }
        }
        return false;
    }

    public boolean isInStaleMate(){
        return !this.isInCheck && !hasEscapeMoves();
    }

    public boolean isCastled(){
        return false;
    }

    public MoveTransition makeMove(final Move move){

        if(!isMoveLegal(move)){
            return new MoveTransition(this.board, move, MoveStatus.ILLEGAL_MOVE);
        }

        final Board transitionBoard = move.execute();

        final Collection<Move> kingAttacks = Player.calculateAttacksOnTile(transitionBoard.currentPlayer().getOpponent().getPlayerKing().getPiecePosition(),
                                                                            transitionBoard.currentPlayer().getLegalMoves());

        if(!kingAttacks.isEmpty()){
            return  new MoveTransition(this.board, move, MoveStatus.LEAVE_PLAYER_IN_CHECK);
        }
        return new MoveTransition(this.board, move, MoveStatus.DONE);
    }



    public abstract Collection<Piece>  getActivePieces();
    public abstract Alliance getAlliance();
    public abstract Player getOpponent();
    public King getPlayerKing() {
        return this.playerKing;
    }
    protected abstract Collection<Move> calculateKingCastles(Collection<Move> playerLegals, Collection<Move> opponentLegals);

}

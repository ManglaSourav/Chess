package com.chess.engine.board;

import com.chess.engine.board.Board;
import com.chess.engine.pieces.Piece;

import static com.chess.engine.board.Board.*;

/**
 * Created by Sourav Mangla on Jan, 2018
 */
public abstract class Move { //this is abstract because we have two type of moves attacking moves and non attacking moves

    final Board board; // to keep track of board on we moved on
    final Piece movedPiece; // to keep track of the piece we have moved
    final int destinationCoordinate; // where we want to move that piece

    public static final Move NULL_MOVE = new NullMove();

   private Move(Board board, Piece movedPiece, int destinationCoordinate) {
        this.board = board;
        this.movedPiece = movedPiece;
        this.destinationCoordinate = destinationCoordinate;
    }
    public int getCurrentCoordinate(){
       return this.getMovedPiece().getPiecePostion();
    }
    public int getDestinationCoordinate(){
       return this.destinationCoordinate;
    }

    public Piece getMovedPiece() {
        return this.movedPiece;
    }

    public Board execute(){
        final Builder  builder = new Builder();
        for(final Piece piece : this.board.currentPlayer().getActivePieces()){
            //TODO hashcode and equals for pieces
            if(!this.movedPiece.equals(piece)){
                builder.setPiece(piece);
            }
        }
        for (final Piece piece : this.board.currentPlayer().getOpponent().getActivePieces()){
            builder.setPiece(piece);
        }
        //move the  moved piece
        builder.  setPiece(this.movedPiece.movePiece(this));
        builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());//set next player turn
        return builder.build();
    }

    public static final class MajorMove extends Move{  //for simple move

        public MajorMove(final Board board,final Piece movedPiece,final int destinationCoordinate) {
            super(board, movedPiece, destinationCoordinate);
        }
    }

    public static class AttackMove extends Move{  // for attacking move
        final Piece attackedPiece;  //to track on which piece we are attacking
        public AttackMove(final Board board,
                          final Piece movedPiece,
                          final int destinationCoordinate,
                          final Piece attackedPiece) {
            super(board, movedPiece, destinationCoordinate);
            this.attackedPiece = attackedPiece;
        }

        @Override
        public Board execute(){
            return null;
        }
    }


    public static final class PawnMove extends Move{  //for Pawn move

        public PawnMove(final Board board,
                        final Piece movedPiece,
                        final int destinationCoordinate) {
            super(board, movedPiece, destinationCoordinate);
        }
    }


    public static class PawnAttackMove extends AttackMove{

        public PawnAttackMove(final Board board,
                              final Piece movedPiece,
                              final int destinationCoordinate,
                              final Piece attackedPiece) {
            super(board, movedPiece, destinationCoordinate, attackedPiece);
        }
    }
    public static final class PawnEnPassAttackMove extends PawnAttackMove{

        public PawnEnPassAttackMove(final Board board,
                              final Piece movedPiece,
                              final int destinationCoordinate,
                              final Piece attackedPiece) {
            super(board, movedPiece, destinationCoordinate, attackedPiece);
        }
    }

    public static final class PawnJump extends Move{

        public PawnJump(final Board board,
                              final Piece movedPiece,
                              final int destinationCoordinate) {
            super(board, movedPiece, destinationCoordinate);
        }
    }

    static final class CastleMove extends Move{

        public CastleMove(final Board board,
                              final Piece movedPiece,
                              final int destinationCoordinate) {
            super(board, movedPiece, destinationCoordinate);
        }
    }
     public static class KingSideCastleMove extends CastleMove{

        public KingSideCastleMove(final Board board,
                          final Piece movedPiece,
                          final int destinationCoordinate) {
            super(board, movedPiece, destinationCoordinate);
        }
    }
    public static class QueenSideCastleMove extends CastleMove{

        public QueenSideCastleMove(final Board board,
                                  final Piece movedPiece,
                                  final int destinationCoordinate) {
            super(board, movedPiece, destinationCoordinate);
        }
    }
    public static class NullMove extends Move{

        public QueenSideCastleMove() {
            super(null, null, -1);
        }

        @Override
        public Board execute() {
            return new RuntimeException("cannot execute this null move!");
        }
    }

    public static class MoveFactory{


       private MoveFactory(){}
       throw new RuntimeException("Not instantiate!");
    }

    public static Move createMove(final Board board,
                                  final int currentCoordination,
                                  final int destinationCoordinate){
       for( final Move move : board.getAllLegalMoves()){
           if(move.getCurrentCoordinate() == currentCoordination &&
                   move.getDestinationCoordinate() == destinationCoordinate){
               return move;
           }
       }
       return NULL_MOVE;
    }

}

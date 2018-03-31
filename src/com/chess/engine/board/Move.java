package com.chess.engine.board;

import com.chess.engine.pieces.Pawn;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Rook;

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
    public boolean isAttack(){
       return false;
    }
    public boolean isCastlingMove(){
       return false;
    }
    public Piece getAttackedPiece(){
       return null;
    }

    //we have moves in form of collection so we need hashcode and equals method
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + this.destinationCoordinate;
        result = prime * result + this.movedPiece.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o){
            return true;
        }
        if(!(o instanceof Move)){
            return false;
        }
        final Move otherMove = (Move)o;
        return getDestinationCoordinate() == otherMove.getDestinationCoordinate() &&
                getMovedPiece().equals(otherMove.getMovedPiece());
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

        @Override
        public boolean isAttack() {
            return true;
        }

        @Override
        public Piece getAttackedPiece() {
            return this.getAttackedPiece();
        }

        @Override
        public int hashCode() {
            return this.attackedPiece.hashCode() + super.hashCode();
        }

        @Override
        public boolean equals(Object o) {
            if(this == o){
                return true;
            }
            if(!(o instanceof AttackMove)){
                return false;
            }
            final AttackMove otherAttackMove = (AttackMove)o;
            return super.equals(otherAttackMove) && getAttackedPiece().equals(otherAttackMove.getAttackedPiece());
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

        @Override
        public Board execute() {
           final Builder builder = new Builder();
           for (final Piece piece : this.board.currentPlayer().getActivePieces()){
               if(!this.movedPiece.equals(piece)) {
                   builder.setPiece(piece);
               }
            }
            for(final Piece piece : this.board.currentPlayer().getOpponent().getActivePieces()){
               builder.setPiece(piece);
            }
            final Pawn movedPawn = (Pawn)this.movedPiece.movePiece(this);
            builder.setPiece(movedPawn);
            builder.setEnPassantPawn(movedPawn);
            builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());
            return builder.build();


        }
    }

    static class CastleMove extends Move{

       protected final Rook castleRook;
       protected final int castleRookStarts;
       protected final int castleRookDestination;

        public CastleMove(final Board board,
                          final Piece movedPiece,
                          final int destinationCoordinate,
                          final Rook castleRook,
                          final int castleRookStarts,
                          final int castleRookDestination) {
            super(board, movedPiece, destinationCoordinate);
            this.castleRook = castleRook;
            this.castleRookStarts = castleRookStarts;
            this.castleRookDestination = castleRookDestination;
        }

        public Rook getCastleRook() {
            return this.castleRook;
        }

        @Override
        public boolean isCastlingMove() {
            return true;
        }

        @Override
        public Board execute() {
            final Builder builder = new Builder();
            for (final Piece piece : this.board.currentPlayer().getActivePieces()){
                if(!this.movedPiece.equals(piece)) {
                    builder.setPiece(piece);
                }
            }
            for(final Piece piece : this.board.currentPlayer().getOpponent().getActivePieces()){
                builder.setPiece(piece);
            }
            builder.setPiece(this.movedPiece.movePiece(this));
            //TODO look into the first move on normal pieces
            builder.setPiece(new Rook(this.castleRook.getPieceAlliance(), this.castleRookDestination));
            builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());
            return builder.build();
        }
    }
     public static class KingSideCastleMove extends CastleMove {

         public KingSideCastleMove(final Board board,
                                   final Piece movedPiece,
                                   final int destinationCoordinate,
                                   final Rook castleRook,
                                   final int castleRookStarts,
                                   final int castleRookDestination) {
             super(board, movedPiece, destinationCoordinate, castleRook, castleRookStarts, castleRookDestination);
         }

         @Override
         public String toString() {
             return "O-O";
         }
     }
    public static class QueenSideCastleMove extends CastleMove {

        public QueenSideCastleMove(final Board board,
                                   final Piece movedPiece,
                                   final int destinationCoordinate,
                                   final Rook castleRook,
                                   final int castleRookStarts,
                                   final int castleRookDestination) {
            super(board, movedPiece, destinationCoordinate, castleRook, castleRookStarts, castleRookDestination);
        }

        @Override
        public String toString() {
            return "O-O-O";
        }
    }

    public static class NullMove extends Move{

        public NullMove() {
            super(null, null, -1);
        }

        @Override
        public Board execute() {
           throw new RuntimeException("cannot execute this null move!");
        }
    }

    public static class MoveFactory {

        private MoveFactory() {
            throw new RuntimeException("Not instantiate!");

        }
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

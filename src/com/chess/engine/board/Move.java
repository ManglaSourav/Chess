package com.chess.engine.board;

import com.chess.engine.board.Board;
import com.chess.engine.pieces.Piece;

import static com.chess.engine.board.Board.*;

/**cin>>a[i];
 * Created by Sourav Mangla on Jan, 2018
 */
public abstract class Move { //this is abstract because we have two type of moves attacking moves and non attacking moves

    final Board board; // to keep track of board on we moved on
    final Piece movedPiece; // to keep track of the piece we have moved
    final int destinationCoordinate; // where we want to move that piece

   private Move(Board board, Piece movedPiece, int destinationCoordinate) {
        this.board = board;
        this.movedPiece = movedPiece;
        this.destinationCoordinate = destinationCoordinate;
    }
    public int getDestinationCoordinate(){
       return this.destinationCoordinate;
    }

    public Piece getMovedPiece() {
        return this.movedPiece;
    }

    public abstract Board execute();

    public static final class MajorMove extends Move{  //for simple move

        public MajorMove(final Board board,final Piece movedPiece,final int destinationCoordinate) {
            super(board, movedPiece, destinationCoordinate);
        }
        @Override
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
            builder.setPiece(this.movedPiece.movePiece(this));
            builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());//set next player turn
            return builder.build();
        }
    }

    public static final class AttackMove extends Move{  // for attacking move
        final Piece attackedPiece;  //to track on which piece we are attacking
        public AttackMove(final Board board, final Piece movedPiece, final int destinationCoordinate, final Piece attackedPiece) {
            super(board, movedPiece, destinationCoordinate);
            this.attackedPiece = attackedPiece;
        }

        @Override
        public Board execute(){
            return null;
        }
    }


}

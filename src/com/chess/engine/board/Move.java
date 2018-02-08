package com.chess.engine.board;

import com.chess.engine.board.Board;
import com.chess.engine.pieces.Piece;

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

    public static final class MajorMove extends Move{  //for simple move

        public MajorMove(final Board board,final Piece movedPiece,final int destinationCoordinate) {
            super(board, movedPiece, destinationCoordinate);
        }
    }


    public static final class AttackMove extends Move{  // for attacking move

        final Piece attackedPiece;  //to track on which piece we are attacking


        public AttackMove(final Board board, final Piece movedPiece, final int destinationCoordinate, final Piece attackedPiece) {
            super(board, movedPiece, destinationCoordinate);
            this.attackedPiece = attackedPiece;
        }
    }


}

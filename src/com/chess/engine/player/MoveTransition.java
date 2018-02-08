package com.chess.engine.player;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

/**
 * Created by Sourav Mangla on Feb, 2018
 */
public class MoveTransition {

    private final Board transitionBoard;
    private final Move move;
    private final MoveStatus moveStatus;//going to tell we are able to move (may be we check condition)

    public MoveTransition(Board transitionBoard, Move move, MoveStatus moveStatus) {
        this.transitionBoard = transitionBoard;
        this.move = move;
        this.moveStatus = moveStatus;
    }
}

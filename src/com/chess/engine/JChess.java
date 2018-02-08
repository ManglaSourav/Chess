package com.chess.engine;

import com.chess.engine.board.Board;

/**
 * Created by Sourav Mangla on Feb, 2018
 */
public class JChess {


    public static void main(String[] args) {
        Board board = Board.createStandardBoard();
        System.out.println(board);
    }
}

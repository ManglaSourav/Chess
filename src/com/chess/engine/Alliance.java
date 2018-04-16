package com.chess.engine;

import com.chess.engine.player.BlackPlayer;
import com.chess.engine.player.Player;
import com.chess.engine.player.WhitePlayer; /**
 * Created by Sourav Mangla on Jan, 2018
 */
//this is useful for both player and for pieces
public enum Alliance {

    WHITE{  //

        @Override
        public Player choosePlayer(final WhitePlayer whitePlayer, final  BlackPlayer blackPlayer) {
            return whitePlayer;
        }

        @Override
        public int getdirection() {  //pawn can move only one side so we use direction means white pawn moves in negative direction
            return -1;
        }

        @Override
        public boolean isWhite() {
            return true;
        }

        @Override
        public boolean isBlack() {
            return false;
        }

        @Override
        public int getOppositeDirection() {
            return 1;
        }
    },
    BLACK{
        @Override
        public Player choosePlayer(final WhitePlayer whitePlayer, final BlackPlayer blackPlayer) {
            return blackPlayer;
        }

        @Override
        public int getdirection() { // black pawn moves in postive diection of board
            return 1;
        }

        @Override
        public boolean isWhite() {
            return false;
        }

        @Override
        public boolean isBlack() {
            return true;
        }

        @Override
        public int getOppositeDirection() {
            return -1;
        }
    };
    public abstract int getdirection();
    public abstract boolean isWhite();
    public abstract boolean isBlack();
    public abstract int getOppositeDirection();

    public abstract Player choosePlayer(WhitePlayer whitePlayer, BlackPlayer blackPlayer);
}

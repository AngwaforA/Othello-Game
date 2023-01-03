package model;
/**
 * Class Player represents the white or black chip which are represented with 1 and 2 respectively in {@link model.GameBoard}*/
public class Player {
    /**
     * Turn is a boolean used to switch turns between the players
     * It is set to false when the first player has had his turn. In this way the next player gets the turn*/
private boolean turn = true;
    /**
     * A setter is used to initialize turn*/
    public void setTurn(boolean turn ) {
        this.turn = turn;
    }
    /**The getter gets the current turn and returns it*/
    public boolean getTurn() {
        return this.turn;
    }
}

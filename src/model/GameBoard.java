package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GameBoard extends Thread{
    /**
    * freeMove represents the places on the board which is possible for a player to make a move
    * */
    private int  blackChips = 2;
    private int whiteChips = 2;

    int turnsWithNoFreeMove = 0;
    public int freeMove = 0;
    /**
     * each value in lowerBoundHorizontal is the left side boundary on the board which should not be
     * exceeded when looking for horizontal freeMoves
     * */
    int[] lowerBoundHorizontal = {0,8,16,24,32,40,48,56};
    /**
     * each value in higherBoundHorizontal is the left side boundary on the board which should not be
     * exceeded when looking for horizontal freeMoves
     * */
    int[] HigherBoundHorizontal = {7,15,23,31,39,47,55,63};
    /**
     * playerWhite is an instance of Player which represents the white chip
     * */

    Player playerWhite = new Player();
    /**
     * playerWhite is an instance of Player which represents the black chip
     * */
    Player playerBlack = new Player();
    /**
     * The Hashset is used to store the freeMoves
     * */
    public ArrayList<Integer> freeMoves = new ArrayList<>();


    /**
     * The board of the game is represented with a single dimensional array with 64 elements
     * */

    public int[] gameBoard = new int[64];
    /*Consumer<Long> getBlackChips = s -> Arrays.stream(gameBoard).filter(i -> i == 2).count();
    Consumer<Long> getWhiteChips = s -> Arrays.stream(gameBoard).filter(i -> i == 1).count();*/

    int temp = 0;
    int end = 0;
    /**
     * At the begining of the game the positions 26,27,35 and 36 are filled with 1,2,2,1 respectively
     * 1 represents the white and 2 the black
     * */
    public GameBoard() {
        gameBoard[27] = 1;
        gameBoard[36] = 1;
        gameBoard[28] = 2;
        gameBoard[35] = 2;
        playerWhite.setTurn(true);
        playerBlack.setTurn(false);
    }
/*board =  [0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 1, 2, 0, 0, 0,
            0, 0, 0, 2, 1, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0]*/
     public int[] temp_gameBoard = new int[gameBoard.length];

    /***
     * This method checks if the position where the player clicked is a valid move and then flips(changes the color)
     * all the chips pf the opponent which have been surrounded. In order to do this it calls
     * the {@link #iterateToFlip(int, int, int, int, boolean) iterateToFlip} method.
     * @param pos is the position where the current player clicks to make a move
     */
    /*
    if(playerBturn) {
    iterateGameBoardForFreeMove(chip);
    if(freemoves empty){
    switchturns}
    else {

    random number from freemoves
    move(random number from free moves);
    }
    }
    */
    public void move(int pos) {
        int chip = playerWhite.getTurn() ? 1 : 2;
        int oppositeChip = chip == 1 ? 2 : 1;
        iterateGameBoardForFreeMove(chip);
        if(freeMoves.isEmpty()) {
            switchTurns();
            turnsWithNoFreeMove++;
        }
        int temp = pos;
        if (freeMoves.contains(pos)) {
            gameBoard[pos] = chip;
            if(horizontalForwardFreeMove(pos, chip)){
                iterateToFlip(1,pos,chip,oppositeChip,false);
                pos = temp;
            }
            if(horizontalBackwardFreeMove(pos, chip)){
                iterateToFlip(1,pos,chip,oppositeChip,true);
                pos = temp;
            }
            if (verticalForwardFreeMove(pos, chip)) {
                iterateToFlip(8,pos,chip,oppositeChip,false);
                pos = temp;
            }
            if (verticalBackwardFreeMove(pos, chip)) {
                iterateToFlip(8,pos,chip,oppositeChip,true);
                pos = temp;
            }
            if(diagonalForwardRightFreeMove(pos,chip)) {
                iterateToFlip(7,pos,chip,oppositeChip,true);
                pos = temp;
            }
            if(diagonalBackwardRightFreeMove(pos,chip)) {
                iterateToFlip(7,pos,chip,oppositeChip,false);
            }
            if(diagonalForwardLeftFreeMove(pos,chip)) {
                iterateToFlip(9,pos,chip,oppositeChip,true);
            }
            if(diagonalBackwardLeftFreeMove(pos,chip)){
                iterateToFlip(9,pos,chip,oppositeChip,false);
            }
            System.arraycopy(gameBoard, 0, temp_gameBoard, 0, temp_gameBoard.length);
            for(int i = 0; i< gameBoard.length; i++){
                if(gameBoard[i] == -1){
                    gameBoard[i] = 0;
                }
            }
            switchTurns();
            freeMoves.clear();
            turnsWithNoFreeMove = 0;
        }
    }

    public void cpuMove(){
        int chip = playerWhite.getTurn() ? 1 : 2;
        if(playerBlack.getTurn()){
            iterateGameBoardForFreeMove(chip);
        }
        if(freeMoves.isEmpty()){
            switchTurns();
        }
        else{
            int size = freeMoves.size();
            int anyMove = new Random().nextInt(size);
            int chosenMove = freeMoves.get(anyMove);
            move(chosenMove);
        }
    }
    void
    switchTurns() {
        if(!playerWhite.getTurn()) {
            playerWhite.setTurn(true);
            playerBlack.setTurn(false);
        }
        else {
            playerWhite.setTurn(false);
            playerBlack.setTurn(true);
        }
    }
    /**
     * This method flips all the chips surrounded by the opponent chip when called in move
     * */
    void iterateToFlip(int steps, int pos, int chip, int oppositeChip, boolean isIncreasing){
        if(!isIncreasing) {
            steps = steps * -1 ;
        }
        while(gameBoard[pos] == oppositeChip) {
            gameBoard[pos] = chip;
            pos = pos + steps;
        }
    }

    /**
     * This method collects all the possible freeMoves upwards,downward,sideways and diagonal
     * The method is called in {@link controller.ControllerOfGame } to get the free moves and represent with a circle
     * @param chip is either 1 or 2 which represents the white or black player respectively
     *
     * */
     public void iterateGameBoardForFreeMove(int chip) {

        for (int i = 0; i < gameBoard.length; i++) {
            if (gameBoard[i] == chip) {
                horizontalBackwardFreeMove(i,chip);
                horizontalForwardFreeMove(i,chip);
                verticalForwardFreeMove(i,chip);
                verticalBackwardFreeMove(i,chip);
                diagonalForwardRightFreeMove(i,chip);
                diagonalBackwardRightFreeMove(i,chip);
                diagonalBackwardLeftFreeMove(i,chip);
                diagonalForwardLeftFreeMove(i,chip);
            }
        }
        freeMoves.stream().forEach(System.out::println);
        //System.out.println("[" + this + "]");
    }
    /**
     *Checks for free moves horizontally in a backward direction in reference to one player only
     * */
    boolean horizontalBackwardFreeMove(int pos,int chip) {

        int oppositeChip = chip == 1 ? 2 : 1;
        for(int i = 0; i < lowerBoundHorizontal.length;i++) {
            if(pos >= lowerBoundHorizontal[i]) {
                end = lowerBoundHorizontal[i];
            }
        }
        for(int i = pos ; i >= end ;i--) {
            if(gameBoard[i] == 0 || gameBoard[i] == -1) {
                temp = i;
                break;
            }
        }
        if(gameBoard[temp+1] == oppositeChip) {
            gameBoard[temp] = -1;
            freeMoves.add(temp);
            return true;
        }
        return false;
    }
    /**
     *Checks for free moves horizontally in a forward direction in reference to one player only
     * */
    boolean horizontalForwardFreeMove(int pos,int chip) {
        int oppositeChip = chip == 1 ? 2 : 1;
        for(int i = 0; i < HigherBoundHorizontal.length;i++) {
            if(pos <= HigherBoundHorizontal[i]) {
                end = HigherBoundHorizontal[i];
            }
        }
        for(int i = pos ; i <= end ;i++) {
            if(gameBoard[i] == 0 || gameBoard[i] == -1) {
                temp = i;
                break;
            }
        }
        if(gameBoard[temp-1] == oppositeChip) {
            gameBoard[temp] = -1;
            freeMoves.add(temp);
            return true;
        }
        return false;
    }
    /**
     *Checks for free moves vertically in a backward direction in reference to one player only
     * */
    boolean verticalBackwardFreeMove(int pos,int chip) {
        int counter = pos ;
        while(counter >= 0) {
            if (gameBoard[counter] == 0 || gameBoard[counter] == -1) {
                end = counter;
                break;
            }
            counter = counter - 8;
        }
        if(gameBoard[end + 8] != chip ) {
            gameBoard[end] = -1;
            freeMoves.add(end);

            return true;
        }
        return false;
    }

    public boolean isPlayerWhitesTurn(){
        return playerWhite.getTurn();
    }

    /**
     *Checks for free moves vertically in a forward direction in reference to one player only
     * */
    boolean verticalForwardFreeMove(int pos,int chip) {
        int counter = pos ;
        while(counter <= gameBoard.length) {
            if (gameBoard[counter] == 0 || gameBoard[counter] == -1) {
                end = counter;
                break;
            }
            counter = counter + 8;
        }
        if(gameBoard[end - 8] != chip ) {
            gameBoard[end] = -1;
            freeMoves.add(end);
            return true;
        }
        return false;
    }
    /**
     *Checks for free moves diagonally in a forward direction to the right in reference to one player only
     * */
    boolean diagonalForwardRightFreeMove(int pos, int chip){
        int counter = pos;
        while(counter <= gameBoard.length){
            if(gameBoard[counter] == 0 || gameBoard[counter] == -1){
                end = counter;
                break;
            }
            counter += 7;
        }
        if(gameBoard[end-7] != chip ){
            gameBoard[end] = -1;
            freeMoves.add(end);
            return  true;
        }
        return false;
    }
    /**
     *Checks for free moves diagonally in a forward direction to the left in reference to one player only
     * */
    boolean diagonalForwardLeftFreeMove(int pos, int chip){
        int counter = pos;
        while(counter <= gameBoard.length){
            if(gameBoard[counter] == 0 || gameBoard[counter] == -1){
                end = counter;
                break;
            }
            counter += 9;
        }
        if(gameBoard[end-9] != chip){
            gameBoard[end] = -1;
            freeMoves.add(end);
            return  true;
        }
        return false;
    }
    /**
     *Checks for free moves diagonally in a backward direction to the left in reference to one player only
     * */
    boolean diagonalBackwardLeftFreeMove(int pos, int chip){
        int counter = pos;
        while(counter >= 0){
            if(gameBoard[counter] == 0 || gameBoard[counter] == -1){
                end = counter;
                break;
            }
            counter -= 9;
        }
        if(gameBoard[end+9] != chip){
            gameBoard[end] = -1;
            freeMoves.add(end);
            return  true;
        }
        return false;
    }
    /**
     *Checks for free moves diagonally in a backward direction to the right in reference to one player only
     * */
    boolean diagonalBackwardRightFreeMove(int pos, int chip){
        int counter = pos;
        while(counter >= 0){
            if(gameBoard[counter] == 0 || gameBoard[counter] == -1){
                end = counter;
                break;
            }
            counter -= 7;
        }
        if(gameBoard[end+7] != chip){
            gameBoard[end] = -1;
            freeMoves.add(end);
            return  true;
        }
        return false;
    }

    boolean gameover(){
        long emptyfields = Arrays.stream(gameBoard).filter(i -> i == 0).count();
        if(emptyfields == 0 || turnsWithNoFreeMove == 2) {
            return true;
        }
        return false;
    }
    Player getWinner() {
        if(whiteChips < blackChips) {
            return  playerBlack;
        }
        else if(blackChips < whiteChips) {
            return  playerWhite;
        }
        return null;
    }
    public String toString(){
        String s = "";
        for(int j = 0; j < gameBoard.length; j++){
            if(j%8 == 0 && gameBoard[j] == 0) s += "\n" ;
            s = s + gameBoard[j];
        }
        return s;
    }

}

/*while(!gameover) {
    move(i);
        }

if(gameover) {
    Player winner = getwinner();
    if(winner == null) {
        tie()
        }
        }*/
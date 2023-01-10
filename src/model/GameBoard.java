package model;

import java.util.*;

public class GameBoard extends Thread {
    /**
     * freeMove represents the places on the board which is possible for a player to make a move
     */


    int turnsWithNoFreeMove = 0;
    public int freeMove = 0;
    /**
     * each value in lowerBoundHorizontal is the left side boundary on the board which should not be
     * exceeded when looking for horizontal freeMoves
     */
    int[] lowerBoundHorizontal = {0, 8, 16, 24, 32, 40, 48, 56};
    /**
     * each value in higherBoundHorizontal is the left side boundary on the board which should not be
     * exceeded when looking for horizontal freeMoves
     */
    int[] HigherBoundHorizontal = {7, 15, 23, 31, 39, 47, 55, 63};

    ArrayList<DirectionForFreeMove> directionForFreeMoves = new ArrayList<>();
    /**
     * playerWhite is an instance of Player which represents the white chip
     */

    Player playerWhite = new Player();
    /**
     * playerWhite is an instance of Player which represents the black chip
     */
    Player playerBlack = new Player();
    /**
     * The Hashset is used to store the freeMoves
     */
    public List<Integer> freeMoves = new ArrayList<>();

    private int score = 0;

    public List<Integer> getFreeMoves(){
        return freeMoves;
    }

    public  GameBoard(ArrayList<Integer> freeMoves){
        this.freeMoves = freeMoves;
    }

    public void run(){

    }
    /**
     * The board of the game is represented with a single dimensional array with 64 elements
     */
    public int[] gameBoard = new int[64];
    private long blackChips =  Arrays.stream(gameBoard).filter(i -> i == 2).count();
    private long whiteChips =  Arrays.stream(gameBoard).filter(i -> i == 1).count();

    int temp = 0;
    int end = 0;

    /**
     * At the begining of the game the positions 26,27,35 and 36 are filled with 1,2,2,1 respectively
     * 1 represents the white and 2 the black
     */
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
    //public int[] temp_gameBoard = new int[gameBoard.length];

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
        if (freeMoves.isEmpty()) {
            switchTurns();
            turnsWithNoFreeMove++;
        }
        if (freeMoves.contains(pos)) {
            gameBoard[pos] = chip;
            for(int i = 0;i < directionForFreeMoves.size();i++) {
                if(directionForFreeMoves.get(i).position == pos) {
                    switch (directionForFreeMoves.get(i).direction) {
                        case "horizontalBackward":
                            iterateToFlip(1, pos , chip, oppositeChip, true);
                            break;

                        case "horizontalForward":
                            iterateToFlip(1, pos , chip, oppositeChip, false);
                            break;

                        case "verticalForward":
                            iterateToFlip(8, pos, chip, oppositeChip, false);
                            break;
                        case "verticalBackward":
                            iterateToFlip(8, pos, chip, oppositeChip, true);
                            break;

                        case "diagonalForwardRight":
                            iterateToFlip(9, pos, chip, oppositeChip, false);
                            break;

                        case "diagonalForwardLeft":
                            iterateToFlip(7, pos, chip, oppositeChip, false);
                            break;
                        case "diagonalBackwardLeft":
                            iterateToFlip(9, pos, chip, oppositeChip, true);
                            break;
                        case "diagonalBackwardRight":
                            iterateToFlip(7, pos, chip, oppositeChip, true);
                            break;


                    }
                }
            }

            /*if (verticalForwardFreeMove(pos, chip)) {
                iterateToFlip(8, pos, chip, oppositeChip, false);
            }
            if (verticalBackwardFreeMove(pos, chip)) {
                iterateToFlip(8, pos, chip, oppositeChip, true);
            }
            if (diagonalForwardRightFreeMove(pos, chip)) {
                iterateToFlip(7, pos, chip, oppositeChip, false);
            }
            if (diagonalBackwardRightFreeMove(pos, chip)) {
                iterateToFlip(7, pos, chip, oppositeChip, true);
            }
            if (diagonalForwardLeftFreeMove(pos, chip)) {
                iterateToFlip(9, pos, chip, oppositeChip, true);
            }
            if (diagonalBackwardLeftFreeMove(pos, chip)) {
                iterateToFlip(9, pos, chip, oppositeChip, false);
            }
            if (horizontalForwardFreeMove(pos, chip)) {
                    iterateToFlip(1, pos, chip, oppositeChip, false);
            }
            if (horizontalBackwardFreeMove(pos, chip)) {
                    iterateToFlip(1, pos, chip, oppositeChip, true);
            }*/
            switchTurns();
            freeMoves.clear();
            directionForFreeMoves.clear();
            turnsWithNoFreeMove = 0;
        }
    }

    void iterateToFlip(int steps,int pos,int chip,int oppositeChip,boolean isIncreasing){
        if(!isIncreasing) {
            steps = steps * -1;
        }
        pos = pos + steps;
        while(gameBoard[pos] == oppositeChip) {

            gameBoard[pos] = chip;
            pos = pos + steps;
        }

    }

    public void cpuMove() {
        int chip = playerBlack.getTurn() ? 2 : 1;
        if (playerBlack.getTurn()) {
            iterateGameBoardForFreeMove(chip);
        }
        if (freeMoves.isEmpty()) {
            switchTurns();
            System.out.println("moves empty");
        } else {
            int size = freeMoves.size();
            int anyMove = new Random().nextInt(size);
            int chosenMove = freeMoves.get(anyMove);
            move(chosenMove);
            System.out.println("playing " + chosenMove);

        }
    }

    void
    switchTurns() {
        if (!playerWhite.getTurn()) {
            playerWhite.setTurn(true);
            playerBlack.setTurn(false);
        } else {
            playerWhite.setTurn(false);
            playerBlack.setTurn(true);
        }
    }

    /**
     * This method flips all the chips surrounded by the opponent chip when called in move
     */
    /*void iterateToFlip(int steps, int pos, int chip, int oppositeChip, boolean isIncreasing) {
        tempPos = pos;
        if (!isIncreasing) {
            steps = steps * -1;
        }
        System.out.println("oppositechip " + oppositeChip);
         pos = pos + steps;        //Goes to the first chip to be flipped. If not while loop ends without even starting.
        System.out.printf("while %d == %d ", gameBoard[pos], oppositeChip);

        while (gameBoard[pos] == oppositeChip) {
            //gameBoard[pos] = chip;

            pos = pos + steps;
        }
        pos = tempPos;
    }*/

    /**
     * This method collects all the possible freeMoves upwards,downward,sideways and diagonal
     * The method is called in {@link controller.ControllerOfGame } to get the free moves and represent with a circle
     *
     * @param chip is either 1 or 2 which represents the white or black player respectively
     */
    public void iterateGameBoardForFreeMove(int chip) {

        for (int i = 0; i < gameBoard.length; i++) {
            if (gameBoard[i] == chip) {
                horizontalBackwardFreeMove(i, chip);
                horizontalForwardFreeMove(i, chip);
                verticalForwardFreeMove(i, chip);
                verticalBackwardFreeMove(i, chip);
                diagonalForwardRightFreeMove(i, chip);
                diagonalBackwardRightFreeMove(i, chip);
                diagonalBackwardLeftFreeMove(i, chip);
                diagonalForwardLeftFreeMove(i, chip);
            }
        }
        //freeMoves.stream().forEach(System.out::println);
        //System.out.println("[" + this + "]");
    }


    /**
     * Checks for free moves horizontally in a backward direction in reference to one player only
     */
    boolean horizontalBackwardFreeMove(int pos, int chip) {

        int oppositeChip = chip == 1 ? 2 : 1;
        for (int i = 0; i < lowerBoundHorizontal.length; i++) {
            if (pos >= lowerBoundHorizontal[i]) {
                end = lowerBoundHorizontal[i];
            }
        }
        for (int i = pos; i >= end; i--) {
            if (gameBoard[i] == 0 ) {
                temp = i;
                break;
            }
        }
        if(temp + 1 <64) {
            if (gameBoard[temp + 1] == oppositeChip ) {
                freeMoves.add(temp);
                directionForFreeMoves.add(new DirectionForFreeMove(temp,"horizontalBackward"));
                return true;
            }
        }
        return false;

    }

    /**
     * Checks for free moves horizontally in a forward direction in reference to one player only
     */
    boolean horizontalForwardFreeMove(int pos, int chip) {
        int oppositeChip = chip == 1 ? 2 : 1;
        for (int i = 0; i < HigherBoundHorizontal.length; i++) {
            if (pos <= HigherBoundHorizontal[i]) {
                end = HigherBoundHorizontal[i];
            }
        }
        for (int i = pos; i <= end; i++) {
            if (gameBoard[i] == 0 ) {
                temp = i;
                break;
            }
        }
        if(temp-1 >= 0)
            if (gameBoard[temp - 1] == oppositeChip ) {
                directionForFreeMoves.add(new DirectionForFreeMove(temp,"horizontalForward"));
                freeMoves.add(temp);
                return true;
            }

        return false;
    }

    /**
     * Checks for free moves vertically in a backward direction in reference to one player only
     */
    boolean verticalBackwardFreeMove(int pos, int chip) {
        int counter = pos;
        while (counter >= 0 && counter < 64) {
            if (gameBoard[counter] == 0 ) {
                end = counter;
                break;
            }
            counter = counter - 8;
        }
        if(end+8 < 64) {
        if (gameBoard[end + 8] != chip && gameBoard[end + 8] != 0) {
                directionForFreeMoves.add(new DirectionForFreeMove(end,"verticalBackward"));
                freeMoves.add(end);

                return true;
            }
        }
        return false;
    }

    public boolean isPlayerWhitesTurn() {
        return playerWhite.getTurn();
    }

    /**
     * Checks for free moves vertically in a forward direction in reference to one player only
     */
    boolean verticalForwardFreeMove(int pos, int chip) {
        int counter = pos;

            while (counter < gameBoard.length) {
                if (gameBoard[counter] == 0 ) {
                    end = counter;
                    break;
                }
                counter = counter + 8;
            }

        if(end - 8 >= 0 )
            if (gameBoard[end - 8] != chip ) {
                directionForFreeMoves.add(new DirectionForFreeMove(end,"verticalForward"));
                freeMoves.add(end);
                return true;
            }

        return false;
    }


    /**
     * Checks for free moves diagonally in a forward direction to the right in reference to one player only
     */
    boolean diagonalForwardRightFreeMove(int pos, int chip) {
        int counter = pos;
        while (counter < gameBoard.length) {
            if (gameBoard[counter] == 0 ) {
                end = counter;
                break;
            }
            counter += 9;
        }
        if(end-9 >= 0) {
            if (gameBoard[end - 9] != chip ) {
                directionForFreeMoves.add(new DirectionForFreeMove(end,"diagonalForwardRight"));
                freeMoves.add(end);
                return true;
            }
        }
        return false;
    }

    /**
     * Checks for free moves diagonally in a forward direction to the left in reference to one player only
     */
    boolean diagonalForwardLeftFreeMove(int pos, int chip) {
        int counter = pos;
        while (counter < gameBoard.length) {
            if (gameBoard[counter] == 0) {
                end = counter;
                break;
            }
            if(counter+7 < gameBoard.length) {
                counter += 7;
            }
        }
        if(end-7 >= 0) {
            if (gameBoard[end - 7] != chip) {
                freeMoves.add(end);
                directionForFreeMoves.add(new DirectionForFreeMove(end,"diagonalForwardLeft"));
                return true;
            }
        }
        return false;
    }

    /**
     * Checks for free moves diagonally in a backward direction to the left in reference to one player only
     */
    boolean diagonalBackwardLeftFreeMove(int pos, int chip) {
        int counter = pos;
        while (counter >= 0) {
            if (gameBoard[counter] == 0 ) {
                end = counter;
                break;
            }
            counter -= 9;
        }
        if(9+end < 64) {
            if (gameBoard[end + 9] != chip ) {
                freeMoves.add(end);
                directionForFreeMoves.add(new DirectionForFreeMove(end,"diagonalBackwardLeft"));
                return true;
            }
        }
        return false;
    }

    /**
     * Checks for free moves diagonally in a backward direction to the right in reference to one player only
     */
    boolean diagonalBackwardRightFreeMove(int pos, int chip) {
        int counter = pos;
        while (counter >= 0) {
            if (gameBoard[counter] == 0) {
                end = counter;
                break;
            }
            counter -= 7;
        }
        if(end + 7 < 64) {
            if (gameBoard[end + 7] != chip ) {
                directionForFreeMoves.add(new DirectionForFreeMove(end,"diagonalBackwardRight"));
                freeMoves.add(end);
                return true;
            }
        }
        return false;
    }

    boolean gameover() {
        long emptyfields = Arrays.stream(gameBoard).filter(i -> i == 0).count();
        if (emptyfields == 0 || turnsWithNoFreeMove == 2) {
            return true;
        }
        return false;
    }

    Player getWinner() {
        if (whiteChips < blackChips) {
            return playerBlack;
        } else if (blackChips < whiteChips) {
            return playerWhite;
        }
        return null;
    }

    public int getWinnerScore() {
        if(gameover()) {
            if (playerWhiteScore() < playerBlackScore()) {
                return playerBlackScore();
            }
            return playerWhiteScore();
        }
        return playerBlackScore();
    }

    public int playerWhiteScore(){
        score = 0;
        for(int i = 0; i< gameBoard.length; i++){
            if(gameBoard[i] == 1){
                score++;
            }
        }
        //System.out.println("playerWhite:" + score1);
        return score;

    }

    public int playerBlackScore(){
        score = 0;
        for(int i = 0; i< gameBoard.length; i++){
            if(gameBoard[i] == 2){
                score ++;
            }
        }
        //System.out.println("playerBlack:" + score2);
        return score;
    }
    public String toString() {
        String s = "";
        for (int j = 0; j < gameBoard.length; j++) {
            if (j % 8 == 0 && gameBoard[j] == 0) s += "\n";
            s = s + gameBoard[j];
        }
        return s;
    }
    /*public  int getPlayerWhiteScore(){
        return (int)whiteChips;
    }

    public int getPlayerBlackScore(){
        return (int)blackChips;
    }*/

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
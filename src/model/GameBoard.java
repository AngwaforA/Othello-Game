package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GameBoard {
    /**
     * turnsWithNoFreeMoves represents the number of turns in which no player had a freeMove.
     * When turnsWithNoFreeMoves is >= 2 it means the game is over, because both players don't have any moves to play.
     */
    private int turnsWithNoFreeMove = 0;
    /**
     * each value in lowerBoundHorizontal is the left side boundary on the board which should not be
     * exceeded when looking for horizontalBackward  freeMoves else wrong moves will be calculated.
     */
    private final int[] lowerBoundHorizontal = {0, 8, 16, 24, 32, 40, 48, 56};
    /**
     * each value in higherBoundHorizontal is the left side boundary on the board which should not be
     * exceeded when looking for horizontalForward freeMoves else wrong moves will be calculated.
     */
    private final int[] HigherBoundHorizontal = {7, 15, 23, 31, 39, 47, 55, 63};
    /**
     * Inorder to know the direction in which the coins are to be flipped it is important to know on what type of
     * freeMove the player clicked on. So an ArrayList directionForFreeMove was created to store the freeMove and
     * the type of freeMove it is.
     * */
    private ArrayList<DirectionForFreeMove> directionForFreeMoves = new ArrayList<>();
    /**
     * playerWhite is an instance of Player which represents the white chip.
     */

    Player playerWhite = new Player();
    /**
     * playerWhite is an instance of Player which represents the black chip.
     */
    Player playerBlack = new Player();
    /**
     * The List freeMoves is used to store the freeMoves calculated from the method {@Link #iterateGameBoardForFreeMove(int) iterateGameBoardForFreeMove method}.
     */
    private  List<Integer> freeMoves = new ArrayList<>();
    /**
     * String is used to print the winner of the game.
     * */
    private String s = " ";
    /**
     * Score is the number of chip each player has during the whole game and is used to determine the winner.
     * */
    private int score = 0;
    /**
     * getFreeMoves is the method used to get the list of freeMoves calculated in the {@Link #iterateGameBoardForFreeMove(int) iterateGameBoardForFreeMove method}
     * */
    public List<Integer> getFreeMoves() {
        return freeMoves;
    }

    /**
     * The board of the game is represented with a single dimensional array with 64 elements
     */
    public int[] gameBoard = new int[64];
    /**
     * The gameBoard is being iterated and the position at which 2 is found is counted and stored in blackChips.
     * This is later on used to determine who won the game
     * */
    private final long blackChips = Arrays.stream(gameBoard).filter(i -> i == 2).count();
    /**
     * The gameBoard is being iterated and the position at which 1 is found is counted and stored in whiteChips.
     * This is later on used to determine who won the game
     * */
    private final long whiteChips = Arrays.stream(gameBoard).filter(i -> i == 1).count();
    /**
     * End represents any value in the higher- und lowerBoundHorizontal.
     * With this variable we can determine the point on a row where we stop checking for a forward- or backwardHorizontalFreeMove.
     * In so doing we do not exceed the bound on any row.*/
    int end = 0;

    /**
     * At the beginning of the game the positions 26,27,35 and 36 are filled with 1,2,2,1 respectively
     * 1 represents the white and 2 the black.
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
     * all the chips of the opponent which have been surrounded. In order to do this it calls
     * the {@link #iterateToFlip(int, int, int, int, boolean) iterateToFlip} method.
     * @param pos is the position where the current player clicks to make a move
     */

    public boolean move(int pos) {
        int chip = playerWhite.getTurn() ? 1 : 2;
        int oppositeChip = chip == 1 ? 2 : 1;
        iterateGameBoardForFreeMove(chip);
        if (freeMoves.isEmpty()) {
            switchTurns();
            turnsWithNoFreeMove++;
        }
        if (freeMoves.contains(pos)) {
            gameBoard[pos] = chip;
            for (int i = 0; i < directionForFreeMoves.size(); i++) {
                if (directionForFreeMoves.get(i).position == pos) {
                    switch (directionForFreeMoves.get(i).direction) {
                        case "horizontalBackward":
                            iterateToFlip(1, pos, chip, oppositeChip, true);
                            break;

                        case "horizontalForward":
                            iterateToFlip(1, pos, chip, oppositeChip, false);
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

            switchTurns();
            freeMoves.clear();
            directionForFreeMoves.clear();
            turnsWithNoFreeMove = 0;
            return true;
        }
        return false;
    }
    /**
     *The cpuMove is implemented so that the user can play against the computer.
     * The cpuMove is based on probability. The user has the first move and after that the  Computer can make his move.
     * The computer makes  a move by selecting any random freeMove from freeMoves and places his chip(1 or 2) at that position
     * in the grid.
     * */
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
            /*try {
                System.out.println("Going to sleep");
                Thread.sleep(1000);
                System.out.println("Waking up");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
            move(chosenMove);
            System.out.println("playing " + chosenMove);
        }
    }
    /**
     *switchTurns enables each player to have a playing turn ones his opponent has placed a chip
     * Or for the next player to have his turn when the current player has no available freeMoves has.
     * */
    void switchTurns() {
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
     * @param steps is the number of chips to be flipped by the current player
     * @param pos is the position on which the player clicks or the computer chooses
     * @param chip is the value (in the model 1 or 2 and in the view white or black) of the current player
     * @param oppositeChip the value of the opposing player
     * @param isIncreasing the direction in which the gameBoard is to be iterated to flip the chips.
     *                     False for backwards (decreasing) and True for forward(increasing).
     */
    void iterateToFlip(int steps, int pos, int chip, int oppositeChip, boolean isIncreasing) {
        if (!isIncreasing) {
            steps = steps * -1;
        }
        pos = pos + steps;

        while (pos >= 0 && pos < gameBoard.length &&gameBoard[pos] == oppositeChip ) {
            gameBoard[pos] = chip;
            pos = pos + steps;
        }
    }
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
     * Checks for free moves horizontally in a backward direction in reference to one player at a time only
     * This method uses an Array which contains the boundaries on the right {0,8,16,24,32,40,48,56}.
     * The current position used in calculating the free moves should always be bigger than or equal to the boundary found on the same row.
     * In this way no wrong moves on a different row will be calculated
     * @param pos is the position with reference to which the free move is being calculated
     * @param chip the value of the current chip(1 or 2)
     */
    boolean horizontalBackwardFreeMove(int pos, int chip) {
        int temp = 0;
        int oppositeChip = chip == 1 ? 2 : 1;
        for (int i = 0; i < lowerBoundHorizontal.length; i++) {
            if (pos >= lowerBoundHorizontal[i]) {
                end = lowerBoundHorizontal[i];
            }
        }
        for (int i = pos-1; i >= end; i--) {
            if (gameBoard[i] == 0) {
                temp = i;
                break;
            }
        }
        if (temp + 1 < 64 && temp >= end) {
            if (gameBoard[temp + 1] == oppositeChip) {
                freeMoves.add(temp);
                directionForFreeMoves.add(new DirectionForFreeMove(temp, "horizontalBackward"));
                return true;
            }
        }
        return false;

    }

    /**
     * Checks for free moves horizontally in a forward direction in reference to one player at a time only
     * This method uses an Array which contains the boundaries on the right {7,15,23,31,39,47,55,63}.
     * The current position used in calculating the free moves should never be bigger than or equal to the boundary found on the same row.
     * In this way no wrong moves on a different row will be calculated
     * @param pos is the position with reference to which the free move is being calculated
     * @param chip the value of the current chip(1 or 2)
     */
    boolean horizontalForwardFreeMove(int pos, int chip) {
        int temp = 0;
        int oppositeChip = chip == 1 ? 2 : 1;
        for (int i = 0; i < HigherBoundHorizontal.length; i++) {
            if (pos <= HigherBoundHorizontal[i]) {
                end = HigherBoundHorizontal[i];
            }
        }
        for (int i = pos+1; i <= end; i++) { //The condition with the %8 != 7
            if (gameBoard[i] == 0) {
                temp = i;
                break;
            }
        }
        if (temp - 1 >= 0 )
            if (gameBoard[temp - 1] == oppositeChip) {
                directionForFreeMoves.add(new DirectionForFreeMove(temp, "horizontalForward"));
                freeMoves.add(temp);
                return true;
            }

        return false;
    }

    /**
     * Checks for free moves vertically in a backward direction in reference to one player at a time only
     * The current position used in calculating the free moves should never be bigger than or equal to the boundary found on the same row.
     * In this way no wrong moves on a different row will be calculated
     * @param pos is the position with reference to which the free move is being calculated
     * @param chip the value of the current chip(1 or 2)
     */
    boolean verticalBackwardFreeMove(int pos, int chip) {
        int counter = pos;
        while (counter >= 0 && counter < 64) {
            if (gameBoard[counter] == 0) {
                end = counter;
                break;
            }
            counter = counter - 8;
        }
        if (end + 8 < 64) {
            if (gameBoard[end + 8] != chip && gameBoard[end + 8] != 0) {
                directionForFreeMoves.add(new DirectionForFreeMove(end, "verticalBackward"));
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

        while (counter < gameBoard.length ) {
            if (gameBoard[counter] == 0) {
                end = counter;
                break;
            }
            counter = counter + 8;
        }

        if (end - 8 >= 0)
            if (gameBoard[end - 8] != chip &&gameBoard[end - 8] != 0) {
                directionForFreeMoves.add(new DirectionForFreeMove(end, "verticalForward"));
                freeMoves.add(end);
                return true;
            }

        return false;
    }


    /**
     * Checks for free moves diagonally in a forward direction to the right in reference to one player only
     */
    boolean diagonalForwardRightFreeMove(int pos, int chip) {
        int counter = pos+9;
        while (counter < gameBoard.length && counter%8 != 0) {
            if (gameBoard[counter] == 0) {
                end = counter;
                break;
            }
            counter += 9;
        }
        if (end - 9 >= 0) {
            if (gameBoard[end - 9] != chip && gameBoard[end - 9] != 0) {
                directionForFreeMoves.add(new DirectionForFreeMove(end, "diagonalForwardRight"));
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
        int counter = pos+7;
        while (counter < gameBoard.length && (counter )%8 != 7) {
            if (gameBoard[counter] == 0 ) {
                end = counter;
                break;
            }
            counter += 7;
        }
        if (end - 7 >= 0) {
            if (gameBoard[end - 7] != chip && gameBoard[end - 7] != 0) {
                freeMoves.add(end);
                directionForFreeMoves.add(new DirectionForFreeMove(end, "diagonalForwardLeft"));
                return true;
            }
        }
        return false;
    }

    /**
     * Checks for free moves diagonally in a backward direction to the left in reference to one player only
     */
    boolean diagonalBackwardLeftFreeMove(int pos, int chip) {
        int counter = pos - 9;
        while (counter >= 0 && counter%8 != 7) {
            if (gameBoard[counter] == 0) {
                end = counter;
                break;
            }
            counter -= 9;
        }
        if (9 + end < 64) {
            if (gameBoard[end + 9] != chip && gameBoard[end + 9] != 0) {
                freeMoves.add(end);
                directionForFreeMoves.add(new DirectionForFreeMove(end, "diagonalBackwardLeft"));
                return true;
            }
        }
        return false;
    }

    /**
     * Checks for free moves diagonally in a backward direction to the right in reference to one player only
     */
    boolean diagonalBackwardRightFreeMove(int pos, int chip) {
        int counter = pos-7;
        while (counter >= 0 && counter%8 != 0) {
            if (gameBoard[counter] == 0) {
                end = counter;
                break;
            }
            counter -= 7;
        }
        System.out.println("Yes");
        if (end + 7 < 64) {
            if (gameBoard[end + 7] != chip && gameBoard[end + 7] != 0) {
                directionForFreeMoves.add(new DirectionForFreeMove(end, "diagonalBackwardRight"));
                freeMoves.add(end);
                return true;
            }
        }
        return false;
    }

    public boolean gameover() {
        long emptyfields = Arrays.stream(gameBoard).filter(i -> i == 0).count();
        return emptyfields == 0 || turnsWithNoFreeMove == 2;
    }

    public String getWinner() {
        if(gameover()) {
            if (whiteChips < blackChips) {
                s += "playerBlack";
            } else if (blackChips == whiteChips) {
                s += "It's a Tie";
            }
            else {
                s = "playerWhite";
            }
        }
        return  s;
    }

    public int getWinnerScore() {
        if (gameover()) {
            if (playerWhiteScore() < playerBlackScore()) {
                return playerBlackScore();
            }
            return playerWhiteScore();
        }
        return playerBlackScore();
    }

    public int playerWhiteScore() {
        score = 0;
        for (int i = 0; i < gameBoard.length; i++) {
            if (gameBoard[i] == 1) {
                score++;
            }
        }
        //System.out.println("playerWhite:" + score1);
        return score;

    }

    public int playerBlackScore() {
        score = 0;
        for (int i = 0; i < gameBoard.length; i++) {
            if (gameBoard[i] == 2) {
                score++;
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
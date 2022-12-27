package model;

import java.util.HashSet;

public class GameBoard {
    int freeMove = 0;
    int[] lowerBoundHorizontal = {0,8,16,24,32,40,48,56};
    int[] HigherBoundHorizontal = {7,15,23,31,39,47,55,63};
    Player playerWhite = new Player();
    Player playerBlack = new Player();
    private HashSet<Integer> freeMoves = new HashSet<>();
    private int gameBoard[] = new int[64];
    int temp = 0;
    int end = 0;
    /**
     * At the begining of the game the positions 26,27,35 and 36 are filled with 1,2,2,1 respectively
     * 1 represents the white and 2 the black
     * */
    public GameBoard() {
        gameBoard[20] = 1;
        gameBoard[26] = 2;
        gameBoard[27] = 2;
        gameBoard[36] = 1;
        gameBoard[28] = 1;
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

    void move(int pos) {
        int chip = playerWhite.getTurn() ? 1 : 2;
        int oppositeChip = chip == 1 ? 2 : 1;
        iterateGameBoardForFreeMove(chip);
        if(freeMoves.isEmpty()) {

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
            }
            if (verticalBackwardFreeMove(pos, chip)) {
                iterateToFlip(8,pos,chip,oppositeChip,true);
            }
            if(diagonalForwardRightFreeMove(pos,chip)) {
                iterateToFlip(7,pos,chip,oppositeChip,true);
            }
            freeMoves.clear();
        }


    }
    void iterateToFlip(int steps, int pos, int chip, int oppositeChip, boolean isIncreasing){
        if(!isIncreasing) {
            steps = steps * -1 ;
        }
        while(gameBoard[pos] == oppositeChip) {
            gameBoard[pos] = chip;
            pos = pos + steps;
        }
    }


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
    }

    boolean horizontalBackwardFreeMove(int pos,int chip) {

        int oppositeChip = chip == 1 ? 2 : 1;
        for(int i = 0; i < lowerBoundHorizontal.length;i++) {
            if(pos >= lowerBoundHorizontal[i]) {
                end = lowerBoundHorizontal[i];
            }
        }
        for(int i = pos ; i >= end ;i--) {
            if(gameBoard[i] == 0) {
                temp = i;
                break;
            }
        }
        if(gameBoard[temp+1] == oppositeChip) {
            freeMove = temp;
            freeMoves.add(temp);
            return true;
        }
        return false;
    }
    boolean horizontalForwardFreeMove(int pos,int chip) {
        int oppositeChip = chip == 1 ? 2 : 1;
        for(int i = 0; i < HigherBoundHorizontal.length;i++) {
            if(pos <= HigherBoundHorizontal[i]) {
                end = HigherBoundHorizontal[i];
            }
        }
        for(int i = pos ; i <= end ;i++) {
            if(gameBoard[i] == 0) {
                temp = i;
                break;
            }
        }
        if(gameBoard[temp-1] == oppositeChip) {
            freeMove = temp;
            freeMoves.add(temp);
            return true;
        }
        return false;
    }
    boolean verticalBackwardFreeMove(int pos,int chip) {
        int counter = pos ;
        while(counter >= 0) {
            if (gameBoard[counter] == 0) {
                end = counter;
                break;
            }
            counter = counter - 8;
        }
        if(gameBoard[end + 8] != chip ) {
            freeMove = end;
            freeMoves.add(end);

            return true;
        }
        return false;
    }
    boolean verticalForwardFreeMove(int pos,int chip) {
        int counter = pos ;
        while(counter <= gameBoard.length) {
            if (gameBoard[counter] == 0) {
                end = counter;
                break;
            }
            counter = counter + 8;
        }
        if(gameBoard[end - 8] != chip ) {
            freeMove = end;
            freeMoves.add(end);
            return true;
        }
        return false;
    }

    boolean diagonalForwardRightFreeMove(int pos, int chip){
        int end = 0;
        int counter = pos;
        while(counter <= gameBoard.length){
            if(gameBoard[counter] == 0){
                end = counter;
                break;
            }
            counter += 7;
        }
        if(gameBoard[end-7] != chip ){
            freeMove = end;
            freeMoves.add(end);
            return  true;
        }
        return false;
    }

    boolean diagonalForwardLeftFreeMove(int pos, int chip){
        int counter = pos;
        while(counter <= gameBoard.length){
            if(gameBoard[counter] == 0){
                end = counter;
                break;
            }
            counter += 9;
        }
        if(gameBoard[end-9] != chip){
            freeMove = end;
            freeMoves.add(end);
            return  true;
        }
        return false;
    }

    boolean diagonalBackwardLeftFreeMove(int pos, int chip){
        int counter = pos;
        while(counter >= 0){
            if(gameBoard[counter] == 0){
                end = counter;
                break;
            }
            counter -= 9;
        }
        if(gameBoard[end+9] != chip){
            freeMove = end;
            freeMoves.add(end);
            return  true;
        }
        return false;
    }
    boolean diagonalBackwardRightFreeMove(int pos, int chip){
        int counter = pos;
        while(counter >= 0){
            if(gameBoard[counter] == 0){
                end = counter;
                break;
            }
            counter -= 7;
        }
        if(gameBoard[end+7] != chip){
            freeMove = end;
            freeMoves.add(end);
            return  true;
        }
        return false;
    }

}

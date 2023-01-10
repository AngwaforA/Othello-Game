package model;

public class Main {
    public static void main(String[] args) {
        GameBoard gameBoard = new GameBoard();
        gameBoard.iterateGameBoardForFreeMove(2);
        gameBoard.cpuMove();

    }
                    /*if (horizontalBackwardFreeMove(i, chip)) {
                    System.out.println("pos: " + i + "horizontalBackwardFreeMove:" + freeMove);
                }
                if (horizontalForwardFreeMove(i, chip)) {
                    System.out.println("pos: " + i + "horizontalForwardFreeMove:" + freeMove);
                }
                if (verticalBackwardFreeMove(i, chip)) {
                    System.out.println("pos: " + i + " verticalBackwardFreeMove:" + freeMove);
                }
                if (verticalForwardFreeMove(i, chip)) {
                    System.out.println("pos: " + i + "verticalForwardFreeMove:" + freeMove);
                }
                if (diagonalForwardRightFreeMove(i, chip)) {
                    System.out.println("pos: " + i + "diagonalForwardRightFreeMove:" + freeMove);
                }
                if (diagonalBackwardRightFreeMove(i, chip)) {
                    System.out.println("pos: " + i + "diagonalBackwardRightFreeMove:" + freeMove);
                }
                if (diagonalBackwardLeftFreeMove(i, chip)) {
                    System.out.println("pos: " + i + "diagonalBackwardLeftFreeMove:" + freeMove);
                }
                if (diagonalForwardLeftFreeMove(i, chip)) {
                    System.out.println("pos: " + i + "diagonalForwardLeftFreeMove:" + freeMove);
                }*/
}
package controller;

import model.GameBoard;
import view.IView;

import java.util.ArrayList;

public class ControllerOfGame implements IController {
    IView view;
    GameBoard game;

    public ControllerOfGame(IView view) {
        this.view = view;
        this.game = new GameBoard();
    }

    @Override
    public void nextFrame() {
        game.iterateGameBoardForFreeMove(game.isPlayerWhitesTurn() ? 1 : 2);
        view.drawBoard(game.gameBoard, (ArrayList<Integer>) game.getFreeMoves());
        view.displayScore(game.playerWhiteScore(), game.playerBlackScore());
        //view.displayScore(game.getWinner(), game.getWinnerScore());
    }

    @Override
    public void userInput() {
        view.mouseAction(game.gameBoard);
        //game.move(game.freeMove);
        game.cpuMove();
        nextFrame();
    }

    public  void play(int position) {
            game.move(position);
            //game.cpuMove();   //Implement Thread for cpuMove in GameBoard class
            nextFrame();

    }
}

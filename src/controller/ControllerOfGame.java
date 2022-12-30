package controller;

import model.GameBoard;
import view.IView;

public class ControllerOfGame implements IController{
    IView view;
    GameBoard game;
    int i;

    public ControllerOfGame(IView view){
        this.view = view;
        this.game = new GameBoard();
    }

    @Override
    public void nextFrame() {
        view.drawBoard(game.gameBoard);
    }

    @Override
    public void mouseAction() {

    }

    @Override
    public void drawFreeMove() {
        for(int i = 0; i<game.gameBoard.length; i++){
            if(game.gameBoard[i] == -1){
                view.representFreeMove(game.gameBoard);
            }
        }
    }
}

package controller;

import model.GameBoard;
import view.IView;

public class ControllerOfGame implements IController{
    IView view;
    GameBoard game;

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
}

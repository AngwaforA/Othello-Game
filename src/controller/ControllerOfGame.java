package controller;

import model.GameBoard;
import view.IView;

import java.util.ArrayList;

public class ControllerOfGame extends  Thread implements IController {
    IView view;
    GameBoard game;
    GameState state = GameState.TITLE_SCREEN;

    public ControllerOfGame(IView view) {
        this.view = view;
        this.game = new GameBoard();
    }

    @Override
    public void nextFrame() {
        switch (state){
            case TITLE_SCREEN: {
                view.drawTitleScreen();
                state = GameState.GAME_SCREEN;
                break;
            }
            case GAME_SCREEN: {
                game.iterateGameBoardForFreeMove(game.isPlayerWhitesTurn() ? 1 : 2);
                view.displayScore(game.playerWhiteScore(), game.playerBlackScore());
                view.drawBoard(game.gameBoard, (ArrayList<Integer>) game.getFreeMoves());
                if(game.gameover()) state = GameState.GAMEOVER;
                break;
            }
            case GAMEOVER:{
                state = GameState.GAMEOVER;
                game.gameover();
                view.drawGameOver(game.getWinner());
                break;
            }

        }

        //view.displayScore(game.getWinner(), game.getWinnerScore());
    }

    @Override
    public void userInput() {
        switch (state){
            case GAME_SCREEN : {
                view.mouseAction(game.gameBoard);
                //game.move(game.freeMove);
                nextFrame();
                System.out.println("complete");
                break;
            }
        }


        //game.cpuMove();  //Have to call move before cpuMove
        //nextFrame();
    }

    public void play(int position) {
        switch (state) {
            case GAME_SCREEN: {
                if (game.move(position)) {

                    game.cpuMove();   //Implement Thread for cpuMove in GameBoard class
                }
                nextFrame();
                break;
            }
        }

    }
    public void cpuMove(){
        game.cpuMove();
        nextFrame();
    }
}


/*package controller;

import model.BoardGame_KP;
import view.IView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ControllerOfGame implements IController {
    IView view;
    BoardGame_KP game;

    public ControllerOfGame(IView view) {
        this.view = view;
        this.game = new BoardGame_KP();
    }

    @Override
    public void nextFrame() {
        List<Integer> freeMoves = new ArrayList<>();
        freeMoves.addAll(game.getCurrentFreeMoves());
        view.displayScore(1, 1);
        view.drawBoard(game.getGameBoard(), freeMoves);
        //  view.drawBoard(game.gameBoard, (ArrayList<Integer>) game.getFreeMoves());
        //view.displayScore(game.getWinner(), game.getWinnerScore());
    }

    @Override
    public void userInput() {
        view.mouseAction(game.getGameBoard());
        //game.move(game.freeMove);
        nextFrame();
        System.out.println("complete");

        //game.cpuMove();  //Have to call move before cpuMove
        //nextFrame();
    }

    public void play(int position) {
        game.play(position);
        nextFrame();

    }

    public void cpuMove() {
     //   game.cpuMove();
        nextFrame();
    }
}*/

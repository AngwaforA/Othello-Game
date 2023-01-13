package view;

import controller.ControllerOfGame;
import controller.GameState;
import controller.IController;
import processing.core.PApplet;

import java.util.List;


public class GameView extends PApplet implements IView {
    public static void main(String[] args) {
        PApplet.main(GameView.class);
    }

    IController controller;
    final int xOffSet = 180;
    final int yOffSet = 60;
    final int sizeOfTile = 80;
    final int sizeOfBorder = 1;
    final int xDisc = sizeOfTile / 2;
    final int yDisc = sizeOfTile / 2;
    boolean mouseOnDisc = false;
    boolean locked = false;
    GameState state = GameState.TITLE_SCREEN;
    Board[] boards = new Board[64];

    public void settings() {
        super.size(1000, 750);
    }

    public void setup() {

        background(0);
        this.controller = new ControllerOfGame(this);
        controller.nextFrame();
    }

    @Override
    public void setUpBoard(int[] grid) {
        noStroke();
        colorMode(0, 35, 100, 25);
    }

    public void draw() {
        // controller.nextFrame();
    }
    public void drawTitleScreen(){
        background(0);
        fill(255);
        textSize(60);
        textAlign(CENTER);
        text("**Welcome to Othello**\n Press Enter Key To Start", width / 2, height / 2);
    }

    @Override
    public void drawGameOver(String winner) {
        background(0);
        fill(255);
        textSize(60);
        textAlign(CENTER);
        text("**GameOver**", width / 2, height / 2);
        text(winner + " won", width / 2, height / 2 + 70);
    }

    @Override
    public void drawBoard(int[] grid, List<Integer> freemoves) {
        if (state == GameState.GAME_SCREEN) {
            int edge_length = Math.round(sqrt(grid.length));
            int i = 0;
            int X, Y;
            int counter = 0;
            for (int y = 0; y < edge_length; y++) {
                Y = yOffSet + sizeOfBorder + y * (sizeOfTile + sizeOfBorder);
                for (int x = 0; x < edge_length; x++) {
                    X = xOffSet + sizeOfBorder + x * (sizeOfTile + sizeOfBorder);
                    int color = color(30 + log(grid[i] + 1) / log(2) * 10, 90, 80);
                    boards[counter] = new Board(X, Y, sizeOfTile, sizeOfTile, this, color, i);
                    boards[counter].display();
                    counter++;
                    if (grid[i] == 1) {
                        fill(color(255));
                        ellipse(X + sizeOfTile / 2, Y + sizeOfTile / 2, sizeOfTile - 1, sizeOfTile - 1);
                    }
                    if (grid[i] == 2) {
                        fill(color(0));
                        ellipse(X + sizeOfTile / 2, Y + sizeOfTile / 2, sizeOfTile - 1, sizeOfTile - 1);
                    }
                    if (freemoves.contains(i)) {
                        tint(255, 128);
                        ellipse(X + sizeOfTile / 2, Y + sizeOfTile / 2, sizeOfTile - 1, sizeOfTile - 1);
                        text(grid[i], X + sizeOfTile / 2 + 1, Y + sizeOfTile / 2 + 1);
                    }
                    i++;
                }
            }
        }
    }

    public void displayScore(int whiteScore, int blackScore) {
        if (state == GameState.GAME_SCREEN) {
            background(0);
            fill(255);
            textSize(40);
            textAlign(LEFT);
            text("White:" + whiteScore, 30, 80);
            textAlign(RIGHT);
            text("Black:" +blackScore, width - 30, 80);
        }
    }

    public void mouseClicked() {
        controller.userInput();

    }


    public void keyPressed() {
        if (keyCode == ENTER) {
            background(0);
            state = GameState.GAME_SCREEN;
            controller.nextFrame();
        }
    }

    @Override
    public void mouseAction(int[] grid) {
        if (state == GameState.GAME_SCREEN) {
            System.out.println(mouseX + ", " + mouseY);
            for (int i = 0; i < boards.length; i++) {
                int boardClicked = boards[i].mouseClicked(mouseX, mouseY);
                if (boardClicked != -1) {
                    System.out.println("Playing mouseAction");
                    controller.play(boardClicked);
                }

            }
        }
    }

}

package view;

import controller.ControllerOfGame;
import processing.core.PApplet;


public class GameView extends PApplet implements IView {
    public static void main(String[] args) {
        PApplet.runSketch(new String[]{""}, new GameView());
    }

    ControllerOfGame controller;
    final int xPos = 0;
    final int yPos = 0;
    final int xOffSet = 150;
    final int yOffSet = 50;
    final int sizeOfTile = 80;
    final int sizeOfBorder = 1;
    final int xDisc = sizeOfTile / 2;
    final int yDisc = sizeOfTile / 2;
    boolean mouseOnDisc = false;
    boolean locked = false;
    GameState state = GameState.TITLE_SCREEN;
    Board[] boards = new Board[64];

    public void settings() {
        super.size(1000, 700);

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
        //controller.nextFrame();
    }

    @Override
    public void drawBoard(int[] grid) {
        switch (state) {
            case TITLE_SCREEN -> {
                background(0);
                fill(255);
                textSize(60);
                textAlign(CENTER);
                text("**Welcome to Othello**\n Press Enter Key To Start", width / 2, height / 2);
            }
            case GAME_SCREEN -> {
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
                        // rect(X, Y, sizeOfTile, sizeOfTile, 15);
                        if (grid[i] == 1) {
                            fill(color(255));
                            ellipse(X + sizeOfTile / 2, Y + sizeOfTile / 2, sizeOfTile - 1, sizeOfTile - 1);
                            text(grid[i], X + sizeOfTile / 2 + 1, Y + sizeOfTile / 2 + 1);
                        }
                        if (grid[i] == 2) {
                            fill(color(0));
                            ellipse(X + sizeOfTile / 2, Y + sizeOfTile / 2, sizeOfTile - 1, sizeOfTile - 1);
                            text(grid[i], X + sizeOfTile / 2 + 1, Y + sizeOfTile / 2 + 1);
                        }
                        if (grid[i] == -1) {
                            tint(255, 128);
                            ellipse(X + sizeOfTile / 2, Y + sizeOfTile / 2, sizeOfTile - 1, sizeOfTile - 1);
                            text(grid[i], X + sizeOfTile / 2 + 1, Y + sizeOfTile / 2 + 1);
                        }
                        i++;
                    }
                }
            }
            case GAMEOVER -> {
                background(0);
                fill(255);
                textSize(60);
                textAlign(CENTER);
                text("**GameOver**", width / 2, height / 2);
            }
        }
    }

    public void mouseClicked() {
        //controller.userInput();
        if (state == GameState.GAME_SCREEN) {
            System.out.println(mouseX + ", " + mouseY);
            for (int i = 0; i < boards.length; i++) {
                if (boards[i].mouseClicked(mouseX, mouseY) != -1) {
                    System.out.println(boards[i].mouseClicked(mouseX, mouseY));
                    controller.play(boards[i].mouseClicked(mouseX, mouseY));
                }
            }
        }
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
        int edge_length = Math.round(sqrt(grid.length));
        int i = 0;
        int X, Y;
        for (int y = 0; y < edge_length; y++) {
            Y = yOffSet + sizeOfBorder + y * (sizeOfTile + sizeOfBorder);
            for (int x = 0; x < edge_length; x++) {
                X = xOffSet + sizeOfBorder + x * (sizeOfTile + sizeOfBorder);
               /* if (mouseX > X - sizeOfTile - 1 && mouseX < X + sizeOfTile - 1 &&
                        mouseY > Y - sizeOfTile - 1 && mouseY < Y + sizeOfTile - 1) {
                    System.out.println("row:"+y+"column"+x);
                    fill(255);
                    ellipse(X+sizeOfTile/2, Y+sizeOfTile/2,sizeOfTile-1, sizeOfTile-1);
                    mouseOnDisc = true;
                }
                if (mouseOnDisc) {
                    locked = true;
                    fill(0);
                    controller.userInput();
                }*/
            }
        }
    }

}

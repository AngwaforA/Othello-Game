package view;

import controller.ControllerOfGame;
import processing.core.PApplet;

public class GameView extends PApplet implements IView{
    public static  void  main(String[] args){PApplet.runSketch(new  String[]{""}, new GameView());}
    ControllerOfGame controller;
    final int xPos = 0;
    final int yPos = 0;
    final int xOffSet = 150;
    final int yOffSet = 50;
    final  int sizeOfTile = 80;
    final int sizeOfBorder = 1;

    public  void  settings(){
        super.size(1000, 700);
    }
    public void setup(){
        background(0);
        this.controller =new ControllerOfGame(this);
    }
    @Override
    public  void setUpBoard(int[] grid){
        noStroke();
        colorMode(0,35,100,25);
    }

    public void draw(){
        controller.nextFrame();
    }
    @Override
    public  void drawBoard(int[] grid){
        int edge_length = Math.round(sqrt(grid.length));
        int i = 0;
        int X, Y;
        for (int y = 0; y < edge_length; y++) {
            Y = yPos + yOffSet + sizeOfBorder + y * (sizeOfTile + sizeOfBorder);
            for (int x = 0; x < edge_length; x++) {
                X = xPos + xOffSet + sizeOfBorder + x * (sizeOfTile + sizeOfBorder);
                fill(color(30 + log(grid[i] + 1) / log(2) * 10, 90, 80));
                rect(X, Y, sizeOfTile, sizeOfTile, 15);
                if (grid[i] == 1) {
                    fill(color(255));
                    ellipse(X+sizeOfTile/2, Y+sizeOfTile/2,sizeOfTile-1, sizeOfTile-1);
                    text(grid[i], X + sizeOfTile / 2 + 1, Y + sizeOfTile / 2 + 1);
                }
                if (grid[i] == 2) {
                    fill(color(0));
                    ellipse(X+sizeOfTile/2, Y+sizeOfTile/2,sizeOfTile-1, sizeOfTile-1);
                    text(grid[i], X + sizeOfTile / 2 + 1, Y + sizeOfTile / 2 + 1);
                }
                i++;
            }

        }
    }
}

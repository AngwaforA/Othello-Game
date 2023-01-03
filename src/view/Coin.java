package view;

import processing.core.PGraphics;

public class Coin {
    int color;
    int x;
    int y;

    Coin(int x, int y, int color) {
        this.x = x;

    }


    public void draw(PGraphics g) {
        g.fill(color);
        g.ellipse(x, y, 100, 100);
    }
}

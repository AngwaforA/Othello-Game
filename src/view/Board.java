package view;

public class Board {
    int xPos = 0;
    int yPos = 0;
    int xOffSet = 150;
    int yOffSet = 50;
    final int sizeOfTile;
    final int sizeOfBorder;
    GameView view;
    int color;
    private final int position;

    public Board(int x, int y, int w, int h, GameView view, int color, int position) {
        this.color = color;
        this.xPos = x;
        this.yPos = y;
        this.xOffSet = w;
        this.yOffSet = h;
        sizeOfTile = 80;
        sizeOfBorder = 1;
        this.view = view;
        this.position = position;
    }

    public int mouseClicked(int x, int y) {
        if (x > xPos && x < xPos + xOffSet && y > yPos && y < yPos + yOffSet) {
            System.out.println("position :" +( position + 1));
            return position;
        }
        return -1;
    }


    public void display() {
        view.fill(color);
        view.rect(xPos, yPos, xOffSet, yOffSet);
    }
}

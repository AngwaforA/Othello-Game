package view;

import java.util.ArrayList;
import java.util.List;

public interface IView {
    void setUpBoard(int[] grid);
    //void drawBoard(int[] grid, )
    void drawBoard(int[] grid, List<Integer> freeMoves);

    void mouseAction(int[] grid);
    void drawTitleScreen();
    void drawGameOver(String winner);

    void displayScore(int score1, int score2);



    // void representFreeMove(int[] grid);
}

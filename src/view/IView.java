package view;

import java.util.ArrayList;

public interface IView {
    void setUpBoard(int[] grid);
    void drawBoard(int[] grid, ArrayList<Integer> freeMoves);
    void mouseAction(int[] grid);
     void  displayScore(int score1, int score2);


    // void representFreeMove(int[] grid);
}

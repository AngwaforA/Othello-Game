package view;

public interface IView {
    void setUpBoard(int[] grid);
    void drawBoard(int[] grid);

    void representFreeMove(int[] grid);
}

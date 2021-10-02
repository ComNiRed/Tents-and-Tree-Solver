package Model;

import java.util.Arrays;

public class Board {

    private Case[][] game;
    private int size;
    private int[] x; //x = une ligne
    private int[] y; //y = une colonne

    public Board(int size) {
        this.size = size;
        game = new Case[size][size];
        x = new int[size];
        y = new int[size];
        for (int i = 0; i < size; i++) {
            x[i] = 0;
            y[i] = 0;
            for (int j = 0; j < size; j++) {
                game[i][j] = Case.Empty;
            }
        }
    }

    public Case getCase(int x, int y) {
        return this.game[x][y];
    }

    public void setCase(int x, int y, Case type) {
        this.game[x][y] = type;
    }

    public int getSize() {
        return size;
    }

    public int getX(int i) {
        return this.x[i];
    }

    public void setX(int i, int value) {
        this.x[i] = value;
    }

    public int getY(int i) {
        return this.y[i];
    }

    public void setY(int i, int value) {
        this.y[i] = value;
    }

    public int[] getX() {
        return Arrays.copyOf(x,size);
    }

    public int[] getY() {
        return Arrays.copyOf(y,size);
    }

}

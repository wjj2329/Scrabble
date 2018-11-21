/**
 * Created by williamjones on 8/27/16.
 */
public class Coordinate {
    private int x;
    private int y;
    private char letter;

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }


    public void setY(int y) {
        this.y = y;
    }

    Coordinate(int x, int y, char letter) {

        this.x = x;
        this.y = y;
        this.letter = letter;
    }
}

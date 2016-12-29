/**
 * Created by williamjones on 8/27/16.
 */
public class Coordinate
{
    int x;
    int y;
    char letter;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Coordinate(int x, int y, char letter) {

        this.x = x;
        this.y = y;
        this.letter=letter;
    }
}

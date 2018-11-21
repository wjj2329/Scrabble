import java.util.ArrayList;

/**
 * Created by williamjones on 8/11/16.
 */
public class Board {
    private ArrayList<ArrayList<Character>> layout = new ArrayList<>();

    Board() {
        for (int i = 0; i < 8; i++) {
            layout.add(new ArrayList<>());
            for (int j = 0; j < 8; j++) {
                layout.get(i).add(null);
            }
        }
    }

    public ArrayList<ArrayList<Character>> getLayout() {
        return layout;
    }

    public void setLayout(ArrayList<ArrayList<Character>> layout) {
        this.layout = layout;
    }
}

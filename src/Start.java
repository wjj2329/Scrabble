
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by williamjones on 8/11/16.
 */

public class Start {
    public static int turns = 0;
    public  Ai player1;
    public  Ai player2;
    public ArrayList<Character> letters = new ArrayList<>();

    public  void createLetters() {
        for (int i = 0; i < 12; i++) {
            letters.add('E');
        }
        for (int i = 0; i < 9; i++) {
            letters.add('A');
            letters.add('I');
        }
        for (int i = 0; i < 8; i++) {
            letters.add('O');
        }
        for (int i = 0; i < 6; i++) {
            letters.add('N');
            letters.add('R');
            letters.add('T');
        }
        for (int i = 0; i < 4; i++) {
            letters.add('L');
            letters.add('S');
            letters.add('U');
            letters.add('D');
        }
        for (int i = 0; i < 3; i++) {
            letters.add('G');
        }
        for (int i = 0; i < 2; i++) {
            letters.add('B');
            letters.add('C');
            letters.add('M');
            letters.add('P');
            letters.add('F');
            letters.add('H');
            letters.add('V');
            letters.add('W');
            letters.add('Y');
        }
        letters.add('K');
        letters.add('J');
        letters.add('X');
        letters.add('Q');
        letters.add('Z');

    }

    public static void main(final String[] args) throws FileNotFoundException {
        Board game = new Board();
        FileReader myfile = new FileReader(args[0]);
        BoardGui boardGui;

        Start start=new Start();
        Set<String> dictionary = new HashSet<>();
        Scanner scan = new Scanner(myfile);
        while (scan.hasNext()) {
            dictionary.add(scan.next().toLowerCase());
        }
        boardGui=new BoardGui(game, start);
        boardGui.testing(game, start);
        start.createLetters();
        start.player1 = new Ai(dictionary);
        start.player2 = new Ai(dictionary);
        start.player1.getmesomeletters(start);
        start.player2.getmesomeletters(start);

    }

}



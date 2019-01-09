import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;


/**
 * Created by williamjones on 8/11/16.
 */

public class Ai {
    private Set<String> dictionary;
    private ArrayList<Character> letters = new ArrayList<>();
    private int greatestValue = 0;
    private StringBuilder greatestWord = new StringBuilder();
    private Coordinate beginningCor;
    private DIRECTION finalDirection;
    private Board game;
    public Ai(Board game){
        this.game=game;
    }

    public ArrayList<Character> getLetters() {
        return letters;
    }

    public void setLetters(ArrayList<Character> letters) {
        this.letters = letters;
    }

    public void takeTurn() throws Exception {
        System.out.println("my board looks like this");
        printBoard();
        System.out.println("The letters the ai has is this ");
        for (int i = 0; i < letters.size(); i++) {
            System.out.println(letters.get(i));
        }
        String word = WordToCreate();

        System.out.println("i create this word " + word);
        drawwordonboard(this.finalDirection, beginningCor, word);
        for (int i = 1; i < word.length(); i++) {
            for (int j = 0; j < letters.size(); j++) {
                if (letters.get(j) == word.charAt(i)) {
                    letters.remove(j);
                    break;
                }
            }
        }
        printBoard();
        greatestWord = null;
        greatestValue = 0;

    }

    public  void printBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (game.getLayout().get(i).get(j) == null) {
                    System.out.print("_");
                } else
                    System.out.print(game.getLayout().get(i).get(j));

            }
            System.out.println();
        }
    }

    private DIRECTION currentdirection = DIRECTION.LEFT;


    public enum DIRECTION {
        LEFT, RIGHT, UP, DOWN
    }

    void drawwordonboard(DIRECTION direction, Coordinate drawing, String word) {
        System.out.println("My direction is " + direction.toString());

        switch (direction) {
            case UP: {
                for (int i = 0; i < word.length(); i++) {
                    JButton button = BoardGui.chessBoardSquares[drawing.getX()][drawing.getY() - 1];
                    game.getLayout().get(drawing.getX()).set(drawing.getY() - i, word.charAt(i));
                    String buttonimage = "src/Letters/";
                    buttonimage += word.charAt(i);
                    buttonimage += ".jpg";
                    ImageIcon icon = new ImageIcon(buttonimage);
                    Image im = icon.getImage();
                    Image imag = im.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
                    button.setIcon(new ImageIcon(imag));
                }
                break;

            }
            case DOWN: {
                for (int i = 0; i < word.length(); i++) {
                    JButton button = BoardGui.chessBoardSquares[drawing.getX()][drawing.getY() + i];
                    game.getLayout().get(drawing.getY() + i).set(drawing.getX(), word.charAt(i));
                    String buttonimage = "src/Letters/";
                    buttonimage += word.charAt(i);
                    buttonimage += ".jpg";
                    ImageIcon icon = new ImageIcon(buttonimage);
                    Image im = icon.getImage();
                    Image imag = im.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
                    button.setIcon(new ImageIcon(imag));

                }
                break;

            }
            case LEFT: {
                for (int i = 0; i < word.length(); i++) {
                    JButton button = BoardGui.chessBoardSquares[drawing.getX() - i][drawing.getY()];
                    game.getLayout().get(drawing.getY()).set(drawing.getX() - i, word.charAt(i));
                    String buttonimage = "src/Letters/";
                    buttonimage += word.charAt(i);
                    buttonimage += ".jpg";
                    ImageIcon icon = new ImageIcon(buttonimage);
                    Image im = icon.getImage();
                    Image imag = im.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
                    button.setIcon(new ImageIcon(imag));

                }
                break;

            }
            case RIGHT: {
                for (int i = 0; i < word.length(); i++) {
                    JButton button = BoardGui.chessBoardSquares[drawing.getX() + i][drawing.getY()];
                    game.getLayout().get(drawing.getY()).set((drawing.getX() + i), word.charAt(i));
                    String buttonimage = "src/Letters/";
                    buttonimage += word.charAt(i);
                    buttonimage += ".jpg";
                    ImageIcon icon = new ImageIcon(buttonimage);
                    Image im = icon.getImage();
                    Image imag = im.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
                    button.setIcon(new ImageIcon(imag));

                }
                break;

            }
        }


    }


    public boolean drawable(String word, Coordinate currentCor) {

        try {
            for (int i = 0; i < word.length(); i++) {
                if (game.getLayout().get(currentCor.getY() + i).get(currentCor.getX()) != null && i != 0) {
                    break;
                }
                if (i == word.length() - 1) {
                    if (currentCor.getY() != 0) {
                        StringBuilder check = new StringBuilder();
                        int count = 1;
                        while (game.getLayout().get(currentCor.getY() - count).get(currentCor.getX()) != null) {
                            check.append(game.getLayout().get(currentCor.getY() - count).get(currentCor.getX()));
                            count++;
                        }
                        if (!containsword(word += check.toString())) {
                            break;
                        }
                    }
                    System.out.println("I make it down " + word);
                    this.currentdirection = DIRECTION.DOWN;
                    return true;
                }
            }
        } catch (Exception e) {
            //System.out.print("I go out of bounds");
        }
        try {
            for (int i = 0; i < word.length(); i++) {
                if (game.getLayout().get(currentCor.getY() - i).get(currentCor.getX()) != (null) && i != 0) {
                    break;
                }

                if (i == word.length() - 1) {
                    if (currentCor.getY() != 0) {
                        StringBuilder check = new StringBuilder();
                        int count = 1;
                        while (game.getLayout().get(currentCor.getY() + count).get(currentCor.getX()) != null) {
                            check.append(game.getLayout().get(currentCor.getY() + count).get(currentCor.getX()));
                            count++;
                        }
                        if (!containsword(word += check.toString())) {
                            break;
                        }
                    }
                    System.out.println("I make it up");
                    this.currentdirection = DIRECTION.UP;
                    return true;
                }
            }
        } catch (Exception e) {
            //System.out.print("I go out of bounds");
        }
        try {
            for (int i = 0; i < word.length(); i++) {
                if (game.getLayout().get(currentCor.getY()).get(currentCor.getX() - i) != (null) && i != 0) {
                    break;
                }

                if (i == word.length() - 1) {
                    System.out.println("I make it left");

                    if (currentCor.getX() != 0) {
                        StringBuilder check = new StringBuilder();
                        int count = 1;
                        while (game.getLayout().get(currentCor.getY()).get(currentCor.getX() + count) != null) {
                            check.append(game.getLayout().get(currentCor.getY()).get(currentCor.getX() + count));
                            count++;
                        }
                        if (!containsword(word += check.toString())) {
                            break;
                        }
                    }
                    this.currentdirection = DIRECTION.LEFT;
                    return true;
                }
            }
        } catch (Exception e) {
            // System.out.print("I go out of bounds");
        }
        try {
            for (int i = 0; i < word.length(); i++) {
            /*
            if(Main.game.getLayout().get(currentCor.getX()).size()<=currentCor.getX())
            {
                break;
            }
*/
                if (game.getLayout().get(currentCor.getY()).get(currentCor.getX() + i) != (null) && i != 0) {
                    break;
                }

                if (i == word.length() - 1) {
                    System.out.println("I make it right");

                    if (currentCor.getX() != 0) {
                        StringBuilder check = new StringBuilder();
                        int count = 1;
                        while (game.getLayout().get(currentCor.getY()).get(currentCor.getX() - count) != null) {
                            check.append(game.getLayout().get(currentCor.getY()).get(currentCor.getX() - count));
                            count++;
                        }
                        if (!containsword(word += check.toString())) {
                            break;
                        }
                    }
                    this.currentdirection = DIRECTION.RIGHT;
                    return true;
                }
            }
        } catch (Exception e) {
            //  System.out.print("I go out of bounds");

        }


        return false;
    }

    private int valueOfLetter(Character letter) throws Exception {
        switch (letter) {
            case 'A':
            case 'S':
            case 'U':
            case 'R':
            case 'O':
            case 'L':
            case 'I':
            case 'T':
            case 'N':
            case 'E':
                return 1;
            case 'D':
            case 'G':
                return 2;
            case 'M':
            case 'P':
            case 'B':
            case 'C':
                return 3;
            case 'V':
            case 'Y':
            case 'H':
            case 'W':
            case 'F':
                return 4;
            case 'K':
                return 5;
            case 'X':
            case 'J':
                return 8;
            case 'Q':
            case 'Z':
                return 10;
        }
        throw (new Exception("Invalid letter " + letter));
    }


    void getmesomeletters(Start start) {
        while (letters.size() < 7 && start.letters.size() > 0) {
            Random random = new Random();
            int position = random.nextInt(start.letters.size());//lets hope I don't go out of bounds with this
            letters.add(start.letters.get(position));
            start.letters.remove(position);
        }
    }

    void everyword(StringBuilder word, Character letter, Coordinate start) throws Exception {
        if (word.length() > letters.size() + 1) {
            return;
        }
        word.append(letter);
        String check = word.toString();
        int biggest = 0;
        //System.out.println(check);
        if (containsword(check)) {
            if (drawable(check, start)) {
                for (int m = 0; m < check.length(); m++) {
                    biggest += valueOfLetter(check.charAt(m));
                }
                if (biggest > greatestValue) {
                    System.out.println("I set the start coordiante to be " + start.getX() + " " + start.getY());
                    beginningCor = start;
                    greatestWord = word;
                    greatestValue = biggest;
                    finalDirection = currentdirection;
                }
            }
        }
        for (int k = 0; k < letters.size(); k++) {
            char lettertocheck = letters.get(k);
            letters.remove(k);
            everyword(new StringBuilder(word), lettertocheck, start);
            letters.add(lettertocheck);
        }
    }

    boolean containsword(String word) {
        return (dictionary.contains(word.toLowerCase() + '\\'));
    }

    Ai(Set<String> dictionary) {
        this.dictionary = dictionary;
    }

    private String WordToCreate() throws Exception {


        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++)//go through each letter on the board
            {
                Character analyzing = game.getLayout().get(i).get(j);
                if (analyzing == null) {
                    continue;
                }
                ArrayList<ArrayList<Boolean>> truth = new ArrayList();
                for (int x = 0; x < 8; x++) {
                    truth.add(new ArrayList<>());
                    for (int p = 0; p < 8; p++) {
                        truth.get(x).add(Boolean.FALSE);
                    }
                }
                everyword(new StringBuilder(), analyzing, new Coordinate(j, i, analyzing));

            }
        }
        return greatestWord.toString();
    }

}

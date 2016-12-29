import javax.swing.*;
import java.awt.*;
import java.util.*;


/**
 * Created by williamjones on 8/11/16.
 */

public class Ai
{
    private Set<String> dictionary;
    private ArrayList<Character>letters=new ArrayList<>();
    private int greatestvalue=0;
    private StringBuilder greatestword=new StringBuilder("");
    private ArrayList<Coordinate>location =new ArrayList<>();
    private Coordinate begginingcor;
    private DIRECTION finaldirection;

    public ArrayList<Character> getLetters() {
        return letters;
    }

    public void setLetters(ArrayList<Character> letters) {
        this.letters = letters;
    }

    public void taketurn() throws Exception {
        System.out.println("my board looks like this");
        printboard();
        System.out.println("The letters the ai has is this ");
        for(int i=0; i<letters.size(); i++)
        {
            System.out.println(letters.get(i));
        }
        String word= WordToCreate();

        System.out.println("i create this word "+ word);
        drawwordonboard(this.finaldirection,begginingcor,word);
        Set<Character>temp=new HashSet<>();
        for(int i=1; i<word.length(); i++)
        {
           for(int j=0; j<letters.size(); j++)
           {
               if(letters.get(j)==word.charAt(i))
               {
                   letters.remove(j);
                   break;
               }
           }
        }
        printboard();
        greatestword=null;
        greatestvalue=0;

    }

    public static void printboard()
    {
        for(int i=0; i<8; i++)
        {
            for (int j=0; j<8; j++)
            {
                if(main.game.getLayout().get(i).get(j)==null)
                {
                    System.out.print("_");
                }
                else
                    System.out.print(main.game.getLayout().get(i).get(j));

            }
            System.out.println();
        }
    }
    private DIRECTION currentdirection=DIRECTION.LEFT;



    public enum DIRECTION
    {
        LEFT, RIGHT,UP, DOWN
    }

    void drawwordonboard(DIRECTION direction, Coordinate drawing, String word)
    {
        System.out.println("My direction is "+direction.toString());

        switch (direction)
        {
            case UP:
            {
                for(int i=0; i<word.length(); i++)
                {
                    JButton button= BoardGui.chessBoardSquares[drawing.getX()][drawing.getY()-1];
                    main.game.getLayout().get(drawing.getX()).set(drawing.getY()-i,word.charAt(i));
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
            case DOWN:
            {
                for(int i=0; i<word.length(); i++)
                {
                    JButton button= BoardGui.chessBoardSquares[drawing.getX()][drawing.getY()+i];
                    main.game.getLayout().get(drawing.getY()+i).set(drawing.getX(),word.charAt(i));
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
            case LEFT:
            {
                for(int i=0; i<word.length(); i++)
                {
                    JButton button= BoardGui.chessBoardSquares[drawing.getX()-i][drawing.getY()];
                    main.game.getLayout().get(drawing.getY()).set(drawing.getX()-i,word.charAt(i));
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
            case RIGHT:
            {
                for(int i=0; i<word.length(); i++)
                {
                    JButton button= BoardGui.chessBoardSquares[drawing.getX()+i][drawing.getY()];
                    main.game.getLayout().get(drawing.getY()).set((drawing.getX()+i),word.charAt(i));
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


    public boolean drawable(String word, Coordinate currentcor)
    {

        try {
            for (int i = 0; i < word.length(); i++) {
                //System.out.println("this coordinate "+(currentcor.getY()+i)+" "+currentcor.getX());
            //System.out.println("I check this letter "+main.game.getLayout().get(currentcor.getY()+i).get(currentcor.getX()));
                if (main.game.getLayout().get(currentcor.getY()+i).get(currentcor.getX())!=null&&i!=0)
                 {
                    break;
                 }
                if (i == word.length() - 1) {
                    if(currentcor.getY()!=0)
                    {
                        StringBuilder check=new StringBuilder("");
                        int count=1;
                        while(main.game.getLayout().get(currentcor.getY()-count).get(currentcor.getX())!=null)
                        {
                            check.append(main.game.getLayout().get(currentcor.getY()-count).get(currentcor.getX()));
                            count++;
                        }
                        if(!containsword(word+=check.toString()))
                        {
                            break;
                        }
                    }
                    System.out.println("I make it down "+word);
                    this.currentdirection = DIRECTION.DOWN;
                    return true;
                }
            }
        }
            catch (Exception e)
            {
                //System.out.print("I go out of bounds");
            }
            try {
                for (int i = 0; i < word.length(); i++) {
            if(main.game.getLayout().get(currentcor.getY()-i).get(currentcor.getX())!=(null)&&i!=0)
            {
                break;
            }

                    if (i == word.length() - 1) {
                        if(currentcor.getY()!=0)
                        {
                            StringBuilder check=new StringBuilder("");
                            int count=1;
                            while(main.game.getLayout().get(currentcor.getY()+count).get(currentcor.getX())!=null)
                            {
                                check.append(main.game.getLayout().get(currentcor.getY()+count).get(currentcor.getX()));
                                count++;
                            }
                            if(!containsword(word+=check.toString()))
                            {
                                break;
                            }
                        }

                        System.out.println("I make it up");

                        this.currentdirection = DIRECTION.UP;
                        return true;
                    }
                }
            }
                catch (Exception e)
                {
                    //System.out.print("I go out of bounds");
                }
                try{
                for (int i = 0; i < word.length(); i++) {
            if(main.game.getLayout().get(currentcor.getY()).get(currentcor.getX()-i)!=(null)&&i!=0)
            {
                break;
            }

                    if (i == word.length() - 1) {
                        System.out.println("I make it left");

                        if(currentcor.getX()!=0)
                        {
                            StringBuilder check=new StringBuilder("");
                            int count=1;
                            while(main.game.getLayout().get(currentcor.getY()).get(currentcor.getX()+count)!=null)
                            {
                                check.append(main.game.getLayout().get(currentcor.getY()).get(currentcor.getX()+count));
                                count++;
                            }
                            if(!containsword(word+=check.toString()))
                            {
                                break;
                            }
                        }
                        this.currentdirection = DIRECTION.LEFT;
                        return true;
                    }
                }
            }
            catch (Exception e)
            {
               // System.out.print("I go out of bounds");
            }
            try {
                for (int i = 0; i < word.length(); i++) {
            /*
            if(main.game.getLayout().get(currentcor.getX()).size()<=currentcor.getX())
            {
                break;
            }
*/
            if(main.game.getLayout().get(currentcor.getY()).get(currentcor.getX()+i)!=(null)&&i!=0)
            {
                break;
            }

                    if (i == word.length() - 1) {
                        System.out.println("I make it right");

                        if(currentcor.getX()!=0)
                        {
                            StringBuilder check=new StringBuilder("");
                            int count=1;
                            while(main.game.getLayout().get(currentcor.getY()).get(currentcor.getX()-count)!=null)
                            {
                                check.append(main.game.getLayout().get(currentcor.getY()).get(currentcor.getX()-count));
                                count++;
                            }
                            if(!containsword(word+=check.toString()))
                            {
                                break;
                            }
                        }
                        this.currentdirection = DIRECTION.RIGHT;
                        return true;
                    }
                }
            }catch (Exception e)
            {
              //  System.out.print("I go out of bounds");

            }


        return false;
    }

    int valueofletter(Character letter) throws Exception {
        switch (letter)
        {
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
            case'D':
            case'G':
                return 2;
            case 'M':
            case 'P':
            case 'B':
            case 'C':
                return 3;
            case'V':
            case'Y':
            case'H':
            case'W':
            case'F':
                return 4;
            case'K':
                return 5;
            case 'X':
            case 'J':
                return 8;
            case'Q':
            case'Z':
                return 10;
        }
        throw (new Exception("Invalid letter "+letter));
    }


    void getmesomeletters()
    {
        while(letters.size()<7&&main.letters.size()>0)
        {
            Random random=new Random();
            int position=random.nextInt(main.letters.size());//lets hope I don't go out of bounds with this
            letters.add(main.letters.get(position));
            main.letters.remove(position);
        }
    }

    void everyword(StringBuilder word, Character letter, Coordinate start) throws Exception {
        if(word.length()>letters.size()+1)
        {
            return;
        }
        word.append(letter);
        String check=word.toString();
        int biggest=0;
        //System.out.println(check);
        if(containsword(check)) {
            if(drawable(check, start)) {
                for (int m = 0; m < check.length(); m++) {
                    biggest += valueofletter(check.charAt(m));
                }
                if (biggest > greatestvalue) {
                    System.out.println("I set the start coordiante to be "+start.getX()+" "+start.getY());
                    begginingcor = start;
                    greatestword = word;
                    greatestvalue = biggest;
                    finaldirection=currentdirection;
                }
            }
        }
        for(int k=0; k<letters.size(); k++)
        {
            char lettertocheck=letters.get(k);
            letters.remove(k);
            everyword( new StringBuilder(word) , lettertocheck, start);
            letters.add(lettertocheck);
        }

        /*
        else {
            word.append(main.game.getLayout().get(i).get(j));
            everyword(truth, word, i + 1, j, null);
            everyword(truth, word, i, j + 1, null);
            everyword(truth, word, i - 1, j, null);
            everyword(truth, word, i, j - 1, null);
        }
        */
    }

    boolean containsword(String word)
    {
       return (dictionary.contains(word.toLowerCase()+'\\'));
    }

    Ai(Set<String> dictionary)
    {
        this.dictionary=dictionary;
    }

   // Map<Character, Integer> mymap=new HashMap<>();
    public String WordToCreate() throws Exception {


        for(int i=0; i<8; i++)
        {
            for(int j=0; j<8;j++)//go through each letter on the board
            {
                Character analyzing=main.game.getLayout().get(i).get(j);
                if(analyzing==null)
                {
                    continue;
                }
                ArrayList<ArrayList<Boolean>> truth=new ArrayList();
                for(int x=0; x<8; x++)
                {
                    truth.add(new ArrayList<Boolean>());
                    for(int p=0; p<8; p++)
                    {
                        truth.get(x).add(new Boolean(false));
                    }
                }
                everyword( new StringBuilder(), analyzing, new Coordinate(j,i, analyzing));

            }
        }
        return greatestword.toString();
    }

}

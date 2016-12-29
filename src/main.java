
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by williamjones on 8/11/16.
 * */

public class main
{
    public static int turns=0;
    public static Board game=new Board();
    public static   Ai player1;
    public static Ai player2;
    public static boolean endgame=false;
    static public Thread lockobject=Thread.currentThread();
    static public BoardGui temp=new BoardGui();
    static public ArrayList<Character>letters=new ArrayList<>();
    public static void  createletters()
    {
        for(int i=0; i<12; i++)
        {
            letters.add('E');
        }
        for(int i=0; i<9; i++)
        {
            letters.add('A');
            letters.add('I');
        }
        for(int i=0; i<8; i++)
        {
            letters.add('O');
        }
        for(int i=0; i<6; i++)
        {
            letters.add('N');
            letters.add('R');
            letters.add('T');
        }
        for(int i=0; i<4; i++)
        {
            letters.add('L');
            letters.add('S');
            letters.add('U');
            letters.add('D');
        }
        for(int i=0; i<3; i++)
        {
            letters.add('G');
        }
        for(int i=0; i<2; i++) {
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

    public static void main(final String[] args) throws FileNotFoundException, InvocationTargetException, InterruptedException {
        FileReader myfile=new FileReader(args[0]);

        Set<String> dictionary=new HashSet<>();
        Scanner scan=new Scanner(myfile);
        while (scan.hasNext())
        {
            dictionary.add(scan.next().toLowerCase());
        }

        temp.testing();
        //System.out.println(dictionary.size());
       // game.getLayout().get(4).set(5,'F');//Temp for now just have a J in the middle of the board when staring off.
        createletters();
        player1=new Ai(dictionary);
        player2=new Ai(dictionary);
        player1.getmesomeletters();
        player2.getmesomeletters();
        //Ai player3=new Ai(dictionary);
        //Ai player4=new Ai(dictionary);


    }

}



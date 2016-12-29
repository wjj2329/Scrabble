/**
 * Created by williamjones on 9/5/16.
 */
import javafx.scene.paint.*;

import java.awt.*;
import java.awt.Color;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.border.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class BoardGui {

    private final JPanel gui = new JPanel(new BorderLayout(3, 3));
    public static JButton[][] chessBoardSquares = new JButton[15][15];
    private boolean firstturn=true;
    private boolean firstletter=true;
    private String word="";
    private JPanel chessBoard;
    private  boolean doneonce=true;
    private  char letter;
    private Dimension mine;
    private ArrayList<Coordinate>letterposition=new ArrayList<>();
    private final int width=15;
    private final int height=15;
    private Icon selectedletter=null;
    private ArrayList<JButton>lettersplacedthistrun=new ArrayList<>();
    private ArrayList<JButton>mybuttons=new ArrayList<>();
    private final JLabel message = new JLabel(
            "Scrabble is ready to play!");
    private static final String COLS = "ABCDEFGH";


    private boolean canplacehere(int x, int y)
    {
      if(main.game.getLayout().get(x).get(y)!=null)
      {
          return false;
      }
      if(x!=0) {
          if (main.game.getLayout().get(x - 1).get(y) != null) {
              //System.out.println("my cordinate is this "+x+" "+y+main.game.getLayout().get(x - 1).get(y));
              return true;
          }
      }
      if(y!=0) {
          if (main.game.getLayout().get(x).get(y - 1) != null) {

              //System.out.println("my cordinate is this "+x+" "+y+main.game.getLayout().get(x).get(y - 1));

              return true;
          }
      }
      if(y!=height) {
          if (main.game.getLayout().get(x).get(y + 1) != null) {
              //System.out.println("my cordinate is this "+x+" "+y+main.game.getLayout().get(x).get(y + 1));

              return true;
          }
      }
      if(x!=width) {
          if (main.game.getLayout().get(x + 1).get(y) != null) {
              //System.out.println("my cordinate is this "+x+" "+y+main.game.getLayout().get(x + 1).get(y));

              return true;
          }
      }
      return false;
    }


    public void updateboard(ArrayList<Coordinate> updates)
    {
       for(ArrayList<Character> a: main.game.getLayout())
       {
           for(Character c: a)
           {

           }
       }
    }

    BoardGui() {
        initializeGui();
    }

    public final void initializeGui() {
        // create the images for the chess pieces
       // createImages();

        // set up the main GUI
        gui.setBorder(new EmptyBorder(5, 5, 5, 5));
        JToolBar tools = new JToolBar();
        tools.setFloatable(false);
        gui.add(tools, BorderLayout.PAGE_START);
        JButton bs= new JButton("Clear");
        tools.add(bs);
        bs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                for(int i=0; i<mybuttons.size(); i++)
                {
                    mybuttons.get(i).setEnabled(true);
                }
                for(int i=0; i<lettersplacedthistrun.size(); i++)
                {
                    lettersplacedthistrun.get(i).setIcon(new ImageIcon((new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB))));
                }
                word="";
               lettersplacedthistrun.clear();
                firstletter=true;
                for(int i=0; i<letterposition.size(); i++)
                {
                    main.game.getLayout().get(letterposition.get(i).getX()).set(letterposition.get(i).getY(), null);
                }
            }
        });
        JButton endturn=new JButton("EndTurn");
        tools.add(endturn);
        endturn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("the word is this" +word);
                Ai.printboard();
                if(word.equals(""))
                {
                    return;
                }
               if(!main.player1.containsword(word))
               {
                   JOptionPane.showMessageDialog(new JFrame(),
                           "That isn't a word you moron",
                          "ERROR!!!!!!!", JOptionPane.WARNING_MESSAGE);
                   for(int i=0; i<mybuttons.size(); i++)
                   {
                       mybuttons.get(i).setEnabled(true);
                   }
                   for(int i=0; i<letterposition.size(); i++)
                   {
                       main.game.getLayout().get(letterposition.get(i).getX()).set(letterposition.get(i).getY(), null);
                   }
                   for(int i=0; i<lettersplacedthistrun.size(); i++)
                   {
                       lettersplacedthistrun.get(i).setIcon(new ImageIcon((new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB))));
                   }
                   lettersplacedthistrun.clear();
                   letterposition.clear();
                   firstletter=true;
                   word="";


               }
               else
               {
                   JOptionPane.showMessageDialog(new JFrame(),
                           "That is a word!",
                           "SUCCESS!!!!!", JOptionPane.WARNING_MESSAGE);
                   firstturn=false;
                   main.turns++;
                   System.out.println("The computer takes his turn");
                   try {
                       main.player1.taketurn();
                   } catch (Exception e1) {
                       e1.printStackTrace();
                   }
                   main.player1.getmesomeletters();

               }

            }
        });
        tools.addSeparator();
        tools.addSeparator();
        tools.add(message);

        gui.add(new JLabel("?"), BorderLayout.LINE_START);

        chessBoard = new JPanel(new GridLayout(0, 9)) {

            /**
             * Override the preferred size to return the largest it can, in
             * a square shape.  Must (must, must) be added to a GridBagLayout
             * as the only component (it uses the parent as a guide to size)
             * with no GridBagConstaint (so it is centered).
             */


            @Override
            public final Dimension getPreferredSize() {

                if (doneonce) {
                Dimension d = super.getPreferredSize();
                Dimension prefSize = null;
                Component c = getParent();
                if (c == null) {
                    prefSize = new Dimension(
                            (int) d.getWidth(), (int) d.getHeight());
                } else if (c != null &&
                        c.getWidth() > d.getWidth() &&
                        c.getHeight() > d.getHeight()) {
                    prefSize = c.getSize();
                } else {
                    prefSize = d;
                }
                int w = (int) prefSize.getWidth();
                int h = (int) prefSize.getHeight();
                // the smaller of the two sizes
                int s = (w > h ? h : w);
                    mine=new Dimension(s,s);
                return new Dimension(s, s);
            }
                return mine;
            }

        };
        chessBoard.setBorder(new CompoundBorder(
                new EmptyBorder(8,8,8,8),
                new LineBorder(Color.BLACK)
        ));
        // Set the BG to be ochre
        Color ochre = new Color(204,119,34);
        chessBoard.setBackground(ochre);
        JPanel boardConstrain = new JPanel(new GridBagLayout());
        boardConstrain.setBackground(ochre);
        boardConstrain.add(chessBoard);
        gui.add(boardConstrain);
        // create the chess board squares
        final Insets buttonMargin = new Insets(0, 0, 0, 0);
        for (int ii = 0; ii < chessBoardSquares.length; ii++) {
            for (int jj = 0; jj < chessBoardSquares[ii].length; jj++) {
                final JButton b = new JButton();
                b.setMargin(buttonMargin);
                ImageIcon icon = new ImageIcon(
                        new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
                b.setIcon(icon);

                chessBoardSquares[jj][ii] = b;
                b.setText(Integer.toString(ii)+","+Integer.toString(jj));
                b.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(selectedletter!=null) {
                            String[] mystuf = b.getText().split(",");
                            int x = Integer.parseInt(mystuf[0]);
                            int y = Integer.parseInt(mystuf[1]);
                            if(firstletter&&firstturn) {
                                b.setIcon(selectedletter);
                                selectedletter = null;
                                lettersplacedthistrun.add(b);
                                firstletter=false;
                                main.game.getLayout().get(x).set(y, letter);
                                word+=letter;
                                Coordinate mycoordinate=new Coordinate(x, y, letter);
                                letterposition.add(mycoordinate);


                            }
                            else
                            {
                                if(canplacehere(x, y))
                                {
                                    b.setIcon(selectedletter);
                                    selectedletter = null;
                                    lettersplacedthistrun.add(b);
                                    firstletter=false;
                                    main.game.getLayout().get(x).set(y, letter);
                                    word+=letter;
                                    Coordinate mycoordinate=new Coordinate(x, y, letter);
                                    letterposition.add(mycoordinate);
                                }
                            }
                        }
                        doneonce=false;
                    }
                });
            }
        }


       //  * fill the chess board

        chessBoard.add(new JLabel(""));
        // fill the top row
        for (int ii = 0; ii < 8; ii++) {
            chessBoard.add(
                    new JLabel(COLS.substring(ii, ii + 1),
                            SwingConstants.CENTER));
        }
        // fill the black non-pawn piece row
        for (int ii = 0; ii < 8; ii++) {
            for (int jj = 0; jj < 8; jj++) {
                switch (jj) {
                    case 0:
                        chessBoard.add(new JLabel("" + (9-(ii + 1)),
                                SwingConstants.CENTER));
                    default:
                        chessBoard.add(chessBoardSquares[jj][ii]);
                }
            }
        }
        if(main.player1!=null) {
            for (int i = 0; i < main.player1.getLetters().size(); i++) {
                final JButton button = new JButton();
                String buttonimage = "src/Letters/";
                buttonimage += main.player1.getLetters().get(i);
                buttonimage += ".jpg";
                ImageIcon icon = new ImageIcon(buttonimage);
                Image im = icon.getImage();
                Image imag = im.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
                button.setIcon(new ImageIcon(imag));
                button.setText(Character.toString(main.player1.getLetters().get(i)));
                chessBoard.add(button);
                mybuttons.add(button);

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                       // if(firstletter) {
                            selectedletter = button.getIcon();
                            button.setEnabled(false);
                            letter = button.getText().charAt(0);
                       // }


                    }
                });

            }
        }

    }

    public final JComponent getGui() {
        return gui;
    }


    /**
     * Initializes the icons of the initial chess board piece places
     */


    public void testing(){
        Runnable r = new Runnable() {

            @Override
            public void run() {
                BoardGui cg = new BoardGui();

                JFrame f = new JFrame("Scrabble");
                f.add(cg.getGui());
                // Ensures JVM closes after frame(s) closed and
                // all non-daemon threads are finished
                f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                // See http://stackoverflow.com/a/7143398/418556 for demo.
                f.setLocationByPlatform(true);

                // ensures the frame is the minimum size it needs to be
                // in order display the components within it
                f.pack();
                // ensures the minimum size is enforced.
                f.setMinimumSize(f.getSize());
                f.setVisible(true);
            }
        };
        // Swing GUIs should be created and updated on the EDT
        // http://docs.oracle.com/javase/tutorial/uiswing/concurrency
        SwingUtilities.invokeLater(r);
    }
}
/**
 * Created by williamjones on 9/5/16.
 */

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class BoardGui {

    private final JPanel gui = new JPanel(new BorderLayout(3, 3));
    public static JButton[][] chessBoardSquares = new JButton[15][15];
    private boolean firstTurn = true;
    private boolean firstLetter = true;
    private String word = "";
    private boolean doneOnce = true;
    private char letter;
    private Dimension mine;
    private ArrayList<Coordinate> letterPosition = new ArrayList<>();
    private Icon selectedLetter = null;
    private ArrayList<JButton> lettersPlacedThisTurn = new ArrayList<>();
    private ArrayList<JButton> buttons = new ArrayList<>();
    private final JLabel message = new JLabel(
            "Scrabble is ready to play!");
    private static final String COLS = "ABCDEFGH";
    private Board game;
    private Start start;
    public BoardGui(Board game, Start start){
        this.game=game;
        this.start=start;
        initializeGui();

    }

    private boolean canplacehere(int x, int y) {
        if (game.getLayout().get(x).get(y) != null) {
            return false;
        }
        if (x != 0) {
            if (game.getLayout().get(x - 1).get(y) != null) {
                return true;
            }
        }
        if (y != 0) {
            if (game.getLayout().get(x).get(y - 1) != null) {
                return true;
            }
        }
        int height = 15;
        if (y != height) {
            if (game.getLayout().get(x).get(y + 1) != null) {
                return true;
            }
        }
        int width = 15;
        if (x != width) {
            return game.getLayout().get(x + 1).get(y) != null;
        }
        return false;
    }


    public void updateboard(ArrayList<Coordinate> updates) {
        for (ArrayList<Character> a : game.getLayout()) {
            for (Character c : a) {

            }
        }
    }

    public final void initializeGui() {
        // set up the Main GUI
        gui.setBorder(new EmptyBorder(5, 5, 5, 5));
        JToolBar tools = new JToolBar();
        tools.setFloatable(false);
        gui.add(tools, BorderLayout.PAGE_START);
        JButton bs = new JButton("Clear");
        tools.add(bs);
        bs.addActionListener(e -> {
            for (int i = 0; i < buttons.size(); i++) {
                buttons.get(i).setEnabled(true);
            }
            for (int i = 0; i < lettersPlacedThisTurn.size(); i++) {
                lettersPlacedThisTurn.get(i).setIcon(new ImageIcon((new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB))));
            }
            word = "";
            lettersPlacedThisTurn.clear();
            firstLetter = true;
            for (int i = 0; i < letterPosition.size(); i++) {
                game.getLayout().get(letterPosition.get(i).getX()).set(letterPosition.get(i).getY(), null);
            }
        });
        JButton endturn = new JButton("EndTurn");
        tools.add(endturn);
        endturn.addActionListener(e -> {
            System.out.println("the word is this" + word);
            //Ai.printBoard();
            if (word.equals("")) {
                return;
            }
            if (!start.player1.containsword(word)) {
                JOptionPane.showMessageDialog(new JFrame(),
                        "That isn't a word you moron",
                        "ERROR!!!!!!!", JOptionPane.WARNING_MESSAGE);
                for (int i = 0; i < buttons.size(); i++) {
                    buttons.get(i).setEnabled(true);
                }
                for (int i = 0; i < letterPosition.size(); i++) {
                    game.getLayout().get(letterPosition.get(i).getX()).set(letterPosition.get(i).getY(), null);
                }
                for (int i = 0; i < lettersPlacedThisTurn.size(); i++) {
                    lettersPlacedThisTurn.get(i).setIcon(new ImageIcon((new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB))));
                }
                lettersPlacedThisTurn.clear();
                letterPosition.clear();
                firstLetter = true;
                word = "";


            } else {
                JOptionPane.showMessageDialog(new JFrame(),
                        "That is a word!",
                        "SUCCESS!!!!!", JOptionPane.WARNING_MESSAGE);
                firstTurn = false;
                Start.turns++;
                System.out.println("The computer takes his turn");
                try {
                    start.player1.takeTurn();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                start.player1.getmesomeletters(start);

            }

        });
        tools.addSeparator();
        tools.addSeparator();
        tools.add(message);

        gui.add(new JLabel("?"), BorderLayout.LINE_START);

        JPanel chessBoard = new JPanel(new GridLayout(0, 9)) {

            /**
             * Override the preferred size to return the largest it can, in
             * a square shape.  Must (must, must) be added to a GridBagLayout
             * as the only component (it uses the parent as a guide to size)
             * with no GridBagConstaint (so it is centered).
             */

            @Override
            public final Dimension getPreferredSize() {

                if (doneOnce) {
                    Dimension d = super.getPreferredSize();
                    Dimension prefSize = null;
                    Component c = getParent();
                    if (c == null) {
                        prefSize = new Dimension(
                                (int) d.getWidth(), (int) d.getHeight());
                    } else if (c.getWidth() > d.getWidth() && c.getHeight() > d.getHeight()) {
                        prefSize = c.getSize();
                    } else {
                        prefSize = d;
                    }
                    int w = (int) prefSize.getWidth();
                    int h = (int) prefSize.getHeight();
                    // the smaller of the two sizes
                    int s = (w > h ? h : w);
                    mine = new Dimension(s, s);
                    return new Dimension(s, s);
                }
                return mine;
            }

        };
        chessBoard.setBorder(new CompoundBorder(
                new EmptyBorder(8, 8, 8, 8),
                new LineBorder(Color.BLACK)
        ));
        // Set the BG to be ochre
        Color ochre = new Color(204, 119, 34);
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
                b.setText(Integer.toString(ii) + "," + Integer.toString(jj));
                b.addActionListener(e -> {
                    if (selectedLetter != null) {
                        String[] mystuf = b.getText().split(",");
                        int x = Integer.parseInt(mystuf[0]);
                        int y = Integer.parseInt(mystuf[1]);
                        if (firstLetter && firstTurn) {
                            b.setIcon(selectedLetter);
                            selectedLetter = null;
                            lettersPlacedThisTurn.add(b);
                            firstLetter = false;
                            game.getLayout().get(x).set(y, letter);
                            word += letter;
                            Coordinate myCoordinate = new Coordinate(x, y, letter);
                            letterPosition.add(myCoordinate);


                        } else {
                            if (canplacehere(x, y)) {
                                b.setIcon(selectedLetter);
                                selectedLetter = null;
                                lettersPlacedThisTurn.add(b);
                                firstLetter = false;
                                game.getLayout().get(x).set(y, letter);
                                word += letter;
                                Coordinate myCoordinate = new Coordinate(x, y, letter);
                                letterPosition.add(myCoordinate);
                            }
                        }
                    }
                    doneOnce = false;
                });
            }
        }

        chessBoard.add(new JLabel(""));
        for (int ii = 0; ii < 8; ii++) {
            chessBoard.add(
                    new JLabel(COLS.substring(ii, ii + 1),
                            SwingConstants.CENTER));
        }
        for (int ii = 0; ii < 8; ii++) {
            for (int jj = 0; jj < 8; jj++) {
                switch (jj) {
                    case 0:
                        chessBoard.add(new JLabel("" + (9 - (ii + 1)),
                                SwingConstants.CENTER));
                    default:
                        chessBoard.add(chessBoardSquares[jj][ii]);
                }
            }
        }
        if (start.player1 != null) {
            for (int i = 0; i < start.player1.getLetters().size(); i++) {
                final JButton button = new JButton();
                String buttonimage = "src/Letters/";
                buttonimage += start.player1.getLetters().get(i);
                buttonimage += ".jpg";
                ImageIcon icon = new ImageIcon(buttonimage);
                Image im = icon.getImage();
                Image imag = im.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
                button.setIcon(new ImageIcon(imag));
                button.setText(Character.toString(start.player1.getLetters().get(i)));
                chessBoard.add(button);
                buttons.add(button);

                button.addActionListener(e -> {
                    selectedLetter = button.getIcon();
                    button.setEnabled(false);
                    letter = button.getText().charAt(0);
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


    public void testing(Board game, Start start) {
        Runnable r = () -> {
            BoardGui cg = new BoardGui(game, start);

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
        };
        // Swing GUIs should be created and updated on the EDT
        // http://docs.oracle.com/javase/tutorial/uiswing/concurrency
        SwingUtilities.invokeLater(r);
    }
}
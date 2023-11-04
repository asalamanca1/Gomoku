package omok.model;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class Omok extends JFrame {
    private List<Integer> xCoordinates;

    private List<Integer> yCoordinates;
    private Map<Integer, Integer> xCoordinatesRange;
    private Map<Integer, Integer> yCoordinatesRange;
    Board omok;
    private int mouseX;
    private int mouseY;

    public Omok() {
        super("Omok");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setPreferredSize(new Dimension(300, 200));
        setPreferredSize(new Dimension(700, 700));

        // sample UI
        var panel = new JPanel();
        panel.setLayout(new BorderLayout());
        omok = new Board();
        var gameBoard = new BoardPanel(omok);
        Player p1 = new Player("Mike");

        panel.add(gameBoard, BorderLayout.CENTER);



        //panel.add(new JLabel("Select Opponent:"));

        setContentPane(panel);
        pack();
        setVisible(true);

        xCoordinates=gameBoard.getXcoordinates();
        yCoordinates=gameBoard.getYcoordinates();

        xCoordinatesRange=gameBoard.getxCoordinatesRange();
        yCoordinatesRange=gameBoard.getyCoordinatesRange();


        var display = new JTextField(15);
        display.setEditable(false);

        AtomicReference<String> gameType= new AtomicReference<>("");
        var humanButton = new JRadioButton("Human");
        //add(humanButton);
        var computerButton = new JRadioButton("Computer");
        //add(computerButton);
        var startGame = new JButton("Start game");
        //add(startGame);
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(humanButton);
        buttonGroup.add(computerButton);
        humanButton.addActionListener(E -> {
            gameType.set("Human game selected!");
        });
        computerButton.addActionListener(E -> {
            gameType.set("Computer game selected!");
        });
        startGame.addActionListener(E -> {
            display.setText(gameType.get());
        });
        //panel.add(display);

//IDEA: MAKE TWO DICTIONARIES, ONE FOR X AND ONE FOR Y, MAKE THE KEYS X/Y COORDINATES(1-15) AND FOR EACH KEY, HAVE THE BOARD PANELS CORRESPONDING COORDINATE
        gameBoard.addMouseListener(new java.awt.event.MouseAdapter(){
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                /*
                if (evt.getX() == 0 && evt.getY() == 0) {
                    Graphics g = gameBoard.getGraphics();
                    g.setColor(Color.BLACK);
                    g.fillOval(95, 95, 10, 10); // Draw a small circle at (100, 100)
                }

                 */

                xCoordinates=gameBoard.getXcoordinates();
                yCoordinates=gameBoard.getYcoordinates();
                //System.out.println(evt.getX());
                //System.out.println(xCoordinates);
                //System.out.println(yCoordinates);
                xCoordinatesRange=gameBoard.getxCoordinatesRange();
                yCoordinatesRange=gameBoard.getyCoordinatesRange();

                if (xCoordinates.contains(evt.getX()) && yCoordinates.contains(evt.getY())) {
                    //System.out.println(xCoordinates);
                    //System.out.println(yCoordinates);

                    omok=getBoard();
                    int x =gameBoard.getXcoordinatesOnPanel().get(xCoordinatesRange.get(evt.getX()));
                    int y=gameBoard.getYcoordinatesOnPanel().get(yCoordinatesRange.get(evt.getY()));
                    System.out.println(gameBoard.getXcoordinatesOnPanel());
                    System.out.println(xCoordinatesRange.get(evt.getX()));
                    System.out.println(x);
                    System.out.println(y);
                    omok.placeStone(x,y,p1);
                    mouseX=evt.getX();
                    mouseY=evt.getY();

                    System.out.println(omok.isWonBy(p1));
                    Graphics g = gameBoard.getGraphics();

                    g.setColor(Color.BLACK);
                    gameBoard.paintComponent(g);
                    //g.fillOval(xCoordinatesRange.get(evt.getX())-15, yCoordinatesRange.get(evt.getY())-15, 30, 30); // Draw a small circle at (100, 100)
                }


            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (evt.getX() == 75 && evt.getY() == 65) {
                    Graphics g = gameBoard.getGraphics();
                    g.setColor(gameBoard.getBackground());
                    g.fillOval(95, 95, 10, 10); // Erase the circle at (100, 100)
                }
            }
        });
        /*
        gameBoard.addMouseMotionListener(new java.awt.event.MouseAdapter()
        {
            public void mouseMoved(java.awt.event.MouseEvent evt) {

                xCoordinates=gameBoard.getXcoordinates();
                yCoordinates=gameBoard.getYcoordinates();
                System.out.println(evt.getX());


                if (xCoordinates.contains(evt.getX()) && yCoordinates.contains(evt.getY())) {
                    System.out.println(xCoordinates);
                    System.out.println(yCoordinates);
                    Graphics g = gameBoard.getGraphics();
                    g.setColor(Color.BLACK);
                    g.fillOval(evt.getX(), evt.getY(), 100, 100); // Draw a small circle at (100, 100)
                }
                else{
                    Graphics g = gameBoard.getGraphics();
                    //gameBoard.paintComponent(g);
                }

            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (evt.getX() == 75 && evt.getY() == 65) {
                    Graphics g = gameBoard.getGraphics();
                    g.setColor(gameBoard.getBackground());
                    g.fillOval(95, 95, 10, 10); // Erase the circle at (100, 100)
                }
            }
        });
*/








    }
    public Board getBoard(){
        return omok;
    }
    public int getMouseX(){
        return mouseX;
    }
    public int getMouseY(){
        return mouseY;
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> new Omok());
    }
}


package omok.model;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
public class Omok extends JFrame {
    //DELETED: private List<Integer> xCoordinates;//

    //DELETED: private List<Integer> yCoordinates;//
    //DELETED: private Map<Integer, Integer> xCoordinatesRange;//
    //DELETED: private Map<Integer, Integer> yCoordinatesRange;//
    //DELETED: Board omok;//
    private int mouseX;
    private int mouseY;
    private  JButton restartGameButton;
    private  JButton stopGameButton;
    //DELETED: private BoardPanel gameBoard;//
    private JPanel mainPanel;

    private boolean boardVisible = false;

    private boolean isPlayer1Turn = true;
    //DELETED: private Player p1;//

    public Omok() {
        super("Omok");
        // sample UI
        //var panel = new JPanel();
        //panel.setLayout(new BorderLayout());
        //DELETED: omok = new Board();//This line should be repainted
        //var gameBoard = new BoardPanel(omok);
        //DELETED: p1 = new Player("Mike");

        GameStartScreen();

    }
    public void GameStartScreen() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(300, 200));
        // sample UI
        var panel = new JPanel();
        setContentPane(panel);
        pack();
        setVisible(true);
        panel.add(new JLabel("Select opponent: "));

        JRadioButton humanButton = new JRadioButton("Human");
        JRadioButton computerButton = new JRadioButton("Computer");

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(humanButton);
        buttonGroup.add(computerButton);

        var playbutton = new JButton("play");
        panel.add(humanButton);
        panel.add(computerButton);
        panel.add(playbutton);
        playbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (humanButton.isSelected()) {
                    playbutton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            //Play button selected
                            setPreferredSize(new Dimension(700, 700)); // Set the preferred size to 700x700 because it wasn't update it after calling the method
                            revalidate(); // Refresh the frame
                            pack(); // Show in the middle of the screen
                            Game();
                            showWindow();
                            //dispose();
                        }
                    });

                }
                else if (computerButton.isSelected()) {
                    //
                } else {
                    //
                }
            }
        });
    }
    private void Game() {

        mainPanel = new JPanel(new BorderLayout());
        //mainPanel.setPreferredSize(new Dimension(700, 700));

        //MAKE SETTERS FOR THESE VARIABLES, IN UI JUST CALL OMOK.SETXCOORDINATES, LEAVE BOARDPANEL AND BOARD LOGIC IN UI
        //THESE NEXT FIVE LINES SHOULD BE IN UI, THEN JUST CALL OMOK.SETXCOORDS, ETC
        gameBoard = new BoardPanel(omok);
        xCoordinates = gameBoard.getXcoordinates();
        yCoordinates = gameBoard.getYcoordinates();
        xCoordinatesRange = gameBoard.getxCoordinatesRange();
        yCoordinatesRange = gameBoard.getyCoordinatesRange();

        restartGameButton = new JButton("Restart Game");
        stopGameButton = new JButton("Stop Game");

        var buttonPanel = new JPanel();
        buttonPanel.add(restartGameButton);
        buttonPanel.add(stopGameButton);

        restartGameButton.addActionListener(e -> {
            //CREATE A METHOD WITH THESE LINES IN UI, UI SHOULD ALREADY HAVE BOARDPANEL AND BOARD LOGIC IMPLEMENTED
            omok.clear();
            gameBoard.repaint();
            isPlayer1Turn = true;
        });

        stopGameButton.addActionListener(e -> {
            GameStartScreen();
        });
        //This works to disappear the board and return it empty
        /*stopGameButton.addActionListener(e -> {
            boardVisible = !boardVisible;
            gameBoard.setVisible(boardVisible);
            gameBoard.revalidate();
            pack();
        });
        */
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        mainPanel.add(gameBoard, BorderLayout.CENTER);

        setContentPane(mainPanel);
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

    }

    public void showWindow() {
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
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
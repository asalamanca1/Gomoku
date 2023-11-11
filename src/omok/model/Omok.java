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
    private List<Integer> xCoordinates;

    private List<Integer> yCoordinates;
    private Map<Integer, Integer> xCoordinatesRange;
    private Map<Integer, Integer> yCoordinatesRange;
    Board omok;
    private int mouseX;
    private int mouseY;
    private  JButton restartGameButton;
    private  JButton stopGameButton;
    private BoardPanel gameBoard;
    private JPanel mainPanel;

    private boolean boardVisible = false;

    private boolean isPlayer1Turn = true;
    private Player p1;
    private Player p2;
    private Player currentPlayer;
    private boolean validMove;
    private boolean isHuman;
    private JRadioButton computerButton;
    private JButton playbutton;
    private JRadioButton humanButton;
    private ButtonGroup buttonGroup;


    public Omok() {
        super("Omok");
        // sample UI
        //var panel = new JPanel();
        //panel.setLayout(new BorderLayout());
        omok = new Board();//This line should be repainted
        //var gameBoard = new BoardPanel(omok);
        ////p1 = new Player("Mike", true);
        //p2 = new Player("Andre", true);
        p1=omok.getPlayers()[0];
        p2=omok.getPlayers()[1];
        currentPlayer=omok.getPlayers()[2];
        validMove=false;
        humanButton = new JRadioButton("Human");
        computerButton = new JRadioButton("Computer");
        buttonGroup = new ButtonGroup();
        playbutton = new JButton("play");
        gameBoard = new BoardPanel(omok);



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

        //JRadioButton humanButton = new JRadioButton("Human");
        //JRadioButton computerButton = new JRadioButton("Computer");

        //ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(humanButton);
        buttonGroup.add(computerButton);

        //var playbutton = new JButton("play");

        panel.add(humanButton);
        panel.add(computerButton);
        panel.add(playbutton);
        addGameBoardActionListener(gameBoard);

        playbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (humanButton.isSelected()) {
                    isHuman=true;
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
                    isHuman=false;
                    p2.setComputerOpponent();
                    playbutton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            //Play button selected
                            setPreferredSize(new Dimension(700, 700)); // Set the preferred size to 700x700 because it wasn't update it after calling the method
                            revalidate(); // Refresh the frame
                            pack(); // Show in the middle of the screen
                            Game();


                        }
                    });
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

        //gameBoard = new BoardPanel(omok);
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
            omok.clear();
            gameBoard.repaint();
            currentPlayer=p1;
            omok.setCurrentPlayer(p1);
            isPlayer1Turn = true;
            addGameBoardActionListener(gameBoard);
            gameBoard.setGameOver(false);

        });


        stopGameButton.addActionListener(e -> {
            omok.clear();
            GameStartScreen();
            currentPlayer=p1;
            addGameBoardActionListener(gameBoard);
            gameBoard.setGameOver(false);

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
/*
        //print players turn
        Graphics g = gameBoard.getGraphics();
        g.setColor(Color.BLACK);
        p1=omok.getPlayers()[0];
        p2=omok.getPlayers()[1];
        currentPlayer=omok.getPlayers()[2];
        if(currentPlayer==p1){
            g.drawString("Player 1's Turn",50,50);
        }
        else{
            g.drawString("Player 2's Turn",50,50);
        }

 */

/*
        gameBoard.addMouseListener(new java.awt.event.MouseAdapter(){
            public void mouseClicked(java.awt.event.MouseEvent evt) {


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
                    currentPlayer=omok.getCurrentPlayer();

                    if(omok.isEmpty(x,y)&&!omok.isOccupied(x,y)){
                        omok.placeStone(x,y,currentPlayer);
                        omok.switchTurns();
                        validMove=true;
                    }




                    mouseX=evt.getX();
                    mouseY=evt.getY();

                    System.out.println(omok.isWonBy(p1));
                    if(omok.isWonBy(p1)||omok.isWonBy(p2)){
                        removePlayButtonActionListener(playbutton);
                    }
                    Graphics g = gameBoard.getGraphics();

                    g.setColor(Color.BLACK);

                    gameBoard.paintComponent(g);

/*
                    //print players turn
                    g.setColor(Color.BLACK);
                    p1=omok.getPlayers()[0];
                    p2=omok.getPlayers()[1];
                    currentPlayer=omok.getPlayers()[2];
                    if(currentPlayer==p1){
                        g.drawString("Player 1's Turn",50,50);
                    }
                    else{
                        g.drawString("Player 2's Turn",50,50);
                    }

 */





            /*
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
*/

    }

    public void addGameBoardActionListener(BoardPanel gameBoard){

        gameBoard.addMouseListener(new java.awt.event.MouseAdapter(){


            public void mouseClicked(java.awt.event.MouseEvent evt) {
                /*
                if (evt.getX() == 0 && evt.getY() == 0) {
                    Graphics g = gameBoard.getGraphics();
                    g.setColor(Color.BLACK);
                    g.fillOval(95, 95, 10, 10); // Draw a small circle at (100, 100)
                }

                 */

                Graphics g = gameBoard.getGraphics();

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

                    currentPlayer=omok.getCurrentPlayer();

                    if(omok.isEmpty(x,y)&&!omok.isOccupied(x,y)){

                        omok.placeStone(x,y,currentPlayer);
                        omok.switchTurns();
                        currentPlayer=omok.getCurrentPlayer();
                        System.out.println("IS HUMAN?"+currentPlayer.isHuman());
                        if(!currentPlayer.isHuman()){

                            currentPlayer.automateMove(omok);


                        }
                        validMove=true;
                    }





                    mouseX=evt.getX();
                    mouseY=evt.getY();

                    System.out.println(omok.isWonBy(p1));
                    if(omok.isWonBy(p1)||omok.isWonBy(p2)){
                        removeGameBoardActionListener(gameBoard);
                    }




                    gameBoard.paintComponent(g);

/*
                    //print players turn
                    g.setColor(Color.BLACK);
                    p1=omok.getPlayers()[0];
                    p2=omok.getPlayers()[1];
                    currentPlayer=omok.getPlayers()[2];
                    if(currentPlayer==p1){
                        g.drawString("Player 1's Turn",50,50);
                    }
                    else{
                        g.drawString("Player 2's Turn",50,50);
                    }

 */





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
    public void removeGameBoardActionListener(BoardPanel gameBoard){
        gameBoard.removeMouseListener(gameBoard.getMouseListeners()[0]);

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




}
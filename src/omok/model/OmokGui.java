//Andre Salamanca and Miguel Angel Garcia Jacquez
package omok.model;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
public class OmokGui extends JFrame {
    private List<Integer> xCoordinates;

    private List<Integer> yCoordinates;
    private Map<Integer, Integer> xCoordinatesRange;
    private Map<Integer, Integer> yCoordinatesRange;
    Board omok;

    private  JButton restartGameButton;
    private  JButton stopGameButton;
    private BoardPanel gameBoard;
    private JPanel mainPanel;
    private JFrame frame;

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
    private boolean gameIsRunning;


    public OmokGui() {
        super("Omok");
        omok = new Board();
        p1 = new Player("Mike", true);
        p2 = new Player("Andre", true);
        p1=omok.getPlayers()[0];
        p2=omok.getPlayers()[1];
        currentPlayer=omok.getPlayers()[2];
        validMove=false;
        humanButton = new JRadioButton("Human");
        computerButton = new JRadioButton("Computer");
        buttonGroup = new ButtonGroup();
        playbutton = new JButton("play");
        gameBoard = new BoardPanel(omok);
        gameIsRunning=true;

        frame = new JFrame(); // Initialize the frame
        frame.setTitle("Omok");
        frame.setSize(700, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        //show game start screen
        GameStartScreen();

    }
    //showcases games start screen menu
    public void GameStartScreen() {
        setJMenuBar(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(300, 200));
        // sample UI
        var panel = new JPanel();
        setContentPane(panel);
        pack();
        setVisible(true);
        panel.add(new JLabel("Select opponent: "));


        buttonGroup.add(humanButton);
        buttonGroup.add(computerButton);



        panel.add(humanButton);
        panel.add(computerButton);
        panel.add(playbutton);
        addGameBoardActionListener(gameBoard);
        //play button action listener
        playbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //if human button is selected, set players to human
                if (humanButton.isSelected()) {
                    isHuman=true;
                    p1.setIsHuman(true);
                    p2.setIsHuman(true);

                    addPlayButtonActionListener();

                }
                //if computer button is selected set players to human and nonhuman
                else if (computerButton.isSelected()) {
                    isHuman=false;
                    p1.setIsHuman(true);
                    p2.setIsHuman(false);
                    addPlayButtonActionListener();

                }
            }
        });


    }
    //showcases game screen
    private void Game() {

        xCoordinates = gameBoard.getXcoordinates();
        yCoordinates = gameBoard.getYcoordinates();
        xCoordinatesRange = gameBoard.getxCoordinatesRange();
        yCoordinatesRange = gameBoard.getyCoordinatesRange();


        // Set the content pane for the frame
        frame.setContentPane(mainPanel);
        //add game board to main panel
        mainPanel.add(gameBoard, BorderLayout.CENTER);
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
        //turn on game board action listener
        addGameBoardActionListener(gameBoard);
        //display main panel
        setContentPane(mainPanel);
        gameIsRunning=true;


    }
    //create menu bar
    private void JMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu customMenu = new JMenu("Game Menu");

        JMenuItem restartGameButton = new JMenuItem("Restart Game");
        JMenuItem stopGameButton = new JMenuItem("Stop Game");
        JMenuItem resignGameButton = new JMenuItem("Resign");
        // Load icons
        int desiredWidth = 15;  // Adjust the size of the icon
        int desiredHeight = 15;

        Icon restartIcon = new ImageIcon(OmokGui.class.getResource("restartIcon.png"));
        Image originalImageR = ((ImageIcon) restartIcon).getImage();
        // Scale the image to the desired size
        Image scaledImage = originalImageR.getScaledInstance(desiredWidth, desiredHeight, Image.SCALE_SMOOTH);
        // Create a new ImageIcon from the scaled image
        Icon resizedRestartIcon = new ImageIcon(scaledImage);

        // Assuming desiredWidth and desiredHeight are defined earlier in your code
        Icon stopIcon = new ImageIcon(OmokGui.class.getResource("stopIcon.png"));
        Image originalImageStop = ((ImageIcon) stopIcon).getImage();
        // Scale the image to the desired size
        Image scaledImageStop = originalImageStop.getScaledInstance(desiredWidth, desiredHeight, Image.SCALE_SMOOTH);
        // Create a new ImageIcon from the scaled image
        Icon resizedStopIcon = new ImageIcon(scaledImageStop);
        // Assuming desiredWidth and desiredHeight are defined earlier in your code

        Icon resignIcon = new ImageIcon(OmokGui.class.getResource("resignIcon.png"));
        Image originalImageResign = ((ImageIcon) resignIcon).getImage();
        // Scale the image to the desired size
        Image scaledImageResign = originalImageResign.getScaledInstance(desiredWidth, desiredHeight, Image.SCALE_SMOOTH);
        // Create a new ImageIcon from the scaled image
        Icon resizedResignIcon = new ImageIcon(scaledImageResign);

        // Set icons for the menu items
        restartGameButton.setIcon(resizedRestartIcon);
        stopGameButton.setIcon(resizedStopIcon);
        resignGameButton.setIcon(resizedResignIcon);

        customMenu.add(restartGameButton);
        customMenu.add(stopGameButton);
        customMenu.add(resignGameButton);

        menuBar.add(customMenu);
        setJMenuBar(menuBar); // Set the menu bar for the frame

        gameBoard = new BoardPanel(omok);

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(gameBoard, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        pack();
        setVisible(true);
        //if restart game button is clicked, show confirm dialog
        restartGameButton.addActionListener(e -> {
            showConfirmationDialog();

        });
        //if stop game button is clicked, show confirm dialog
        stopGameButton.addActionListener(e -> {
            //clear board
            omok.clear();
            //revert to game start screen menu
            GameStartScreen();
            //reset player turns
            currentPlayer=p1;
            addGameBoardActionListener(gameBoard);

            gameBoard.setGameOver(false);
            gameIsRunning=false;

        });
        //if resign game button is clicked, show confirm dialog
        resignGameButton.addActionListener(e -> {
            //switch turns and let other player win
            omok.switchTurns();
            gameBoard.setGameOver(true);
            //remove game board action listener
            removeGameBoardActionListener(gameBoard);
            //repaint board
            gameBoard.repaint();
            gameIsRunning=false;



        });
        //create new toolbar
        JToolBar toolBar = new JToolBar();

        //Create buttons for the toolbar
        JButton button1 = new JButton("Info");


        //Add action listeners to the buttons
        button1.addActionListener(e -> {
            //Display an information message when button1 is clicked
            String message = "Welcome to the Omok Game!\n The player who achieves five stones in a row wins.";
            String title = "Omok Game Info";
            JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
        });
        // Add buttons to the toolbar
        toolBar.add(button1);
        // Add the toolbar to the main panel
        mainPanel.add(toolBar, BorderLayout.SOUTH);
    }
    //confirm dialog
    private void showConfirmationDialog() {
        int option = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to restart the game?",
                "Confirmation",
                JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            // User clicked Yes-->
            //clear board
            omok.clear();
            //repaint boardpanel
            gameBoard.repaint();
            //reset turns
            currentPlayer = p1;
            omok.setCurrentPlayer(p1);
            isPlayer1Turn = true;
            addGameBoardActionListener(gameBoard);
            gameBoard.setGameOver(false);
            gameIsRunning=true;
        }
    }
    //board panel action listener
    public void addGameBoardActionListener(BoardPanel gameBoard){

        gameBoard.addMouseListener(new java.awt.event.MouseAdapter(){


            public void mouseClicked(java.awt.event.MouseEvent evt) {


                Graphics g = gameBoard.getGraphics();

                //get coordinate range of intersections on board
                xCoordinates=gameBoard.getXcoordinates();
                yCoordinates=gameBoard.getYcoordinates();

                xCoordinatesRange=gameBoard.getxCoordinatesRange();
                yCoordinatesRange=gameBoard.getyCoordinatesRange();

                //if mouse was clicked within range of intersections
                if (xCoordinates.contains(evt.getX()) && yCoordinates.contains(evt.getY())) {

                    omok=getBoard();

                    //get the corresponding coordinates on board
                    int x =gameBoard.getXcoordinatesOnPanel().get(xCoordinatesRange.get(evt.getX()));
                    int y=gameBoard.getYcoordinatesOnPanel().get(yCoordinatesRange.get(evt.getY()));

                    currentPlayer=omok.getCurrentPlayer();

                    //if intersection is empty and not occupied
                    if(omok.isEmpty(x,y)&&!omok.isOccupied(x,y)){
                        //place stone on board
                        omok.placeStone(x,y,currentPlayer);
                        //if game is won after stone placed, remove gameboard action listener making it unclickable
                        if(omok.isWonBy(p1)||omok.isWonBy(p2)){
                            removeGameBoardActionListener(gameBoard);
                            gameBoard.paintComponent(g);
                            gameIsRunning=false;

                        }
                        //if game isnt over, switch turns
                        else{
                            omok.switchTurns();
                            gameBoard.paintComponent(g);
                        }
                        //if the opponent is a computer
                        currentPlayer=omok.getCurrentPlayer();
                        if(gameIsRunning&&!currentPlayer.isHuman()){
                            //automate computers game move
                            currentPlayer.automateMove(omok);
                            //if computer won, remove game board action listener making it unclickable
                            if(omok.isWonBy(p1)||omok.isWonBy(p2)){
                                removeGameBoardActionListener(gameBoard);
                                gameBoard.paintComponent(g);
                                gameIsRunning=false;
                            }
                            //switch turns
                            else{
                                omok.switchTurns();
                                gameBoard.paintComponent(g);
                            }

                        }

                    }



                }


            }

        });
    }
    //makes game board unclickable, used when player wins game
    public void removeGameBoardActionListener(BoardPanel gameBoard){
        gameBoard.removeMouseListener(gameBoard.getMouseListeners()[0]);

    }
    //adds play button action listener
    public void addPlayButtonActionListener(){
        playbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Play button selected
                setPreferredSize(new Dimension(700, 700)); // Set the preferred size to 700x700 because it wasn't update it after calling the method
                revalidate(); // Refresh the frame
                pack(); // Show in the middle of the screen
                JMenuBar();
                Game();
                gameIsRunning=true;


            }
        });
    }
    //checks for win and makes board unclickable
    public void checkForWin(){
        if(omok.isWonBy(p1)||omok.isWonBy(p2)){
            removeGameBoardActionListener(gameBoard);

        }
    }


    public Board getBoard(){
        return omok;
    }





}
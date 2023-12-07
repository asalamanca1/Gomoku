//Andre Salamanca and Miguel Angel Garcia Jacquez
package omok.model;
//import omok.net.ListenForMoveThread;
import omok.net.NetworkAdapter;
import omok.net.PairDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import java.util.Random;
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
    private JRadioButton p2pButton;
    private ButtonGroup buttonGroup;
    private boolean gameIsRunning;
    private JButton pairButton;
    private PairDialog pairDialog;
    private JButton pairPlayButton;
    private NetworkAdapter networkAdapter;
    private boolean networkAdapterMessageListenerIsOn;
    private boolean isReceiver;
    private boolean isRequestor;
    private boolean expectingPlay;
    private boolean expectingPlayAck;
    private boolean expectingMove;
    private boolean expectingMoveAck;



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
        p2pButton = new JRadioButton("P2P");
        buttonGroup = new ButtonGroup();
        playbutton = new JButton("play");
        gameBoard = new BoardPanel(omok);
        gameIsRunning=true;
        networkAdapterMessageListenerIsOn=false;
        isRequestor=false;
        isReceiver=false;

        expectingMove=true;
        expectingMoveAck=true;
        expectingPlay=true;
        expectingPlayAck=true;


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
        buttonGroup.add(p2pButton);



        panel.add(humanButton);
        panel.add(computerButton);
        panel.add(p2pButton);
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
                //if p2p game is selected set players to human and isOnNetwork to true
                else if(p2pButton.isSelected()){
                    isHuman=true;
                    p1.setIsHuman(true);
                    p2.setIsHuman(true);
                    p1.setIsOnNetwork(true);
                    p2.setIsOnNetwork(true);
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
        if(!p1.isOnNetwork()&&!p2.isOnNetwork()){
            addGameBoardActionListener(gameBoard);
        }

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
        if(isRequestor){
            if(pairDialog!=null){
                System.out.println("game board action listener turned on for requestor"+pairDialog.getPort());
            }

        }
        else{
            if(pairDialog!=null){
                System.out.println("game board action listener turn on for receiver"+pairDialog.getPort());
            }

        }

        gameBoard.addMouseListener(new java.awt.event.MouseAdapter(){


            public void mouseClicked(java.awt.event.MouseEvent evt) {

                if(isRequestor){
                    if(pairDialog!=null){
                        System.out.println("game board mouse clicked by requestor"+pairDialog.getPort());
                    }

                }
                else{
                    if(pairDialog!=null){
                        System.out.println("game board mouse clicked by receiver"+pairDialog.getPort());
                    }

                }

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
                            omok.switchTurns();
                            pairDialog.networkAdapter().writeMoveAck(x,y);

                            p1.setWinner(true);
                            p2.setWinner(true);


                        }
                        //if game isnt over, switch turns
                        else{
                            omok.switchTurns();
                            gameBoard.paintComponent(g);


                        }


                        currentPlayer=omok.getCurrentPlayer();


                        //if the opponent is on network playing p2p, make gameboard unclickable during their turn
                        if(currentPlayer.isOnNetwork()){
                            //send move to opponent on network

                            setExpectingMove(false);
                            setExpectingMoveAck(true);


                            pairDialog.networkAdapter().writeMove(x,y);
                            removeGameBoardActionListener(gameBoard);

                            setExpectingMove(true);
                            setExpectingMoveAck(true);

                        }


                        //if the opponent is a computer
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
        if(isRequestor){
            if(pairDialog!=null){
                System.out.println("game board action listener turned off for requestor"+pairDialog.getPort());
            }

        }
        else{
            if(pairDialog!=null){
                System.out.println("game board action listener turn off for receiver"+pairDialog.getPort());
            }

        }
        if(gameBoard.getMouseListeners().length!=0){
            gameBoard.removeMouseListener(gameBoard.getMouseListeners()[0]);
        }


    }
    //adds play button action listener
    public void addPlayButtonActionListener(){
        OmokGui omokGui=this;
        playbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //Play button selected
                setPreferredSize(new Dimension(700, 700)); // Set the preferred size to 700x700 because it wasn't update it after calling the method
                revalidate(); // Refresh the frame
                pack(); // Show in the middle of the screen

                //if its a human or computer game
                if(!p1.isOnNetwork()&&!p2.isOnNetwork()){
                    //add menu bar to screen
                    JMenuBar();
                    //add game board to screen
                    Game();
                    //start game
                    gameIsRunning=true;
                }
                //if its a p2p game
                else{
                    System.out.println(449);

                    //add menu bar to screen
                    JMenuBar();

                    //add game board to screen
                    Game();

                    //make game board unclickable
                    //removeGameBoardActionListener(gameBoard);
                    //game is not yet running
                    gameIsRunning=false;
                    //show pair with other player screen before starting game
                    //pairWithPlayerScreen();
                    addPairButton();
                    addPairButtonActionListener();
                    addPairPlayButton();
                    addPairPlayButtonActionListener();
                    // Generate a random integer between 9000 and 9500
                    Random random = new Random();
                    int randomPort = random.nextInt(501) + 9000;
                    pairDialog=new PairDialog(omokGui,randomPort);
                    //new Thread(new ListenForMoveThread(omokGui,pairDialog)).start();






                }



            }
        });
    }
    public void addPairButton(){
        pairButton = new JButton("Pair with Player");

        // Set the preferred size of the button
        Dimension buttonSize = new Dimension(275, 30); // Adjust the width and height as needed
        pairButton.setPreferredSize(buttonSize);
        //add(pairButton,BorderLayout.NORTH);





        // Set the preferred size of the button
        Dimension pairButtonSize = new Dimension(275, 30); // Adjust the width and height as needed
        ;
        pairButton.setSize(pairButtonSize);

        add(pairButton,BorderLayout.NORTH);



    }
    public void addPairPlayButton(){
        pairPlayButton = new JButton("Play P2P Game");
        Dimension pairPlayButtonSize = new Dimension(275, 30); // Adjust the width and height as needed
        pairPlayButton.setPreferredSize(pairPlayButtonSize);
        pairPlayButton.setSize(pairPlayButtonSize);
        add(pairPlayButton,BorderLayout.NORTH);


    }
    public void addPairButtonActionListener(){
        pairButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Generate a random integer between 9000 and 9500
                //Random random = new Random();
                //int randomPort = random.nextInt(501) + 9000;
                //pairDialog=new PairDialog(randomPort);
                pairDialog.setVisible(true);
                pairDialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);



            }

        });
    }
    public void removePairButtonActionListener() {
        ActionListener[] actionListeners = pairButton.getActionListeners();

        for (ActionListener listener : actionListeners) {
            pairButton.removeActionListener(listener);
        }
    }
    public void addPairPlayButtonActionListener(){
        OmokGui omokGui=this;



        pairPlayButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println(pairDialog.isConnected());

                if(pairDialog.isConnected()){
                    if(!networkAdapterMessageListenerIsOn){
                        //createNetworkAdapterMessageListener();
                    }


                    isRequestor=true;
                    isReceiver=false;

                    omokGui.setExpectingPlay(false);
                    omok.switchTurns();
                    pairDialog.networkAdapter().writePlay();

                    omokGui.setExpectingPlayAck(true);
                    omokGui.setExpectingMove(true);
                    omokGui.setExpectingMoveAck(false);








                }

            }
        });
    }


    public void removePairButton(){
        remove(pairButton);
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
    public void showGameRequestDialog() {
        // Use JOptionPane for a simple dialog
        int option = JOptionPane.showConfirmDialog(this, "Do you want to play a game requested by the other player?", "Game Request", JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            // Handle yes option
            gameIsRunning=true;
            pairDialog.setIsConnected(true);

            isRequestor=false;
            isReceiver=true;
            currentPlayer.setIsOnNetwork(false);




            pairDialog.writePlayAck(true,false);
            addGameBoardActionListener(gameBoard);

            setExpectingMove(false);
            setExpectingMoveAck(true);

        } else {
            // Handle no option
            gameIsRunning=false;
            pairDialog.writePlayAck(false,false);
            removeGameBoardActionListener(gameBoard);
            isRequestor=false;
            isReceiver=false;
            pairDialog.setIsConnected(false);

        }
    }
    public void createNetworkAdapterMessageListener(){

        networkAdapter=pairDialog.networkAdapter();

        networkAdapter.setMessageListener(new NetworkAdapter.MessageListener() {


            @Override
            public void messageReceived(NetworkAdapter.MessageType type, int x, int y) {
                Graphics g = gameBoard.getGraphics();
                switch (type) {
                    // Handle different message types here
                    case PLAY:
                        System.out.println("port: "+pairDialog.getPort()+" RECEIVED A PLAY");
                        pairDialog.msgDisplay().append("play: ");
                        showGameRequestDialog();
                        //hasPlay=true;
                        break;

                    case PLAY_ACK:
                        if(expectingPlayAck){
                            System.out.println("port: "+pairDialog.getPort()+" RECEIVED A PLAY ACK");
                            pairDialog.msgDisplay().append("play_ack: ");
                            //game accepted
                            if(x==1){
                                gameIsRunning=true;
                                //play receiver has first turn
                                if(y==0){
                                    removeGameBoardActionListener(gameBoard);

                                    System.out.println("play receiver has first turn");

                                    omok.switchTurns();
                                    currentPlayer.setIsOnNetwork(false);
                                    omok.switchTurns();





                                }

                                //play requestor has first turn
                                else{
                                    addGameBoardActionListener(gameBoard);

                                    System.out.println("play requestor has first turn");
                                }
                                isRequestor=true;
                                isReceiver=false;
                            }
                            //game rejected
                            else{
                                gameIsRunning=false;
                                removeGameBoardActionListener(gameBoard);
                                isReceiver=false;
                                isRequestor=false;

                            }
                        }
                        break;

                    //hasPlayAck=true;
                    case MOVE:
                        if(expectingMove){
                            System.out.println("port: "+pairDialog.getPort()+" RECEIVED A MOVE, x:"+x+" y:"+y);
                            pairDialog.msgDisplay().append("move: ");

                            //hasMove=true;
                            currentPlayer=omok.getCurrentPlayer();
                            omok.placeStone(x,y,currentPlayer);

                            //if game is won after stone placed, remove gameboard action listener making it unclickable
                            if(omok.isWonBy(p1)||omok.isWonBy(p2)){
                                removeGameBoardActionListener(gameBoard);
                                gameBoard.paintComponent(g);
                                gameIsRunning=false;
                                omok.switchTurns();
                                pairDialog.networkAdapter().writeMoveAck(x,y);

                                p1.setWinner(false);
                                p2.setWinner(false);



                            }
                            //if game isnt over, switch turns
                            else{
                                setExpectingMoveAck(false);
                                setExpectingMove(true);

                                omok.switchTurns();
                                gameBoard.paintComponent(g);
                                //if the opponent is on network playing p2p, make gameboard unclickable during their turn

                                //write play acknowledge on network
                                pairDialog.networkAdapter().writeMoveAck(x,y);
                                System.out.println(694);
                                addGameBoardActionListener(gameBoard);

                            }
                        }
                        break;


                    case MOVE_ACK:
                        if(expectingMoveAck){

                            setExpectingMove(true);
                            setExpectingMoveAck(true);

                            System.out.println("port: "+pairDialog.getPort()+" RECEIVED A MOVE ACK, x: "+x+" y: "+y);
                            pairDialog.msgDisplay().append("move_ack: ");
                            //hasMoveAck=true;
                            removeGameBoardActionListener(gameBoard);

                            gameBoard.paintComponent(g);
                            System.out.println("PAINT COMPONENT");

                        }
                        break;

                    case QUIT:
                        //hasQuit=true;

                        break;

                }


            }
        });

        // Start receiving messages asynchronously
        networkAdapter.receiveMessagesAsync();

    }
    public void setNetworkAdapterMessageListenerIsOn(boolean isOn){
        networkAdapterMessageListenerIsOn=isOn;
    }

    public void setExpectingPlay(boolean expecting){
        expectingPlay=expecting;
    }
    public void setExpectingPlayAck(boolean expecting){

        expectingPlayAck=expecting;
    }
    public void setExpectingMove(boolean expecting){

        expectingMove=expecting;
    }
    public void setExpectingMoveAck(boolean expecting){

        expectingMoveAck=expecting;
    }












}





/*
                    case MOVE:
                        if(expectingMove){
                            System.out.println("port: "+pairDialog.getPort()+" RECEIVED A MOVE, x:"+x+" y:"+y);
                            pairDialog.msgDisplay().append("move: ");

                            //hasMove=true;
                            currentPlayer=omok.getCurrentPlayer();
                            omok.placeStone(x,y,currentPlayer);

                            //if game is won after stone placed, remove gameboard action listener making it unclickable
                            if(omok.isWonBy(p1)||omok.isWonBy(p2)){
                                removeGameBoardActionListener(gameBoard);
                                gameBoard.paintComponent(g);
                                gameIsRunning=false;

                            }
                            //if game isnt over, switch turns
                            else{
                                setExpectingMoveAck(false);
                                //setExpectingMove(true);  DELETED

                                setExpectingMove(false);//ADDED

                                omok.switchTurns();
                                gameBoard.paintComponent(g);
                                //if the opponent is on network playing p2p, make gameboard unclickable during their turn

                                //write play acknowledge on network
                                pairDialog.networkAdapter().writeMoveAck(x,y);

                                addGameBoardActionListener(gameBoard);

                                //setExpectingMoveAck(false); DELETED
                                setExpectingMoveAck(true);
                                //setExpectingMove(true); DELETED
                                setExpectingMove(false);//ADDED

                            }
                        }
                        break;


                    case MOVE_ACK:
                        if(expectingMoveAck){

                            //setExpectingMove(false); DELETED
                            setExpectingMove(true);
                            //setExpectingMoveAck(true); DELETED
                            setExpectingMoveAck(false);//ADDED

                            omok.switchTurns();//ADDED

                            System.out.println("port: "+pairDialog.getPort()+" RECEIVED A MOVE ACK, x: "+x+" y: "+y);
                            pairDialog.msgDisplay().append("move_ack: ");
                            //hasMoveAck=true;
                            removeGameBoardActionListener(gameBoard);
                        }
                        break;

                     */
package omok.net;
// $Id: ChatDialogUI.java,v 1.3 2018/04/06 21:32:56 cheon Exp $

import omok.model.OmokGui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.*;
import java.util.Enumeration;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class PairDialog extends JDialog  {

    private final static Dimension DIMENSION = new Dimension(400, 400);

    private JButton connectButton;
    private JButton sendButton;
    private JTextField serverEdit;
    private JTextField portEdit;
    private JTextArea msgDisplay;
    private JTextField msgEdit;

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private Socket connectionRequestSocket;
    private boolean isConnected;
    private PrintWriter messageOutput;

    private int port;

    private NetworkAdapter networkAdapter;
    private boolean hasPlay;
    private boolean hasPlayAck;
    private boolean hasMove;
    private boolean hasMoveAck;
    private boolean hasQuit;
    private OmokGui omokGui;

    public PairDialog(OmokGui omokGui, int port) {

        this(DIMENSION, port);
        this.omokGui=omokGui;
    }

    public PairDialog(Dimension dim, int port) {
        super((JFrame) null, "Omok");
        this.port = port;

        createServerSocket(port);





        configureGui();

        setSize(dim);
        setLocationRelativeTo(null);
        isConnected=false;
        connectButton.addActionListener(this::connectClicked);

        hasPlay=false;
        hasPlayAck=false;
        hasMove=false;
        hasMoveAck=false;
        hasQuit=false;


    }

    /*
        private void configureGui() {
            // Create components
            JPanel connectPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
            connectButton = new JButton("Connect");
            connectButton.setFocusPainted(false);
            serverEdit = new JTextField("localhost", 15);
            portEdit = new JTextField(Integer.toString(port), 4);
            connectPanel.add(connectButton);
            connectPanel.add(serverEdit);
            connectPanel.add(portEdit);

            msgDisplay = new JTextArea(10, 30);
            msgDisplay.setEditable(false);
            DefaultCaret caret = (DefaultCaret) msgDisplay.getCaret();
            caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE); // autoscroll
            JScrollPane msgScrollPane = new JScrollPane(msgDisplay);

            JPanel sendPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
            msgEdit = new JTextField("Enter a message.", 20);
            sendButton = new JButton("Send");
            sendButton.setFocusPainted(false);
            sendPanel.add(msgEdit);
            sendPanel.add(sendButton);

            // Set layout
            setLayout(new BorderLayout());
            add(connectPanel, BorderLayout.NORTH);
            add(msgScrollPane, BorderLayout.CENTER);
            add(sendPanel, BorderLayout.SOUTH);




        }

     */
    private void configureGui() {
        // Create components
        JPanel connectPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(7, 7, 7, 7); // Adjust spacing

        // Text for Server
        JLabel serverLabel = new JLabel("Server:");
        serverEdit = new JTextField("localhost", 15);

        // Add Server components to connectPanel
        connectPanel.add(serverLabel, gbc);
        gbc.gridx++;
        connectPanel.add(serverEdit, gbc);
        gbc.gridx = 0;
        gbc.gridy++;

        // Text for the third set (Port)
        JLabel label3 = new JLabel("Port");
        portEdit = new JTextField(Integer.toString(port), 4);
        portEdit.setEditable(false);

        // Add the third set of components to connectPanel
        connectPanel.add(label3, gbc);
        gbc.gridx++;
        connectPanel.add(portEdit, gbc);
        gbc.gridx = 0;
        gbc.gridy++;

        // Text for the IP Address
        JLabel ipLabel = new JLabel("IP Address:");
        JTextField textFieldIP = new JTextField(getLocalIpAddress(), 15);
        textFieldIP.setEditable(false);

        // Add the IP Address components to connectPanel
        connectPanel.add(ipLabel, gbc);
        gbc.gridx++;
        connectPanel.add(textFieldIP, gbc);
        gbc.gridx = 0;
        gbc.gridy++;

        // Text for the connect port
        JLabel connectPortLabel = new JLabel("Connect Port:");
        portEdit = new JTextField(Integer.toString(port), 4);

        // Add the connect port components to connectPanel
        connectPanel.add(connectPortLabel, gbc);
        gbc.gridx++;
        connectPanel.add(portEdit, gbc);
        gbc.gridx = 0;
        gbc.gridy++;

        // Text for the second Port
        JLabel portLabel2 = new JLabel("ID:");
        JTextField portEdit2 = new JTextField("80717003", 15);

        // Add the second set of Port components to connectPanel
        connectPanel.add(portLabel2, gbc);
        gbc.gridx++;
        connectPanel.add(portEdit2, gbc);
        gbc.gridx = 0;
        gbc.gridy++;

        // Text for the fourth set
        JLabel label4 = new JLabel("4-----:");
        JTextField textField4 = new JTextField("", 15);

        // Add the fourth set of components to connectPanel
        connectPanel.add(label4, gbc);
        gbc.gridx++;
        connectPanel.add(textField4, gbc);
        gbc.gridx = 0;
        gbc.gridy++;

        // Text for the fifth set
        JLabel label5 = new JLabel("5-----:");
        JTextField textField5 = new JTextField("", 15);

        // Add the fifth set of components to connectPanel
        connectPanel.add(label5, gbc);
        gbc.gridx++;
        connectPanel.add(textField5, gbc);
        gbc.gridx = 0;
        gbc.gridy++;

        // Create JButton for Connect
        connectButton = new JButton("Connect");
        connectButton.setFocusPainted(false);

        // Add the Connect button to connectPanel
        gbc.gridwidth = 2; // Make the button span two columns
        connectPanel.add(connectButton, gbc);

        // Create another JPanel for additional spacing
        JPanel spacingPanel = new JPanel();
        spacingPanel.setPreferredSize(new Dimension(10, 10)); // Add vertical spacing

        // Message display area
        msgDisplay = new JTextArea(10, 30);
        msgDisplay.setEditable(false);
        DefaultCaret caret = (DefaultCaret) msgDisplay.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE); // autoscroll
        JScrollPane msgScrollPane = new JScrollPane(msgDisplay);

        // Set layout
        setLayout(new BorderLayout());

        // Add connectPanel to the NORTH
        add(connectPanel, BorderLayout.NORTH);

        // Add spacingPanel to the CENTER
        add(spacingPanel, BorderLayout.CENTER);

        // Add msgScrollPane to the CENTER
        add(msgScrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();

        // Create JButton with ActionListener for Cancel
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFocusPainted(false);
        cancelButton.addActionListener(e -> dispose());

        // Add the Cancel button to buttonPanel
        buttonPanel.add(cancelButton);

        // Add buttonPanel to the SOUTH
        add(buttonPanel, BorderLayout.SOUTH);
    }
    private String getLocalIpAddress2() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return "Unknown";
        }
    }
    private String getLocalIpAddress() {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress address = addresses.nextElement();
                    if (!address.isLoopbackAddress() && address instanceof Inet4Address) {
                        return address.getHostAddress();
                    }
                }
            }
            return "Unknown";
        } catch (SocketException e) {
            e.printStackTrace();
            return "Unknown";
        }
    }

    private void connectClicked(ActionEvent event) {
        String server = serverEdit.getText();
        int port = Integer.parseInt(portEdit.getText());


        if (isConnected) {
            // Disconnect
            try {
                connectButton.setText("Connect");
                serverSocket.close();
                isConnected = false;
                warn("Disconnected");

                // Use NetworkAdapter to send QUIT message
                networkAdapter.writeQuit();
            } catch (IOException e) {
                warn("Error");
            }
        } else {
            // Connect

            connectButton.setText("Disconnect");
            //warn("Connected");

            isConnected = true;
            createClientSocket(Integer.parseInt(portEdit.getText()));


        }
    }


    private void disconnect() {
        try {
            connectButton.setText("Connect");
            serverSocket.close();
            isConnected = false;
            warn("Disconnected from the server.");
            msgEdit.setText("");
        } catch (IOException e) {
            warn("Error disconnecting from the server.");
        }
    }

    //protected void showPairingConfirmation(NetworkAdapter networkAdapter){
    protected void showPairingConfirmation() {
        //this.networkAdapter=networkAdapter;
        int option = JOptionPane.showConfirmDialog(this,
                "Opponent player from port: "+clientSocket.getPort()+" wants to pair with you. Do you accept?",
                "Pairing Confirmation",
                JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            // Pairing confirmed
            try {
                messageOutput = new PrintWriter(clientSocket.getOutputStream(), true);
                messageOutput.println("PAIR_CONFIRMED");
                //omokGui.createNetworkAdapterMessageListener();
                //networkAdapter.writePlayAck(true,true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Pairing declined
            try {
                messageOutput = new PrintWriter(clientSocket.getOutputStream(), true);
                messageOutput.println("PAIR_DECLINED");
                closeConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    protected void closeConnection() {
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
            if (clientSocket != null && !clientSocket.isClosed()) {
                clientSocket.close();
            }
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(this::dispose);
    }

    protected void warn(String msg) {
        JOptionPane.showMessageDialog(this, msg, "JavaChat", JOptionPane.PLAIN_MESSAGE);
    }


    public int getPort(){
        return this.port;
    }
    public void createServerSocket(int port){
        new Thread(new ListenForConnection(this,port)).start();
    }


    public void createClientSocket(int clientPort){
        //int clientPort = Integer.parseInt(portEdit.getText());
        try {
            //clientSocket = new Socket("opuntia.cs.utep.edu",clientPort );
            clientSocket = new Socket(serverEdit.getText(),clientPort );
            networkAdapter = new NetworkAdapter(clientSocket);
            //createNetworkAdapterMessageListener();
            omokGui.createNetworkAdapterMessageListener();



        } catch (IOException e) {
            // handle exception …
            e.printStackTrace();
            //throw new RuntimeException();
        }

    }
    protected void setServerSocket(ServerSocket serverSocket){
        this.serverSocket=serverSocket;
    }
    protected void setClientSocket(Socket clientSocket){
        this.clientSocket=clientSocket;
    }



    protected JTextArea getMsgDisplay(){
        return msgDisplay;
    }
    protected void setMsgDisplay(JTextArea msgDisplay){
        this.msgDisplay=msgDisplay;
    }


    // Add this method to update the msgDisplay
    protected void addToMsgDisplay(String message) {
        SwingUtilities.invokeLater(() -> {
            msgDisplay.append(message + "\n");
            msgDisplay.setCaretPosition(msgDisplay.getDocument().getLength());
        });
    }
    protected void setNetworkAdapter(NetworkAdapter networkAdapter){
        this.networkAdapter=networkAdapter;
    }
    protected NetworkAdapter getNetworkAdapter(){
        return networkAdapter;
    }
    public void setIsConnected(boolean isConnected){
        this.isConnected=isConnected;
    }
    /*
    protected void createNetworkAdapterMessageListener(){

        networkAdapter.setMessageListener(new NetworkAdapter.MessageListener() {


            @Override
            public void messageReceived(NetworkAdapter.MessageType type, int x, int y) {

                switch (type) {
                    // Handle different message types here
                    case PLAY:
                        msgDisplay.append("PLAY RECEIVED");
                        hasPlay=true;

                    case PLAY_ACK:
                        msgDisplay.append("PLAY_ACK RECEIVED");
                        hasPlayAck=true;
                    case MOVE:
                        hasMove=true;
                    case MOVE_ACK:
                        hasMoveAck=true;
                    case QUIT:
                        hasQuit=true;

                }


            }
        });

        // Start receiving messages asynchronously
        networkAdapter.receiveMessagesAsync();
        //networkAdapter.writePlay();
        //networkAdapter.writePlayAck(true,true);
    }

     */
    public boolean isConnected(){
        return isConnected;
    }
    public void writePlay(){
        networkAdapter.writePlay();

    }
    public void writePlayAck(boolean response, boolean turn){

        networkAdapter.writePlayAck(response,turn);
    }
    public boolean hasPlay(){return hasPlay;}
    public boolean hasPlayAck(){return hasPlayAck;}
    public boolean hasMove(){return hasMove;}
    public boolean hasMoveAck(){return hasMoveAck;}
    public boolean hasQuit(){return hasQuit;}
    public void setHasPlay(boolean hasPlay){
        this.hasPlay=hasPlay;
    }
    public void setHasPlayAck(boolean hasPlayAck){
        this.hasPlayAck=hasPlayAck;
    }
    public void setHasMove(boolean hasMove){
        this.hasMove=hasMove;
    }
    public void setHasMoveAck(boolean hasMoveAck){
        this.hasMoveAck=hasMoveAck;
    }
    public void setHasQuit(boolean hasQuit){
        this.hasQuit=hasQuit;
    }
    public NetworkAdapter networkAdapter(){return networkAdapter;}
    public JTextArea msgDisplay(){return msgDisplay;}
    public OmokGui omokGui(){return omokGui;}






}

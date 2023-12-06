package omok.net;

import java.io.*;
import java.net.*;
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
public class ListenForConnection implements Runnable {
    private int port;
    private Socket clientSocket;
    private PairDialog pairDialog;
    private ServerSocket serverSocket;
    private NetworkAdapter networkAdapter;
    public ListenForConnection(PairDialog pairDialog,int port){
        this.port=port;
        this.pairDialog=pairDialog;
    }
    public void run() {
        try {
            serverSocket = new ServerSocket(port);
            pairDialog.setServerSocket(serverSocket);

            while (true) {

                // Wait for a client to connect
                clientSocket = serverSocket.accept();
                pairDialog.setClientSocket(clientSocket);
                pairDialog.showPairingConfirmation();
                pairDialog.setIsConnected(true);


                networkAdapter=new NetworkAdapter(clientSocket);
                pairDialog.setNetworkAdapter(networkAdapter);
                //pairDialog.createNetworkAdapterMessageListener();

                pairDialog.omokGui().createNetworkAdapterMessageListener();
                pairDialog.omokGui().setNetworkAdapterMessageListenerIsOn(true);
                //createNetworkAdapterMessageListener();



                break;







            }
        } catch (IOException e) {
            e.printStackTrace();  // Handle the exception appropriately (log it or show an error dialog)
        }
    }


}

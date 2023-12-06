package omok.net;

import omok.model.OmokGui;

import javax.swing.*;

public class ListenForMoveThread implements Runnable {
    private PairDialog pairDialog;
    private OmokGui omokGui;

    public ListenForMoveThread(OmokGui omokGui, PairDialog pairDialog) {
        this.omokGui = omokGui;
        this.pairDialog = pairDialog;
    }

    public void run() {
        // code to run concurrently
        System.out.println("listen for move thread started");
        while(true){
            System.out.println(true);

            if (pairDialog.hasPlay()) {
                System.out.println("WE found that pair dialog has a play");

                // show pair dialog asking if they want to play a game requested by other player
                // write create pair dialog method
                //SwingUtilities.invokeLater(() -> omokGui.showGameRequestDialog());
                omokGui.showGameRequestDialog();
                pairDialog.setHasPlay(false);

            } else if (pairDialog.hasPlayAck()) {
                // start game
            } else if (pairDialog.hasMove()) {
                // make gameboard unclickable
                // other player is sending you a move
            } else if (pairDialog.hasMoveAck()) {
                // it's your turn
                // ...
            } else if (pairDialog.hasQuit()) {
                // end game right then and there
            }
        }
        // System.out.println(pairDialog.hasPlay());

    }
}

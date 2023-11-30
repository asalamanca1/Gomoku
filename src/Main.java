//Andre Salamanca and Miguel Angel Garcia Jacquez
import omok.model.OmokGui;

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> new OmokGui());

    }
}
/*
IDEAS:
add a isOnNetwork parameter to player,
modify the omokgui for each move: if isHuman, if isComputer, if isOnNetwork

how to access move messages from pairdialog class? add variables in pairdialog: boolean opponentHasMove, array move:[x,y],
inside of omokgui: if pairdialog.opponentHasMove: place stone at pairdialog.getMove()

have write methods inside of pairdialog, that way i can call pairdialog.writeMove inside of omokGui, and the writeMove method inside of pair dialog literally just calls networkAdapter.writeplay(), same logic with
pairdialog.receivedMessage() returns boolean indicating if we have a message to open

 */
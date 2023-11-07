package omok.model;

import java.util.List;
import java.util.Map;

public class UserInterface {
    private List<Integer> xCoordinates;

    private List<Integer> yCoordinates;
    private Map<Integer, Integer> xCoordinatesRange;
    private Map<Integer, Integer> yCoordinatesRange;
    Board omok;
    private BoardPanel gameBoard;
    private Player p1;


    public UserInterface(){
        omok = new Board();//This line should be repainted
        //var gameBoard = new BoardPanel(omok);
        p1 = new Player("Mike");
    }
}

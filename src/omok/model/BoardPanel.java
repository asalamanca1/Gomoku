package omok.model;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class BoardPanel extends JPanel{
    private Board board;

    private int size;

    //stores all x/y coordinates on panel in a list
    protected List<Integer> xCoordinates;
    protected List<Integer> yCoordinates;


    //dictionary stores all x/y coordinates within a range of 20, KEY: all coordinates within range of 20 from x/y coordinate -> VALUE: corressponding intersection coordinate on panel
    protected Map<Integer, Integer> xCoordinatesRange;
    protected Map<Integer, Integer> yCoordinatesRange;


    //dictionary stores x/y coordinates on panel, KEY: panel coordinate-> VALUE: x/y coordinate on game board(1-15)
    protected Map<Integer, Integer> xCoordinatesOnPanel;
    protected Map<Integer, Integer> yCoordinatesOnPanel;

    //dictionary stores x/y coordinates on board, KEY: x/y coordinate on game board(1-15) -> VALUE: corressponding panel coordinate
    protected Map<Integer, Integer> xCoordinatesOnBoard;
    protected Map<Integer, Integer> yCoordinatesOnBoard;


    protected omok.model.Board.Place[][]intersections;





    public BoardPanel(Board board){

        this.board=board;
        size=board.size();
        yCoordinates = new ArrayList<>();
        xCoordinates = new ArrayList<>();
        xCoordinatesRange = new HashMap<>();
        yCoordinatesRange = new HashMap<>();
        xCoordinatesOnPanel=new HashMap<>();
        yCoordinatesOnPanel=new HashMap<>();
        xCoordinatesOnBoard=new HashMap<>();
        yCoordinatesOnBoard=new HashMap<>();
        intersections=board.getIntersections();


    }
    protected void paintComponent(Graphics g) {


        intersections=board.getIntersections();
        int j=1;
        Dimension d = getSize();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, d.width, d.height);
        g.setColor(Color.YELLOW);

        g.fillRect(75, 65, 550, 550);

        g.setColor(Color.BLACK);

        //draw vertical lines
        for(int i=0;i<=size;i++){
            j = i*(550/size)+75;
            xCoordinatesOnPanel.put(j,i+1);

            xCoordinatesOnBoard.put(i+1,j);

            g.drawLine(j,65,j,615);
            for(int k=(j-10);k<=(j+10);k++){
                xCoordinatesRange.put(k,j);
                xCoordinates.add(k);
            }
            //xCoordinates.add(i,j);

        }
        int h=j;

        //draw horizontal lines
        for(int i=0;i<=size;i++){
            j = i*(550/size)+65;
            yCoordinatesOnPanel.put(j,i+1);
            yCoordinatesOnBoard.put(i+1,j);
            g.drawLine(75,j,625,j);
            for(int k=(j-10);k<=(j+10);k++){
                yCoordinatesRange.put(k,j);
                yCoordinates.add(k);
            }
            //yCoordinates.add(i,j);

        }
        Collections.reverse(yCoordinates);


        //fill in the offset lines with white

        g.setColor(Color.WHITE);
        g.fillRect(75, j, 550, 20);
        g.fillRect(h, 65, 20, 550);


        //draw all stones on board
        for(int i=0;i<intersections.length;i++){
            for(int j=0;j<intersections[i].length;j++){
                if(intersections[i][j]!=null){


                }
            }
        }



        g.setColor(Color.RED);
        g.drawOval(9,10,15,15);
        g.fillOval(9,10,15,15);
        g.setColor(Color.BLUE);
        g.drawOval(145,180,15,15);
        g.fillOval(145,180,15,15);
        g.setColor(Color.MAGENTA);
        g.drawString("Andre Salamanca", 60, 40);
        //repaint();


    }
    public List<Integer> getXcoordinates(){

        return xCoordinates;
    }
    public List<Integer> getYcoordinates(){

        return yCoordinates;
    }
    public Map<Integer, Integer> getxCoordinatesRange(){
        return xCoordinatesRange;
    }
    public Map<Integer, Integer> getyCoordinatesRange(){
        return yCoordinatesRange;
    }
    public Map<Integer, Integer> getXcoordinatesOnPanel(){
        return xCoordinatesOnPanel;
    }
    public Map<Integer, Integer> getYcoordinatesOnPanel(){
        return yCoordinatesOnPanel;
    }

    public static void main(String[] args) {
        //Board omok = new Board();
        //SwingUtilities.invokeLater(() -> new BoardPanel(omok));
    }
}

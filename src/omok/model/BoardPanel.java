package omok.model;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
//PROBLEMS:
//whenever i win a game, then stop it, game always restarts with player 2 as first player
//add draw game, if board is full, end game

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
    private Player currentPlayer;
    private Player p1;
    private Player p2;
    private boolean gameOver;
    private int numMoves;
    private List<omok.model.Board.Place> winningRow;






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
        p1=board.getPlayers()[0];
        p2=board.getPlayers()[1];
        currentPlayer=board.getPlayers()[2];
        gameOver=false;




    }
    protected void paintComponent(Graphics g) {

        numMoves=0;
        intersections=board.getIntersections();
        int j=1;
        Dimension d = getSize();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, d.width, d.height);
        g.setColor(Color.YELLOW);

        g.fillRect(75, 65, 550, 550);

        g.setColor(Color.BLACK);
        g.drawRect(75, 65, 550, 550);

        //draw vertical lines
        for(int i=1;i<=(size+1);i++){
            j = i*(550/(size+1))+75;

            xCoordinatesOnPanel.put(j,i);

            xCoordinatesOnBoard.put(i,j);

            g.drawLine(j,65,j,615);
            for(int k=(j-10);k<=(j+10);k++){
                xCoordinatesRange.put(k,j);
                xCoordinates.add(k);
            }
            //xCoordinates.add(i,j);

        }
        int h=j;

        //draw horizontal lines
        for(int i=1;i<=(size+1);i++){
            j = i*(550/(size+1))+65;

            yCoordinatesOnPanel.put(j,i);
            yCoordinatesOnBoard.put(i,j);
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
/*
        System.out.println(intersections.length);
        //draw all stones on board
        for(int i=0;i<intersections.length;i++){
        System.out.println(113);
            for(int k=0;k<intersections[i].length;k++){
                System.out.println(115);
                if(intersections[k][j]!=null){
                    System.out.println(117);
                    int x = xCoordinatesOnBoard.get(j+1);
                    int y = yCoordinatesOnBoard.get(k+1);
                    g.setColor(Color.BLACK);
                    g.fillOval(x, y, 30, 30);

                }
            }
        }

 */
        for(int i=1;i<16;i++){
            for(int k=1;k<16;k++){
                if(!board.isEmpty(i,k)&&board.isOccupied(i,k)){
                    int x = xCoordinatesOnBoard.get(i);
                    int y = yCoordinatesOnBoard.get(k);
                    p1=board.getPlayers()[0];
                    p2=board.getPlayers()[1];
                    currentPlayer=board.getCurrentPlayer();


                    if(intersections[k-1][i-1].getPlayer()==p1){


                        g.setColor(Color.BLACK);
                        g.fillOval(x-15, y-15, 30, 30);
                        g.setColor(Color.WHITE);
                        g.drawOval(x-15, y-15, 30, 30);
                        numMoves+=1;


                    }
                    else if(intersections[k-1][i-1].getPlayer()==p2){



                        g.setColor(Color.WHITE);
                        g.fillOval(x-15, y-15, 30, 30);
                        g.setColor(Color.BLACK);
                        g.drawOval(x-15, y-15, 30, 30);
                        numMoves+=1;

                    }


                }
            }
        }
        p1=board.getPlayers()[0];
        p2=board.getPlayers()[1];
        currentPlayer=board.getCurrentPlayer();


        if(numMoves>2){
            if(board.isWonBy(p1)||board.isWonBy(p2)){
                gameOver=true;
                winningRow=board.winningRow();
                System.out.println("WINNING ROW"+board.winningRow());

                //highlight winning row
                for(int i =0;i<winningRow.size();i++){
                    int x = xCoordinatesOnBoard.get(winningRow.get(i).getX());
                    int y = yCoordinatesOnBoard.get(winningRow.get(i).getY());
                    g.setColor(Color.RED);
                    g.fillOval(x-15, y-15, 30, 30);

                }


            }

        }



        //if game isnt over, print current players turn
        if(!gameOver){
            if(currentPlayer==p1){
                g.setColor(Color.WHITE);
                g.drawRect(50,50,200,13);
                g.fillRect(50,50,200,13);
                g.setColor(Color.BLACK);
                g.drawString("Player 1's Turn:",50,50);
            }
            else if(currentPlayer==p2){
                g.setColor(Color.WHITE);
                g.drawRect(50,50,200,13);
                g.fillRect(50,50,200,13);
                g.setColor(Color.BLACK);
                g.drawString("Player 2's Turn:",50,50);
            }
        }
        //if game is over, print the winners name on screen
        else if(gameOver){
            //erase current players turn
            g.setColor(Color.WHITE);
            g.drawRect(50,50,200,13);
            g.fillRect(50,50,200,13);
            g.setColor(Color.BLACK);
            if(p2==currentPlayer){
                System.out.println("PLAYER 1 HAS WON GAME");
                g.drawString("PLAYER 1 HAS WON GAME",50,50);
                g.drawString("GAME OVER",550,50);
            }
            else {
                g.drawString("PLAYER 2 HAS WON GAME",50,50);
                g.drawString("GAME OVER",550,50);
            }



        }














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

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }


}

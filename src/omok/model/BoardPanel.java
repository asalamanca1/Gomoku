//Andre Salamanca and Miguel Angel Garcia Jacquez
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
    private Player currentPlayer;
    private Player p1;
    private Player p2;
    private boolean gameOver;
    private int numMoves;
    private List<omok.model.Board.Place> winningRow;
    private boolean isFull;






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

        //draw yellow background
        g.setColor(Color.YELLOW);
        g.fillRect(75, 65, 550, 550);

        //draw black border
        g.setColor(Color.BLACK);
        g.drawRect(75, 65, 550, 550);

        //draw vertical lines
        for(int i=1;i<=(size+1);i++){
            j = i*(550/(size+1))+75;

            //store x coordinates on panel to corressponding intersection coordinate, key: panel coordinate -> x/y coordinate(1-15)
            xCoordinatesOnPanel.put(j,i);
            //store x coordinates on board to corressponding panel coordinates, key: x/y coordinate(1-15) -> panel coordinate
            xCoordinatesOnBoard.put(i,j);

            g.drawLine(j,65,j,615);

            //create a range of 20 for this coordinate and store it in a dictionary and list
            for(int k=(j-10);k<=(j+10);k++){
                //assign all coordinates within a range of 20 as a key, the key yields the correct coordinate
                xCoordinatesRange.put(k,j);
                //add range of 20 for this coordinate to list
                xCoordinates.add(k);
            }


        }
        int h=j;

        //draw horizontal lines
        for(int i=1;i<=(size+1);i++){
            j = i*(550/(size+1))+65;
            //store y coordinates on panel to corressponding intersection coordinate, key: panel coordinate -> x/y coordinate(1-15)
            yCoordinatesOnPanel.put(j,i);
            //store y coordinates on board to corressponding panel coordinates, key: x/y coordinate(1-15) -> panel coordinate
            yCoordinatesOnBoard.put(i,j);
            g.drawLine(75,j,625,j);

            //create a range of 20 for this coordinate and store it in a dictionary and list
            for(int k=(j-10);k<=(j+10);k++){
                //assign all coordinates within a range of 20 as a key, the key yields the correct coordinate
                yCoordinatesRange.put(k,j);
                //add range of 20 for this coordinate to list
                yCoordinates.add(k);
            }


        }
        //reverse list because it was made backwards(from top to bottom)
        Collections.reverse(yCoordinates);


        //fill in the offset lines with white
        g.setColor(Color.WHITE);
        g.fillRect(75, j, 550, 20);
        g.fillRect(h, 65, 20, 550);


        //draw stones
        for(int i=1;i<16;i++){
            for(int k=1;k<16;k++){
                //if a stone exists
                if(!board.isEmpty(i,k)&&board.isOccupied(i,k)){
                    int x = xCoordinatesOnBoard.get(i);
                    int y = yCoordinatesOnBoard.get(k);
                    p1=board.getPlayers()[0];
                    p2=board.getPlayers()[1];
                    currentPlayer=board.getCurrentPlayer();

                    //if stone belongs to player 1
                    if(intersections[k-1][i-1].getPlayer()==p1){

                        //draw black stone
                        g.setColor(Color.BLACK);
                        g.fillOval(x-15, y-15, 30, 30);
                        g.setColor(Color.WHITE);
                        g.drawOval(x-15, y-15, 30, 30);
                        numMoves+=1;


                    }
                    //if stone belongs to player 2
                    else if(intersections[k-1][i-1].getPlayer()==p2){


                        //draw white stone
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

        //if each player has made at least one move, we can start checking for a won game
        if(numMoves>2){
            //if game is won
            if(board.isWonBy(p1)||board.isWonBy(p2)){
                gameOver=true;
                winningRow=board.winningRow();
                System.out.println("WINNING ROW"+board.winningRow());

                //highlight winning row
                for(int i =0;i<winningRow.size();i++){
                    int x = xCoordinatesOnBoard.get(winningRow.get(i).getX());
                    int y = yCoordinatesOnBoard.get(winningRow.get(i).getY());
                    //draw winning row as red stones
                    g.setColor(Color.RED);
                    g.fillOval(x-15, y-15, 30, 30);

                }


            }

        }
        //if the board is full
        if(board.isFull()){
            isFull=true;
            gameOver=true;
        }
        else{
            isFull=false;
        }

        //if game isnt over and board isnt full, print current players turn
        if(!gameOver&&!isFull){
            //draw Player 1s turn
            if(currentPlayer==p1){
                g.setColor(Color.WHITE);
                g.drawRect(50,50,200,13);
                g.fillRect(50,50,200,13);
                g.setColor(Color.BLACK);
                g.drawString("Player 1's Turn:",50,50);
            }
            //draw player 2s turn
            else if(currentPlayer==p2&&!isFull){
                g.setColor(Color.WHITE);
                g.drawRect(50,50,200,13);
                g.fillRect(50,50,200,13);
                g.setColor(Color.BLACK);
                g.drawString("Player 2's Turn:",50,50);
            }
        }
        //if game is over and board isnt full, print the winners name on screen
        else if(gameOver&&!isFull){
            //erase current players turn
            g.setColor(Color.WHITE);
            g.drawRect(50,50,200,13);
            g.fillRect(50,50,200,13);
            g.setColor(Color.BLACK);

            if(p2==currentPlayer){

                g.drawString("PLAYER 2 HAS WON GAME",50,50);
                if(!currentPlayer.isWinner()){



                    g.setColor(Color.WHITE);
                    g.drawRect(50,39,200,13);
                    g.fillRect(50,39,200,13);
                    g.setColor(Color.BLACK);

                    g.drawString("YOU HAVE LOST GAME...",50,50);



                }
                else{
                    g.setColor(Color.WHITE);
                    g.drawRect(50,39,200,13);
                    g.fillRect(50,39,200,13);
                    g.setColor(Color.BLACK);

                    g.drawString("CONGRATULATIONS YOU WON THE GAME!",50,50);
                }
                g.drawString("GAME OVER",550,50);
            }
            else {
                g.drawString("PLAYER 1 HAS WON GAME",50,50);
                if(!currentPlayer.isWinner()){



                    g.setColor(Color.WHITE);
                    g.drawRect(50,39,200,13);
                    g.fillRect(50,39,200,13);
                    g.setColor(Color.BLACK);

                    g.drawString("YOU HAVE LOST GAME...",50,50);



                }
                else{
                    g.setColor(Color.WHITE);
                    g.drawRect(50,39,200,13);
                    g.fillRect(50,39,200,13);
                    g.setColor(Color.BLACK);

                    g.drawString("CONGRATULATIONS YOU WON THE GAME!",50,50);
                }
                g.drawString("GAME OVER",550,50);
            }



        }
        //if game is over and board is full, print draw game on screen
        else if(gameOver&&isFull){
            g.drawString("GAME HAS RESULTED IN A DRAW",50,50);
            g.drawString("GAME OVER",550,50);
        }



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




















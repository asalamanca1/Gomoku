//Andre Salamanca and Miguel Angel Garcia Jacquez
package omok.model;

import java.util.Random;

/**
 * A player in an Omok game. It holds the name of the player and
 * can be used to identify a specific player throughout the game.
 * The Player class helps to keep track of the moves made by each
 * player during the game.
 */
public class Player {
    /** Name of this player. */
    private final String name;
    /** Create a new player with the given name. */
    public Player(String name) {
        this.name = name;
    }
    /** Return the name of this player. */
    public String name() {
        return name;
    }
    private int x;
    private int y;
    public void setX(int x){
        this.x=x;
    }
    public void setY(int y){
        this.y=y;
    }
    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
    public boolean isHuman(){
        return isHuman;
    }
    public void setComputerOpponent(){
        this.isHuman=false;
    }
    public void automateMove(Board omok){
        if(!isHuman){
            omok.model.Board.Place[][]intersections=omok.getIntersections();;
            Random random = new Random();
            int randX=1;
            System.out.println(randX+" XY "+y);
            while(!omok.isEmpty(randX,y+1)){
                randX = random.nextInt(15);
                System.out.println(randX+" XY "+y);
            }

            omok.placeStone(randX,y+1,this);
            omok.switchTurns();
        }


    }






}
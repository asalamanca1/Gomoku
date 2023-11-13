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

    private boolean isHuman;
    /** Create a new player with the given name.
     * @param name The name of the player.
     */
    public Player(String name, boolean isHuman) {
        this.name = name;
        this.isHuman = isHuman;
    }
    /** Return the name of this player.
     * @return The name of this player.
     */
    public String name() {
        return name;
    }
    /**
     * The X-coordinate of this place.
     */
    private int x;
    /**
     * The Y-coordinate of this place.
     */
    private int y;
    /**
     * Set the X-coordinate of this place.
     *
     * @param x The new X-coordinate to set.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Set the Y-coordinate of this place.
     *
     * @param y The new Y-coordinate to set.
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Get the X-coordinate of this place.
     *
     * @return The X-coordinate of this place.
     */
    public int getX() {
        return this.x;
    }

    /**
     * Get the Y-coordinate of this place.
     *
     * @return The Y-coordinate of this place.
     */
    public int getY() {
        return this.y;
    }
    public boolean isHuman(){
        return isHuman;
    }
    public void setIsHuman(boolean isHuman){
        this.isHuman=isHuman;
    }
    public void automateMove(Board omok){
        int count=1;
        if(!isHuman){
            omok.model.Board.Place[][]intersections=omok.getIntersections();;
            Random random = new Random();
            int randX=1;
            System.out.println(randX+" XY "+y);
            while(!omok.isEmpty(randX,y+1)&&omok.isOccupied(randX,y+1)){
                count++;
                if(count==13){
                    y+=1;
                    count=1;
                }
                randX = random.nextInt(1,15);

                System.out.println(randX+" XY "+y);
            }

            omok.placeStone(randX,y+1,this);

        }


    }







}
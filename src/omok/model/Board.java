//Andre Salamanca and Miguel Angel Garcia Jacquez
package omok.model;
import omok.model.Player;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
/**
 * Abstraction of an Omok board, which consists of n x n intersections
 * or places where players can place their stones. The board can be
 * accessed using a pair of 0-based indices (x, y), where x and y
 * denote the column and row number, respectively. The bottom-left
 * intersection is represented by the indices (1, 1), and the
 * top-right intersection is represented by the indices (n, n).
 */
public class Board {
    // 2D array to represent the intersections on the board
    Place[][] intersections;

    // The size of the board
    private int size;

    // List to store the winning row of places
    private List<Place> winningRow;
    private Player p1;
    private Player p2;
    private Player currentPlayer;

    /** Create a new board of the default size. */
    public Board() {
        // The size of the board. By default, it's set to 15.
        this.size=15;

        // A 2D array representing the intersections on the board.
        intersections = new Place[size][size];

        // A list of winning places that form a winning row.
        winningRow = new ArrayList<Place>();
        p1=new Player("Mike", true);
        p2=new Player("Andre", true);
        currentPlayer=p1;
    }
    /** Create a new board of the specified size.
     * @param size The size of the board, indicating the number of intersections in both rows and columns.
     */
    public Board(int size) {
        // Initialize the size attribute with the provided size.
        this.size=size;

        // Initialize the intersections as a 2D array with the specified size.
        intersections = new Place[size][size];

        // Initialize the winningRow as an empty ArrayList.
        winningRow = new ArrayList<Place>();
        p1=new Player("Mike", true);
        p2=new Player("Andre", true);
        currentPlayer=p1;
    }
    /**
     * Get the size of this board, which represents the number of intersections in both rows and columns.
     *
     * @return The size of the board.
     */
    public int size() {
        return this.size;
    }

    /** Removes all the stones placed on the board, effectively
     * resetting the board to its original state.
     */
    public void clear() {
        // Loop through all intersections on the board
        for(int i=0;i< size;i++){
            for(int j=0;j< size;j++){
                // Set each intersection to null (empty)
                intersections[i][j]=null;

            }
        }
        // Clear the list of the winning row.
        winningRow.clear();
    }
    /** Return a boolean value indicating whether all the places
     * on the board are occupied or not.
     *  @return true if all places on the board are occupied, false otherwise.
     */
    public boolean isFull() {
        // Loop through all intersections on the board
        for(int i=0;i< size;i++){
            for(int j=0;j< size;j++){
                // If any intersection is empty, return false
                if(intersections[i][j]==null){
                    return false;
                }
            }
        }
        // If no empty intersections were found, the board is full.
        return true;
    }

    /**
     * Place a stone for the specified player at a specified
     * intersection (x, y) on the board.
     *
     * @param x 0-based column (vertical) index
     * @param y 0-based row (horizontal) index
     * @param player Player whose stone is to be placed
     */
    public void placeStone(int x, int y, Player player) {
        // Create a new Place object for the stone at the specified coordinates
        Place stone = new Place(x,y);
        // Set the intersection at (x, y) to the new stone
        intersections[y-1][x-1]=stone;
        // Set the player for the new stone
        intersections[y-1][x-1].setPlayer(player);
        // Update the player's coordinates to the new position
        player.setX(x-1);
        player.setY(y-1);
        System.out.println(intersections[y-1][x-1].getPlayer());
    }

    /**
     * Return a boolean value indicating whether the specified
     * intersection (x, y) on the board is empty or not.
     *
     * @param x 0-based column (vertical) index
     * @param y 0-based row (horizontal) index
     * @return true if the specified intersection is empty, false if it is occupied.
     */
    public boolean isEmpty(int x, int y) {
        // Check if the intersection at (x, y) is null (empty)
        if(intersections[y-1][x-1]==null){
            return true;
        }
        // The intersection is occupied
        return false;
    }

    /**
     * Is the specified place on the board occupied?
     *
     * @param x 0-based column (vertical) index
     * @param y 0-based row (horizontal) index
     * @return true if the specified intersection is occupied, false if it is empty.
     */
    public boolean isOccupied(int x, int y) {
        // Check if the intersection at (x, y) is null (empty)
        if(intersections[y-1][x-1]==null){
            return false;
        }
        // Check if the intersection is occupied by a player's stone
        if(intersections[y-1][x-1].getPlayer()!=null){
            return true;
        }
        return false;
    }
    /**
     * Return a boolean value indicating whether the specified
     * intersection (x, y) on the board is occupied by the given
     * player or not.
     *
     * @param x 0-based column (vertical) index
     * @param y 0-based row (horizontal) index
     * @param player The player to check for stone ownership
     * @return true if the specified intersection is empty, false if it is occupied.
     */
    public boolean isOccupiedBy(int x, int y, Player player) {
        // Check if the intersection x and y is empty
        if(intersections[y-1][x-1]==null){
            return false;
        }
        // Check if the intersection is occupied by the specified player's stone
        if(intersections[y-1][x-1].getPlayer()==player){
            return true;
        }
        return false;
    }
    /**
     * Return the player who occupies the specified intersection (x, y)
     * on the board. If the place is empty, this method returns null.
     *
     * @param x 0-based column (vertical) index
     * @param y 0-based row (horizontal) index
     * @return The Player who occupies the specified intersection, or null if the intersection is empty.
     */
    public Player playerAt(int x, int y) {
        // Check if the intersection at (x, y) is empty

        if(intersections[y-1][x-1]==null){
            return null;
        }
        // Return the player who occupies that intersection
        return intersections[y-1][x-1].getPlayer();
    }
    /**
     * Return a boolean value indicating whether the given player
     * has a winning row on the board. A winning row is a consecutive
     * sequence of five or more stones placed by the same player in
     * a horizontal, vertical, or diagonal direction.
     * @param player The player to check for a winning row.
     * @return true if the specified player has a winning row, false otherwise.
     */
    public boolean isWonBy(Player player) {
        //variables to store the length of vertical row, horizontal row, and diagonal row
        int diagonalCount=1;
        int verticalCount=1;
        int horizontalCount=1;

        //variables used to traverse through intersections on board
        int xTemp=0;
        int yTemp=0;

        //variables to tell if the stone has any diagonal neighboring stones
        boolean hasUpLeft;
        boolean hasDownRight;
        //variables to tell if the stone has any diagonal neighboring stones
        boolean hasUpRight;
        boolean hasDownLeft;
        //variables to tell if the stone has any vertical neighboring stones
        boolean hasUp;
        boolean hasDown;
        ////variables to tell if the stone has any horizontal neighboring stones
        boolean hasLeft;
        boolean hasRight;


        String name= player.name();
        int x = player.getX();
        int y = player.getY();
        int length=5;
        Place stone=intersections[player.getY()][player.getX()];









        //FIX: WE ARE ONLY CHECKING IF WE ARE AT THE TOP LEFT EDGE, BUT WE SHOULD BE CHECKING IN GENERAL IF WE ARE AT THE LEFT EDGE INSTEAD SINCE WERE MOVING TOP LEFT DIAGONALLY
        //CHECK FOR DIAGONAL ROW #1
        xTemp=player.getX();
        yTemp=player.getY();
        //stone has diagonal row up/left and is not at the top left of board, start counting
        if((y!=(size-1)&&x!=0) && intersections[y+1][x-1]!=null&&  stone!=null &&intersections[y+1][x-1].getPlayer()==stone.getPlayer()){
            hasUpLeft=true;
            while(hasUpLeft){
                //if the stone to the up/left does not belong to player or if we are at the top left edge of board, stop counting
                //if((yTemp==(size-1)&&xTemp==0) || intersections[yTemp+1][xTemp-1].getPlayer()!=stone.getPlayer()){
                if((yTemp==(size-1)||xTemp==0) || intersections[yTemp+1][xTemp-1]==null|| intersections[yTemp+1][xTemp-1].getPlayer()!=stone.getPlayer()){
                    hasUpLeft=false;
                    winningRow.add(intersections[yTemp][xTemp]);//
                    break;
                }
                winningRow.add(intersections[yTemp][xTemp]);
                yTemp+=1;
                xTemp-=1;
                diagonalCount+=1;
            }
        }
        //has diagonal row down/right
        xTemp=player.getX();
        yTemp=player.getY();
        //if we are not at bottom right edge of board and the bottom/right neigboring stone belongs to player, start counting
        //if((y!=0&&x!=(size-1)) && intersections[y-1][x+1].getPlayer()==stone.getPlayer()){
        if((y!=0&&x!=(size-1)) && intersections[y-1][x+1]!=null && stone!=null &&intersections[y-1][x+1].getPlayer()==stone.getPlayer()){
            hasDownRight=true;
            while(hasDownRight){
                //if the stone to the down/right does not belong to player or if we are at the bottom right edge of board, stop counting
                if((yTemp==0||xTemp==(size-1)) ||intersections[yTemp-1][xTemp+1]==null|| intersections[yTemp-1][xTemp+1].getPlayer()!=stone.getPlayer()){
                    //if((yTemp==0&&xTemp==(size-1)) || intersections[yTemp-1][xTemp+1].getPlayer()!=stone.getPlayer()){
                    hasDownRight=false;
                    winningRow.add(intersections[yTemp][xTemp]);//
                    break;
                }
                winningRow.add(intersections[yTemp][xTemp]);
                yTemp-=1;
                xTemp+=1;
                diagonalCount+=1;
            }
        }
        //if we found 5 stones placed diagonally
        if(diagonalCount>=length){
            System.out.println("diagonal row found");
            return true;
        }


        //CHECK FOR DIAGONAL ROW #2
        winningRow.clear();
        xTemp=player.getX();
        yTemp=player.getY();
        diagonalCount=1;
        //if we are not at the top right edge of board and the neighboring top/right stone belongs to player, start counting
        //if((y!=(size-1)&&x!=(size-1)) && intersections[y+1][x+1]!=null && intersections[y+1][x+1].getPlayer()==stone.getPlayer()){
        if((y!=(size-1)&&x!=(size-1)) && intersections[y+1][x+1]!=null && stone!=null&&intersections[y+1][x+1].getPlayer()==stone.getPlayer()){
                //if((y!=(size-1)&&x!=(size-1)) && intersections[y+1][x+1].getPlayer()==stone.getPlayer()){
                hasUpRight=true;
                while(hasUpRight){
                    //if the stone to the top/right does not belong to player or if we are at the top right edge of board, stop counting
                    if((yTemp==(size-1)||xTemp==(size-1))||intersections[yTemp+1][xTemp+1]==null||intersections[yTemp+1][xTemp+1].getPlayer()!=stone.getPlayer()){
                        //if((yTemp==(size-1)&&xTemp==(size-1))||intersections[yTemp+1][xTemp+1].getPlayer()!=stone.getPlayer()){
                        hasUpRight=false;
                        winningRow.add(intersections[yTemp][xTemp]);//
                        break;
                    }
                    winningRow.add(intersections[yTemp][xTemp]);
                    yTemp+=1;
                    xTemp+=1;
                    diagonalCount+=1;
                }

        }
        //has diagonal row down/left
        xTemp=player.getX();
        yTemp=player.getY();
        //if we are not at the bottom left edge of board and bottom/left neighboring stone belongs to player, start counting
        if((y!=0&&x!=0) && intersections[y-1][x-1]!=null && stone!=null &&intersections[y-1][x-1].getPlayer()==stone.getPlayer()){
            //if((y!=0&&x!=0) && intersections[y-1][x-1].getPlayer()==stone.getPlayer()){
            hasDownLeft=true;
            while(hasDownLeft){
                //if the stone to the left does not belong to player or if we are at the right edge of board, stop counting
                //if((yTemp==0&&xTemp==0)||intersections[yTemp-1][xTemp-1].getPlayer()!=stone.getPlayer()){
                if((yTemp==0||xTemp==0)||intersections[yTemp-1][xTemp-1]==null||intersections[yTemp-1][xTemp-1].getPlayer()!=stone.getPlayer()){
                    hasDownLeft=false;
                    winningRow.add(intersections[yTemp][xTemp]);//
                    break;
                }
                winningRow.add(intersections[yTemp][xTemp]);
                yTemp-=1;
                xTemp-=1;
                diagonalCount+=1;
            }
        }
        //if we found 5 stones placed diagonally
        if(diagonalCount>=length){
            System.out.println("diagonal row found");
            return true;
        }


        //CHECK FOR VERTICAL ROW
        winningRow.clear();
        xTemp=player.getX();
        yTemp=player.getY();
        //if we are not at the top edge of board and the neighboring top stone belongs to player, start counting
        //if(y!=(size-1) && intersections[y+1][x].getPlayer()==stone.getPlayer()){
        //if(y!=(size-1) && intersections[y+1][x]!=null&&intersections[y+1][x].getPlayer()==player){
        if(y!=(size-1) && intersections[y+1][x]!=null && stone!=null && intersections[y+1][x].getPlayer()==stone.getPlayer()){
            hasUp=true;
            while(hasUp){
                //if we are at the top of the board or if the top neighboring stone doesnt belong to player, stop counting
                //if(yTemp==(size-1)||intersections[yTemp+1][xTemp].getPlayer()!=stone.getPlayer()){
                if(yTemp==(size-1)||intersections[yTemp+1][xTemp]==null||intersections[yTemp+1][xTemp].getPlayer()!=stone.getPlayer()){
                    hasUp=false;
                    winningRow.add(intersections[yTemp][xTemp]);//
                    break;

                }
                winningRow.add(intersections[yTemp][xTemp]);
                yTemp+=1;
                verticalCount+=1;
            }
        }
        //has down
        xTemp=player.getX();
        yTemp=player.getY();
        //if we are not at the bottom edge of board and the neighboring bottome stone belongs to player, start counting
        //if(y!=0 && intersections[y-1][x].getPlayer()==stone.getPlayer()){
        if(y!=0 && intersections[y-1][x]!=null&& stone!=null &&intersections[y-1][x].getPlayer()==stone.getPlayer()){
            hasDown=true;
            while(hasDown){
                //if the stone to the left does not belong to player or if we are at the right edge of board, stop counting
                //if(yTemp==0 || intersections[yTemp-1][xTemp].getPlayer()!=stone.getPlayer()){
                if(yTemp==0 || intersections[yTemp-1][xTemp]==null||intersections[yTemp-1][xTemp].getPlayer()!=player){
                    hasDown=false;
                    winningRow.add(intersections[yTemp][xTemp]);//
                    break;
                }
                winningRow.add(intersections[yTemp][xTemp]);
                yTemp-=1;
                verticalCount+=1;
            }
        }
        //if we found 5 stones placed vertically
        if(verticalCount>=length){
            System.out.println("vertical row found");
            return true;
        }


        //CHECK FOR HORIZONTAL ROW
        winningRow.clear();
        xTemp=player.getX();
        yTemp=player.getY();
        //if the intersection to left of the stone belongs to player and stone is not on the left edge of board, start counting
        //if(x!=0 && intersections[y][x-1].getPlayer()==stone.getPlayer()){//
        if(x!=0 && intersections[y][x-1]!=null&& stone!=null &&intersections[y][x-1].getPlayer()==stone.getPlayer()){
            hasLeft=true;
            while(hasLeft){
                //if the stone to the left does not belong to player or if we are at the left edge of board, stop counting
                //if(xTemp==0 || intersections[yTemp][xTemp-1].getPlayer()!=stone.getPlayer()){
                if(xTemp==0 || intersections[yTemp][xTemp-1]==null||intersections[yTemp][xTemp-1].getPlayer()!=stone.getPlayer()){
                    hasLeft=false;
                    winningRow.add(intersections[yTemp][xTemp]);//
                    break;
                }
                winningRow.add(intersections[yTemp][xTemp]);
                xTemp-=1;
                horizontalCount+=1;
            }
        }
        //if the intersection to the right of the stone belongs to player and stone is not on the right edge of board, start counting
        xTemp=player.getX();
        yTemp=player.getY();
        //if(x!=(size-1) && intersections[y][x+1].getPlayer()==stone.getPlayer()){//el orden
        if(x!=(size-1) && intersections[y][x+1]!=null&& stone!=null &&intersections[y][x+1].getPlayer()==stone.getPlayer()){//el orden
            hasRight=true;
            while(hasRight){
                //if the stone to the left does not belong to player or if we are at the right edge of board, stop counting
                //if(xTemp==(size-1)||intersections[yTemp][xTemp+1].getPlayer()!=stone.getPlayer()){
                if(xTemp==(size-1)||intersections[yTemp][xTemp+1]==null||intersections[yTemp][xTemp+1].getPlayer()!=stone.getPlayer()){
                    hasRight=false;
                    winningRow.add(intersections[yTemp][xTemp]);//
                    break;
                }
                winningRow.add(intersections[yTemp][xTemp]);
                xTemp+=1;
                horizontalCount+=1;
            }
        }
        //if we found 5 stones placed horizontally
        if(horizontalCount>=length){
            System.out.println("horizontal row found");
            return true;
        }

        //if we didnt find a row of 5
        winningRow.clear();
        return false;


    }
    /** Return the winning row. For those who are not familiar with
     * the Iterable interface, you may return an object of
     *
     * @return A list of places representing the winning row, or an empty list if no winning row exists.
     */
    public List<Place> winningRow() {
        // Return the list of places representing the winning row
        return winningRow;
    }
    /**
     * This method returns a 2D array of Place objects, where each element of the array
     * represents an intersection on the board.
     *
     * @return The 2D array of Place objects representing the intersections on the board.
     */
    public Place[][] getIntersections(){
        // Return the 2D array of Place objects representing the intersections on the board
        return intersections;
    }
    public Player getCurrentPlayer(){
        return currentPlayer;
    }
    public void setCurrentPlayer(Player player){
        currentPlayer=player;
    }
    public void switchTurns(){
        if(p1==currentPlayer){
            currentPlayer=p2;
        }
        else {
            currentPlayer = p1;
        }
    }
    public Player[] getPlayers(){
        Player[] players = new Player[]{p1, p2, currentPlayer};
        return players;
    }
    /**
     * An intersection on an Omok board identified by its 0-based column
     * index (x) and row index (y). The indices determine the position
     * of a place on the board, with (0, 0) denoting the top-left
     * corner and (n-1, n-1) denoting the bottom-right corner,
     * where n is the size of the board.
     */
    public static class Place {
        /** 0-based column index of this place. */
        public final int x;
        /** 0-based row index of this place. */
        public final int y;
        /** The player who placed a stone on this place. */
        public Player player;
        /** The status of the place (e.g., whether it contains a stone). */
        public String stone;
        /** Create a new place of the given indices.
         *
         * @param x 0-based column (vertical) index
         * @param y 0-based row (horizontal) index
         */
        public Place(int x, int y) {
            // Set the x and y indices to represent the location of this place
            this.x = x;
            this.y = y;
            // Initialize the player and stone properties to null and "-"
            this.player=null;
            this.stone="-";
        }
        /**
         * Set the player who occupies this place and mark it with a stone.
         *
         * @param player The player to be set as the occupant of this place.
         */
        public void setPlayer(Player player){
            // Set the player occupying this place
            this.player=player;
            // Mark this place with a stone to indicate the player's presence
            this.stone="*";
        }
        /**
         * Get the player who occupies this place.
         *
         * @return The player who occupies this place, or null if the place is empty.
         */
        public Player getPlayer(){

            // Return the player who occupies this place, or null if it's empty
            return this.player;
        }
        public int getX(){
            return this.x;
        }
        public int getY(){
            return this.y;
        }
        /*
        public Player currentPlayer(){
            if(currentPlayer==p1){
                currentPlayer=p2;
            }
            else{
                currentPlayer=p1;
            }
            return currentPlayer();
        }

         */
// other methods if needed ...
    }
}


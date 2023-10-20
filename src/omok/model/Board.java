package omok.model;
import omok.model.Player;
/**
 * Abstraction of an Omok board, which consists of n x n intersections
 * or places where players can place their stones. The board can be
 * accessed using a pair of 0-based indices (x, y), where x and y
 * denote the column and row number, respectively. The top-left
 * intersection is represented by the indices (0, 0), and the
 * bottom-right intersection is represented by the indices (n-1, n-1).
 */
public class Board {
    Place[][] intersections;
    int size;

    /** Create a new board of the default size. */
    public Board() {
        this.size=15;
        intersections = new Place[size][size];
    }
    /** Create a new board of the specified size. */
    public Board(int size) {
        this.size=size;
        intersections = new Place[size][size];
    }
    /** Return the size of this board. */
    public int size() {
        return this.size;
    }

    /** Removes all the stones placed on the board, effectively
     * resetting the board to its original state.
     */
    public void clear() {
        intersections = new Place[size][size];
    }
    /** Return a boolean value indicating whether all the places
     * on the board are occupied or not.
     */
    public boolean isFull() {
        for(int i=0;i< intersections.length;i++){
            for(int j=0;i< intersections[i].length;j++){
                if(intersections[i][j].getPlayer()==null){
                    return false;
                }
            }
        }
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
        intersections[y-1][x-1].setPlayer(player);
    }

    /**
     * Return a boolean value indicating whether the specified
     * intersection (x, y) on the board is empty or not.
     *
     * @param x 0-based column (vertical) index
     * @param y 0-based row (horizontal) index
     */
    public boolean isEmpty(int x, int y) {
        if(intersections[y-1][x-1].getPlayer()==null){
            return true;
        }
        return false;
    }

    /**
     * Is the specified place on the board occupied?
     *
     * @param x 0-based column (vertical) index
     * @param y 0-based row (horizontal) index
     */
    public boolean isOccupied(int x, int y) {
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
     */
    public boolean isOccupiedBy(int x, int y, Player player) {
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
     */
    public Player playerAt(int x, int y) {
        return intersections[y-1][x-1].getPlayer();
    }
    /**
     * Return a boolean value indicating whether the given player
     * has a winning row on the board. A winning row is a consecutive
     * sequence of five or more stones placed by the same player in
     * a horizontal, vertical, or diagonal direction.
     */
    public boolean isWonBy(Player player) {
    }
    /** Return the winning row. For those who are not familiar with
     * the Iterable interface, you may return an object of
     * List<Place>. */
    public Iterable<Place> winningRow() {
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
        public Player player;
        public String stone;
        /** Create a new place of the given indices.
         *
         * @param x 0-based column (vertical) index
         * @param y 0-based row (horizontal) index
         */
        public Place(int x, int y) {
            this.x = x;
            this.y = y;
            this.player=null;
            this.stone="-";
        }
        public void setPlayer(Player player){
            this.player=player;
            this.stone="*";
        }
        public Player getPlayer(){
            return this.player;
        }
// other methods if needed ...
    }
}



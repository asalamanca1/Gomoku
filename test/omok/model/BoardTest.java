//Andre Salamanca and Miguel Angel Garcia Jacquez
package omok.model;
import java.awt.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.ArrayList;


/**
 * The BoardTest class is used for testing the Board class, which represents a game board for the game Omok.
 */
class BoardTest {
    /**
     * Test for the size() method of the Board class.
     */
    @org.junit.jupiter.api.Test
    void size() {
        // Create a new Omok board default size (15)
        Board omok = new Board();
        // Check if the size() method returns 15 for the default board size
        assertEquals(15,omok.size());
        // Create a new Omok board with a custom size (20)
        omok = new Board(20);
        // Check if the size() method returns 20 for the custom-sized board
        assertEquals(20,omok.size());

    }
    /**
     * Test for the clear() method of the Board class.
     */
    @org.junit.jupiter.api.Test
    void clear() {
        // Create a new instance of board
        Board omok = new Board();
        // Create a player
        Player andre = new Player("Andre",true);
        // Get the 2D array of board intersections
        Board.Place[][]intersections=omok.getIntersections();
        // Place stones on the board
        omok.placeStone(3,3,andre);
        omok.placeStone(4,5,andre);
        // Clear the board
        omok.clear();
        // Check if all intersections are empty after clearing
        for(int i=1;i<=omok.size();i++){
            for(int j=1;j<=omok.size();j++){
                assertTrue(omok.isEmpty(i,j));
            }
        }


    }
    /**
     * Test for the isFull() method of the Board class.
     */
    @org.junit.jupiter.api.Test
    void isFull() {
        // Create new instance of board
        Board omok = new Board();
        // Create a player
        Player andre = new Player("Andre",true);
        // Get the 2D array of board intersections
        Board.Place[][]intersections=omok.getIntersections();
        // Place stones on all intersections
        for(int i=1; i<=omok.size();i++){
            for(int j=1;j<=omok.size();j++){
                omok.placeStone(i,j,andre);
            }
        }
        // Check if the board is full
        assertTrue(omok.isFull());

        // Clear the board for the next test
        omok.clear();
        // Place stones on all intersections of the second column
        for(int i=2; i<=omok.size();i++){
            for(int j=1;j<=omok.size();j++){
                omok.placeStone(i,j,andre);
            }
        }
        // Check if the board is not full
        assertFalse(omok.isFull());

    }
    /**
     * Test for the placeStone() method of the Board class.
     */
    @org.junit.jupiter.api.Test
    void placeStone() {
        // Create a new Omok board
        Board omok = new Board();
        // Create a player
        Player andre = new Player("Andre",true);
        // Get the 2D array of board intersections
        Board.Place[][]intersections=omok.getIntersections();
        // Place a stone at intersection (3, 3) and check if it's occupied by Andre
        omok.placeStone(3,3,andre);
        assertTrue(omok.isOccupiedBy(3,3,andre));
        // Place a stone at intersection (4, 8) and check if it's occupied by Andre
        omok.placeStone(4,8,andre);
        assertTrue(omok.isOccupiedBy(4,8,andre));
        // Attempt to place a stone at an empty intersection (9, 9) and check that it's not occupied by Andre
        assertFalse(omok.isOccupiedBy(9,9,andre));

    }
    /**
     * Test for the isEmpty() method of the Board class.
     */
    @org.junit.jupiter.api.Test
    void isEmpty() {
        // Create new instance of board
        Board omok = new Board();
        // Create a player
        Player andre = new Player("Andre",true);
        // Get the 2D array of board intersections
        Board.Place[][]intersections=omok.getIntersections();
        // Place a stone at intersection (3, 3)
        omok.placeStone(3,3,andre);
        // Check that intersection (3, 3) is not empty
        assertFalse(omok.isEmpty(3,3));
        // Check that an empty intersection (5, 5) is indeed empty
        assertTrue(omok.isEmpty(5,5));
    }
    /**
     * Test for the isOccupied() method of the Board class.
     */
    @org.junit.jupiter.api.Test
    void isOccupied() {
        // Create new instance of board
        Board omok = new Board();
        // Create a player
        Player andre = new Player("Andre",true);
        // Get the 2D array of board intersections
        Board.Place[][]intersections=omok.getIntersections();
        // Place a stone at intersection (3, 3)
        omok.placeStone(3,3,andre);
        // Check that an empty intersection (5, 5) is not occupied
        assertFalse(omok.isOccupied(5,5));
        // Check that intersection (3, 3) is occupied
        assertTrue(omok.isOccupied(3,3));

    }
    /**
     * Test for the isOccupiedBy() method of the Board class.
     */
    @org.junit.jupiter.api.Test
    void isOccupiedBy() {
        // Create a new instance of board
        Board omok = new Board();
        // Create players
        Player andre = new Player("Andre",true);
        Player mike = new Player("Mike",true);
        // Get the 2D array of board intersections
        Board.Place[][]intersections=omok.getIntersections();
        // Place a stone at intersection (3, 3) for player Andre
        omok.placeStone(3,3,andre);
        // Check that an empty intersection (5, 5) is not occupied by Andre
        assertFalse(omok.isOccupiedBy(5,5,andre));
        // Check that intersection (3, 3) is occupied by Andre
        assertTrue(omok.isOccupiedBy(3,3,andre));
        // Check that intersection (3, 3) is not occupied by Mike
        assertFalse(omok.isOccupiedBy(3,3,mike));
    }
    /**
     * Test for the playerAt() method of the Board class.
     */
    @org.junit.jupiter.api.Test
    void playerAt() {
        // Create a new instance of board
        Board omok = new Board();
        // Create players
        Player andre = new Player("Andre",true);
        Player mike = new Player("Mike",true);
        // Get the 2D array of board intersections
        Board.Place[][]intersections=omok.getIntersections();
        // Place stones at intersections for Andre and Mike
        omok.placeStone(3,3,andre);
        omok.placeStone(5,5,mike);
        // Check that intersection (3, 3) contains player Andre
        assertEquals(omok.playerAt(3,3),andre);
        // Check that intersection (5, 5) contains player Mike
        assertEquals(omok.playerAt(5,5),mike);
        // Check that an empty intersection (6, 6) contains no player
        assertNull(omok.playerAt(6,6));
    }
    /**
     * Test for the isWonBy() method of the Board class.
     */
    @org.junit.jupiter.api.Test
    void isWonBy() {
        // Create a new instance of board
        Board omok = new Board();
        // Create players
        Player andre = new Player("Andre",true);
        Player mike = new Player("Mike",true);
        // Get the 2D array of board intersections
        Board.Place[][]intersections=omok.getIntersections();

        // Simulate a vertical row of 5 from (7, 4) to (7, 8)
        omok.placeStone(7, 4, andre);
        omok.placeStone(7, 5, andre);
        omok.placeStone(7, 6, andre);
        omok.placeStone(7, 7, andre);
        //make sure the method returns false if there is no row of 5 found
        assertFalse(omok.isWonBy(andre));
        omok.placeStone(7, 8, andre);
        assertTrue(omok.isWonBy(andre));

        omok.clear();

        // Simulate a vertical row of 5 touching the bottom edge (7, 1 to 7, 5)
        omok.placeStone(7, 1, andre);
        omok.placeStone(7, 2, andre);
        omok.placeStone(7, 3, andre);
        //make sure the method returns false if there is no row of 5 found
        assertFalse(omok.isWonBy(andre));
        omok.placeStone(7, 4, andre);
        omok.placeStone(7, 5, andre);
        assertTrue(omok.isWonBy(andre));

        omok.clear();

        // Simulate a horizontal row of 5 from (3, 6) to (7, 6)
        omok.placeStone(3, 6, andre);
        omok.placeStone(4, 6, andre);
        //make sure the method returns false if there is no row of 5 found
        assertFalse(omok.isWonBy(andre));
        omok.placeStone(5, 6, andre);
        omok.placeStone(6, 6, andre);
        omok.placeStone(7, 6, andre);
        assertTrue(omok.isWonBy(andre));

        omok.clear();

        // Simulate a horizontal row of 5 touching the left edge (1, 6 to 5, 6)
        omok.placeStone(1, 6, andre);
        //make sure the method returns false if there is no row of 5 found
        assertFalse(omok.isWonBy(andre));
        omok.placeStone(2, 6, andre);
        omok.placeStone(3, 6, andre);
        omok.placeStone(4, 6, andre);
        omok.placeStone(5, 6, andre);
        assertTrue(omok.isWonBy(andre));

        omok.clear();

        // Simulate a horizontal row of 5 touching the top right edge (11, 15 to 15,15)
        omok.placeStone(15, 15, andre);
        //make sure the method returns false if there is no row of 5 found
        assertFalse(omok.isWonBy(andre));
        omok.placeStone(14, 15, andre);
        omok.placeStone(13, 15, andre);
        omok.placeStone(12, 15, andre);
        omok.placeStone(11, 15, andre);
        assertTrue(omok.isWonBy(andre));

        omok.clear();

        // Simulate a diagonal row of 5 from (4, 4) to (8, 8) (southwest to northeast)
        //make sure the method returns false if there is no row of 5 found
        assertFalse(omok.isWonBy(andre));
        omok.placeStone(4, 4, andre);
        omok.placeStone(5, 5, andre);
        omok.placeStone(6, 6, andre);
        omok.placeStone(7, 7, andre);
        omok.placeStone(8, 8, andre);
        assertTrue(omok.isWonBy(andre));

        omok.clear();

        // Simulate a diagonal row of 5 touching the bottom right edge of board from (11,5) to (15,1) (northeast to southwest)
        assertFalse(omok.isWonBy(andre));
        omok.placeStone(15, 1, andre);
        omok.placeStone(14, 2, andre);
        omok.placeStone(13, 3, andre);
        omok.placeStone(12, 4, andre);
        omok.placeStone(11, 5, andre);
        assertTrue(omok.isWonBy(andre));

        omok.clear();

        // Simulate a diagonal row of 5 touching the top right edge of board from (11,11) to (15,15) (southwest to northeast)
        assertFalse(omok.isWonBy(andre));
        omok.placeStone(15, 15, andre);
        omok.placeStone(14, 14, andre);
        omok.placeStone(13, 13, andre);
        omok.placeStone(12, 12, andre);
        omok.placeStone(11, 11, andre);
        assertTrue(omok.isWonBy(andre));

        omok.clear();

        // Simulate a diagonal row of 5 from (4, 8) to (8, 4) (northeast to southwest)
        omok.placeStone(4, 8, andre);
        omok.placeStone(5, 7, andre);
        omok.placeStone(6, 6, andre);
        omok.placeStone(7, 5, andre);
        //make sure the method returns false if there is no row of 5 found
        assertFalse(omok.isWonBy(andre));
        omok.placeStone(8, 4, andre);
        assertTrue(omok.isWonBy(andre));

        omok.clear();

        // Simulate a diagonal row of 5 touching the bottom left edge (1, 1 to 5, 5) (southwest to northeast)
        omok.placeStone(1, 1, andre);
        omok.placeStone(2, 2, andre);
        omok.placeStone(3, 3, andre);
        omok.placeStone(4, 4, andre);
        //make sure the method returns false if there is no row of 5 found
        assertFalse(omok.isWonBy(andre));
        omok.placeStone(5, 5, andre);
        assertTrue(omok.isWonBy(andre));

        omok.clear();

        // Simulate a diagonal row of 5 touching the top left edge (1, 14 to 5, 10) (southwest to northeast)
        omok.placeStone(1, 14, andre);
        omok.placeStone(2, 13, andre);
        omok.placeStone(3, 12, andre);
        //make sure the method returns false if there is no row of 5 found
        assertFalse(omok.isWonBy(andre));
        omok.placeStone(4, 11, andre);
        omok.placeStone(5, 10, andre);
        assertTrue(omok.isWonBy(andre));


    }
    /**
     * Test for the winningRow() method of the Board class.
     */
    @org.junit.jupiter.api.Test
    void winningRow() {
        //create new instance of board
        Board omok = new Board();
        //create new instances of players
        Player andre = new Player("Andre",true);
        Player mike = new Player("Mike",true);
        List<Board.Place> emptyList=new ArrayList<>();



        // Simulate a vertical row of 5 from (7, 4) to (7, 8)
        omok.placeStone(7, 4, andre);
        //check that a winning row is null if a winning row was not found
        omok.isWonBy(andre);
        assertEquals(emptyList,omok.winningRow());
        omok.placeStone(7, 5, andre);
        omok.placeStone(7, 6, andre);
        omok.placeStone(7, 7, andre);
        omok.placeStone(7, 8, andre);
        //check if player has won game
        omok.isWonBy(andre);
        //generate winning row from stones placed on board
        List<Board.Place> row = omok.winningRow();
        //make an array of boards intersections
        Board.Place[][] intersections = omok.getIntersections();
        //create a new list and add the correct answers of intersections from the winning row
        java.util.List<Board.Place> expectedRow=new ArrayList<Board.Place>();
        expectedRow.add(intersections[3][6]);
        expectedRow.add(intersections[4][6]);
        expectedRow.add(intersections[5][6]);
        expectedRow.add(intersections[6][6]);
        expectedRow.add(intersections[7][6]);
        //check that both lists match
        assertTrue(row.containsAll(expectedRow));
        //clear board
        omok.clear();



        // Simulate a horizontal row of 5 from (3, 6) to (7, 6)
        omok.placeStone(3, 6, andre);
        omok.placeStone(4, 6, andre);
        //check that a winning row is null if a winning row was not found
        omok.isWonBy(andre);
        assertEquals(emptyList,omok.winningRow());
        omok.placeStone(5, 6, andre);
        omok.placeStone(6, 6, andre);
        omok.placeStone(7, 6, andre);
        //check if player has won game
        omok.isWonBy(andre);
        //generate winning row from stones placed on board
        row = omok.winningRow();
        //clear previous list made from last test
        expectedRow.clear();
        //make an array of boards intersections
        intersections = omok.getIntersections();
        //create a new list and add the correct answers of intersections from the winning row
        expectedRow.add(intersections[5][2]);
        expectedRow.add(intersections[5][3]);
        expectedRow.add(intersections[5][4]);
        expectedRow.add(intersections[5][5]);
        expectedRow.add(intersections[5][6]);
        //check that both lists match
        assertTrue(row.containsAll(expectedRow));
        //clear board
        omok.clear();




        // Simulate a diagonal row of 5 from (3, 3) to (7, 7) (northwest to southeast)
        omok.placeStone(3, 3, andre);
        omok.placeStone(4, 4, andre);
        omok.placeStone(5, 5, andre);
        omok.placeStone(6, 6, andre);
        //check that a winning row is null if a winning row was not found
        omok.isWonBy(andre);
        assertEquals(emptyList,omok.winningRow());
        omok.placeStone(7, 7, andre);
        //check if player has won game
        omok.isWonBy(andre);
        //generate winning row from stones placed on board
        row = omok.winningRow();
        //clear previous list made from last test
        expectedRow.clear();
        //make an array of boards intersections
        intersections = omok.getIntersections();
        //create a new list and add the correct answers of intersections from the winning row
        expectedRow.add(intersections[2][2]);
        expectedRow.add(intersections[3][3]);
        expectedRow.add(intersections[4][4]);
        expectedRow.add(intersections[5][5]);
        expectedRow.add(intersections[6][6]);
        //check that both lists match
        assertTrue(row.containsAll(expectedRow));
        //clear board
        omok.clear();


        // Simulate a diagonal row of 5 touching the bottom right corner from (11,5) to (15, 1) (northeast to southwest)
        omok.placeStone(11, 5, andre);
        omok.placeStone(12, 4, andre);
        omok.placeStone(13, 3, andre);
        //check that a winning row is null if a winning row was not found
        omok.isWonBy(andre);
        assertEquals(emptyList,omok.winningRow());
        omok.placeStone(14, 2, andre);
        omok.placeStone(15, 1, andre);
        //check if player has won game
        omok.isWonBy(andre);
        //generate winning row from stones placed on board
        row = omok.winningRow();
        //clear previous list made from last test
        expectedRow.clear();
        //make an array of boards intersections
        intersections = omok.getIntersections();
        //create a new list and add the correct answers of intersections from the winning row
        expectedRow.add(intersections[4][10]);
        expectedRow.add(intersections[3][11]);
        expectedRow.add(intersections[2][12]);
        expectedRow.add(intersections[1][13]);
        expectedRow.add(intersections[0][14]);
        //check that both lists match
        assertTrue(row.containsAll(expectedRow));



        // Simulate a vertical row of 5 touching the top right corner from (15,15) to (15,11)
        omok.placeStone(15, 15, andre);
        omok.placeStone(15, 14, andre);
        omok.placeStone(15, 13, andre);
        //check that a winning row is null if a winning row was not found
        omok.isWonBy(andre);
        assertEquals(emptyList,omok.winningRow());
        omok.placeStone(15, 12, andre);
        omok.placeStone(15, 11, andre);
        //check if player has won game
        omok.isWonBy(andre);
        //generate winning row from stones placed on board
        row = omok.winningRow();
        //clear previous list made from last test
        expectedRow.clear();
        //make an array of boards intersections
        intersections = omok.getIntersections();
        //create a new list and add the correct answers of intersections from the winning row
        expectedRow.add(intersections[14][14]);
        expectedRow.add(intersections[13][14]);
        expectedRow.add(intersections[12][14]);
        expectedRow.add(intersections[11][14]);
        expectedRow.add(intersections[10][14]);
        //check that both lists match
        assertTrue(row.containsAll(expectedRow));




        // Simulate a horizontal row of 5 touching the top left corner from (1, 15) to (5, 15)
        omok.placeStone(1, 15, andre);
        omok.placeStone(2, 15, andre);
        //check that a winning row is null if a winning row was not found
        omok.isWonBy(andre);
        assertEquals(emptyList,omok.winningRow());
        omok.placeStone(3, 15, andre);
        omok.placeStone(4, 15, andre);
        omok.placeStone(5, 15, andre);
        //check if player has won game
        omok.isWonBy(andre);
        //generate winning row from stones placed on board
        row = omok.winningRow();
        //clear previous list made from last test
        expectedRow.clear();
        //make an array of boards intersections
        intersections = omok.getIntersections();
        //create a new list and add the correct answers of intersections from the winning row
        expectedRow.add(intersections[14][0]);
        expectedRow.add(intersections[14][1]);
        expectedRow.add(intersections[14][2]);
        expectedRow.add(intersections[14][3]);
        expectedRow.add(intersections[14][4]);
        //check that both lists match
        assertTrue(row.containsAll(expectedRow));
        //clear board
        omok.clear();



    }



}
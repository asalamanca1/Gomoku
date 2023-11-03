//Andre Salamanca and Miguel Angel Garcia Jacquez
package omok.model;
import java.awt.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.ArrayList;



class BoardTest {

    @org.junit.jupiter.api.Test
    void size() {
        Board omok = new Board();
        assertEquals(15,omok.size());
        omok = new Board(20);
        assertEquals(20,omok.size());

    }

    @org.junit.jupiter.api.Test
    void clear() {
        Board omok = new Board();
        Player andre = new Player("Andre");
        Board.Place[][]intersections=omok.getIntersections();
        omok.placeStone(3,3,andre);
        omok.placeStone(4,5,andre);
        omok.clear();
        for(int i=1;i<=omok.size();i++){
            for(int j=1;j<=omok.size();j++){
                assertTrue(omok.isEmpty(i,j));
            }
        }


    }

    @org.junit.jupiter.api.Test
    void isFull() {
        Board omok = new Board();
        Player andre = new Player("Andre");
        Board.Place[][]intersections=omok.getIntersections();
        for(int i=1; i<=omok.size();i++){
            for(int j=1;j<=omok.size();j++){
                omok.placeStone(i,j,andre);
            }
        }
        assertTrue(omok.isFull());

        omok.clear();
        for(int i=2; i<=omok.size();i++){
            for(int j=1;j<=omok.size();j++){
                omok.placeStone(i,j,andre);
            }
        }
        assertFalse(omok.isFull());

    }

    @org.junit.jupiter.api.Test
    void placeStone() {
        Board omok = new Board();
        Player andre = new Player("Andre");
        Board.Place[][]intersections=omok.getIntersections();
        omok.placeStone(3,3,andre);
        assertTrue(omok.isOccupiedBy(3,3,andre));
        omok.placeStone(4,8,andre);
        assertTrue(omok.isOccupiedBy(4,8,andre));
        assertFalse(omok.isOccupiedBy(9,9,andre));

    }

    @org.junit.jupiter.api.Test
    void isEmpty() {
        Board omok = new Board();
        Player andre = new Player("Andre");
        Board.Place[][]intersections=omok.getIntersections();
        omok.placeStone(3,3,andre);
        assertFalse(omok.isEmpty(3,3));
        assertTrue(omok.isEmpty(5,5));
    }

    @org.junit.jupiter.api.Test
    void isOccupied() {
        Board omok = new Board();
        Player andre = new Player("Andre");
        Board.Place[][]intersections=omok.getIntersections();
        omok.placeStone(3,3,andre);
        assertFalse(omok.isOccupied(5,5));
        assertTrue(omok.isOccupied(3,3));

    }

    @org.junit.jupiter.api.Test
    void isOccupiedBy() {
        Board omok = new Board();
        Player andre = new Player("Andre");
        Player mike = new Player("Mike");
        Board.Place[][]intersections=omok.getIntersections();
        omok.placeStone(3,3,andre);
        assertFalse(omok.isOccupiedBy(5,5,andre));
        assertTrue(omok.isOccupiedBy(3,3,andre));
        assertFalse(omok.isOccupiedBy(3,3,mike));
    }

    @org.junit.jupiter.api.Test
    void playerAt() {
        Board omok = new Board();
        Player andre = new Player("Andre");
        Player mike = new Player("Mike");
        Board.Place[][]intersections=omok.getIntersections();
        omok.placeStone(3,3,andre);
        omok.placeStone(5,5,mike);
        assertEquals(omok.playerAt(3,3),andre);
        assertEquals(omok.playerAt(5,5),mike);
        assertNull(omok.playerAt(6,6));
    }

    @org.junit.jupiter.api.Test
    void isWonBy() {
        Board omok = new Board();
        Player andre = new Player("Andre");
        Player mike = new Player("Mike");
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

    @org.junit.jupiter.api.Test
    void winningRow() {
        //create new instance of board
        Board omok = new Board();
        //create new instances of players
        Player andre = new Player("Andre");
        Player mike = new Player("Mike");
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
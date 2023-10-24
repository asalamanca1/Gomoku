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
        assertFalse(omok.isWonBy(andre));
        omok.placeStone(7, 8, andre);
        assertTrue(omok.isWonBy(andre));

        omok.clear();

        // Simulate a vertical row of 5 touching the top edge (7, 1 to 7, 5)
        omok.placeStone(7, 1, andre);
        omok.placeStone(7, 2, andre);
        omok.placeStone(7, 3, andre);
        assertFalse(omok.isWonBy(andre));
        omok.placeStone(7, 4, andre);
        omok.placeStone(7, 5, andre);
        assertTrue(omok.isWonBy(andre));

        omok.clear();

        // Simulate a horizontal row of 5 from (3, 6) to (7, 6)
        omok.placeStone(3, 6, andre);
        omok.placeStone(4, 6, andre);
        assertFalse(omok.isWonBy(andre));
        omok.placeStone(5, 6, andre);
        omok.placeStone(6, 6, andre);
        omok.placeStone(7, 6, andre);
        assertTrue(omok.isWonBy(andre));

        omok.clear();

        // Simulate a horizontal row of 5 touching the left edge (1, 6 to 5, 6)
        omok.placeStone(1, 6, andre);
        assertFalse(omok.isWonBy(andre));
        omok.placeStone(2, 6, andre);
        omok.placeStone(3, 6, andre);
        omok.placeStone(4, 6, andre);
        omok.placeStone(5, 6, andre);
        assertTrue(omok.isWonBy(andre));

        omok.clear();

        // Simulate a diagonal row of 5 from (4, 4) to (8, 8) (northwest to southeast)
        assertFalse(omok.isWonBy(andre));
        omok.placeStone(4, 4, andre);
        omok.placeStone(5, 5, andre);
        omok.placeStone(6, 6, andre);
        omok.placeStone(7, 7, andre);
        omok.placeStone(8, 8, andre);
        assertTrue(omok.isWonBy(andre));

        omok.clear();

        // Simulate a diagonal row of 5 from (4, 8) to (8, 4) (northeast to southwest)
        omok.placeStone(4, 8, andre);
        omok.placeStone(5, 7, andre);
        omok.placeStone(6, 6, andre);
        omok.placeStone(7, 5, andre);
        assertFalse(omok.isWonBy(andre));
        omok.placeStone(8, 4, andre);
        assertTrue(omok.isWonBy(andre));

        omok.clear();

        // Simulate a diagonal row of 5 touching the top left edge (1, 1 to 5, 5) (northwest to southeast)
        omok.placeStone(1, 1, andre);
        omok.placeStone(2, 2, andre);
        omok.placeStone(3, 3, andre);
        omok.placeStone(4, 4, andre);
        assertFalse(omok.isWonBy(andre));
        omok.placeStone(5, 5, andre);
        assertTrue(omok.isWonBy(andre));

        omok.clear();

        // Simulate a diagonal row of 5 touching the top right edge (1, 14 to 5, 10) (northeast to southwest)
        omok.placeStone(1, 14, andre);
        omok.placeStone(2, 13, andre);
        omok.placeStone(3, 12, andre);
        assertFalse(omok.isWonBy(andre));
        omok.placeStone(4, 11, andre);
        omok.placeStone(5, 10, andre);
        assertTrue(omok.isWonBy(andre));


    }

    @org.junit.jupiter.api.Test
    void winningRow() {
        Board omok = new Board();
        Player andre = new Player("Andre");
        Player mike = new Player("Mike");

        // Simulate a vertical row of 5 from (7, 4) to (7, 8)
        omok.placeStone(7, 4, andre);
        omok.placeStone(7, 5, andre);
        omok.placeStone(7, 6, andre);
        omok.placeStone(7, 7, andre);
        omok.placeStone(7, 8, andre);
        omok.isWonBy(andre);
        List<Board.Place> row = omok.winningRow();


        Board.Place[][] intersections = omok.getIntersections();
        java.util.List<Board.Place> expectedRow=new ArrayList<Board.Place>();;
        expectedRow.add(intersections[4][7]);
        expectedRow.add(intersections[5][7]);
        expectedRow.add(intersections[6][7]);
        expectedRow.add(intersections[7][7]);
        expectedRow.add(intersections[8][7]);

        assertTrue(row.containsAll(expectedRow));

        omok.clear();

        // Simulate a horizontal row of 5 from (3, 6) to (7, 6)
        omok.placeStone(3, 6, andre);
        omok.placeStone(4, 6, andre);
        omok.placeStone(5, 6, andre);
        omok.placeStone(6, 6, andre);
        omok.placeStone(7, 6, andre);

        omok.isWonBy(andre);
        row = omok.winningRow();


        expectedRow.clear();
        expectedRow.add(intersections[3][6]);
        expectedRow.add(intersections[4][6]);
        expectedRow.add(intersections[5][6]);
        expectedRow.add(intersections[6][6]);
        expectedRow.add(intersections[7][6]);

        assertTrue(row.containsAll(expectedRow));

        omok.clear();

        // Simulate a diagonal row of 5 from (3, 3) to (7, 7) (northwest to southeast)
        omok.placeStone(3, 3, andre);
        omok.placeStone(4, 4, andre);
        omok.placeStone(5, 5, andre);
        omok.placeStone(6, 6, andre);
        omok.placeStone(7, 7, andre);

        omok.isWonBy(andre);
        row = omok.winningRow();


        expectedRow.clear();
        expectedRow.add(intersections[3][3]);
        expectedRow.add(intersections[4][4]);
        expectedRow.add(intersections[5][5]);
        expectedRow.add(intersections[6][6]);
        expectedRow.add(intersections[7][7]);

        assertTrue(row.containsAll(expectedRow));
    }



}
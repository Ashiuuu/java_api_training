package fr.lernejo.navy_battle;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BoardTest {
    @Test
    public void takeFireTest() {
        Board testBoard = new Board();
        Assertions.assertEquals(testBoard.takeFireFromEnemy(new BoardPosition(1,1)), Board.FireResult.HIT);
        Assertions.assertEquals(testBoard.takeFireFromEnemy(new BoardPosition(1,2)), Board.FireResult.SUNK);
        Assertions.assertEquals(testBoard.takeFireFromEnemy(new BoardPosition(1,1)), Board.FireResult.MISS);
    }

    @Test
    public void shipLeftTest() {
        Board testBoard = new Board();
        Assertions.assertTrue(testBoard.shipLeft());
    }

    @Test
    public void getCellTest() {
        Board testBoard = new Board();
        Assertions.assertEquals(testBoard.getCellState(0, 0), Board.State.FREE);
    }
}

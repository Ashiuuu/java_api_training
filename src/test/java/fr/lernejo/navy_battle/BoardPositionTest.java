package fr.lernejo.navy_battle;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BoardPositionTest {
    @Test
    public void equalsTest() {
        BoardPosition test1 = new BoardPosition(0,0);
        BoardPosition test2 = new BoardPosition(0,0);
        BoardPosition test3 = new BoardPosition(1,1);
        Assertions.assertEquals(test1.equals(test2), true);
        Assertions.assertEquals(test1.equals(test3), false);
    }

    @Test
    public void getTest() {
        BoardPosition pos = new BoardPosition(0, 10);
        Assertions.assertEquals(pos.getX(), 0);
        Assertions.assertEquals(pos.getY(), 10);
    }

    @Test
    public void toStringTest() {
        BoardPosition pos = new BoardPosition(1, 2);
        Assertions.assertEquals(pos.toString(), "(1,2)");
    }
}

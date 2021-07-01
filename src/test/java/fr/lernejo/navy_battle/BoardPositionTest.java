package fr.lernejo.navy_battle;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BoardPositionTest {
    @Test
    public void equalsTest() {
        BoardPosition test1 = new BoardPosition(0,0);
        BoardPosition test2 = new BoardPosition(0,0);
        BoardPosition test3 = new BoardPosition(1,1);
        Assertions.assertEquals(test1, test2);
        Assertions.assertNotEquals(test1, test3);
    }

    @Test
    public void emptyEqualsTest() {
        BoardPosition test = new BoardPosition(0,0);
        Assertions.assertNotEquals(null, test);
    }

    @Test
    public void wrongEqualsTest() {
        BoardPosition test = new BoardPosition(0, 0);
        String testString = "salut";
        Assertions.assertNotEquals(testString, test);
    }

    @Test
    public void getXTest() {
        BoardPosition pos = new BoardPosition(5, 10);
        Assertions.assertEquals(pos.getX(), 5);
    }

    @Test
    public void getYTest() {
        BoardPosition pos = new BoardPosition(5, 10);
        Assertions.assertEquals(pos.getY(), 10);
    }

    @Test
    public void toStringTest() {
        BoardPosition pos = new BoardPosition(1, 2);
        Assertions.assertEquals(pos.toString(), "(1,2)");
    }
}

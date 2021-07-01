package fr.lernejo.navy_battle;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UtilitiesTest {
    @Test
    public void transAlphaTest() {
        Assertions.assertEquals(Utilities.translatePosToAlpha(new BoardPosition(0,0)), "A1");
    }
}

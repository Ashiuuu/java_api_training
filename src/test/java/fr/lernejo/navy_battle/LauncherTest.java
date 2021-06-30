package fr.lernejo.navy_battle;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class LauncherTest {
    @Test
    public void testRandomPos() {
        GameState game = new GameState();
        BoardPosition pos = Launcher.findRandomPos(game);
        Assertions.assertTrue(pos.getX() < 10);
    }

    @Test
    public void transAlphaTest() {
        Assertions.assertEquals(Launcher.translatePosToAlpha(new BoardPosition(0,0)), "A1");
    }
}

package fr.lernejo.navy_battle;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GameStateTest {
    @Test
    public void isGameOverTest() {
        GameState game = new GameState();
        Assertions.assertFalse(game.is_game_over());
    }

    @Test
    public void getTurnTest() {
        GameState game = new GameState();
        Assertions.assertFalse(game.get_turn());
    }

    @Test
    public void checkShipTest() {
        GameState game = new GameState();
        Assertions.assertTrue(game.check_ships_left());
    }

    @Test
    public void takeFireTest() {
        GameState game = new GameState();
        Assertions.assertEquals(game.takeFireFromEnemy(0, 0), "hit");
    }

    @Test
    public void getOppTest() {
        GameState game = new GameState();
        game.setOpponentAddress("test");
        Assertions.assertEquals(game.getOpponentAddress(), "test");
    }

    @Test
    public void getPosTest() {
        GameState game = new GameState();
        Assertions.assertEquals(game.getPosState(0,0), Board.State.FREE);
    }
}

package fr.lernejo.navy_battle;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GameStateTest {
    @Test
    public void isGameOverTest() {
        GameState game = new GameState();
        Assertions.assertEquals(game.is_game_over(), false);
    }

    @Test
    public void getTurnTest() {
        GameState game = new GameState();
        Assertions.assertEquals(game.get_turn(), false);
    }

    @Test
    public void checkShipTest() {
        GameState game = new GameState();
        Assertions.assertEquals(game.check_ships_left(), true);
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

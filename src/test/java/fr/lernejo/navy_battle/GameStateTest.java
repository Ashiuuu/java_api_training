package fr.lernejo.navy_battle;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GameStateTest {
    @Test
    public void isGameOverTest() {
        GameState game = new GameState("");
        Assertions.assertFalse(game.is_game_over());
    }

    @Test
    public void getTurnTest() {
        GameState game = new GameState("");
        Assertions.assertFalse(game.get_turn());
    }

    @Test
    public void checkShipTest() {
        GameState game = new GameState("");
        Assertions.assertTrue(game.check_ships_left());
    }

    @Test
    public void takeFireTest() {
        GameState game = new GameState("");
        Assertions.assertEquals(game.takeFireFromEnemy(0, 0), "hit");
    }

    @Test
    public void getOppTest() {
        GameState game = new GameState("");
        game.setOpponentAddress("test");
        Assertions.assertEquals(game.getOpponentAddress(), "test");
    }

    @Test
    public void getPosTest() {
        GameState game = new GameState("");
        Assertions.assertEquals(game.getPosState(0,0), Board.State.FREE);
    }

    @Test
    public void setTurnTest() {
        GameState game = new GameState("");
        game.set_turn(true);
        Assertions.assertTrue(game.get_turn());
    }

    @Test
    public void setGameOverTest() {
        GameState game = new GameState("");
        game.set_game_over(true);
        Assertions.assertTrue(game.is_game_over());
    }

    @Test
    public void getPortTest() {
        GameState game = new GameState("http://localhost:1234");
        Assertions.assertEquals(game.getPort(), 1234);
    }

    @Test
    public void getOwnAddressTest() {
        GameState game = new GameState("test_address");
        Assertions.assertEquals(game.getOwnAddress(), "test_address");
    }

    @Test
    public void newGameTest() {
        GameState game = new GameState("test");
        game.set_game_over(true).newGame();
        Assertions.assertFalse(game.is_game_over());
    }

    @Test
    public void fireAtTest() {
        GameState game = new GameState("test");
        game.fireAtCell(new BoardPosition(0,0), false);
        Assertions.assertEquals(game.getPosState(0,0), Board.State.FIRED);
    }
}

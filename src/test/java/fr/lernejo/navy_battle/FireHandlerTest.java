package fr.lernejo.navy_battle;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpServer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.http.HttpResponse;

class FireHandlerTest {
    @Test
    public void extractTest() {
        Assertions.assertEquals(FireHandler.extract_cell("cell=coucou"), "coucou");
    }

    @Test
    public void fireHandleTest() {
        try {
            GameState game = new GameState("http://localhost:9876");
            game.set_game_over(true);
            HttpServer server = Launcher.setupServer(game);

            HttpResponse<String> response = Utilities.sendGetRequest("http://localhost:9876/api/game/fire?cell=A1");

            ObjectMapper mapper = new ObjectMapper();
            JsonNode jnode = mapper.readTree(response.body());
            String consequence = jnode.get("consequence").asText();
            boolean shipLeft = jnode.get("shipLeft").asBoolean();

            Assertions.assertNotEquals(consequence, "sunk");
            Assertions.assertTrue(shipLeft);
            server.stop(1);
        } catch (Exception e) {
            System.out.println("Exception occured during test : " + e);
        }
    }

    @Test
    public void fireHandler404Test() {
        try {
            GameState game = new GameState("http://localhost:9876");
            game.set_game_over(true);
            HttpServer server = Launcher.setupServer(game);

            HttpResponse<String> response = Utilities.sendPostRequest("http://localhost:9876/api/game/fire?cell=A1", "shouldn't matter");

            Assertions.assertEquals(response.body(), "Not Found");
            Assertions.assertEquals(response.statusCode(), 404);
            server.stop(1);
        } catch (Exception e) {
            System.out.println("Exception occurred during test 404 FH : " + e);
        }
    }
}

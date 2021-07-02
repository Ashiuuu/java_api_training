package fr.lernejo.navy_battle;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpServer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpResponse;

public class StartHandlerTest {
    @Test
    public void streamTest() {
        try {
            String testString = "This is a test!";
            InputStream stream = new ByteArrayInputStream(testString.getBytes());
            Assertions.assertEquals(testString, StartHandler.stream_to_string(stream));
        } catch (IOException e) {
            System.out.print("[*] " + e);
        }
    }

    @Test
    public void startApiTest() {
        try {
            GameState game = new GameState("http://localhost:9876");
            game.set_game_over(true);
            HttpServer server = Launcher.setupServer(game);

            HttpResponse<String> response = Utilities.sendPostRequest("http://localhost:9876/api/game/start",
                "{\"url\": \"promis pas besoin\"}");

            ObjectMapper mapper = new ObjectMapper();
            JsonNode jnode = mapper.readTree(response.body());
            int id = jnode.get("id").asInt();
            String address = jnode.get("url").asText();
            String message = jnode.get("message").asText();

            Assertions.assertEquals(id, 2);
            Assertions.assertEquals(address, "http://localhost:9876");
            Assertions.assertEquals(message, "Salut poto");
            server.stop(1);
        } catch (Exception e) {
            System.out.println("Exception occurred during test : " + e);
        }
    }

    @Test
    public void start404Test() {
        try {
            GameState game = new GameState("http://localhost:9876");
            HttpServer server = Launcher.setupServer(game);

            HttpResponse<String> response = Utilities.sendGetRequest("http://localhost:9876/api/game/start");

            Assertions.assertEquals(response.statusCode(), 404);
            Assertions.assertEquals(response.body(), "Not Found");

            server.stop(1);
        } catch (Exception e) {
            System.out.println("Exception occurred during start 404 test : " + e);
        }
    }
}

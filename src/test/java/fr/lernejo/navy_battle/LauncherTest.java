package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpsExchange;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

class LauncherTest {
    @Test
    public void testRandomPos() {
        GameState game = new GameState("");
        BoardPosition pos = Launcher.findRandomPos(game);
        Assertions.assertTrue(pos.getX() < 10);
    }

    @Test
    public void pingTest() {
        try {
            GameState game = new GameState("http://localhost:9876");
            HttpServer server = Launcher.setupServer(game);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9876/ping"))
                .build();
            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
            Assertions.assertEquals(response.body(), "OK");
            server.stop(1);
        } catch (Exception e) {
            System.out.println("Exception occurred during test : " + e);
        }
    }
}

package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpServer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.http.HttpResponse;

class UtilitiesTest {
    @Test
    public void transAlphaTest() {
        Assertions.assertEquals(Utilities.translatePosToAlpha(new BoardPosition(0,0)), "A1");
    }

    @Test
    public void getWithHeaderTest() {
        try {
            GameState game = new GameState("http://localhost:9876");
            HttpServer server = Launcher.setupServer(game);

            HttpResponse<String> response = Utilities.sendGetRequestWithHeader("http://localhost:9876/ping");
            Assertions.assertEquals(response.statusCode(), 200);
            Assertions.assertEquals(response.body(), "OK");

            server.stop(1);
        } catch (Exception e) {
            System.out.println("Exception occurred during get test : " + e);
        }
    }
}

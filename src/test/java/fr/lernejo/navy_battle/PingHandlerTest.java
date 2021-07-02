package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpServer;
import jdk.jshell.execution.Util;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

class PingHandlerTest {
    @Test
    public void pingTest() {
        try {
            GameState game = new GameState("http://localhost:9876");
            HttpServer server = Launcher.setupServer(game);

            HttpResponse<String> response = Utilities.sendGetRequest("http://localhost:9876/ping");
            Assertions.assertEquals(response.body(), "OK");
            server.stop(1);
        } catch (Exception e) {
            System.out.println("Exception occurred during test : " + e);
        }
    }
}

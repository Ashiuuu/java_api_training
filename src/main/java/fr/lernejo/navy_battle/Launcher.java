package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Launcher
{
    public static void main(String[] args)
    {
        if (args.length == 0 || args.length >= 3)
        {
            System.out.println("Usage : {HTTP Port} [Server Address]");
            return;
        }

        try
        {
            GameState game = new GameState();
            ExecutorService executor = Executors.newFixedThreadPool(1);
            HttpServer server = HttpServer.create(new InetSocketAddress(Integer.parseInt(args[0])), 0);
            server.createContext("/ping", new PingHandler());
            server.createContext("/api/game/start", new StartHandler());
            server.createContext("/api/game/fire", new FireHandler(args, game));
            server.setExecutor(executor);
            server.start();

            if (args.length == 2)
            {
                game.set_turn(true); // the client starts the game
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(args[1] + "/api/game/start"))
                    .setHeader("Accept", "application/json")
                    .setHeader("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString("{\"id\":\"1\", \"url\":\"http://localhost:" + args[0] + "\", \"message\":\"hello\"}"))
                    .build();
                try
                {
                    HttpResponse<String> r = client.send(request, HttpResponse.BodyHandlers.ofString());
                }
                catch(Exception e)
                {
                    System.out.println("Could not send");
                    return;
                }
            }

            // while the game is not over
            while (!game.is_game_over())
            {
                if (game.get_turn()) // is it our turn ?
                {
                    // select a random unfired cell
                    // send get request to opponent using api
                    // receive response, update game
                }
                else
                {
                    // wait for get request, update turn
                }
            }

        } catch (IOException e) {
            System.out.println("Exception occurred");
        }
    }
}

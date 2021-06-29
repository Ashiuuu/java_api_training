package fr.lernejo.navy_battle;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Random;
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
            server.createContext("/api/game/start", new StartHandler(game));
            server.createContext("/api/game/fire", new FireHandler(game));
            server.setExecutor(executor);
            server.start();

            if (args.length == 2)
            {
                game.set_turn(true); // the client starts the game
                game.setOpponentAddress(args[1]);
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(args[1] + "/api/game/start"))
                    .setHeader("Accept", "application/json")
                    .setHeader("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString("{\"id\":\"1\", \"url\":\"http://localhost:" + args[0] + "\", \"message\":\"hello\"}"))
                    .build();
                try
                {
                    client.send(request, HttpResponse.BodyHandlers.ofString());
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
                    Random random = new Random();
                    int x;
                    int y;
                    do {
                        x = random.nextInt(10);
                        y = random.nextInt(10);
                    } while (!game.getPosState(x, y).equals(Board.State.FREE));
                    BoardPosition random_pos = new BoardPosition(x, y);

                    // send get request to opponent using api
                    runFireProcedure(game, random_pos);
                }
            }
        } catch (IOException e) {
            System.out.println("Exception occurred");
        }
    }

    public static void runFireProcedure(GameState game, BoardPosition pos) {
        // send GET request to opponent
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(game.getOpponentAddress() + "/api/game/fire?cell="))
                .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jnode = mapper.readTree(response.body());

            // update game
            String consequence = jnode.get("consequence").asText();
            boolean shipLeft = jnode.get("shipLeft").asBoolean();
            game.set_game_over(shipLeft);
            game.fireAtCell(pos, consequence.equals("hit") || consequence.equals("sunk"));
            game.set_turn(false); // we just played, not our turn anymore
        } catch (Exception e) {
            System.out.println("Error sending request");
        }
    }
}

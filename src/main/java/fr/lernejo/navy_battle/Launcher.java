package fr.lernejo.navy_battle;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpServer;

import java.net.*;
import java.net.http.HttpResponse;
import java.util.Random;
import java.util.concurrent.Executors;

public class Launcher
{
    public static void main(String[] args) {
        if (args.length == 0 || args.length >= 3) {
            System.out.println("Usage : {HTTP Port} [Server Address]");
            return;
        }
        GameState game = new GameState("http://localhost:" + Integer.parseInt(args[0]));
        Launcher.setupServer(game);
        if (args.length == 2) {
            Launcher.setupClient(game, args);
        }
        while (!game.is_game_over()) {
            if (game.get_turn()) {
                runFireProcedure(game); } }
        System.out.println("Game is Over!");
    }

    public static HttpServer setupServer(GameState game) {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(game.getPort()), 0);
            server.createContext("/ping", new PingHandler());
            server.createContext("/api/game/start", new StartHandler(game));
            server.createContext("/api/game/fire", new FireHandler(game));
            server.setExecutor(Executors.newSingleThreadExecutor());
            server.start();
            return server;
        } catch (Exception e) {
            System.out.println("Exception occurred :" + e);
            return null;
        }
    }

    public static void setupClient(GameState game, String[] args) {
        try {
            game.set_turn(true); // the client starts the game
            game.setOpponentAddress(args[1]);
            Utilities.sendPostRequest(args[1] + "/api/game/start", "{\"id\":\"1\", \"url\":\"http://localhost:" + args[0] + "\", \"message\":\"hello\"}");
        } catch(Exception e) {
            System.out.println("Could not send game start request : " + e);
        }
    }

    public static BoardPosition findRandomPos(GameState game) {
        Random random = new Random();
        int x;
        int y;
        do {
            x = random.nextInt(10);
            y = random.nextInt(10);
        } while (!game.getPosState(x, y).equals(Board.State.FREE));
        return new BoardPosition(x, y);
    }

    public static void runFireProcedure(GameState game) {
        try {
            BoardPosition pos = findRandomPos(game);
            String cellAlpha = Utilities.translatePosToAlpha(pos);
            HttpResponse<String> response = Utilities.sendGetRequestWithHeader(game.getOpponentAddress() + "/api/game/fire?cell=" + cellAlpha);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jnode = mapper.readTree(response.body());
            String consequence = jnode.get("consequence").asText();
            boolean shipLeft = jnode.get("shipLeft").asBoolean();
            System.out.println("[PROCEDURE] Cell " + cellAlpha + " got " + consequence + "; shipLeft = " + shipLeft);
            if (!shipLeft) {
                game.set_game_over(true); return; }
            game.fireAtCell(pos, consequence.equals("hit") || consequence.equals("sunk")).set_turn(false);
        } catch (Exception e) { System.out.println("Exception occurred in FP : " + e); }
    }
}

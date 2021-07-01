package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.util.Arrays;
import java.util.stream.Collectors;

public class FireHandler implements HttpHandler
{
    private final GameState game;

    public FireHandler(GameState g)
    {
        this.game = g;
    }

    @Override
    public void handle(HttpExchange t) throws IOException {
        if (t.getRequestMethod().equals("GET")) { // receiving update to game
            String cell = extract_cell(t.getRequestURI().getQuery());
            String consequence = this.game.takeFireFromEnemy(cell.charAt(0) - 'A', Integer.parseInt(cell.substring(1)) - 1);
            System.out.println("[FP] Cell " + cell + " has been " + consequence);
            String response = "{\"consequence\": \"" + consequence + "\", \"shipLeft\": " + this.game.check_ships_left() + "}";
            t.getResponseHeaders().set("Content-Type", "application/json");
            Utilities.sendResponse(t, 200, response);
            if (game.set_turn(true).check_ships_left() && !game.is_game_over())
                Launcher.runFireProcedure(game);
        }
        else
            Utilities.sendResponse(t, 404, "Not Found");
    }

    public static String extract_cell(String query)
    {
        return Arrays.stream(query.split("&"))
            .filter(p -> p.startsWith("cell="))
            .collect(Collectors.toList())
            .get(0)
            .split("=")[1];
    }
}

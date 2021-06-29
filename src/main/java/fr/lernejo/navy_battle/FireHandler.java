package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

public class FireHandler implements HttpHandler
{
    private final GameState game;

    public FireHandler(GameState g)
    {
        this.game = g;
    }

    @Override
    public void handle(HttpExchange t) throws IOException
    {
        if (t.getRequestMethod().equals("GET"))
        { // receiving update to game
            String cell = extract_cell(t.getRequestURI().getQuery());

            int x = cell.charAt(0) - 'A';
            int y = Integer.parseInt(cell.substring(1)) - 1;

            String consequence = this.game.takeFireFromEnemy(x, y);
            boolean shipLeft = this.game.check_ships_left();
            System.out.println("[FP] Cell " + cell + " has been " + consequence);

            String response = "{\"consequence\": \"" + consequence + "\", \"shipLeft\": " + shipLeft + "}";
            t.getResponseHeaders().set("Content-Type", "application/json");
            t.sendResponseHeaders(200, response.length());
            try (OutputStream os = t.getResponseBody())
            {
                os.write(response.getBytes());
            }
            game.set_turn(true);
            game.check_ships_left();
            System.out.println("[FP] Game over = " + game.is_game_over());
            if (!game.is_game_over())
                Launcher.runFireProcedure(game);
        }
        else
        {
            String body = "Not Found";
            t.sendResponseHeaders(404, body.length());
            try (OutputStream os = t.getResponseBody())
            {
                os.write(body.getBytes());
            }
        }
    }

    public String extract_cell(String query)
    {
        return Arrays.stream(query.split("&"))
            .filter(p -> p.startsWith("cell="))
            .collect(Collectors.toList())
            .get(0)
            .split("=")[1];
    }
}

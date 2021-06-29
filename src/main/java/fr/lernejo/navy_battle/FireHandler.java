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
    public void handle(HttpExchange t) throws IOException
    {
        if (t.getRequestMethod().equals("GET"))
        { // receiving update to game
            String cell = extract_cell(t.getRequestURI().getQuery());
            System.out.println("cell = " + cell);

            int x = 'A' - cell.charAt(0);
            int y = Integer.parseInt(cell.substring(1));
            System.out.println("x = " + x + "   y = " + y);

            String consequence = this.game.takeFireFromEnemy(x, y);
            boolean shipLeft = this.game.check_ships_left();

            String response = "{\"consequence\": " + consequence + ", \"shipLeft\": " + shipLeft + "}";
            t.sendResponseHeaders(200, response.length());
            try (OutputStream os = t.getResponseBody())
            {
                os.write(response.getBytes());
            }
            game.set_turn(true);
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

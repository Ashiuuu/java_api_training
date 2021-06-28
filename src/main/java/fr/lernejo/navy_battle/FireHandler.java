package fr.lernejo.navy_battle;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FireHandler implements HttpHandler
{
    private final String[] args;
    private GameState game;

    public FireHandler(String[] a, GameState g)
    {
        this.args = a;
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

            String consequence = ((this.game.check_friend_cell(x, y)) ? "hit" : "miss");
            boolean shipLeft = this.game.check_ships_left();

            // send result
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(args[1] + "/api/game/fire"))
                .setHeader("Accept", "application/json")
                .setHeader("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("{\"consequence\": \"" + consequence + "\", \"shipLeft\": " + shipLeft + "}"))
                .build();
            try
            {
                HttpResponse<String> r = client.send(request, HttpResponse.BodyHandlers.ofString());
                System.out.println(r.statusCode());
                System.out.println(r.body());
            }
            catch(Exception e)
            {
                System.out.println("Could not send");
            }
            game.set_turn(true);
        }
        else if (t.getRequestMethod().equals("POST"))
        { // receiving answer to our GET request
            String request = stream_to_string(t.getRequestBody());
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jnode = mapper.readTree(request);

            String consequence = jnode.get("consequence").asText();
            boolean shipLeft = jnode.get("shipLeft").asBoolean();

            if (!shipLeft)
            {
                game.set_game_over(true);
                return;
            }

            game.set_turn(false);
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

    public String stream_to_string(InputStream s) throws IOException
    {
        InputStreamReader isr = new InputStreamReader(s, "utf-8");
        BufferedReader br = new BufferedReader(isr);

        int b;
        StringBuilder buf = new StringBuilder();
        while ((b = br.read()) != -1)
        {
            buf.append((char) b);
        }

        br.close();
        isr.close();
        return buf.toString();
    }
}

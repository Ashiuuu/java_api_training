package fr.lernejo.navy_battle;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;

public class StartHandler implements HttpHandler
{
    private final GameState game;

    public StartHandler(GameState g) {
        this.game = g;
    }

    @Override
    public void handle(HttpExchange t) throws IOException
    {
        if (t.getRequestMethod().equals("POST"))
        {
            String request = stream_to_string(t.getRequestBody());
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jnode = mapper.readTree(request);
            String id = jnode.get("id").asText();
            String url = jnode.get("url").asText();
            String message = jnode.get("message").asText();

            System.out.println("[" + id + "](" + url + "): " + message);
            game.newGame();
            game.setOpponentAddress(url);
            // TODO : add check to see if request is good

            String body = "Accepted";
            t.sendResponseHeaders(202, body.length());
            try (OutputStream os = t.getResponseBody())
            {
                os.write(body.getBytes());
            }
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

    public static String stream_to_string(InputStream s) throws IOException
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

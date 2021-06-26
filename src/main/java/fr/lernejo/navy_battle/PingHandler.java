package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

class PingHandler implements HttpHandler
{
    @Override
    public void handle(HttpExchange t) throws IOException
    {
        String response = "OK";
        t.sendResponseHeaders(200, response.length());
        try (OutputStream os = t.getResponseBody())
        {
            os.write(response.getBytes());
        }
    }
}

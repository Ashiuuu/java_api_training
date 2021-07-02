package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

class PingHandler implements HttpHandler
{
    @Override
    public void handle(HttpExchange t) throws IOException {
        Utilities.sendResponse(t, 200, "OK");
    }
}

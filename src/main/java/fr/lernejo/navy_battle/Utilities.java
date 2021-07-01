package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

public class Utilities {
    public static void sendResponse(HttpExchange exchange, int code, String body) throws IOException {
        exchange.sendResponseHeaders(code, body.length());
        try (OutputStream os = exchange.getResponseBody())
        {
            os.write(body.getBytes());
        }
    }

    public static String translatePosToAlpha(BoardPosition p) {
        return Character.toString('A' + p.getX()) + (p.getY() + 1);
    }
}

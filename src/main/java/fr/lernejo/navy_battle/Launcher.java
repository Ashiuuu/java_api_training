package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import java.io.IOException;
import java.io.OutputStream;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.IntToDoubleFunction;

public class Launcher
{
    public static void main(String[] args)
    {
        if (args.length == 1)
        {
            try {
                ExecutorService executor = Executors.newFixedThreadPool(1);
                HttpServer server = HttpServer.create(new InetSocketAddress(Integer.parseInt(args[0])), 0);
                server.createContext("/ping", new PingHandler());
                server.createContext("/api/game/start", new StartHandler());
                server.createContext("/api/game/fire", new FireHandler(args));
                server.setExecutor(executor);
                server.start();
            } catch (IOException e) {
                System.out.println("Exception occurred");
            }
        }
        else if (args.length == 2)
        {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(args[1] + "/api/game/start"))
                .setHeader("Accept", "application/json")
                .setHeader("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("{\"id\":\"1\", \"url\":\"http://localhost:" + args[0] + "\", \"message\":\"hello\"}"))
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
        }
        else
        {
            System.out.println("Usage : [HTTP Port] [Server Address]");
        }
    }
}

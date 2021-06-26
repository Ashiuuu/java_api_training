package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Launcher
{
    public static void main(String[] args)
    {
        try
        {
            ExecutorService executor = Executors.newFixedThreadPool(1);
            HttpServer server = HttpServer.create(new InetSocketAddress(Integer.parseInt(args[0])), 0);
            server.createContext("/ping", new PingHandler());
            server.setExecutor(executor);
            server.start();
        }
        catch (IOException e)
        {
            System.out.println("Exception occurred");
        }
    }
}

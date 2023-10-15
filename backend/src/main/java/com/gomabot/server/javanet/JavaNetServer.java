package com.gomabot.server.javanet;

import com.gomabot.server.RequestHandler;
import com.gomabot.utils.ConnectionSupplier;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class JavaNetServer {

    private final HttpServer server;

    public JavaNetServer(final int port, final ConnectionSupplier connectionSupplier, final RequestHandler handler) {
        try {
            server = HttpServer.create(new InetSocketAddress(port), 0);
            server.createContext("/api/", new JavaNetHandler(connectionSupplier, handler));
            server.setExecutor(null);
            System.out.println("Server listening in port " + port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void start() {
        server.start();
    }
}

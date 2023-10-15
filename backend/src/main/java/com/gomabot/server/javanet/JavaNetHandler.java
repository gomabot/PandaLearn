package com.gomabot.server.javanet;

import com.gomabot.server.Method;
import com.gomabot.server.Request;
import com.gomabot.server.RequestHandler;
import com.gomabot.server.Response;
import com.gomabot.utils.ConnectionSupplier;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JavaNetHandler implements HttpHandler {

    private final ConnectionSupplier connectionSupplier;
    private final RequestHandler requestHandler;

    public JavaNetHandler(final ConnectionSupplier connectionSupplier, final RequestHandler requestHandler) {
        this.connectionSupplier = connectionSupplier;
        this.requestHandler = requestHandler;
    }

    private static void sendResponse(final HttpExchange exchange, final Response response) {
        try {
            exchange.sendResponseHeaders(response.getCode(), response.getContent().length);
            final OutputStream body = exchange.getResponseBody();
            body.write(response.getContent());
            body.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void handle(final HttpExchange exchange) {
        try {
            final Response response = getResponse(exchange);
            sendResponse(exchange, response);
        } catch (RuntimeException e) {
            e.printStackTrace();
            final Response response = new Response(500, "Unexpected internal server error");
            sendResponse(exchange, response);
        }
    }

    private Response getResponse(final HttpExchange exchange) {
        final Optional<Method> method = getMethod(exchange.getRequestMethod());
        if (!method.isPresent()) {
            return new Response(415, "Method not allowed");
        }
        final URI uri = exchange.getRequestURI();
        final Map<String, String> headers = getHeaders(exchange.getRequestHeaders());
        final byte[] body = getBody(exchange.getRequestBody());
        final Request request = new Request(method.get(), uri, headers, body);
        return requestHandler.handle(connectionSupplier, request);
    }

    private byte[] getBody(final InputStream requestBody) {
        try {
            final ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int next = requestBody.read();
            while (next > -1) {
                bos.write(next);
                next = requestBody.read();
            }
            bos.flush();
            byte[] result = bos.toByteArray();
            bos.close();
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Optional<Method> getMethod(final String requestMethod) {
        try {
            return Optional.of(Method.valueOf(requestMethod));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    private Map<String, String> getHeaders(final Headers requestHeaders) {
        final Map<String, String> result = new HashMap<>();
        for (final String key : requestHeaders.keySet()) {
            final List<String> listOfValues = requestHeaders.get(key);
            if (listOfValues.size() != 1) {
                throw new RuntimeException("Dont know how to handle header " + key + " with values " + listOfValues);
            }
            result.put(key, listOfValues.get(0));
        }
        return result;
    }
}

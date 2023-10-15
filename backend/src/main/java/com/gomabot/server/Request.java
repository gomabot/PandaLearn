package com.gomabot.server;

import java.net.URI;
import java.util.Map;

public class Request {
    private final Method method;
    private final URI uri;
    private final Map<String, String> headers;
    private final byte[] body;

    public Request(final Method method, final URI uri, final Map<String, String> headers, final byte[] body) {
        this.method = method;
        this.uri = uri;
        this.headers = headers;
        this.body = body;
    }

    public Method getMethod() {
        return method;
    }

    public URI getUri() {
        return uri;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public byte[] getBody() {
        return body;
    }
}

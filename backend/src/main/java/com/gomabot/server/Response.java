package com.gomabot.server;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;

public class Response {
    private final int code;
    private final byte[] content;

    public Response(final int code, final byte[] content) {
        this.code = code;
        this.content = content;
    }

    public Response(final int code, final String content) {
        this.code = code;
        this.content = content.getBytes(StandardCharsets.UTF_8);
    }

    public Response(final int code, final String format, final Object... args) {
        this.code = code;
        this.content = String.format(format, args).getBytes();
    }

    public int getCode() {
        return code;
    }

    public byte[] getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "Response{" + "code=" + code + ", content=" + new String(content) + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Response response = (Response) o;
        return code == response.code && Arrays.equals(content, response.content);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(code);
        result = 31 * result + Arrays.hashCode(content);
        return result;
    }
}

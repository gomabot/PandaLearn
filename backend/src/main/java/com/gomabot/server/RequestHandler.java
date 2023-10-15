package com.gomabot.server;

import com.gomabot.utils.ConnectionSupplier;

public interface RequestHandler {
    Response handle(final ConnectionSupplier connectionSupplier, final Request request);
}

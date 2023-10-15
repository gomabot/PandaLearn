package com.gomabot.requesthandlers.get;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gomabot.server.Request;
import com.gomabot.server.RequestHandler;
import com.gomabot.server.Response;
import com.gomabot.server.dbmanager.RegisteredUserDBManager;
import com.gomabot.server.dbo.RegisteredUser;
import com.gomabot.utils.ConnectionSupplier;
import com.gomabot.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;

import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;

public class UserLoginRequestHandler implements RequestHandler {
    @Override
    public Response handle(final ConnectionSupplier connectionSupplier, final Request request) {
        try {
            final Map<String, String> headers = request.getHeaders();
            final String username = headers.get("Username");
            final String passwordHash = headers.get("Password-hash");
            if (StringUtils.isBlank(username) || StringUtils.isBlank(passwordHash)) {
                return new Response(400, "Request must provide value for the headers Username and Password-hash");
            }
            final Optional<RegisteredUser> user = RegisteredUserDBManager.getUserByUsername(connectionSupplier, username);
            if (!user.isPresent()) {
                return new Response(404, "Username not found");
            }
            if (!user.get().getPasswordHash().equals(passwordHash)) {
                return new Response(403, "Password hash is incorrect");
            }
            return new Response(200, JsonUtils.stringify(user.get()));
        } catch (SQLException e) {
            e.printStackTrace();
            return new Response(500, "Error interacting with DDBB");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new Response(500, "Error parsing JSON");
        }
    }
}

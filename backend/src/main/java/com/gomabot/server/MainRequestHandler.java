package com.gomabot.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gomabot.server.dbmanager.LessonDBManager;
import com.gomabot.server.dbmanager.RegisteredUserDBManager;
import com.gomabot.server.dbo.RegisteredUser;
import com.gomabot.utils.ConnectionSupplier;
import com.gomabot.utils.JsonUtils;
import com.gomabot.utils.RequestManagementUtils;
import org.apache.commons.lang3.StringUtils;

import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;

public class MainRequestHandler implements RequestHandler {
    public Response handle(final ConnectionSupplier connectionSupplier, final Request request) {
        try {
            final Method method = request.getMethod();
            switch (method) {
                case PUT:
                    return handlePutRequest(connectionSupplier, request);
                case GET:
                    return GetRequestHandler.getHandler(request).map(handler -> handler.handle(connectionSupplier, request)) //
                            .orElseGet(() -> new Response(404, "Path '%s' not found for method GET", request.getUri().getPath()));
                case POST:
                    return handlePostRequest(connectionSupplier, request);
                default:
                    return new Response(418, "Cannot handle method " + method);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return new Response(500, "Error interacting with DDBB");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new Response(500, "Error processing JSON");
        }
    }

    private Response handlePutRequest(final ConnectionSupplier connectionSupplier, final Request request) throws SQLException, JsonProcessingException {
        final String path = request.getUri().getPath();
        if (path.equals("/api/user")) {
            return handleUserRegistration(connectionSupplier, request);
        }
        return new Response(404, "Path '%s' not found for method PUT", path);
    }

    private Response handleUserRegistration(final ConnectionSupplier connectionSupplier, final Request request) throws SQLException, JsonProcessingException {
        final Map<String, String> headers = request.getHeaders();
        final String username = headers.get("Username");
        final String passwordHash = headers.get("Password-hash");
        if (StringUtils.isBlank(username) || StringUtils.isBlank(passwordHash)) {
            return new Response(400, "Request must provide value for the headers Username and Password-hash");
        }
        if (RegisteredUserDBManager.getUserByUsername(connectionSupplier, username).isPresent()) {
            return new Response(409, "Username already registered");
        }
        RegisteredUserDBManager.createNewUser(connectionSupplier, username, passwordHash);
        final RegisteredUser user = RegisteredUserDBManager.getUserByUsername(connectionSupplier, username).get();
        return new Response(201, JsonUtils.stringify(user));
    }

    private Response handlePostRequest(final ConnectionSupplier connectionSupplier, final Request request) {
        final String path = request.getUri().getPath();
        if (path.equals("/api/user/increase_lesson_level")) {
            return handleUserLessonLevelIncrease(connectionSupplier, request);
        }
        return new Response(404, "Path '%s' not found for method POST", path);
    }

    private Response handleUserLessonLevelIncrease(final ConnectionSupplier connectionSupplier, final Request request) {
        final Map<String, String> headers = request.getHeaders();
        final Optional<Integer> idAsString = RequestManagementUtils.readIntegerHeader(request, "User-id");
        if (!idAsString.isPresent()) {
            return new Response(400, "You need to provide an integer for the header User-id");
        }
        final int id = idAsString.get();
        final String passwordHash = headers.get("Password-hash");
        if (StringUtils.isBlank(passwordHash)) {
            return new Response(400, "Request must provide value for the header Password-hash");
        }
        final Optional<Integer> lessonId = RequestManagementUtils.readIntegerHeader(request, "Lesson-id");
        if (!lessonId.isPresent()) {
            return new Response(400, "You need to provide an integer for the header Lesson-id");
        }
        try {
            final Optional<RegisteredUser> userSearch = RegisteredUserDBManager.getUserById(connectionSupplier, id);
            if (!userSearch.isPresent()) {
                return new Response(404, "User not found");
            }
            final RegisteredUser user = userSearch.get();
            if (!passwordHash.equals(user.getPasswordHash())) {
                return new Response(403, "Password hash is not correct");
            }
            if (user.getLesson() != lessonId.get()) {
                return new Response(409, "Lesson will only change if user current lesson is equal to the lesson it has completed");
            }
            if (user.getLesson() == LessonDBManager.getHighestLessonId(connectionSupplier)) {
                return new Response(409, "User already at max lesson, will not increase");
            }
            RegisteredUserDBManager.setUserLessonLevel(connectionSupplier, user.getId(), user.getLesson() + 1);
            return new Response(200, "Changed");
        } catch (SQLException e) {
            e.printStackTrace();
            return new Response(500, "Error interacting with DDBB");
        }
    }
}
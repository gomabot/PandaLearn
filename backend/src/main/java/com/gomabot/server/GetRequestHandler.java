package com.gomabot.server;

import com.gomabot.requesthandlers.get.*;
import com.gomabot.utils.ConnectionSupplier;
import com.gomabot.utils.RegexUtils;

import java.util.Optional;
import java.util.regex.Pattern;

public enum GetRequestHandler {
    USER("user", new UserRequestHandler()),
    USER_LOGIN("user/login", new UserLoginRequestHandler()),
    VOCABULARY("vocabulary", new VocabularyRequestHandler()),
    LESSON("lessons", new LessonsRequestHandler()),
    EXAM("exams", new ExamsRequestHandler()),
    EXERCISES_BY_LESSON("lesson/exercises", new ExercicesByLessonRequestHandler()),
    EXERCISES_BY_EXAM("exam/exercises", new ExercicesByExamRequestHandler()),
    ;

    private static final Pattern expectedUrlPattern = Pattern.compile("^/api/([a-z/]+)$");

    private final String endpoint;
    private final RequestHandler requestHandler;

    GetRequestHandler(final String endpoint, final RequestHandler requestHandler) {
        this.endpoint = endpoint;
        this.requestHandler = requestHandler;
    }

    public static Optional<GetRequestHandler> getHandler(final Request request) {
        if (!Method.GET.equals(request.getMethod())) {
            throw new IllegalStateException("Get handler cannot be handling requests with the method " + request.getMethod());
        }
        final String path = request.getUri().getPath();
        final Optional<String> endpoint = RegexUtils.getFirstGroupOf(path, expectedUrlPattern);
        if (!endpoint.isPresent()) {
            return Optional.empty();
        }
        for (final GetRequestHandler handler : GetRequestHandler.values()) {
            if (handler.endpoint.equals(endpoint.get())) {
                return Optional.of(handler);
            }
        }
        return Optional.empty();
    }

    public Response handle(final ConnectionSupplier connectionSupplier, final Request request) {
        return requestHandler.handle(connectionSupplier, request);
    }
}

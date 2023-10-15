package com.gomabot.requesthandlers.get;

import com.gomabot.server.Request;
import com.gomabot.server.RequestHandler;
import com.gomabot.server.Response;
import com.gomabot.server.dbmanager.ExerciseDBManager;
import com.gomabot.utils.ConnectionSupplier;
import com.gomabot.utils.RequestManagementUtils;

import java.util.Optional;

public class ExercicesByLessonRequestHandler implements RequestHandler {
    @Override
    public Response handle(final ConnectionSupplier connectionSupplier, final Request request) {
        final Optional<Integer> lessonId = RequestManagementUtils.readIntegerHeader(request, "Lesson-id");
        if (!lessonId.isPresent()) {
            return new Response(400, "You need to provide an integer for the header Lesson-id");
        }
        return RequestManagementUtils.handleSimpleRequestToDatabaseTable(() -> ExerciseDBManager.getExercisesByLessonId(connectionSupplier, lessonId.get()));
    }
}

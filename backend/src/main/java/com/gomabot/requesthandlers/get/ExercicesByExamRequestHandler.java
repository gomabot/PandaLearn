package com.gomabot.requesthandlers.get;

import com.gomabot.server.Request;
import com.gomabot.server.RequestHandler;
import com.gomabot.server.Response;
import com.gomabot.server.dbmanager.ExerciseDBManager;
import com.gomabot.utils.ConnectionSupplier;
import com.gomabot.utils.RequestManagementUtils;

import java.util.Optional;

public class ExercicesByExamRequestHandler implements RequestHandler {
    @Override
    public Response handle(final ConnectionSupplier connectionSupplier, final Request request) {
        final Optional<Integer> examId = RequestManagementUtils.readIntegerHeader(request, "Exam-id");
        if (!examId.isPresent()) {
            return new Response(400, "You need to provide an integer for the header Exam-id");
        }
        return RequestManagementUtils.handleSimpleRequestToDatabaseTable(() -> ExerciseDBManager.getExercisesByExamId(connectionSupplier, examId.get()));
    }
}

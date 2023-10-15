package com.gomabot.requesthandlers.get;

import com.gomabot.server.Request;
import com.gomabot.server.RequestHandler;
import com.gomabot.server.Response;
import com.gomabot.server.dbmanager.LessonDBManager;
import com.gomabot.utils.ConnectionSupplier;
import com.gomabot.utils.RequestManagementUtils;

public class LessonsRequestHandler implements RequestHandler {
    @Override
    public Response handle(final ConnectionSupplier connectionSupplier, final Request request) {
        return RequestManagementUtils.handleSimpleRequestToDatabaseTable(() -> LessonDBManager.getAllLessons(connectionSupplier));
    }
}

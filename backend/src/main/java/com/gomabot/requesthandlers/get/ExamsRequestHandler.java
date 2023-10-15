package com.gomabot.requesthandlers.get;

import com.gomabot.server.Request;
import com.gomabot.server.RequestHandler;
import com.gomabot.server.Response;
import com.gomabot.server.dbmanager.ExamDBManager;
import com.gomabot.utils.ConnectionSupplier;
import com.gomabot.utils.RequestManagementUtils;

public class ExamsRequestHandler implements RequestHandler {
    @Override
    public Response handle(final ConnectionSupplier connectionSupplier, final Request request) {
        return RequestManagementUtils.handleSimpleRequestToDatabaseTable(() -> ExamDBManager.getAllExams(connectionSupplier));
    }
}

package com.gomabot.requesthandlers.get;

import com.gomabot.server.Request;
import com.gomabot.server.RequestHandler;
import com.gomabot.server.Response;
import com.gomabot.server.dbmanager.VocabularyDBManager;
import com.gomabot.utils.ConnectionSupplier;
import com.gomabot.utils.RequestManagementUtils;

public class VocabularyRequestHandler implements RequestHandler {
    @Override
    public Response handle(final ConnectionSupplier connectionSupplier, final Request request) {
        return RequestManagementUtils.handleSimpleRequestToDatabaseTable(() -> VocabularyDBManager.getAllVocabulary(connectionSupplier));
    }
}

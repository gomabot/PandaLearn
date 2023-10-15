package com.gomabot.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gomabot.server.Request;
import com.gomabot.server.Response;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableSupplier;

import java.sql.SQLException;
import java.util.Optional;

public class RequestManagementUtils {
    public static <T> Response handleSimpleRequestToDatabaseTable(final FailableSupplier<T, SQLException> dataSupplier) {
        try {
            final T items = dataSupplier.get();
            final String json = JsonUtils.stringify(items);
            return new Response(200, json);
        } catch (SQLException e) {
            e.printStackTrace();
            return new Response(500, "Error requesting data from DDBB");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new Response(500, "Error parsing JSON");
        }
    }

    public static Optional<Integer> readIntegerHeader(final Request request, final String headerName) {
        final String headerValue = request.getHeaders().get(headerName);
        if (StringUtils.isBlank(headerValue)) {
            return Optional.empty();
        }
        try {
            return Optional.of(Integer.parseInt(headerValue));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
}

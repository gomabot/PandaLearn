package com.gomabot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gomabot.config.Config;
import com.gomabot.server.MainRequestHandler;
import com.gomabot.server.RequestHandler;
import com.gomabot.server.javanet.JavaNetServer;
import com.gomabot.utils.ConnectionSupplier;
import com.gomabot.utils.JsonUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(final String[] args) {
        final Config config = getConfig(args);
        final ConnectionSupplier connectionSupplier = new ConnectionSupplier(config.getDatabaseConfig());
        final RequestHandler requestHandler = new MainRequestHandler();
        final JavaNetServer server = new JavaNetServer(config.getPort(), connectionSupplier, requestHandler);
        server.start();
    }

    private static Config getConfig(final String[] args) {
        if (args.length != 1) {
            exitWithError(String.format("A single parameter with the path of the config was expected but %d where found", args.length));
        }
        final Path path = Paths.get(args[0]);
        if (!Files.isRegularFile(path)) {
            exitWithError("The first parameter was expected to be the config file, but it does not exist (or is not a regular file)");
        }
        try {
            return JsonUtils.parse(Config.class, new String(Files.readAllBytes(path)));
        } catch (JsonProcessingException e) {
            exitWithError("Could not parse config json file: " + e.getMessage());
        } catch (IOException e) {
            exitWithError("Could not read config file: " + e.getMessage());
        }
        throw new IllegalStateException("If failed system should already be halted");
    }

    private static void exitWithError(final String error) {
        System.err.println(error);
        System.exit(1);
    }

}
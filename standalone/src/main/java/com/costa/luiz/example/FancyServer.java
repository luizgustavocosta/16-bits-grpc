package com.costa.luiz.example;

import io.grpc.Grpc;
import io.grpc.InsecureServerCredentials;
import io.grpc.protobuf.services.ProtoReflectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static java.util.Objects.nonNull;

public class FancyServer {

    protected static final Logger logger = LoggerFactory.getLogger(FancyServer.class);
    private static final int GREETING_CONTROLLER_SERVICE_PORT = 50051;
    private io.grpc.Server server;

    public static void main(String[] args) {
        FancyServer fancyServer = new FancyServer();
        try {
            fancyServer.start();
            fancyServer.blockUntilShutdown();
        } catch (IOException | InterruptedException exception) {
            logger.error(exception.getMessage(), exception);
            Thread.currentThread().interrupt();
        }
    }

    private void blockUntilShutdown() throws InterruptedException {
        if (nonNull(server)) server.awaitTermination();
    }

    private void start() throws IOException {
        server = Grpc.newServerBuilderForPort(GREETING_CONTROLLER_SERVICE_PORT,
                        InsecureServerCredentials.create())
                .addService(new GreetingMessageService())
                .addService(ProtoReflectionService.newInstance())
                .build()
                .start();
        logger.info("Fancy Server started at port {}", GREETING_CONTROLLER_SERVICE_PORT);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                FancyServer.this.stop();
            } catch (InterruptedException exception) {
                logger.error(exception.getMessage(), exception);
                Thread.currentThread().interrupt();
            }
        }));
    }

    private void stop() throws InterruptedException {
        if (nonNull(server)) {
            int timeout = 30;
            logger.info("Server will terminate at maximum of {} seconds", timeout);
            server.shutdown().awaitTermination(timeout, TimeUnit.SECONDS);
        }
    }
}

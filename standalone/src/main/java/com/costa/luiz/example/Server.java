package com.costa.luiz.example;

import io.grpc.Grpc;
import io.grpc.InsecureServerCredentials;
import io.grpc.protobuf.services.ProtoReflectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static java.util.Objects.nonNull;

public class Server {

    protected static final Logger logger = LoggerFactory.getLogger(Server.class);
    private static final int PORT = 50052;
    private io.grpc.Server server;

    public static void main(String[] args) {
        Server helloServer = new Server();
        try {
            helloServer.start();
            helloServer.blockUntilShutdown();
        } catch (IOException | InterruptedException exception) {
            GreetingMessageService.logger.error(exception.getMessage(), exception);
        }
    }

    private void blockUntilShutdown() throws InterruptedException {
        if (nonNull(server)) server.awaitTermination();
    }

    private void start() throws IOException {
        server = Grpc.newServerBuilderForPort(PORT,
                        InsecureServerCredentials.create())
                .addService(new GreetingMessageService.ExampleImpl())
                .addService(ProtoReflectionService.newInstance())
                .build()
                .start();
        logger.info("Server started at port {}", PORT);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                Server.this.stop();
            } catch (InterruptedException exception) {
                throw new RuntimeException(exception);
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

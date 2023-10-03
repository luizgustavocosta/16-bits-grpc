package com.costa.luiz.example;

import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class SimpleServer {

    private static final Logger logger = LoggerFactory.getLogger(SimpleServer.class);
    private static final int GREETING_CONTROLLER_SERVICE_PORT = 50051;

    public static void main(String[] args)
            throws IOException, InterruptedException {
        io.grpc.Server server =
                ServerBuilder.forPort(GREETING_CONTROLLER_SERVICE_PORT)
                        .addService(new GreetingMessageService())
                        .build();
        server.start();
        logger.info("Simple Server started at port {}", GREETING_CONTROLLER_SERVICE_PORT);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            server.shutdown();
            logger.info("Successfully stopped the server");
        }));
        server.awaitTermination();
    }
}

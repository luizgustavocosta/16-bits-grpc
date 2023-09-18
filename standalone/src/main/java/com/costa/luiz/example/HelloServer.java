package com.costa.luiz.example;

import com.costa.luiz.grpc.example.HelloGrpc;
import com.costa.luiz.grpc.example.Id;
import com.costa.luiz.grpc.example.Request;
import com.costa.luiz.grpc.example.Response;
import com.costa.luiz.grpc.example.ResponseList;
import io.grpc.Grpc;
import io.grpc.InsecureServerCredentials;
import io.grpc.Server;
import io.grpc.protobuf.services.ProtoReflectionService;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.util.Objects.nonNull;

public class HelloServer {

    private static final int PORT = 50052;
    private static final Logger logger = LoggerFactory.getLogger(HelloServer.class);

    private Server server;

    public static void main(String[] args) {
        HelloServer helloServer = new HelloServer();
        try {
            helloServer.start();
            helloServer.blockUntilShutdown();
        } catch (IOException | InterruptedException exception) {
            logger.error(exception.getMessage(), exception);
        }
    }

    private void blockUntilShutdown() throws InterruptedException {
        if (nonNull(server)) server.awaitTermination();
    }

    private void start() throws IOException {
        server = Grpc.newServerBuilderForPort(PORT,
                        InsecureServerCredentials.create())
                .addService(new ExampleImpl())
                .addService(ProtoReflectionService.newInstance())
                .build()
                .start();
        logger.info("Server started at port {}", PORT);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                HelloServer.this.stop();
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

    static class ExampleImpl extends HelloGrpc.HelloImplBase {

        @Override
        public void findOneHelloMessage(Request request, StreamObserver<Response> responseObserver) {
            logger.info("New request has been arrived to method findOneHelloMessage");
            responseObserver.onNext(
                    Response.newBuilder()
                            .setMessage("This was made using gRPC")
                            .build()
            );
            responseObserver.onCompleted();
        }

        @Override
        public void findManyHelloMessages(Id request, StreamObserver<ResponseList> responseObserver) {
            logger.info("New request has been arrived to method findManyHelloMessages");
            logger.info("Fake call to DB to retrieve data");
            responseObserver.onNext(
                    ResponseList.newBuilder()
                            .addAllResponse(
                                    List.of(
                                            Response.newBuilder().setMessage("First").build(),
                                            Response.newBuilder().setMessage("Second").build(),
                                            Response.newBuilder().setMessage("Third").build()
                                    )
                            )
                            .build()
            );
            responseObserver.onCompleted();
        }
    }
}

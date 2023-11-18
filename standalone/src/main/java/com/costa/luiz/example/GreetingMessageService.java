package com.costa.luiz.example;

import com.costa.luiz.grpc.example.GreetingId;
import com.costa.luiz.grpc.example.GreetingRequest;
import com.costa.luiz.grpc.example.GreetingResponse;
import com.costa.luiz.grpc.example.GreetingResponseList;
import com.costa.luiz.grpc.example.GreetingServiceGrpc;
import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class GreetingMessageService extends GreetingServiceGrpc.GreetingServiceImplBase {
    private static final Logger logger = LoggerFactory.getLogger(GreetingMessageService.class);

    @Override
    public void findOneHelloMessage(GreetingRequest request, StreamObserver<GreetingResponse> responseObserver) {
        logger.info("New request has been arrived to method findOneHelloMessage");
        var message = "";
        switch (request.getLanguage()) {
            case ENGLISH -> message = "Hello";
            case PORTUGUESE -> message = "Olá";
            case SPANISH -> message = "Hola";
            default -> throw new IllegalStateException("Unexpected value: " + request.getLanguage());
        }
        responseObserver.onNext(
                GreetingResponse.newBuilder()
                        .setMessage(message +" "+ request.getName())
                        .build()
        );
        responseObserver.onCompleted();
    }

    @Override
    public void findHelloMessagesById(GreetingId request, StreamObserver<GreetingResponseList> responseObserver) {
        logger.info("New request has been arrived to method findManyHelloMessages");
        logger.info("Fake call to DB to retrieve data");
        responseObserver.onNext(
                GreetingResponseList.newBuilder()
                        .addAllResponse(
                                List.of(
                                        GreetingResponse.newBuilder().setMessage("First").build(),
                                        GreetingResponse.newBuilder().setMessage("Second").build(),
                                        GreetingResponse.newBuilder().setMessage("Third").build()
                                )
                        )
                        .build()
        );
        responseObserver.onCompleted();
    }

    @Override
    public void sayHello(Empty request, StreamObserver<GreetingResponse> responseObserver) {
        logger.info("Say a generic hello");
        responseObserver.onNext(
                GreetingResponse.newBuilder()
                        .setMessage("Hello there!")
                        .build()
        );
        responseObserver.onCompleted();
    }

    @Override
    public void deleteHelloMessage(GreetingId request, StreamObserver<Empty> responseObserver) {
        //Fake call to search the entity id
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

}

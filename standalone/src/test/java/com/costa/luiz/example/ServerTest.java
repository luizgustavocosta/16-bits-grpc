package com.costa.luiz.example;

import com.costa.luiz.grpc.example.GreetingId;
import com.costa.luiz.grpc.example.GreetingRequest;
import com.costa.luiz.grpc.example.GreetingResponse;
import com.costa.luiz.grpc.example.GreetingResponseList;
import com.costa.luiz.grpc.example.GreetingServiceGrpc;
import com.costa.luiz.grpc.example.Language;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.testing.GrpcCleanupRule;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Copied from
 * <a href="https://github.com/grpc/grpc-java/blob/master/examples/src/test/java/io/grpc/examples/helloworld/HelloWorldServerTest.java">GH grpc</a>
 */
class ServerTest {

    public final GrpcCleanupRule grpcCleanup = new GrpcCleanupRule();

    @Test
    void whenCallFindOneHelloMessageThenReturnAMessage() throws Exception {
        // Generate a unique in-process server name.
        String serverName = InProcessServerBuilder.generateName();

        // Create a server, add service, start, and register for automatic graceful shutdown.
        grpcCleanup.register(InProcessServerBuilder
                .forName(serverName).directExecutor().addService(new GreetingMessageService.ExampleImpl()).build().start());

        GreetingServiceGrpc.GreetingServiceBlockingStub blockingStub = GreetingServiceGrpc.newBlockingStub(
                // Create a client channel and register for automatic graceful shutdown.
                grpcCleanup.register(InProcessChannelBuilder.forName(serverName).directExecutor().build()));

        GreetingResponse response = blockingStub.findOneHelloMessage(GreetingRequest.newBuilder()
                .setName("Luiz")
                .setLanguage(Language.ENGLISH)
                .build());

        assertEquals("Hello there", response.getMessage());
    }

    @Test
    void whenCallFindManyHelloMessagesThenReturnMessages() throws Exception {
        // Generate a unique in-process server name.
        String serverName = InProcessServerBuilder.generateName();

        // Create a server, add service, start, and register for automatic graceful shutdown.
        grpcCleanup.register(InProcessServerBuilder
                .forName(serverName).directExecutor().addService(new GreetingMessageService.ExampleImpl()).build().start());

        GreetingServiceGrpc.GreetingServiceBlockingStub blockingStub = GreetingServiceGrpc.newBlockingStub(
                // Create a client channel and register for automatic graceful shutdown.
                grpcCleanup.register(InProcessChannelBuilder.forName(serverName).directExecutor().build()));

        GreetingResponseList response = blockingStub.findHelloMessagesById(GreetingId.newBuilder().setId(42).build());

        assertEquals(3, response.getResponseList().size());
    }

}

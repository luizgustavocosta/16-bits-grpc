package com.costa.luiz.example;

import com.costa.luiz.grpc.example.HelloGrpc;
import com.costa.luiz.grpc.example.Id;
import com.costa.luiz.grpc.example.Request;
import com.costa.luiz.grpc.example.Response;
import com.costa.luiz.grpc.example.ResponseList;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.testing.GrpcCleanupRule;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Copied from
 * <a href="https://github.com/grpc/grpc-java/blob/master/examples/src/test/java/io/grpc/examples/helloworld/HelloWorldServerTest.java">GH grpc</a>
 */
public class HelloServerTest {

    public final GrpcCleanupRule grpcCleanup = new GrpcCleanupRule();

    @Test
    void whenCallFindOneHelloMessageThenReturnAMessage() throws Exception {
        // Generate a unique in-process server name.
        String serverName = InProcessServerBuilder.generateName();

        // Create a server, add service, start, and register for automatic graceful shutdown.
        grpcCleanup.register(InProcessServerBuilder
                .forName(serverName).directExecutor().addService(new HelloServer.ExampleImpl()).build().start());

        HelloGrpc.HelloBlockingStub blockingStub = HelloGrpc.newBlockingStub(
                // Create a client channel and register for automatic graceful shutdown.
                grpcCleanup.register(InProcessChannelBuilder.forName(serverName).directExecutor().build()));

        Response reply = blockingStub.findOneHelloMessage(Request.newBuilder().setName("Luiz").build());

        assertEquals("This was made using gRPC", reply.getMessage());
    }

    @Test
    void whenCallFindManyHelloMessagesThenReturnMessages() throws Exception {
        // Generate a unique in-process server name.
        String serverName = InProcessServerBuilder.generateName();

        // Create a server, add service, start, and register for automatic graceful shutdown.
        grpcCleanup.register(InProcessServerBuilder
                .forName(serverName).directExecutor().addService(new HelloServer.ExampleImpl()).build().start());

        HelloGrpc.HelloBlockingStub blockingStub = HelloGrpc.newBlockingStub(
                // Create a client channel and register for automatic graceful shutdown.
                grpcCleanup.register(InProcessChannelBuilder.forName(serverName).directExecutor().build()));

        ResponseList responseList = blockingStub.findManyHelloMessages(Id.newBuilder().setId(42).build());

        assertEquals(3, responseList.getResponseList().size());
    }

}

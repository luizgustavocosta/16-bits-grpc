package com.costa.luiz.grpc;

import com.costa.luiz.grpc.example.GreetingRequest;
import com.costa.luiz.grpc.example.GreetingResponse;
import com.costa.luiz.grpc.example.GreetingServiceGrpc;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GreetingClient {
    private static final Logger logger = Logger.getLogger(GreetingClient.class.getName());

    private final GreetingServiceGrpc.GreetingServiceBlockingStub blockingStub;

    private static final int PORT = 50051;

    /**
     * Construct client for accessing HelloWorld server using the existing channel.
     */
    public GreetingClient(Channel channel) {
        // 'channel' here is a Channel, not a ManagedChannel, so it is not this code's responsibility to
        // shut it down.
        // Passing Channels to code makes code easier to test and makes it easier to reuse Channels.
        blockingStub = GreetingServiceGrpc.newBlockingStub(channel);
    }

    /**
     * Say hello to server.
     */
    public void greet(String name) {
        var message = "Will try to greet to " + name;
        logger.info(message);
        var request = GreetingRequest.newBuilder().setName(name).build();
        GreetingResponse response;
        try {
            response = blockingStub.findOneHelloMessage(request);
        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
            return;
        }
        logger.info("Greeting: " + response.getMessage());
    }

    /**
     * Greet server. If provided, the first element of {@code args} is the name to use in the
     * greeting. The second argument is the target server.
     */
    public static void main(String[] args) throws Exception {
        var user = "world";
        // Access a service running on the local machine on port 50051
        var target = "localhost:" + PORT;
        // For the example we use plaintext insecure credentials to avoid needing TLS certificates. To
        // use TLS, use TlsChannelCredentials instead.
        var channel = ManagedChannelBuilder.forTarget(target).usePlaintext().build();
        try {
            var client = new GreetingClient(channel);
            client.greet(user);
        } finally {
            // ManagedChannels use resources like threads and TCP connections. To prevent leaking these
            // resources the channel should be shut down when it will no longer be used. If it may be used
            // again leave it running.
            channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
        }
    }
}

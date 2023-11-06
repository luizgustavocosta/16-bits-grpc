package com.costa.luiz.example;

import com.costa.luiz.grpc.example.GreetingId;
import com.costa.luiz.grpc.example.GreetingRequest;
import com.costa.luiz.grpc.example.GreetingResponse;
import com.costa.luiz.grpc.example.GreetingResponseList;
import com.costa.luiz.grpc.example.GreetingServiceGrpc;
import com.costa.luiz.grpc.example.Language;
import com.google.protobuf.Empty;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.stub.StreamObserver;
import io.grpc.testing.GrpcCleanupRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class GreetingMessageServiceTest {

    GreetingMessageService service;

    @BeforeEach
    void setUp() {
        service = new GreetingMessageService();
    }

    @ParameterizedTest
    @MethodSource("generateLocalizedMessage")
    void whenSendTheLanguageReturnTheLocalizedGreetingMessage(Language language, String expectedResponse) {
        var streamObserver = mock(StreamObserver.class);

        var request = GreetingRequest.newBuilder()
                .setLanguage(language)
                .build();

        service.findOneHelloMessage(request, streamObserver);

        var responseArgumentCaptor = ArgumentCaptor.forClass(GreetingResponse.class);
        verify(streamObserver).onNext(responseArgumentCaptor.capture());
        var response = responseArgumentCaptor.getValue();

        assertEquals(expectedResponse, response.getMessage());

    }

    static Stream<Arguments> generateLocalizedMessage() {
        return Stream.of(
                Arguments.of(Language.PORTUGUESE, "Olá"),
                Arguments.of(Language.SPANISH, "Hola"),
                Arguments.of(Language.ENGLISH, "Hello there")
        );
    }

    @Test
    void whenReceiveAnIdThenReturnTheMessages() {
        var streamObserver = mock(StreamObserver.class);

        var request = GreetingId.newBuilder()
                .setId(42)
                .build();

        service.findHelloMessagesById(request, streamObserver);

        var responseArgumentCaptor = ArgumentCaptor.forClass(GreetingResponseList.class);
        verify(streamObserver).onNext(responseArgumentCaptor.capture());
        var response = responseArgumentCaptor.getValue();

        assertThat(response.getResponseList())
                .extracting("message")
                .contains("First", "Second", "Third");
    }

    @Test
    void whenRequestedThenReturnAMessage() {
        var streamObserver = mock(StreamObserver.class);

        service.sayHello(Empty.newBuilder().build(), streamObserver);

        var responseArgumentCaptor = ArgumentCaptor.forClass(GreetingResponse.class);
        verify(streamObserver).onNext(responseArgumentCaptor.capture());
        var response = responseArgumentCaptor.getValue();

        assertEquals("Hello there!", response.getMessage());
    }

    @Test
    void whenReceiveAnIdDeleteMessage() {
        var streamObserver = mock(StreamObserver.class);

        var request = GreetingId.newBuilder()
                .setId(42)
                .build();

        service.deleteHelloMessage(request, streamObserver);

        var responseArgumentCaptor = ArgumentCaptor.forClass(Empty.class);
        verify(streamObserver).onNext(responseArgumentCaptor.capture());
        var response = responseArgumentCaptor.getValue();

        assertTrue(response.getAllFields().isEmpty());
    }

    @Nested
    class GreetingGrpcTest {
        private final GrpcCleanupRule grpcCleanup = new GrpcCleanupRule();

        @Test
        void whenRequestedThenReturnAMessage() throws Exception {
            // Generate a unique in-process server name.
            String serverName = InProcessServerBuilder.generateName();

            // Create a server, add service, start, and register for automatic graceful shutdown.
            grpcCleanup.register(InProcessServerBuilder
                    .forName(serverName).directExecutor().addService(new GreetingMessageService()).build().start());

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
        void whenReceiveAnIdThenReturnTheMessages() throws Exception {
            // Generate a unique in-process server name.
            String serverName = InProcessServerBuilder.generateName();

            // Create a server, add service, start, and register for automatic graceful shutdown.
            grpcCleanup.register(InProcessServerBuilder
                    .forName(serverName).directExecutor().addService(new GreetingMessageService()).build().start());

            GreetingServiceGrpc.GreetingServiceBlockingStub blockingStub = GreetingServiceGrpc.newBlockingStub(
                    // Create a client channel and register for automatic graceful shutdown.
                    grpcCleanup.register(InProcessChannelBuilder.forName(serverName).directExecutor().build()));

            GreetingResponseList response = blockingStub.findHelloMessagesById(GreetingId.newBuilder().setId(42).build());

            assertEquals(3, response.getResponseList().size());
        }
    }
}
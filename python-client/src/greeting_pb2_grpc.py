# Generated by the gRPC Python protocol compiler plugin. DO NOT EDIT!
"""Client and server classes corresponding to protobuf-defined services."""
import grpc

from google.protobuf import empty_pb2 as google_dot_protobuf_dot_empty__pb2
import greeting_pb2 as greeting__pb2


class GreetingServiceStub(object):
    """Missing associated documentation comment in .proto file."""

    def __init__(self, channel):
        """Constructor.

        Args:
            channel: A grpc.Channel.
        """
        self.SayHello = channel.unary_unary(
                '/example.GreetingService/SayHello',
                request_serializer=google_dot_protobuf_dot_empty__pb2.Empty.SerializeToString,
                response_deserializer=greeting__pb2.GreetingResponse.FromString,
                )
        self.DeleteHelloMessage = channel.unary_unary(
                '/example.GreetingService/DeleteHelloMessage',
                request_serializer=greeting__pb2.GreetingId.SerializeToString,
                response_deserializer=google_dot_protobuf_dot_empty__pb2.Empty.FromString,
                )
        self.FindOneHelloMessage = channel.unary_unary(
                '/example.GreetingService/FindOneHelloMessage',
                request_serializer=greeting__pb2.GreetingRequest.SerializeToString,
                response_deserializer=greeting__pb2.GreetingResponse.FromString,
                )
        self.FindHelloMessagesById = channel.unary_unary(
                '/example.GreetingService/FindHelloMessagesById',
                request_serializer=greeting__pb2.GreetingId.SerializeToString,
                response_deserializer=greeting__pb2.GreetingResponseList.FromString,
                )


class GreetingServiceServicer(object):
    """Missing associated documentation comment in .proto file."""

    def SayHello(self, request, context):
        """Missing associated documentation comment in .proto file."""
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')

    def DeleteHelloMessage(self, request, context):
        """Missing associated documentation comment in .proto file."""
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')

    def FindOneHelloMessage(self, request, context):
        """Missing associated documentation comment in .proto file."""
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')

    def FindHelloMessagesById(self, request, context):
        """Missing associated documentation comment in .proto file."""
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')


def add_GreetingServiceServicer_to_server(servicer, server):
    rpc_method_handlers = {
            'SayHello': grpc.unary_unary_rpc_method_handler(
                    servicer.SayHello,
                    request_deserializer=google_dot_protobuf_dot_empty__pb2.Empty.FromString,
                    response_serializer=greeting__pb2.GreetingResponse.SerializeToString,
            ),
            'DeleteHelloMessage': grpc.unary_unary_rpc_method_handler(
                    servicer.DeleteHelloMessage,
                    request_deserializer=greeting__pb2.GreetingId.FromString,
                    response_serializer=google_dot_protobuf_dot_empty__pb2.Empty.SerializeToString,
            ),
            'FindOneHelloMessage': grpc.unary_unary_rpc_method_handler(
                    servicer.FindOneHelloMessage,
                    request_deserializer=greeting__pb2.GreetingRequest.FromString,
                    response_serializer=greeting__pb2.GreetingResponse.SerializeToString,
            ),
            'FindHelloMessagesById': grpc.unary_unary_rpc_method_handler(
                    servicer.FindHelloMessagesById,
                    request_deserializer=greeting__pb2.GreetingId.FromString,
                    response_serializer=greeting__pb2.GreetingResponseList.SerializeToString,
            ),
    }
    generic_handler = grpc.method_handlers_generic_handler(
            'example.GreetingService', rpc_method_handlers)
    server.add_generic_rpc_handlers((generic_handler,))


 # This class is part of an EXPERIMENTAL API.
class GreetingService(object):
    """Missing associated documentation comment in .proto file."""

    @staticmethod
    def SayHello(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            insecure=False,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_unary(request, target, '/example.GreetingService/SayHello',
            google_dot_protobuf_dot_empty__pb2.Empty.SerializeToString,
            greeting__pb2.GreetingResponse.FromString,
            options, channel_credentials,
            insecure, call_credentials, compression, wait_for_ready, timeout, metadata)

    @staticmethod
    def DeleteHelloMessage(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            insecure=False,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_unary(request, target, '/example.GreetingService/DeleteHelloMessage',
            greeting__pb2.GreetingId.SerializeToString,
            google_dot_protobuf_dot_empty__pb2.Empty.FromString,
            options, channel_credentials,
            insecure, call_credentials, compression, wait_for_ready, timeout, metadata)

    @staticmethod
    def FindOneHelloMessage(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            insecure=False,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_unary(request, target, '/example.GreetingService/FindOneHelloMessage',
            greeting__pb2.GreetingRequest.SerializeToString,
            greeting__pb2.GreetingResponse.FromString,
            options, channel_credentials,
            insecure, call_credentials, compression, wait_for_ready, timeout, metadata)

    @staticmethod
    def FindHelloMessagesById(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            insecure=False,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_unary(request, target, '/example.GreetingService/FindHelloMessagesById',
            greeting__pb2.GreetingId.SerializeToString,
            greeting__pb2.GreetingResponseList.FromString,
            options, channel_credentials,
            insecure, call_credentials, compression, wait_for_ready, timeout, metadata)

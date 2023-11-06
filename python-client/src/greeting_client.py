import grpc
import greeting_pb2
import greeting_pb2_grpc

def run():
    with grpc.insecure_channel('localhost:50051') as channel:
        stub = greeting_pb2_grpc.GreetingServiceStub(channel)
        empty = greeting_pb2.google_dot_protobuf_dot_empty__pb2.Empty()
        response = stub.SayHello(empty)
        print("Python client received the message.: " + response.message)

        print("Calling by gRPC API by id")
        print(stub.FindHelloMessagesById(greeting_pb2.GreetingId(id=42)))

run()

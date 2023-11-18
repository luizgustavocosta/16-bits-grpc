import grpc from 'tests/k6/net/grpc';
import { check, sleep } from 'k6';

const client = new grpc.Client();
client.load(['definitions'], 'greeting.proto');

export default () => {
  client.connect('localhost:50051', {
    plaintext: true
  });

  const data = {};
  const response = client.invoke('example.GreetingService/SayHello', data);

  check(response, {
    'status is OK': (r) => r && r.status === grpc.StatusOK,
  });

  console.log(JSON.stringify(response.message));

  client.close();
  sleep(1);
};


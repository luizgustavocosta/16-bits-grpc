//main idea from https://blog.lsantos.dev/o-guia-do-grpc-2/
const grpc = require('@grpc/grpc-js')
const protoLoader = require('@grpc/proto-loader')
const path = require('path')

const protoObject = protoLoader.loadSync(path.resolve(__dirname, 'greeting.proto'))
const GreetingDefinition = grpc.loadPackageDefinition(protoObject)
const client = new GreetingDefinition.example.GreetingService('localhost:50051', grpc.credentials.createInsecure())

function callAsync (client, method, parameters) {
  return new Promise((resolve, reject) => {
    client[method](parameters, (err, response) => {
      if (err) reject(err)
      resolve(response)
    })
  })
}

callAsync(client, 'sayHello', {}).then(console.log).catch(console.error)

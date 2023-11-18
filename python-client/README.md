## References
- https://grpc.io/docs/languages/python/quickstart/
## Dependencies
### Generate
```shell
pip freeze > requirements.txt
```

### Install
```shell
pip install -r requirements.txt
pip install grpcio
pip install grpcio-tools
```

## Generate the client

```shell
python3 -m grpc_tools.protoc -I./proto --python_out=./src --pyi_out=./src --grpc_python_out=./src greeting.proto
```

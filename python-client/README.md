## References
- https://grpc.io/docs/languages/python/quickstart/
## Dependencies
### Generate
```shell
pip freeze > requirements.txt
```

## Generate the client
python3 -m grpc_tools.protoc --python_out=. --pyi_out=. --grpc_python_out=. ../proto/greeting.proto


protoc --plugin=protoc-gen-grpc-python=/Users/luizcosta/workspace/git/16-bits/grpc/target/protoc-plugins/protoc-gen-grpc-java-1.36.0-osx-x86_64.exe 
--grpc-python_out=/Users/luizcosta/workspace/16-bits-grpc/python-client/grpc-client --python_out=. greeting.proto

protoc --proto_path=src --python_out=build/gen src/foo.proto src/bar/baz.proto


protoc --proto_path=src --python_out=build/gen . greeting.proto
protoc --proto_path=src --python_out=proto/greeting.proto

protoc --python_out=. --pyi_out=. --grpc_python_out=src/greeting.proto

protoc --plugin=protoc-gen-grpc_python=/Users/luizcosta/Downloads/protobuf-3.7.1/ --proto_path=. --pyi_out=. --python_out=. --grpc_python_out=. greeting.proto

/Users/luizcosta/Downloads/protobuf-3.7.1/

python -m grpc_tools.protoc --python_out=. --grpc_python_out=. greeting.proto

python -m grpc_tools.protoc -I../../protos --python_out=. --pyi_out=. --grpc_python_out=. greeting.proto
python -m grpc_tools.protoc --python_out=. --pyi_out=. --grpc_python_out=. greeting.proto


python -m grpc_tools.protoc --proto_path=. greeting.proto --python_out=generated --pyi_out=src --grpc_python_out=. greeting.proto
python -m grpc_tools.protoc --proto_path=. greeting.proto --python_out=src

Generate the client - Habemus client
```shell
python -m grpc_tools.protoc \
    --proto_path=./proto greeting.proto \
    --python_out=src \
    --pyi_out=src
```


--pyi_out=OUT_DIR           Generate python pyi stub.
--python_out=OUT_DIR        Generate Python source file.




OK
python -m pip install grpcio
python -m pip install grpcio-tools



### Install
```shell
pip install -r requirements.txt
pip install grpcio
pip install grpcio-tools
```

Right
```shell
python3 -m grpc_tools.protoc -I./proto --python_out=. --pyi_out=. --grpc_python_out=. greeting.proto
```


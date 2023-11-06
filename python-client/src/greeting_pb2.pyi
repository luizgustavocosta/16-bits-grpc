from google.protobuf import empty_pb2 as _empty_pb2
from google.protobuf.internal import containers as _containers
from google.protobuf.internal import enum_type_wrapper as _enum_type_wrapper
from google.protobuf import descriptor as _descriptor
from google.protobuf import message as _message
from typing import ClassVar as _ClassVar, Iterable as _Iterable, Mapping as _Mapping, Optional as _Optional, Union as _Union

DESCRIPTOR: _descriptor.FileDescriptor

class Language(int, metaclass=_enum_type_wrapper.EnumTypeWrapper):
    __slots__ = []
    ENGLISH: _ClassVar[Language]
    PORTUGUESE: _ClassVar[Language]
    SPANISH: _ClassVar[Language]
ENGLISH: Language
PORTUGUESE: Language
SPANISH: Language

class GreetingResponse(_message.Message):
    __slots__ = ["message"]
    MESSAGE_FIELD_NUMBER: _ClassVar[int]
    message: str
    def __init__(self, message: _Optional[str] = ...) -> None: ...

class GreetingRequest(_message.Message):
    __slots__ = ["name", "nickName", "dob", "language"]
    NAME_FIELD_NUMBER: _ClassVar[int]
    NICKNAME_FIELD_NUMBER: _ClassVar[int]
    DOB_FIELD_NUMBER: _ClassVar[int]
    LANGUAGE_FIELD_NUMBER: _ClassVar[int]
    name: str
    nickName: str
    dob: str
    language: Language
    def __init__(self, name: _Optional[str] = ..., nickName: _Optional[str] = ..., dob: _Optional[str] = ..., language: _Optional[_Union[Language, str]] = ...) -> None: ...

class GreetingId(_message.Message):
    __slots__ = ["id"]
    ID_FIELD_NUMBER: _ClassVar[int]
    id: int
    def __init__(self, id: _Optional[int] = ...) -> None: ...

class GreetingResponseList(_message.Message):
    __slots__ = ["response"]
    RESPONSE_FIELD_NUMBER: _ClassVar[int]
    response: _containers.RepeatedCompositeFieldContainer[GreetingResponse]
    def __init__(self, response: _Optional[_Iterable[_Union[GreetingResponse, _Mapping]]] = ...) -> None: ...

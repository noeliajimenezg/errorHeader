## Micronaut 4.2.0

Project to show the error due to large header/message on grpc

Current behavior

grpcurl -import-path ~/Downloads/errorHeader-master/src/main/proto -proto ~/Downloads/errorHeader-master/src/main/proto/ErrorHeader.proto -vv -plaintext -d '{"name":"toto"}' localhost:50054 com.example.ErrorHeaderService/sendWithError

See file [grpcurl_resultSendWithError.json](grpcurl_resultSendWithError.json)

-max-msg-sz 999999999 does not change the result

grpcurl -import-path ~/Downloads/errorHeader-master/src/main/proto -proto ~/Downloads/errorHeader-master/src/main/proto/ErrorHeader.proto -vv -plaintext -d '{"name":"toto"}' localhost:50054 com.example.ErrorHeaderService/sendOk

See file [grpcurl_resultSendOk.json](grpcurl_resultSendOk.json)


On Kreya client, the error message is:

Error starting gRPC call. HttpRequestException: An error occurred while sending the request. IOException: The request was aborted. HPackDecodingException: The HTTP headers length exceeded the set limit of 65536 bytes.



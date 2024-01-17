package com.example;

import io.micronaut.runtime.Micronaut;

/*
grpcurl -import-path ~/Downloads/errorHeader-master/src/main/proto -proto ~/Downloads/errorHeader-master/src/main/proto/ErrorHeader.proto -vv -plaintext -d '{"name":"toto"}' localhost:50054 com.example.ErrorHeaderService/sendWithError
grpcurl -import-path ~/Downloads/errorHeader-master/src/main/proto -proto ~/Downloads/errorHeader-master/src/main/proto/ErrorHeader.proto -vv -plaintext -d '{"name":"toto"}' localhost:50054 com.example.ErrorHeaderService/sendOk
 */
public class Application {

    public static void main(String[] args) {
        Micronaut.run(Application.class, args);
    }
}

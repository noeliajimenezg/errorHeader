package com.example;

import com.google.protobuf.Any;
import io.grpc.Status;
import io.grpc.protobuf.StatusProto;
import io.grpc.stub.StreamObserver;
import io.micronaut.context.ApplicationContext;
import io.micronaut.grpc.server.GrpcEmbeddedServer;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Base64;

import static com.example.ExceptionData.MESSAGE;

@Singleton
public class ErrorHeaderService extends ErrorHeaderServiceGrpc.ErrorHeaderServiceImplBase {

    @Inject
    ApplicationContext appContext;
    @Override
    public void sendWithError(ErrorHeaderRequest request, StreamObserver<ErrorHeaderReply> responseObserver) {
        try {
            Object npe = null;
            npe.toString();
//            responseObserver.onNext(
//                    ErrorHeaderReply.newBuilder()
//                            .setMessage("testing")
//                            .build());
//            responseObserver.onCompleted();
        } catch (Exception ex) {

            responseObserver.onError(
                    StatusProto.toStatusRuntimeException(convert(ex, ex.getMessage(), true)));
        }
    }

    @Override
    public void sendOk(ErrorHeaderRequest request, StreamObserver<ErrorHeaderReply> responseObserver) {
        try {
            Object npe = null;
            npe.toString();
//            responseObserver.onNext(
//                    ErrorHeaderReply.newBuilder()
//                            .setMessage("testing")
//                            .build());
//            responseObserver.onCompleted();
        } catch (Exception ex) {
            responseObserver.onError(
                    StatusProto.toStatusRuntimeException(convert(ex, ex.getMessage(), false)));
        }
    }

    private Exception buildBigException(){
        return new Exception(MESSAGE, new Throwable(MESSAGE));
    }

     com.google.rpc.Status convert(Exception exception, String message, boolean forceError) {
         GrpcEmbeddedServer grpcEmbeddedServer = appContext.getBean(GrpcEmbeddedServer.class);
         var messageValue = forceError ? MESSAGE : MESSAGE.substring(0, MESSAGE.length() - 7600);
        return com.google.rpc.Status.newBuilder()
                .setCode(Status.Code.INVALID_ARGUMENT.value())
                .setMessage(messageValue)
                .addDetails(convertToGrpcException(exception))
                .build();
    }

    private Any convertToGrpcException(Exception exception) {
        // default exception
        Exception bigException = buildBigException();
        return Any.pack(
                GenericException.newBuilder()
                        .setMessage(((Exception) exception).getMessage().concat(bigException.getMessage()))
                        .setCause(toString(((Exception) exception).getCause()).concat(toString(bigException.getCause())))
                        .setSerializedException(toString(((Exception) exception).getCause()).concat(toString(bigException.getCause())))
                        .setDataField("toto")
                        .setSerializedException(Base64.getEncoder().encodeToString(MESSAGE.getBytes()).concat(Base64.getEncoder().encodeToString(toString(exception.getCause()).getBytes())))
                        .build());
    }

    private static String toString(Throwable th) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        if (th != null) {
            th.printStackTrace(pw);
        }
        return sw.toString();
    }
}

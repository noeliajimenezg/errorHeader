package org.sample;

import com.example.ErrorHeaderReply;
import com.example.ErrorHeaderRequest;
import com.example.ErrorHeaderServiceGrpc;
import com.google.protobuf.Any;
import io.grpc.Grpc;
import io.grpc.InsecureChannelCredentials;
import io.grpc.ManagedChannel;


public class ErrorHeaderClient {

    public static void main(String[] args) {

        // Channel is used by the client to communicate with the server using the domain localhost and port 5003.
        // This is the port where our server is starting.
        ManagedChannel channel =  Grpc.newChannelBuilder("localhost:50054", InsecureChannelCredentials.create())
                        .maxInboundMetadataSize(99999999)
        //                .maxInboundMessageSize(21552922)
                        .build();

        // Auto generated stub class with the constructor wrapping the channel.
        ErrorHeaderServiceGrpc.ErrorHeaderServiceBlockingStub stub = ErrorHeaderServiceGrpc.newBlockingStub(channel);
        ErrorHeaderRequest errorHeaderRequest = ErrorHeaderRequest.newBuilder()
                .setName("toto")
                .build();

        // Start calling the `sendOk` method
        try {
        ErrorHeaderReply errorHeaderReply = stub.sendOk(errorHeaderRequest);
        System.out.println("Response for the first call: " + errorHeaderReply.getSerializedSize());
        }catch (Exception ex){
            ex.printStackTrace();
        }
        // Call to the `sendOk` method is successfully completed. Now calling the `sendWithError` method.
//        // Hold the thread for 10s before calling the other method.
//        System.out.println("### Initiating the second request ###");
//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        try {
            ErrorHeaderReply errorHeaderReplyWithError = stub.sendWithError(errorHeaderRequest);
            System.out.println("Response for the second call: " + errorHeaderReplyWithError.getSerializedSize());
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}

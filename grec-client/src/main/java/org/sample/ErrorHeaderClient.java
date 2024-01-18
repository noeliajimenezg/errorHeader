package org.sample;

import com.example.ErrorHeaderReply;
import com.example.ErrorHeaderRequest;
import com.example.ErrorHeaderServiceGrpc;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import io.grpc.*;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;

import java.io.*;
import java.util.Map;
import java.util.Objects;

import static java.nio.charset.StandardCharsets.UTF_8;


public class ErrorHeaderClient {

    public static void main(String[] args) {

        // Channel is used by the client to communicate with the server using the domain localhost and port 5003.
        // This is the port where our server is starting.

        /**
           plaintext config
           399990 make works both metadata, 339990 make only work the sendOk
         */
//        ManagedChannel channel =  Grpc.newChannelBuilder("localhost:50054",  getChannelCredentialsInsecure())
//                        .maxInboundMetadataSize(339990)
////                        .maxInboundMessageSize(9999999)
//                        .build();

        /**
         ssl config
         */
        ManagedChannel channel =  Grpc.newChannelBuilder("localhost:50054",  getChannelCredentialsTlsInsecure())
                .maxInboundMetadataSize(999990)
                .maxInboundMessageSize(20194304)
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

    private static ChannelCredentials getChannelCredentialsInsecure(){
        return InsecureChannelCredentials.create();
    }
    private static ChannelCredentials getChannelCredentialsTlsInsecure(){
        return TlsChannelCredentials.newBuilder()
                .trustManager(InsecureTrustManagerFactory.INSTANCE.getTrustManagers())
                .build();
    }

    public static ChannelCredentials getChannelCredentialsTls() {
        try {

            return TlsChannelCredentials.newBuilder().trustManager(getTrustedRootCertificate()).build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static InputStream getTrustedRootCertificate() throws FileNotFoundException {
        return getResourceAsStream("ca-cert.pem");
    }
    public static Map<String, ?> getServiceConfig(String filename) {
        return new Gson()
                .fromJson(
                        new JsonReader(
                                new InputStreamReader(
                                        Objects.requireNonNull(getResourceAsStream(filename)), UTF_8)),
                        Map.class);
    }

    public static InputStream getResourceAsStream(String filename) {
        return ErrorHeaderClient.class.getClassLoader().getResourceAsStream(filename);
    }
}

package com.sjy.grpc;


import com.sjy.grpc.helloworld.GreeterGrpc;
import com.sjy.grpc.helloworld.HelloReply;
import com.sjy.grpc.helloworld.HelloRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.concurrent.TimeUnit;

public class GRPCApplication {

    public static void main(String[] args) throws InterruptedException {
        ManagedChannel channel = ManagedChannelBuilder.forTarget("localhost:50052").usePlaintext().build();
        GreeterGrpc.GreeterBlockingStub stub = GreeterGrpc.newBlockingStub(channel);

        HelloRequest request = HelloRequest.newBuilder().setName("sjy").build();
        HelloReply  response = stub.sayHello(request);
        System.out.println(response.getMessage());
        channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
    }
}

package com.pm.billingservice.grpc.BillingGrpcService;

import billing.BillingRequest;
import billing.BillingResponse;
import billing.BillingServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GrpcService
public class BillingGrpcService extends BillingServiceGrpc.BillingServiceImplBase {
    private static final Logger log = LoggerFactory.getLogger(BillingGrpcService.class);

    @Override
    public void createBillingAccount(BillingRequest billingRequest,
                                     StreamObserver<BillingResponse> responseObserver) {
        log.info("createBillingAccount request received {}", billingRequest.toString());

        // business logic - e.g. save to db, perform calculates etc

        BillingResponse response = BillingResponse.newBuilder()
                .setAccountId("12345").setStatus("ACTIVE").build();

        // send rep from out grpc service
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}

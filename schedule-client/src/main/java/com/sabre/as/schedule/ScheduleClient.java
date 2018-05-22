package com.sabre.as.schedule;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

/**
 * Created by sg0501095 on 5/1/18.
 */
public class ScheduleClient {

    public static void main(String[] args) {
//        ManagedChannel channel = ManagedChannelBuilder.forAddress("scheduleservice-schedule-service.192.168.64.16.nip.io", 8080)
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8080)
                .usePlaintext()
                .build();

        ScheduleServiceGrpc.ScheduleServiceBlockingStub stub
                = ScheduleServiceGrpc.newBlockingStub(channel);

        ProtoFlightLegs flightLegResponse = stub.getFlightLegsByDepartureAirport(ProtoQueryByAirlineAndAirport.newBuilder().setAirportCode("DFW").setAirlineCode("9W").build());
        flightLegResponse.getFlightLegsList().stream().forEach(protoFlightLeg -> System.out.println("protoFlightLeg = " + protoFlightLeg));

        channel.shutdown();
    }
}

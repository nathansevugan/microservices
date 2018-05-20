package com.sabre.as.schedule;

import com.sabre.as.flight.schedule.service.ProtoFlightLegs;
import com.sabre.as.flight.schedule.service.ProtoQueryByAirlineAndAirport;
import com.sabre.as.flight.schedule.service.ScheduleServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

/**
 * Created by sg0501095 on 5/1/18.
 */
public class ScheduleClient {

    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090)
                .usePlaintext()
                .build();

        ScheduleServiceGrpc.ScheduleServiceBlockingStub stub
                = ScheduleServiceGrpc.newBlockingStub(channel);

        ProtoFlightLegs flightLegResponse = stub.getFlightLegsByDepartureAirport(ProtoQueryByAirlineAndAirport.newBuilder().setAirportCode("DFW").setAirlineCode("9W").build());
        flightLegResponse.getFlightLegsList().stream().forEach(protoFlightLeg -> System.out.println("protoFlightLeg = " + protoFlightLeg));

        channel.shutdown();
    }
}

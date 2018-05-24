package com.sabre.as.schedule;

import com.sabre.as.flight.schedule.service.*;
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

//        readFlightLegs(channel);

        //        FlightLeg flightLeg = FlightLeg.FlightLegBuilder.newBuilder()
//                .setId(Id.IdBuilder.newBuilder().setLegId(1).setVersion(1).build())
//                .setStatus("SCH")
//                .setRegistration("5BDAQ")
//                .setServiceType("J")
//                .setFlightTimes(FlightTimes.FlightTimesBuilder.newBuilder()
//                        .setScheduleDepartureTime(ISODateTimeFormat.dateTimeParser().parseDateTime("2018-05-01T07:00:00-05:00"))
//                        .setLatestDepartureTime(ISODateTimeFormat.dateTimeParser().parseDateTime("2018-05-01T07:00:00-05:00"))
//                        .setScheduleArrivalTime(ISODateTimeFormat.dateTimeParser().parseDateTime("2018-05-01T09:00:00-05:00"))
//                        .setLatestArrivalTime(ISODateTimeFormat.dateTimeParser().parseDateTime("2018-05-01T09:00:00-05:00"))
//                        .build())
//                .setFlightLegId(FlightLegId.FlightLegIdBuilder.newBuilder()
//                        .setFlightNumber("1")
//                        .setAirlineCode("AA")
//                        .setArrivalAirport("LAX")
//                        .setDepartureAirport("DFW")
//                        .setFlightDate(
//                                ISODateTimeFormat.dateTimeParser().parseDateTime("2018-05-01T07:00:00-05:00"))
//                        .build()).build();
//        mongoTemplate.insert(flightLeg);
        updateFlightLeg(channel);
        channel.shutdown();
    }

    private static void updateFlightLeg(ManagedChannel channel) {
        ScheduleServiceGrpc.ScheduleServiceBlockingStub stub
                = ScheduleServiceGrpc.newBlockingStub(channel);

        ProtoFlightLeg flightLeg = stub.getFlightLegById(ProtoId.newBuilder().setId(1).setVersion(1).build());
        ProtoFlightLeg canceledFlightLeg = ProtoFlightLeg.newBuilder(flightLeg).setStatus("CNL").build();
        stub.updateFlightLeg(canceledFlightLeg);
    }

    private static void readFlightLegs(ManagedChannel channel) {
        ScheduleServiceGrpc.ScheduleServiceBlockingStub stub
                = ScheduleServiceGrpc.newBlockingStub(channel);

        ProtoFlightLegs flightLegResponse = stub.getFlightLegsByDepartureAirport(ProtoQueryByAirlineAndAirport.newBuilder().setAirportCode("DFW").setAirlineCode("9W").build());
        flightLegResponse.getFlightLegsList().stream().forEach(protoFlightLeg -> System.out.println("protoFlightLeg = " + protoFlightLeg));
    }
}

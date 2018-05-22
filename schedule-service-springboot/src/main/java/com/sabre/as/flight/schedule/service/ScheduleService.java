package com.sabre.as.flight.schedule.service;

import com.sabre.as.flight.schedule.domain.FlightLeg;
import com.sabre.as.flight.schedule.domain.FlightLegId;
import com.sabre.as.flight.schedule.domain.FlightTimes;
import com.sabre.as.flight.schedule.repositories.FlightLegRepository;
import io.grpc.stub.StreamObserver;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.lognet.springboot.grpc.GRpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Created by sg0501095 on 4/30/18.
 */
@GRpcService
public class ScheduleService extends ScheduleServiceGrpc.ScheduleServiceImplBase {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleService.class);
    @Autowired
    private FlightLegRepository flightLegRepository;


    @Override
    public void getFlightLegsByDepartureAirport(ProtoQueryByAirlineAndAirport request, StreamObserver<ProtoFlightLegs> responseObserver) {
        logger.info("retrieving flights by airport code {} and airline code {}", request.getAirlineCode(), request.getAirportCode());
        Collection<FlightLeg> flightLegByAirport = flightLegRepository.findFlightLegByAirlineAndAirport(request.getAirlineCode(), request.getAirportCode());

        logger.info("number of flights retrieved by {} and  {} is {}", request.getAirlineCode(), request.getAirlineCode(), flightLegByAirport.size());

        List<ProtoFlightLeg> flightLegs = flightLegByAirport.stream().map(flightLeg -> {
            FlightLegId flightLegId = flightLeg.getFlightLegId();
            DateTimeFormatter isoDateTimeFormatter = ISODateTimeFormat.dateTime();
            ProtoFlightLegId protoFlightLegId = ProtoFlightLegId.newBuilder()
                    .setFlightNumber(flightLegId.getFlightNumber())
                    .setDepartureAirport(flightLegId.getDepartureAirport())
                    .setArrivalAirport(flightLegId.getArrivalAirport())
                    .setFlightDate(flightLegId.getFlightDate().toString(isoDateTimeFormatter))
                    .setAirlineCode(flightLegId.getAirlineCode())
                    .build();

            FlightTimes flightTimes = flightLeg.getFlightTimes();

            ProtoFlightTimes protoFlightTimes = ProtoFlightTimes.newBuilder()
                    .setScheduleDepartureTime(flightTimes.getScheduleDepartureTime().toString(isoDateTimeFormatter))
                    .setScheduleArrivalTime(flightTimes.getScheduleArrivalTime().toString(isoDateTimeFormatter))
                    .setLatestDepartureTime(flightTimes.getLatestDepartureTime().toString(isoDateTimeFormatter))
                    .setLatestArrivalTime(flightTimes.getLatestArrivalTime().toString(isoDateTimeFormatter)).build();


            ProtoFlightLeg protoFlightLeg = ProtoFlightLeg.newBuilder()
                    .setTechnicalId(ProtoTechnicalId.newBuilder().setId(flightLeg.getTechnicalId().getLegId()).
                            setVersion(flightLeg.getTechnicalId().getVersion()).build())
                    .setFlightLegId(protoFlightLegId)
                    .setRegistration(flightLeg.getRegistration())
                    .setServiceType(flightLeg.getServiceType())
                    .setStatus(flightLeg.getStatus())
                    .setFlightTimes(protoFlightTimes)
                    .build();
            return protoFlightLeg;
        }).collect(toList());


        responseObserver.onNext(ProtoFlightLegs.newBuilder().addAllFlightLegs(flightLegs).build());
        responseObserver.onCompleted();
    }

    @Override
    public void updateFlightLeg(ProtoFlightLeg flightLeg,
                                io.grpc.stub.StreamObserver<ProtoStatus> responseObserver) {
//        FlightLeg

        responseObserver.onNext(ProtoStatus.newBuilder().setStatus(true).build());
        responseObserver.onCompleted();
    }
}

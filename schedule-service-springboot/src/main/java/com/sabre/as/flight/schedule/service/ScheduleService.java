package com.sabre.as.flight.schedule.service;

import com.sabre.as.flight.schedule.domain.FlightLeg;
import com.sabre.as.flight.schedule.repositories.FlightLegRepository;
import com.sabre.as.flight.schedule.service.mappers.FlightLegMapperFunction;
import com.sabre.as.flight.schedule.service.mappers.IdMapperFunction;
import com.sabre.as.flight.schedule.service.mappers.ProtoFlightLegMapperFunction;
import io.grpc.stub.StreamObserver;
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
        Collection<FlightLeg> flightLegByAirport = flightLegRepository.findFlightLegsByAirlineAndAirport(request.getAirlineCode(), request.getAirportCode());

        logger.info("number of flights retrieved by {} and  {} is {}", request.getAirlineCode(), request.getAirlineCode(), flightLegByAirport.size());

        List<ProtoFlightLeg> flightLegs = flightLegByAirport.stream()
                .map(flightLeg -> ProtoFlightLegMapperFunction.map(flightLeg)).collect(toList());
        responseObserver.onNext(ProtoFlightLegs.newBuilder().addAllFlightLegs(flightLegs).build());
        responseObserver.onCompleted();
    }

    @Override
    public void updateFlightLeg(ProtoFlightLeg protoFlightLeg,
                                io.grpc.stub.StreamObserver<ProtoStatus> responseObserver) {
        FlightLeg flightLeg = new FlightLegMapperFunction().apply(protoFlightLeg);
        boolean success = true;
        try{
            flightLegRepository.update(flightLeg);
        }catch (Exception e){
            logger.error("Failed to update flight leg" + flightLeg.getId());
            success = false;
        }

        responseObserver.onNext(ProtoStatus.newBuilder().setStatus(success).build());
        responseObserver.onCompleted();
    }

    @Override
    public void getFlightLegById(com.sabre.as.flight.schedule.service.ProtoId protoId,
                              io.grpc.stub.StreamObserver<com.sabre.as.flight.schedule.service.ProtoFlightLeg> responseObserver) {
        FlightLeg flightLeg = flightLegRepository.findFlightLegById(IdMapperFunction.map(protoId));
        responseObserver.onNext(ProtoFlightLegMapperFunction.map(flightLeg));
        responseObserver.onCompleted();
    }

}

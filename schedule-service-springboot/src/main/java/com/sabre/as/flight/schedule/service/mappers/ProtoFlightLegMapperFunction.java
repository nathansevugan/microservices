package com.sabre.as.flight.schedule.service.mappers;

import com.sabre.as.flight.schedule.domain.FlightLeg;
import com.sabre.as.flight.schedule.domain.FlightLegId;
import com.sabre.as.flight.schedule.domain.FlightTimes;
import com.sabre.as.flight.schedule.service.ProtoFlightLeg;
import com.sabre.as.flight.schedule.service.ProtoFlightLegId;
import com.sabre.as.flight.schedule.service.ProtoFlightTimes;
import com.sabre.as.flight.schedule.service.ProtoLegId;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.util.function.Function;

/**
 * Created by sg0501095 on 5/22/18.
 */
public class ProtoFlightLegMapperFunction implements Function<FlightLeg, ProtoFlightLeg> {

    private ProtoFlightLegId map(FlightLegId flightLegId) {
        DateTimeFormatter isoDateTimeFormatter = ISODateTimeFormat.dateTime();
        return ProtoFlightLegId.newBuilder()
                .setFlightNumber(flightLegId.getFlightNumber())
                .setDepartureAirport(flightLegId.getDepartureAirport())
                .setArrivalAirport(flightLegId.getArrivalAirport())
                .setFlightDate(flightLegId.getFlightDate().toString(isoDateTimeFormatter))
                .setAirlineCode(flightLegId.getAirlineCode())
                .build();
    }

    private ProtoFlightTimes map(FlightTimes flightTimes) {
        DateTimeFormatter isoDateTimeFormatter = ISODateTimeFormat.dateTime();
        return ProtoFlightTimes.newBuilder()
                .setScheduleDepartureTime(flightTimes.getScheduleDepartureTime().toString(isoDateTimeFormatter))
                .setScheduleArrivalTime(flightTimes.getScheduleArrivalTime().toString(isoDateTimeFormatter))
                .setLatestDepartureTime(flightTimes.getLatestDepartureTime().toString(isoDateTimeFormatter))
                .setLatestArrivalTime(flightTimes.getLatestArrivalTime().toString(isoDateTimeFormatter)).build();
    }

    @Override
    public ProtoFlightLeg apply(FlightLeg flightLeg) {
        return ProtoFlightLeg.newBuilder()
                .setLegId(ProtoLegId.newBuilder().setId(flightLeg.getLegId().getId()).
                        setVersion(flightLeg.getLegId().getVersion()).build())
                .setFlightLegId(map(flightLeg.getFlightLegId()))
                .setRegistration(flightLeg.getRegistration())
                .setServiceType(flightLeg.getServiceType())
                .setStatus(flightLeg.getStatus())
                .setFlightTimes(map(flightLeg.getFlightTimes()))
                .build();
    }

    public static ProtoFlightLeg map(FlightLeg flightLeg){
        return new ProtoFlightLegMapperFunction().apply(flightLeg);
    }
}

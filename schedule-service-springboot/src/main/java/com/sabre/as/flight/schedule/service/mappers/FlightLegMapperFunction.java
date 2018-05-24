package com.sabre.as.flight.schedule.service.mappers;

import com.sabre.as.flight.schedule.domain.FlightLeg;
import com.sabre.as.flight.schedule.domain.FlightLegId;
import com.sabre.as.flight.schedule.domain.FlightTimes;
import com.sabre.as.flight.schedule.domain.FlightTimes.FlightTimesBuilder;
import com.sabre.as.flight.schedule.service.ProtoFlightLeg;
import com.sabre.as.flight.schedule.service.ProtoFlightLegId;
import com.sabre.as.flight.schedule.service.ProtoFlightTimes;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.util.function.Function;

/**
 * Created by sg0501095 on 5/22/18.
 */
public class FlightLegMapperFunction implements Function<ProtoFlightLeg, FlightLeg> {

    public static final DateTimeFormatter ISO_DATE_TIME_FORMATTER = ISODateTimeFormat.dateTime();

    public static FlightLegId map(ProtoFlightLegId flightLegId) {
        return FlightLegId.FlightLegIdBuilder.newBuilder()
                .setAirlineCode(flightLegId.getAirlineCode())
                .setArrivalAirport(flightLegId.getArrivalAirport())
                .setDepartureAirport(flightLegId.getDepartureAirport())
                .setFlightNumber(flightLegId.getDepartureAirport())
                .setFlightDate(ISO_DATE_TIME_FORMATTER.parseDateTime(flightLegId.getFlightDate())).build();
    }

    @Override
    public FlightLeg apply(ProtoFlightLeg protoFlightLeg) {

        return FlightLeg.FlightLegBuilder.newBuilder()
                .setFlightLegId(map(protoFlightLeg.getFlightLegId()))
                .setServiceType(protoFlightLeg.getServiceType())
                .setStatus(protoFlightLeg.getStatus())
                .setLegId(IdMapperFunction.map(protoFlightLeg.getLegId()))
        .setRegistration(protoFlightLeg.getRegistration())
        .setFlightTimes(map(protoFlightLeg.getFlightTimes())).build();
    }

    private FlightTimes map(ProtoFlightTimes flightTimes) {
        return new FlightTimesBuilder()
                .setScheduleDepartureTime(ISO_DATE_TIME_FORMATTER.parseDateTime(flightTimes.getScheduleDepartureTime()))
                .setScheduleArrivalTime(ISO_DATE_TIME_FORMATTER.parseDateTime(flightTimes.getScheduleArrivalTime()))
                .setLatestDepartureTime(ISO_DATE_TIME_FORMATTER.parseDateTime(flightTimes.getLatestDepartureTime()))
                .setLatestArrivalTime(ISO_DATE_TIME_FORMATTER.parseDateTime(flightTimes.getLatestArrivalTime()))
                .build();
    }

}

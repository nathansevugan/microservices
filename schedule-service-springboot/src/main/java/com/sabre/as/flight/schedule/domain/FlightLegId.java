package com.sabre.as.flight.schedule.domain;

import org.joda.time.DateTime;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Created by sg0501095 on 5/8/18.
 */
public class FlightLegId {
    @Field
    private String flightNumber;
    @Field
    private String departureAirport;
    @Field
    private String arrivalAirport;
    @Field
    private String airlineCode;
    @Field
    private DateTime flightDate;

    private FlightLegId(){

    }

    public static FlightLegIdBuilder newBuilder(){
        return new FlightLegIdBuilder();
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getDepartureAirport() {
        return departureAirport;
    }

    public String getArrivalAirport() {
        return arrivalAirport;
    }

    public String getAirlineCode() {
        return airlineCode;
    }

    public DateTime getFlightDate() {
        return flightDate;
    }

    public static class FlightLegIdBuilder{
        private FlightLegId flightLegId;

        FlightLegIdBuilder(){
            this.flightLegId = new FlightLegId();
        }

        public FlightLegIdBuilder setFlightNumber(String flightNumber){
            flightLegId.flightNumber = flightNumber;
            return this;
        }
       public FlightLegIdBuilder setDepartureAirport(String departureAirport){
            flightLegId.departureAirport = departureAirport;
            return this;
        }
        public FlightLegIdBuilder setArrivalAirport(String arrivalAirport){
            flightLegId.arrivalAirport = arrivalAirport;
            return this;
        }

        public FlightLegIdBuilder setAirlineCode(String airlineCode){
            flightLegId.airlineCode = airlineCode;
            return this;
        }
        public FlightLegIdBuilder setFlightDate(DateTime flightDate){
            flightLegId.flightDate = flightDate;
            return this;
        }

        public FlightLegId build(){
            return flightLegId;
        }
    }

}

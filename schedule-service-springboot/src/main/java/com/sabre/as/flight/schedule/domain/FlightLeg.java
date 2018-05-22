package com.sabre.as.flight.schedule.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Created by sg0501095 on 5/7/18.
 */

@Document(collection = "flight_leg")
public class FlightLeg {
    @Id
    private TechnicalId technicalId;

    @Field
    private FlightLegId flightLegId;

    @Field
    private FlightTimes flightTimes;

    @Field
    private String registration;

    @Field
    private String status;

    @Field
    private String serviceType;


    public FlightLegId getFlightLegId() {
        return flightLegId;
    }


    public String getRegistration() {
        return registration;
    }

    public String getStatus() {
        return status;
    }

    public String getServiceType() {
        return serviceType;
    }

    public FlightTimes getFlightTimes() {
        return flightTimes;
    }

    public TechnicalId getTechnicalId() {
        return technicalId;
    }

    public static class FlightLegBuilder{
        private FlightLeg flightLeg;
        public FlightLegBuilder(){
            this.flightLeg = new FlightLeg();
        }
        public static FlightLegBuilder newBuilder(){
            return new FlightLegBuilder();
        }
        public FlightLegBuilder setFlightLegId(FlightLegId flightLegId){
            flightLeg.flightLegId = flightLegId;
            return this;
        }

        public FlightLegBuilder setStatus(String status){
            this.flightLeg.status = status;
            return this;
        }

        public FlightLegBuilder setFlightTimes(FlightTimes flightTimes){
            this.flightLeg.flightTimes = flightTimes;
            return this;
        }

        public FlightLegBuilder setServiceType(String serviceType){
            this.flightLeg.serviceType = serviceType;
            return this;
        }

        public FlightLegBuilder setRegistration(String registration){
            this.flightLeg.registration = registration;
            return this;
        }

        public FlightLegBuilder setTechnicalId(TechnicalId technicalId){
            this.flightLeg.technicalId = technicalId;
            return this;
        }

        public FlightLeg build(){
            return flightLeg;
        }
    }
}

package com.sabre.as.flight.schedule.domain;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Created by sg0501095 on 5/7/18.
 */

@Document(collection = "flight_leg")
public class FlightLeg {
    @Id
    private ObjectId id;

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
}

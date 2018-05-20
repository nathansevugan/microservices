package com.sabre.as.flight.schedule.repositories;

import com.sabre.as.flight.schedule.domain.FlightLeg;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

/**
 * Created by sg0501095 on 5/7/18.
 */

public interface FlightLegRepository extends CrudRepository<FlightLeg, Long> {

    @Query("{'flightLegId.airlineCode' : ?0, 'flightLegId.departureAirport' : ?1}")
    Collection<FlightLeg> findFlightLegByAirlineAndAirport(String airlineCode, String airportcode);
}

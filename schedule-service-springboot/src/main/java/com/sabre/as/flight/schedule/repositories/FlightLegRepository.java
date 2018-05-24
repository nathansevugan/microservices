package com.sabre.as.flight.schedule.repositories;

import com.sabre.as.flight.schedule.domain.FlightLeg;
import com.sabre.as.flight.schedule.domain.Id;
import com.sabre.as.flight.schedule.domain.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.Collection;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * Created by sg0501095 on 5/7/18.
 */

@Repository
public class FlightLegRepository {
    private static final Logger logger = LoggerFactory.getLogger(FlightLegRepository.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private VersionRepository versionRepository;

    public void update(FlightLeg leg) {
        Version version = versionRepository.addNewVersion("user update", "test");
        FlightLeg result = mongoTemplate.findAndModify(
                query(where("_id.id").is(leg.getId().getId()).and("_id.version")
                        .is(leg.getId().getVersion())),
                Update.update("shutdown_version", version.getId()),
                options().returnNew(true),
                FlightLeg.class);

        if (result == null) {
            logger.error("Failed to shutdown version: " + leg.getId());
            throw new DBException("Update failed, could not shutdown version: " + leg.getId());
        }
        leg.getId().setVersion(version.getId());
        mongoTemplate.insert(leg);
    }

    public Collection<FlightLeg> findFlightLegsByAirlineAndAirport(String airlineCode, String airportcode) {
        return mongoTemplate.find(
                query(where("flightLegId.airlineCode").is(airlineCode).and("flightLegId.departureAirport").is(airportcode)
                        .and("shutdown_verison").exists(false)), FlightLeg.class);
    }


    public FlightLeg findFlightLegById(Id id) {
        return mongoTemplate.findOne(Query.query(Criteria.where("_id.id").is(id.getId())
                .and("_id.version").is(id.getVersion())), FlightLeg.class);
    }

}

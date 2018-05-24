package com.sabre.as.flight.schedule.service.mappers;

import com.sabre.as.flight.schedule.domain.FlightLeg;
import com.sabre.as.flight.schedule.domain.Id;
import com.sabre.as.flight.schedule.service.ProtoFlightLeg;
import com.sabre.as.flight.schedule.service.ProtoId;

import java.util.function.Function;

/**
 * Created by sg0501095 on 5/24/18.
 */
public class IdMapperFunction  implements Function<ProtoId, Id> {

    public static Id map(ProtoId protoId) {
        return new IdMapperFunction().apply(protoId);
    }

    @Override
    public Id apply(ProtoId protoId) {
        return new Id.IdBuilder()
                .setLegId(protoId.getId())
                .setVersion(protoId.getVersion())
                .build();
    }
}

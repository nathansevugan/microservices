package com.sabre.as.flight.schedule.service.mappers;

import com.sabre.as.flight.schedule.domain.LegId;
import com.sabre.as.flight.schedule.service.ProtoLegId;

import java.util.function.Function;

/**
 * Created by sg0501095 on 5/24/18.
 */
public class IdMapperFunction  implements Function<ProtoLegId, LegId> {

    public static LegId map(ProtoLegId protoLegId) {
        return new IdMapperFunction().apply(protoLegId);
    }

    @Override
    public LegId apply(ProtoLegId protoLegId) {
        return new LegId.IdBuilder()
                .setId(protoLegId.getId())
                .setVersion(protoLegId.getVersion())
                .build();
    }
}

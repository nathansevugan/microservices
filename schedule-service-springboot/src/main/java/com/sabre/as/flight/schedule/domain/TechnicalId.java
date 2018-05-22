package com.sabre.as.flight.schedule.domain;

import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Created by sg0501095 on 5/20/18.
 */
public class TechnicalId {
    @Field("leg_id")
    private long legId;

    @Field
    private int version;


    public long getLegId() {
        return legId;
    }

    public void setLegId(long legId) {
        this.legId = legId;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public static class TechnicalIdBuilder{
        private final TechnicalId technicalId;

        public TechnicalIdBuilder(){
            this.technicalId = new TechnicalId();
        }

        public TechnicalId build(){
            return this.technicalId;
        }

        public TechnicalIdBuilder setLegId(long legId){
            this.technicalId.legId = legId;
            return this;
        }

        public TechnicalIdBuilder setVersion(int version){
            this.technicalId.version = version;
            return this;
        }


    }
}

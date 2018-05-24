package com.sabre.as.flight.schedule.domain;

import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Created by sg0501095 on 5/20/18.
 */

public class LegId {
    @Field("legId")
    private long id;

    @Field
    private long version;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "LegId{" +
                "legId=" + id +
                ", version=" + version +
                '}';
    }

    public static class IdBuilder {
        private final LegId legId;

        public static IdBuilder newBuilder(){return new IdBuilder();}
        public IdBuilder(){
            this.legId = new LegId();
        }

        public LegId build(){
            return this.legId;
        }

        public IdBuilder setId(long id){
            this.legId.id = id;
            return this;
        }

        public IdBuilder setVersion(long version){
            this.legId.version = version;
            return this;
        }


    }
}

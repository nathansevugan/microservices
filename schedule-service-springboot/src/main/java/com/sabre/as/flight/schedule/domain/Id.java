package com.sabre.as.flight.schedule.domain;

import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Created by sg0501095 on 5/20/18.
 */

public class Id {
    @Field("id")
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
        return "Id{" +
                "id=" + id +
                ", version=" + version +
                '}';
    }

    public static class IdBuilder {
        private final Id id;

        public static IdBuilder newBuilder(){return new IdBuilder();}
        public IdBuilder(){
            this.id = new Id();
        }

        public Id build(){
            return this.id;
        }

        public IdBuilder setLegId(long legId){
            this.id.id = legId;
            return this;
        }

        public IdBuilder setVersion(long version){
            this.id.version = version;
            return this;
        }


    }
}

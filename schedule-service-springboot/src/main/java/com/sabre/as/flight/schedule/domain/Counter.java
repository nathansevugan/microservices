package com.sabre.as.flight.schedule.domain;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Created by sg0501095 on 5/24/18.
 */
@Document(collection = "counters")
public class Counter {
    @org.springframework.data.annotation.Id
    private String id;

    @Field(value = "seq")
    private long seq;

    public String getId() {
        return id;
    }

    public long getSeq() {
        return seq;
    }
}

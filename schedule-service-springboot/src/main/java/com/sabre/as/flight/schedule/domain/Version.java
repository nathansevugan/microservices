package com.sabre.as.flight.schedule.domain;

import org.joda.time.DateTime;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Created by sg0501095 on 5/24/18.
 */

@Document(collection = "versions")
public class Version {
    @org.springframework.data.annotation.Id
    private long id;
    @Field
    private String description;
    @Field
    private String user;
    @Field
    private DateTime timestamp;

    public Version(long id, String description, String user, DateTime timestamp) {
        this.id = id;
        this.description = description;
        this.user = user;
        this.timestamp = timestamp;
    }

    public long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getUser() {
        return user;
    }

    public DateTime getTimestamp() {
        return timestamp;
    }
}

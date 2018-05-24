package com.sabre.as.flight.schedule.repositories;

/**
 * Created by sg0501095 on 5/24/18.
 */
public class DBException extends RuntimeException {
    public DBException(String message) {
        super(message);
    }
}

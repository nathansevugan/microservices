package com.sabre.as.flight.schedule.domain;

import org.joda.time.DateTime;

/**
 * Created by sg0501095 on 5/9/18.
 */
public class FlightTimes {
    private DateTime scheduleDepartureTime;
    private DateTime scheduleArrivalTime;
    private DateTime latestDepartureTime;
    private DateTime latestArrivalTime;

    public DateTime getScheduleDepartureTime() {
        return scheduleDepartureTime;
    }

    public DateTime getScheduleArrivalTime() {
        return scheduleArrivalTime;
    }

    public DateTime getLatestDepartureTime() {
        return latestDepartureTime;
    }

    public DateTime getLatestArrivalTime() {
        return latestArrivalTime;
    }

    public static class FlightTimesBuilder {
        private FlightTimes flightTimes;

        public FlightTimesBuilder() {
            flightTimes = new FlightTimes();
        }

        void setScheduleDepartureTime(DateTime scheduleDepartureTime) {
            this.flightTimes.scheduleDepartureTime = scheduleDepartureTime;
        }

        void setScheduleArrivalTime(DateTime scheduleArrivalTime) {
            this.flightTimes.scheduleArrivalTime = scheduleArrivalTime;
        }

        void setLatestDepartureTime(DateTime latestDepartureTime) {
            this.flightTimes.latestDepartureTime = latestDepartureTime;
        }

        void setLatestArrivalTime(DateTime latestArrivalTime) {
            this.flightTimes.latestArrivalTime = latestArrivalTime;
        }

        public FlightTimes build() {
            return flightTimes;
        }

    }
}
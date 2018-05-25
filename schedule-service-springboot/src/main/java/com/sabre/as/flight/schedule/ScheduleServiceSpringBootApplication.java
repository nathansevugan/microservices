package com.sabre.as.flight.schedule;

import com.sabre.as.flight.schedule.service.ScheduleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = {"com.sabre.as.flight.schedule.repositories"})
@ComponentScan({"com.sabre.as.flight.schedule"})
public class ScheduleServiceSpringBootApplication {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleServiceSpringBootApplication.class);

    public ScheduleService scheduleService() {
        return new ScheduleService();
    }

    public static void main(String[] args) {

        logger.info("Attempting to start schedule service.....");
        SpringApplication.run(ScheduleServiceSpringBootApplication.class,args);

    }
}

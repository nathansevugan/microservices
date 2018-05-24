package com.sabre.as.flight.schedule.config;

import com.mongodb.MongoClient;
import com.sabre.as.flight.schedule.repositories.converter.StringToDateTimeReadingConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.convert.CustomConversions;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.util.Arrays;

/**
 * Created by sg0501095 on 5/8/18.
 */
@Configuration
public class MongoConfig extends AbstractMongoConfiguration {
    @Value("${mongodb.hostname}")
    private String mongodbHostname;
    @Value("${mongodb.port}")
    private int mongodbPort;

    public MongoConfig() {
    }

    @Override
    protected String getDatabaseName() {
        return "ops_db";
    }

    @Override
    public CustomConversions customConversions() {
        MongoCustomConversions mongoCustomConversions = new MongoCustomConversions(
                Arrays.asList(StringToDateTimeReadingConverter.INSTANCE));
        return mongoCustomConversions;
    }


    @Override
    @Bean
    public MongoClient mongoClient(){
        return new MongoClient(mongodbHostname, mongodbPort);
    }

}

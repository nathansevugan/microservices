package com.sabre.as.flight.schedule.repositories;

import com.sabre.as.flight.schedule.domain.Counter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * Created by sg0501095 on 5/24/18.
 */
@Repository
public class CounterRepository {
    @Autowired
    private MongoTemplate mongoTemplate;

    public long getNextIdFor(String collectionName){
        Counter counter = mongoTemplate.findAndModify(
                query(where("_id").is(collectionName)),
                new Update().inc("seq", 1),
                options().returnNew(true),
                Counter.class);

        return counter.getSeq();
    }
}

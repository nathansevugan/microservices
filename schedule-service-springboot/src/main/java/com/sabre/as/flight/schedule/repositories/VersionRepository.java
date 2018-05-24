package com.sabre.as.flight.schedule.repositories;

import com.sabre.as.flight.schedule.domain.Version;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by sg0501095 on 5/24/18.
 */

/*
*
* def get_next_id(db, entity):
    next_id_record = db.counters.find_one_and_update(
        {'_id': entity}, {'$inc': {'seq': 1}},
        return_document=ReturnDocument.AFTER
    )
    if next_id_record is None:
        db.counters.insert({'_id':entity, 'seq':1})
        next_id_record = db.counters.find_one({'_id':entity})
        print('version returned: ' + str(next_id_record['seq']))
    return next_id_record['seq']


def insert_version(db, entity, description):
    return db.versions.insert({
        '_id':get_next_id(db=db, entity=entity),
        'description':description,
        'user':'system',
        'timestamp':datetime.now()
    })


* */
@Repository
public class VersionRepository {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private CounterRepository counterRepository;

    public Version addNewVersion(String description, String user){

        Version version = new Version(counterRepository.getNextIdFor("version"), description, user,
                DateTime.now());
        mongoTemplate.insert(version);
        return version;
    }
}



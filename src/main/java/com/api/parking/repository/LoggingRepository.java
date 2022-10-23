package com.api.parking.repository;

import com.api.parking.model.LoggingModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoggingRepository extends MongoRepository<LoggingModel,String> {
}

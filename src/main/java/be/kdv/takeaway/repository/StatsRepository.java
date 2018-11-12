package be.kdv.takeaway.repository;

import be.kdv.takeaway.model.Stats;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StatsRepository extends MongoRepository<Stats, String> {


}

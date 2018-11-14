package be.kdv.takeaway.repository;

import be.kdv.takeaway.model.MealStats;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "stats", path = "stats")
public interface MealStatsRepository extends MongoRepository<MealStats, String> {

    List<MealStats> findAll();
}

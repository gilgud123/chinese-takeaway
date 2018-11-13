package be.kdv.takeaway.repository;

import be.kdv.takeaway.model.MealStats;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MealStatsRepository extends MongoRepository<MealStats, String> {
}

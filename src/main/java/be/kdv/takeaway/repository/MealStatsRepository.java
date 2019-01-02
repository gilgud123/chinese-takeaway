package be.kdv.takeaway.repository;

import be.kdv.takeaway.model.MealStats;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface MealStatsRepository extends MongoRepository<MealStats, String>, QueryByExampleExecutor<MealStats> {
}

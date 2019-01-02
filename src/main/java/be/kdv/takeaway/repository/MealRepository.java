package be.kdv.takeaway.repository;

import be.kdv.takeaway.model.Meal;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface MealRepository extends MongoRepository<Meal, String>, QueryByExampleExecutor<Meal> {

    //Optional<Meal> getByMenuNumber(int menuNumber);
}

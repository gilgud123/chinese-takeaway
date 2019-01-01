package be.kdv.takeaway.repository;

import be.kdv.takeaway.model.Allergy;
import be.kdv.takeaway.model.Meal;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.List;
import java.util.Optional;

public interface MealRepository extends MongoRepository<Meal, String>, QueryByExampleExecutor<Meal> {

    //Optional<Meal> getByMenuNumber(int menuNumber);
}

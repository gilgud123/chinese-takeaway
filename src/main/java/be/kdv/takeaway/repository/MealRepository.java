package be.kdv.takeaway.repository;

import be.kdv.takeaway.model.Allergy;
import be.kdv.takeaway.model.Meal;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface MealRepository extends MongoRepository<Meal, String> {

    Optional<Meal> getByMenuNumber(int menuNumber);

    Optional<List<Meal>> getAllByAllergies(Allergy... allergies);

    Optional<List<Meal>> findByOrderByStatsDesc();
}

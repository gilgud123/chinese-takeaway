package be.kdv.takeaway.repository;

import be.kdv.takeaway.model.Meal;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "meals", path = "meals")
public interface MealRepository extends MongoRepository<Meal, String> {

    Optional<Meal> getByMenuNumber(@Param("menuNumber")int menuNumber);
}

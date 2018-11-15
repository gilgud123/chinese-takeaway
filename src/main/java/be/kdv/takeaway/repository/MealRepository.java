package be.kdv.takeaway.repository;

import be.kdv.takeaway.model.Allergy;
import be.kdv.takeaway.model.Meal;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "meals", path = "meals")
public interface MealRepository extends MongoRepository<Meal, String> {

    // e.g. http://localhost:8090/takeaway/meals/search/number?menuNumber=1
    @RestResource(path = "number", rel = "number")
    Optional<Meal> getByMenuNumber(@Param("menuNumber") int menuNumber);

    //e.g. http://localhost:8090/takeaway/meals/search/allergy?allergy=LACTOSE
    @RestResource(path = "allergy", rel = "allergy")
    Optional<List<Meal>> getByAllergiesNotContains(@Param("allergy") Allergy allergy);
}

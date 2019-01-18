package be.kdv.takeaway.service;

import be.kdv.takeaway.bootstrap.SeedMongoDb;
import be.kdv.takeaway.exception.EntityNotFoundException;
import be.kdv.takeaway.model.Allergy;
import be.kdv.takeaway.model.Meal;
import be.kdv.takeaway.repository.MealRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MealService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SeedMongoDb.class);

    private final MealRepository mealRepository;

    public MealService(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    public List<Meal> getAllMeals(){
        return mealRepository.findAll();
    }

    public Meal getMealById(String id){
        return mealRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Meal.class, id));
    }

    public List<Meal> excludeAllergy(String... allergies){
        List<Meal> allergenicMeals = new ArrayList<>();

        for (Meal meal : getAllMeals()) {
            Arrays.stream(allergies)
                    .forEach(allergy -> {
                        if (meal.getAllergies().contains(Allergy.fromString(allergy)))
                        {
                            allergenicMeals.add(meal);
                            LOGGER.info("meal {}, allergy {}, standard allergy {}", meal.getDescription(), Allergy.fromString(allergy), Allergy.NUTS);
                        }
                    });
        }
        LOGGER.info("{}", allergenicMeals.toString());
        return getAllMeals().stream().filter(meal -> !allergenicMeals.contains(meal)).collect(Collectors.toList());
    }

    public List<Meal> getByMealName(String mealName){
        Meal meal = Meal.builder().name(mealName).build();
        ExampleMatcher matcher = ExampleMatcher.matchingAny()
                .withIgnoreNullValues()
                .withIgnorePaths("allergies")
                .withIgnoreCase();
        Example<Meal> mealExample = Example.of(meal,matcher);
        List<Meal> meals = mealRepository.findAll(mealExample);
        LOGGER.info("Meals with the name {} : {}", meal.getName(), meals);
        return meals;
    }

}

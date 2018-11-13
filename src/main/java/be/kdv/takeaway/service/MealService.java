package be.kdv.takeaway.service;

import be.kdv.takeaway.bootstrap.Bootstrap;
import be.kdv.takeaway.exception.InputNotValidException;
import be.kdv.takeaway.exception.MealNotFoundException;
import be.kdv.takeaway.model.Allergy;
import be.kdv.takeaway.model.Meal;
import be.kdv.takeaway.repository.MealRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class MealService {

    private final Logger LOGGER = LoggerFactory.getLogger(Bootstrap.class);

    private final MealRepository mealRepository;

    public MealService(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    public List<Meal> getAllMeals(){
        return mealRepository.findAll();
    }

    public List<Meal> excludeAllergy(String... allergies){
        if(allergies == null){
            throw new InputNotValidException();
        }

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

    //TODO to be refactored
    public List<Meal> getStats(){
        return null;
    }

}

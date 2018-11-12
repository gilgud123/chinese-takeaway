package be.kdv.takeaway.service;

import be.kdv.takeaway.exception.InputNotValidException;
import be.kdv.takeaway.exception.MealNotFoundException;
import be.kdv.takeaway.model.Allergy;
import be.kdv.takeaway.model.Meal;
import be.kdv.takeaway.repository.MealRepository;
import lombok.extern.slf4j.Slf4j;
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

    private final MealRepository mealRepository;

    public MealService(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    public List<Meal> getAllMeals(){
        return mealRepository.findAll();
    }

    public List<Meal> excludeAllergy(Allergy... allergies){
        if(allergies == null){
            throw new InputNotValidException();
        }

        List<Meal> allergyFreeMeals = getAllMeals();

        for (Meal meal : getAllMeals()) {
            Arrays.stream(allergies)
                    .forEach(allergy -> {
                        if (meal.getAllergies().contains(allergy)) allergyFreeMeals.remove(meal);
                    });
        }
        return allergyFreeMeals;
    }

    //TODO to be refactored
    public List<Meal> getStats(){
        Optional<List<Meal>> optionalMeals = mealRepository.findByOrderByStatsDesc();
        return optionalMeals.orElseThrow(MealNotFoundException::new);
    }

}

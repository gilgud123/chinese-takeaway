package be.kdv.takeaway.service;

import be.kdv.takeaway.exception.InputNotValidException;
import be.kdv.takeaway.exception.MealNotFoundException;
import be.kdv.takeaway.model.Allergy;
import be.kdv.takeaway.model.Meal;
import be.kdv.takeaway.repository.MealRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        Optional<List<Meal>> optionalAllergies = mealRepository.getAllByAllergies(allergies);
        return optionalAllergies.orElseThrow(MealNotFoundException::new);
    }

}

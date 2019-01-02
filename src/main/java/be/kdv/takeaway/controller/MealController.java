package be.kdv.takeaway.controller;

import be.kdv.takeaway.model.Meal;
import be.kdv.takeaway.service.MealService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/meals")
public class MealController {

    //private final Logger LOGGER = LoggerFactory.getLogger(SeedMongoDb.class);

    private final MealService mealService;

    public MealController(MealService mealService) {
        this.mealService = mealService;
    }

    @GetMapping
    public ResponseEntity<?> getAllMeals(){
        try {
            return new ResponseEntity<>(mealService.getAllMeals(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMealById(@PathVariable String id){
        try {
            return new ResponseEntity<>(mealService.getMealById(id), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/allergies")
    public ResponseEntity<?> getAllergyFreeMeals(@RequestBody String... allergies) {
        try {
            return new ResponseEntity<>(mealService.excludeAllergy(allergies), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    //TODO to be refactored and saved into a separate collection/document
    @GetMapping("/stats")
    public List<Meal> showStats(){
        return null;
    }

    @GetMapping("/query/{mealName}")
    public ResponseEntity<?> getMealByName(@PathVariable String mealName){
        try{
            return new ResponseEntity<>(mealService.getByMealName(mealName), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }

    }

}

package be.kdv.takeaway.controller;

import be.kdv.takeaway.service.MealStatsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stats")
public class StatsController {

    private final MealStatsService mealStatsService;

    public StatsController(MealStatsService mealStatsService) {
        this.mealStatsService = mealStatsService;
    }

    @GetMapping
    public ResponseEntity<?> showStats(){
        try{
            return new ResponseEntity<>(mealStatsService.showStats(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }
}

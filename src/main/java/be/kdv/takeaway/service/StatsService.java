package be.kdv.takeaway.service;

import be.kdv.takeaway.model.Meal;
import be.kdv.takeaway.model.Stats;
import be.kdv.takeaway.repository.MealRepository;
import be.kdv.takeaway.repository.StatsRepository;
import org.springframework.stereotype.Service;

@Service
public class StatsService {

    private StatsRepository statsRepository;
    private MealRepository mealRepository;

    public StatsService(
            StatsRepository statsRepository,
            MealRepository mealRepository) {
        this.statsRepository = statsRepository;
        this.mealRepository = mealRepository;
    }

    private void initStats(){
        Stats stats = new Stats(mealRepository.findAll());
        statsRepository.save(stats);
    }

    public void addStatsToMeal(Meal meal){
        if(statsRepository.findAll().isEmpty()){
            initStats();
        };

        statsRepository.findAll().get(0).addMealToStats(meal.getMenuNumber());
    }
}

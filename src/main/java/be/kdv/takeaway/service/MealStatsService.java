package be.kdv.takeaway.service;

import be.kdv.takeaway.model.Meal;
import be.kdv.takeaway.model.MealStats;
import be.kdv.takeaway.repository.MealRepository;
import be.kdv.takeaway.repository.MealStatsRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MealStatsService {

    private final Logger LOGGER = LoggerFactory.getLogger(MealStats.class);

    private final MealStatsRepository mealStatsRepository;
    private final MealRepository mealRepository;

    public MealStatsService(MealStatsRepository mealStatsRepository, MealRepository mealRepository) {
        this.mealStatsRepository = mealStatsRepository;
        this.mealRepository = mealRepository;
    }

    private MealStats initStats(){
        //List<Integer> mealNumbers = mealRepository.findAll().stream().map(Meal::getMenuNumber).collect(Collectors.toList());
        MealStats stats = new MealStats();
        LOGGER.info("Stats: {}", stats.toString());
        return stats;
    }

    public void addStats(int mealnumber){
        MealStats stats;
        if(mealStatsRepository.findAll().isEmpty()){
            LOGGER.info("The repo is empty");
            stats = initStats();
            stats.getMealStatistics().put(mealnumber, 1);
            mealStatsRepository.save(stats);
        }else {
            stats = mealStatsRepository.findAll().get(0);
            int mealStatValue = stats.getMealStatistics().getOrDefault(mealnumber, 0);
            LOGGER.info("value: {}", mealStatValue);
            stats.getMealStatistics().put(mealnumber, ++mealStatValue);
            mealStatsRepository.save(stats);
        }
        LOGGER.info("Meal stats are: {}", mealStatsRepository.findAll().toString());
    }


    public MealStats showStats(){
        if(mealStatsRepository.findAll().isEmpty()) return mealStatsRepository.save(initStats());
        return mealStatsRepository.findAll().get(0);
    }

}


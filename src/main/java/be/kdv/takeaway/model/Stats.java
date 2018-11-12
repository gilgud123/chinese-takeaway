package be.kdv.takeaway.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "stats")
public class Stats {

    private final Logger LOGGER = LoggerFactory.getLogger(Stats.class);

    @Id
    private String id;

    private List<Meal> meals;

    private Map<Integer, Integer> mealStats = new HashMap<>();

    public Stats(List<Meal> meals){
        this.meals = new ArrayList<Meal>(meals);

        meals.forEach(meal -> {
            mealStats.put(meal.getMenuNumber(), 0);
        });

        LOGGER.info("Statistics for {} meals will be logged from now on", mealStats.keySet().size());
    }

    public void addMealToStats(int mealNumber){
        if(mealStats.containsKey(mealNumber)){
            int mealStat = mealStats.get(mealNumber);
            LOGGER.info("The meal value is {}", mealStat);
            mealStats.replace(mealNumber, ++mealStat);
        }else{
            LOGGER.info("The meal number is is not present in our menu");
        }
    }

}




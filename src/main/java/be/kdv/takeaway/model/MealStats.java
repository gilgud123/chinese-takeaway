package be.kdv.takeaway.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Document(collection = "stats")
public class MealStats {

    @Id
    private String id;

    private Map<Integer, Integer> mealStatistics = new HashMap<>();

    public void addStats(int mealNr, int value){
        mealStatistics.put(mealNr, value);
    }

}

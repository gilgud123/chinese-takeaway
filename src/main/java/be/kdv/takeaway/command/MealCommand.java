package be.kdv.takeaway.command;

import be.kdv.takeaway.model.Allergy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonSerialize(as = MealCommand.class)
@JsonDeserialize(as = MealCommand.class)
public class MealCommand {

    private int menuNumber;

    private List<Allergy> allergies = new ArrayList<Allergy>();

}

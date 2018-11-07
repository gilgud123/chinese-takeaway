package be.kdv.takeaway.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "meals")
public class Meal {

    @Id
    private String id;

    @NotNull
    private int menuNumber;

    @NotNull
    private String name;

    private String description;

    private List<Allergy> allergies = new ArrayList<Allergy>();

    private int stats;

}

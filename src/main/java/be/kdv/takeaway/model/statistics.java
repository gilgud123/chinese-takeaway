package be.kdv.takeaway.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "stats")
public class statistics {

    HashMap<Integer, Meal> statsMeal;
}

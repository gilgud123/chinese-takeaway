package be.kdv.takeaway.model;

import be.kdv.takeaway.exception.AllergyNotFoundException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import static org.springframework.data.util.Pair.toMap;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum Allergy {

    LACTOSE("lactose"),
    NUTS("nuts"),
    GLUTEN("gluten"),
    SHELLFISH("shellfish"),
    SOY("soy");

    private final String allergy;

    Allergy(String allergy) {
        this.allergy = allergy;
    }

    @JsonValue
    public String getAllergy(){
        return allergy;
    }

    @JsonCreator
    public static Allergy fromString(String allergy){
        return Arrays.stream(Allergy.values()).filter(e -> e.getAllergy().equals(allergy)).findFirst().orElseThrow(AllergyNotFoundException::new);
    }
}

package be.kdv.takeaway.model;

import be.kdv.takeaway.exception.AllergyNotFoundException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum Allergy {

    LACTOSE("lactose"),
    NUTS("nuts"),
    GLUTEN("gluten"),
    SHELLFISH("shellfish"),
    SOY("soy");

    private String allergy;

    Allergy(String allergy) {
        this.allergy = allergy;
    }

    @JsonValue
    public String getAllergy(){
        return allergy;
    }

    @JsonCreator
    public static Allergy fromString(String allergy){
        return Arrays.stream(Allergy.values()).filter(e -> e.allergy.equals(allergy)).findFirst().orElseThrow(AllergyNotFoundException::new);
    }
}

package be.kdv.takeaway.command;

import be.kdv.takeaway.model.Allergy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonSerialize(as = AllergyCommand.class)
@JsonDeserialize(as = AllergyCommand.class)
public class AllergyCommand {

    private String allergy;
}

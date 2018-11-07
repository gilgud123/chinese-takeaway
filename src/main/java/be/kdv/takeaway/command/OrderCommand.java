package be.kdv.takeaway.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderCommand {

    @NotNull
    private String customerName;

    @NotNull
    private List<Integer> meals;
}

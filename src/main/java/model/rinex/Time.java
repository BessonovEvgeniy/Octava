package model.rinex;


import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

public @Data class Time {

    @NotNull @Min(0) @Max(86400) @Size(max = 86400)
    private Set<Integer> time;
}

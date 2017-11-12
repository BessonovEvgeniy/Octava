package rinex.model.observations.header;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@EqualsAndHashCode(callSuper = true)
@Component("TIME OF FIRST OBS")
public @Data class TimeOfFirstObs extends AbstractTimeOfObs {}

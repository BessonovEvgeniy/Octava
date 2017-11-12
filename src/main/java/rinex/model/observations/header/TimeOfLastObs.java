package rinex.model.observations.header;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@EqualsAndHashCode(callSuper = true)
@Component("TIME OF LAST OBS")
public @Data class TimeOfLastObs extends AbstractTimeOfObs {}

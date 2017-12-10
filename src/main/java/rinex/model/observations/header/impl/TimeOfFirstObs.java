package rinex.model.observations.header.impl;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Component;
import rinex.model.observations.header.AbstractTimeOfObs;

@EqualsAndHashCode(callSuper = true)
@Component("TIME OF FIRST OBS")
public @Data class TimeOfFirstObs extends AbstractTimeOfObs {}

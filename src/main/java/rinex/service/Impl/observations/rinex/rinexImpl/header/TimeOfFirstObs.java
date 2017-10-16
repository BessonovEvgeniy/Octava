package rinex.service.Impl.observations.rinex.rinexImpl.header;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Service;

@EqualsAndHashCode(callSuper = true)
@Service
public @Data class TimeOfFirstObs extends AbstractTimeOfObs {}

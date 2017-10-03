package rinex.model.rinex;

import lombok.Data;

import javax.persistence.Entity;

@Entity
public @Data abstract class CodeObservations extends Observations implements GPS, GLONASS {}
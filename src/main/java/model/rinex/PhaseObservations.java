package model.rinex;

import lombok.Data;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public @Data abstract class PhaseObservations extends Observations {
}

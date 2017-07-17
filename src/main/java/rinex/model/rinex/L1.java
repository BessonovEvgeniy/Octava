package rinex.model.rinex;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "L1")
public @Data class L1 extends PhaseObservations{}
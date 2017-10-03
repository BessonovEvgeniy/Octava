package rinex.model.rinex;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "L2")
public @Data class L2 extends PhaseObservations {
}

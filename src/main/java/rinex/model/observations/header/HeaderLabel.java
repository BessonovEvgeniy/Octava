package rinex.model.observations.header;

@FunctionalInterface
public interface HeaderLabel {

    boolean parse(String line);
}

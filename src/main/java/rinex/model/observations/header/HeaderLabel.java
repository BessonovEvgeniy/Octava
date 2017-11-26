package rinex.model.observations.header;

@FunctionalInterface
public interface HeaderLabel {

    int MAX_LINE_LENGTH = 80;

    boolean parse(String line);
}

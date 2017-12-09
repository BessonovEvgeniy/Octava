package rinex.model.observations.header;

@FunctionalInterface
public interface HeaderLabel {

    int RINEX_LINE_LENGTH = 80;

    boolean parse(String line);
}

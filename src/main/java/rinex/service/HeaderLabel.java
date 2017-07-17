package rinex.service;

@FunctionalInterface
public interface HeaderLabel {

    boolean parse(String line);
}

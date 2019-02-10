package converter;

import java.util.List;

public interface PopulatorList<SOURCE, TARGET> {

    List<Populator<SOURCE, TARGET>> getPopulators();

    void setPopulators(List<Populator<SOURCE, TARGET>> populators);
}
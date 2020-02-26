package octava.converter;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public abstract class AbstractPopulatingConverter<SOURCE, TARGET> extends AbstractConverter<SOURCE, TARGET> implements
        PopulatorList<SOURCE, TARGET> {

    private List<Populator<SOURCE, TARGET>> populators;

    @Override
    public List<Populator<SOURCE, TARGET>> getPopulators()
    {
        return populators;
    }

    @Override
    public void setPopulators(final List<Populator<SOURCE, TARGET>> populators)
    {
        this.populators = populators;
    }

    @Override
    public void populate(final SOURCE source, final TARGET target) {
        final List<Populator<SOURCE, TARGET>> list = getPopulators();

        if (CollectionUtils.isNotEmpty(list)) {
            list.stream().filter(Objects::nonNull).forEach(populator -> populator.populate(source, target));
        }
    }

    @Override
    public TARGET convert(final SOURCE source) {

        TARGET target = createFromClass();
        populate(source, target);
        return target;
    }

    public TARGET convert(final SOURCE source, final TARGET target) {
        populate(source, target);
        return target;
    }
}


package octava.converter;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

public abstract class AbstractConverter<SOURCE, TARGET> implements Converter<SOURCE, TARGET>, Populator<SOURCE, TARGET> {

    private Class<TARGET> targetClass;

    @Nullable
    @Override
    public abstract TARGET convert(SOURCE source);

    public List<TARGET> convertAll(Collection<SOURCE> sources) {
        List<TARGET> targets = emptyList();

        if (CollectionUtils.isNotEmpty(sources)) {
            targets = sources.stream().map(source -> convert(source)).collect(Collectors.toList());
        }
        return targets;
    }


    public void setTargetClass(final Class<TARGET> targetClass) {
        this.targetClass = targetClass;

        if (targetClass != null) {
            createFromClass();
        }
    }

    protected TARGET createFromClass() {
        try {
            return targetClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}

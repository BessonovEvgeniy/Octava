package converter;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

public abstract class AbstractConverter<SOURCE, TARGET> implements Converter<SOURCE, TARGET>, Populator<SOURCE, TARGET> {

    private Class<TARGET> targetClass;

    @Nullable
    @Override
    public abstract TARGET convert(SOURCE source);

    public Collection<TARGET> convertAll(Collection<SOURCE> sources) {
        Collection<TARGET> targets = Collections.emptyList();

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

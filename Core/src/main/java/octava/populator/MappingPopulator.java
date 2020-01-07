package octava.populator;

import octava.converter.Populator;
import octava.dto.MappingDto;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;

import java.util.Arrays;
import java.util.Optional;
import java.util.StringJoiner;

import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

public class MappingPopulator implements Populator<HandlerMethod, MappingDto> {

    @Override
    public void populate(final HandlerMethod source, final MappingDto target) {

        final String controllerName = source.getBean().toString();
        final String methodName = source.getMethod().getName();
        final String returnType = source.getMethod().getReturnType().getTypeName();
        final String controllerMapping = getControllerMapping(source);
        final String methodMapping = getMethodMapping(source);

        target.setController(controllerName);
        target.setMethod(methodName);
        target.setReturnType(returnType);
        target.setControllerMapping(controllerMapping);
        target.setMethodMapping(methodMapping);

        StringJoiner fullMapping = new StringJoiner(StringUtils.EMPTY);
        fullMapping.add(controllerMapping);
        fullMapping.add(methodMapping);

        target.setFullMapping(defaultIfNull(fullMapping, StringUtils.EMPTY).toString());
    }

    private String getControllerMapping(HandlerMethod handlerMethod) {
        Optional<String> controllerMapping = Optional.empty();

        RequestMapping annotation = handlerMethod.getBeanType().getAnnotation(RequestMapping.class);
        if (annotation != null) {
            String[] controllerMappings = annotation.value();

            if (ArrayUtils.isNotEmpty(controllerMappings)) {
                StringJoiner mapping = new StringJoiner(",");
                Arrays.stream(controllerMappings).forEach(i -> mapping.add(i));
                controllerMapping = controllerMapping.of(mapping.toString());
            }
        }
        return controllerMapping.orElse(StringUtils.EMPTY);
    }

    private String getMethodMapping(HandlerMethod handlerMethod) {
        Optional<String> methodMapping = Optional.empty();

        RequestMapping annotation = handlerMethod.getMethodAnnotation(RequestMapping.class);

        if (annotation != null) {
            String[] methodMappings = annotation.path();

            if (ArrayUtils.isNotEmpty(methodMappings)) {
                methodMapping = methodMapping.of(methodMappings[0]);
            }
        }

        return methodMapping.orElse(StringUtils.EMPTY);
    }
}

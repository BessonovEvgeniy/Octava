package converter.populator;

import converter.Populator;
import dto.MappingDto;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;

import java.util.Arrays;
import java.util.Optional;
import java.util.StringJoiner;

public class MappingPopulator implements Populator<HandlerMethod, MappingDto> {

    @Override
    public void populate(HandlerMethod source, MappingDto target) {

        String controllerName = source.getBean().toString();
        String methodName = source.getMethod().getName();
        String returnType = source.getMethod().getReturnType().getTypeName();
        String controllerMapping = getControllerMapping(source);
        String methodMapping = getMethodMapping(source);

        target.setController(controllerName);
        target.setMethod(methodName);
        target.setReturnType(returnType);
        target.setControllerMapping(controllerMapping);
        target.setMethodMapping(methodMapping);

        StringJoiner fullMapping = new StringJoiner("");
        fullMapping.add(controllerMapping);
        fullMapping.add(methodMapping);

        target.setFullMapping(fullMapping.toString());
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
        return controllerMapping.orElse("");
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

        return methodMapping.orElse("");
    }
}

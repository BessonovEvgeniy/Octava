package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.MappingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ContextConfigInfoController {

    private final RequestMappingHandlerMapping handlerMapping;

    @Resource
    private Converter<HandlerMethod, MappingDto> mappingConverter;

    @Autowired
    public ContextConfigInfoController(RequestMappingHandlerMapping handlerMapping) {
        this.handlerMapping = handlerMapping;
    }

    @GetMapping(value="/endpointdoc")
    public String show() {

        GsonBuilder builder = new GsonBuilder();
        builder.serializeNulls();
        builder.setPrettyPrinting();
        Gson json = builder.create();

        List<MappingDto> mappings = handlerMapping.getHandlerMethods().values().stream()
                .map(source -> mappingConverter.convert(source))
                .collect(Collectors.toList());

        return json.toJson(mappings);
    }
}

package octava.config;

import com.google.common.collect.Lists;
import octava.converter.AbstractPopulatingConverter;
import octava.converter.Populator;
import octava.dto.MappingDto;
import octava.dto.RinexFileDto;
import octava.handler.RestTemplateResponseErrorHandler;
import octava.interceptor.BearerAuthorizationInterceptor;
import octava.interceptor.RestTemplateApiCallInterceptor;
import octava.model.rinex.RinexFileModel;
import octava.populator.MappingPopulator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.method.HandlerMethod;

import java.util.List;

@Configuration
public class CoreBeans {

    @Bean
    public RestTemplate createRestTemplate() {
        final RestTemplate restTemplate = new RestTemplate();

        restTemplate.setErrorHandler(createRestTemplateResponseErrorHandler());

        final List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
        interceptors.add(createRestTemplateApiCallInterceptor());

        return restTemplate;
    }

    @Bean
    public RestTemplateResponseErrorHandler createRestTemplateResponseErrorHandler() {
        return new RestTemplateResponseErrorHandler();
    }

    @Bean
    public RestTemplateApiCallInterceptor createRestTemplateApiCallInterceptor() {
        return new RestTemplateApiCallInterceptor();
    }

    @Bean
    public BearerAuthorizationInterceptor createBearerAuthorizationInterceptor() {
        return new BearerAuthorizationInterceptor();
    }

    @Bean("mappingConverter")
    public AbstractPopulatingConverter<HandlerMethod, MappingDto> mappingConverter() {
        AbstractPopulatingConverter<HandlerMethod, MappingDto> mappingConverter = new AbstractPopulatingConverter(){};
        mappingConverter.setTargetClass(MappingDto.class);
        mappingConverter.setPopulators(mappingPopulators());
        return mappingConverter;
    }

    @Bean("mappingPopulator")
    public Populator<HandlerMethod, MappingDto> mappingPopulator() {
        return new MappingPopulator();
    }

    private List<Populator<HandlerMethod, MappingDto>> mappingPopulators() {
        return Lists.newArrayList(mappingPopulator());
    }

    @Bean("rinexFileConverter")
    public AbstractPopulatingConverter<RinexFileModel, RinexFileDto> rinexFileConverter() {
        AbstractPopulatingConverter<RinexFileModel, RinexFileDto> mappingConverter = new AbstractPopulatingConverter(){};
        mappingConverter.setTargetClass(RinexFileDto.class);
        mappingConverter.setPopulators(Lists.newArrayList());
        return mappingConverter;
    }
}

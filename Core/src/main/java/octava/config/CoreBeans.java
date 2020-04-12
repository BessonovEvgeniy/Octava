package octava.config;

import com.google.common.collect.Lists;
import octava.converter.AbstractPopulatingConverter;
import octava.converter.Populator;
import octava.converter.populator.MappingPopulator;
import octava.converter.populator.MediaPopulator;
import octava.converter.populator.MultipartFile2MediaPopulator;
import octava.converter.populator.RinexFilePopulator;
import octava.dto.MappingDto;
import octava.dto.MediaDto;
import octava.dto.RinexFileMediaDto;
import octava.handler.RestTemplateResponseErrorHandler;
import octava.interceptor.BearerAuthorizationInterceptor;
import octava.interceptor.RestTemplateApiCallInterceptor;
import octava.model.media.MediaModel;
import octava.model.rinex.RinexFileMediaModel;
import octava.service.impl.storage.LocalStorage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Configuration
@PropertySource("classpath:core.storage.properties")
public class CoreBeans {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfig() {
        return new PropertySourcesPlaceholderConfigurer();
    }

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
    public AbstractPopulatingConverter<RinexFileMediaModel, RinexFileMediaDto> rinexFileConverter() {
        AbstractPopulatingConverter<RinexFileMediaModel, RinexFileMediaDto> mappingConverter = new AbstractPopulatingConverter(){};
        mappingConverter.setTargetClass(RinexFileMediaDto.class);
        mappingConverter.setPopulators(Lists.newArrayList(rinexFilePopulator()));
        return mappingConverter;
    }

    @Bean
    public RinexFilePopulator<RinexFileMediaModel, RinexFileMediaDto> rinexFilePopulator() {
        return new RinexFilePopulator();
    }

    @Bean("mediaConverter")
    public AbstractPopulatingConverter<MediaModel, MediaDto> mediaConverter() {
        AbstractPopulatingConverter<MediaModel, MediaDto> mappingConverter = new AbstractPopulatingConverter(){};
        mappingConverter.setTargetClass(MediaDto.class);
        mappingConverter.setPopulators(Lists.newArrayList(mediaPopulator()));
        return mappingConverter;
    }

    @Bean
    public MultipartFile2MediaPopulator<MultipartFile, RinexFileMediaModel> multipartFile2MediaPopulator() {
        return new MultipartFile2MediaPopulator();
    }

    @Bean("multipartFile2MediaConverter")
    public AbstractPopulatingConverter<MultipartFile, RinexFileMediaModel> multipartFile2MediaConverter() {
        AbstractPopulatingConverter<MultipartFile, RinexFileMediaModel> mappingConverter = new AbstractPopulatingConverter(){};
        mappingConverter.setTargetClass(RinexFileMediaModel.class);
        mappingConverter.setPopulators(Lists.newArrayList(multipartFile2MediaPopulator()));
        return mappingConverter;
    }

    @Bean("rinexLocalStorage")
    public LocalStorage<RinexFileMediaModel> createRinexLocalStorage() {
        return new LocalStorage<>();
    }

    @Bean
    public MediaPopulator<MediaModel, MediaDto> mediaPopulator() {
        return new MediaPopulator();
    }
}

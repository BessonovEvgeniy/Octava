package config;

import converter.AbstractPopulatingConverter;
import converter.Populator;
import converter.populator.MappingPopulator;
import converter.populator.RinexFilePopulator;
import converter.populator.StoredFilePopulator;
import dto.MappingDto;
import dto.RinexFileDto;
import model.rinex.RinexFileModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class CoreBeans {

    @Bean
    @Qualifier("mappingConverter")
    public AbstractPopulatingConverter<HandlerMethod, MappingDto> mappingConverter() {
        AbstractPopulatingConverter<HandlerMethod, MappingDto> mappingConverter = new AbstractPopulatingConverter(){};
        mappingConverter.setTargetClass(MappingDto.class);
        mappingConverter.setPopulators(mappingPopulators());
        return mappingConverter;
    }

    @Bean
    @Qualifier("mappingPopulator")
    public Populator<HandlerMethod, MappingDto> mappingPopulator() {
        return new MappingPopulator();
    }

    private List<Populator<HandlerMethod, MappingDto>> mappingPopulators() {
        List<Populator<HandlerMethod, MappingDto>> populators = new ArrayList<>();
        populators.add(mappingPopulator());
        return populators;
    }

    @Bean
    @Qualifier("rinexFileConverter")
    public AbstractPopulatingConverter<RinexFileModel, RinexFileDto> rinexFileConverter() {
        AbstractPopulatingConverter<RinexFileModel, RinexFileDto> mappingConverter = new AbstractPopulatingConverter(){};
        mappingConverter.setTargetClass(RinexFileDto.class);
        mappingConverter.setPopulators(rinexFilePopulators());
        return mappingConverter;
    }

    @Bean
    @Qualifier("rinexFilePopulator")
    public Populator<RinexFileModel, RinexFileDto> rinexFilePopulator() {
        return new RinexFilePopulator();
    }

    @Bean
    @Qualifier("storedFilePopulator")
    public Populator<RinexFileModel, RinexFileDto> storedFileConverter() {
        return new StoredFilePopulator();
    }

    private List<Populator<RinexFileModel, RinexFileDto>> rinexFilePopulators() {
        List<Populator<RinexFileModel, RinexFileDto>> populators = new ArrayList<>();
        populators.add(rinexFilePopulator());
        populators.add(storedFileConverter());
        return populators;
    }




}

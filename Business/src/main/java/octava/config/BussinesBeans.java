package octava.config;

import com.google.common.collect.Lists;
import octava.converter.AbstractPopulatingConverter;
import octava.converter.Populator;
import octava.populator.ProjectPopulator;
import octava.dto.ProjectDto;
import octava.model.project.ProjectModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class BussinesBeans {

    @Bean
    @Qualifier("projectConverter")
    public AbstractPopulatingConverter<ProjectModel, ProjectDto> projectConverter() {
        AbstractPopulatingConverter<ProjectModel, ProjectDto> converter = new AbstractPopulatingConverter(){};
        converter.setTargetClass(ProjectDto.class);
        converter.setPopulators(projectPopulators());
        return converter;
    }

    @Bean
    @Qualifier("projectPopulator")
    public Populator<ProjectModel, ProjectDto> projectPopulator() {
        return new ProjectPopulator();
    }

    private List<Populator<ProjectModel, ProjectDto>> projectPopulators() {
        return Lists.newArrayList(projectPopulator());
    }
}

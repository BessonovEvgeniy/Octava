package config;

import com.google.common.collect.Lists;
import converter.AbstractPopulatingConverter;
import converter.Populator;
import converter.populator.ProjectPopulator;
import dto.ProjectDto;
import model.project.ProjectModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
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

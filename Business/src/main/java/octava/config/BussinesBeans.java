package octava.config;

import com.google.common.collect.Lists;
import octava.converter.AbstractPopulatingConverter;
import octava.converter.Populator;
import octava.converter.populator.ProjectPopulator;
import octava.converter.populator.ReverseProjectPopulator;
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

    @Bean(name = "reverseProjectConverter")
    public AbstractPopulatingConverter<ProjectDto, ProjectModel> reverseProjectConverter() {
        AbstractPopulatingConverter<ProjectDto, ProjectModel> converter = new AbstractPopulatingConverter(){};
        converter.setTargetClass(ProjectModel.class);
        converter.setPopulators(reverseProjectPopulators());
        return converter;
    }

    @Bean(name = "reverseProjectPopulator")
    public Populator<ProjectDto, ProjectModel> reverseProjectPopulator() {
        return new ReverseProjectPopulator();
    }

    private List<Populator<ProjectDto, ProjectModel>> reverseProjectPopulators() {
        return Lists.newArrayList(reverseProjectPopulator());
    }

    private List<Populator<ProjectModel, ProjectDto>> projectPopulators() {
        return Lists.newArrayList(projectPopulator());
    }
}

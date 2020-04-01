package octava.config;

import com.google.common.collect.Lists;
import octava.converter.AbstractPopulatingConverter;
import octava.converter.Populator;
import octava.converter.populator.ProjectPopulator;
import octava.converter.populator.ReverseProjectPopulator;
import octava.converter.populator.UserPopulator;
import octava.dto.ProjectDto;
import octava.dto.UserDto;
import octava.model.project.ProjectModel;
import octava.model.user.UserPrincipal;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BussinesBeans {

    @Bean(name = "projectConverter")
    public AbstractPopulatingConverter<ProjectModel, ProjectDto> projectConverter() {
        AbstractPopulatingConverter<ProjectModel, ProjectDto> converter = new AbstractPopulatingConverter(){};
        converter.setTargetClass(ProjectDto.class);
        converter.setPopulators(Lists.newArrayList(projectPopulator()));
        return converter;
    }

    @Bean(name = "projectPopulator")
    public Populator<ProjectModel, ProjectDto> projectPopulator() {
        return new ProjectPopulator();
    }

    @Bean(name = "userConverter")
    public AbstractPopulatingConverter<UserPrincipal, UserDto> userConverter() {
        AbstractPopulatingConverter<UserPrincipal, UserDto> converter = new AbstractPopulatingConverter(){};
        converter.setTargetClass(UserDto.class);
        converter.setPopulators(Lists.newArrayList(userPopulator()));
        return converter;
    }

    @Bean(name = "userPopulator")
    public Populator<UserPrincipal, UserDto> userPopulator() {
        return new UserPopulator();
    }

    @Bean(name = "reverseProjectConverter")
    public AbstractPopulatingConverter<ProjectDto, ProjectModel> reverseProjectConverter() {
        AbstractPopulatingConverter<ProjectDto, ProjectModel> converter = new AbstractPopulatingConverter(){};
        converter.setTargetClass(ProjectModel.class);
        converter.setPopulators(Lists.newArrayList(reverseProjectPopulator()));
        return converter;
    }

    @Bean(name = "reverseProjectPopulator")
    public Populator<ProjectDto, ProjectModel> reverseProjectPopulator() {
        return new ReverseProjectPopulator();
    }


//    @Bean(name = "hibernateInterceptorStrategies")
//    public Map<Class, EmptyInterceptor> hibernateInterceptorStrategies() {
//        final Map<Class, EmptyInterceptor> hibernateInterceptorStrategies = new HashMap<>();
//        hibernateInterceptorStrategies.putIfAbsent(ProjectModel.class, projectInterceptor());
//        return hibernateInterceptorStrategies;
//    }

//    @Bean(name = "projectInterceptor")
//    public EmptyInterceptor projectInterceptor() {
//        return new ProjectInterceptorImpl();
//    }
}

package octava.facade.impl;


import lombok.Data;
import octava.converter.AbstractPopulatingConverter;
import octava.dto.ProjectDto;
import octava.dto.RinexFileMediaDto;
import octava.facade.ProjectFacade;
import octava.model.project.ProjectModel;
import octava.model.rinex.RinexFileMediaModel;
import octava.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component("projectFacade")
public @Data class ProjectFacadeImpl implements ProjectFacade {

    private static final Logger LOG = LoggerFactory.getLogger(ProjectFacadeImpl.class);

    @Resource
    private ProjectService projectService;

    @Resource
    private RinexMediaService rinexMediaService;

    @Resource
    private StorageService<RinexFileMediaModel> rinexLocalStorage;

    @Resource
    private AbstractPopulatingConverter<RinexFileMediaModel, RinexFileMediaDto> rinexConverter;

    @Resource
    private AbstractPopulatingConverter<ProjectModel, ProjectDto> projectConverter;

    @Resource
    private AbstractPopulatingConverter<ProjectDto, ProjectModel> reverseProjectConverter;

    @Override
    public ProjectDto create(final ProjectDto project) {

        final ProjectModel projectModel = getReverseProjectConverter().convert(project);

        getProjectService().insert(projectModel);

        final ProjectDto projectDto = getProjectConverter().convert(projectModel);

        return projectDto;
    }

    @Override
    public ProjectDto update(final ProjectDto projectDto) {

        final String projectName = projectDto.getName();

        final ProjectModel projectModel = getProjectService().getProject(projectName);

        getReverseProjectConverter().convert(projectDto, projectModel);

        getProjectService().update(projectModel);

        final ProjectDto updatedProjectDto = getProjectConverter().convert(projectModel);

        return updatedProjectDto;
    }

    @Override
    public ProjectDto get(final String projectName) {

        final ProjectModel projectModel = getProjectService().getProject(projectName);

        final ProjectDto projectDto = getProjectConverter().convert(projectModel);

        return projectDto;
    }

    @Override
    public List<ProjectDto> getAll() {

        final List<ProjectModel> projectModels = getProjectService().getAll();

        final List<ProjectDto> projectDtos = getProjectConverter().convertAll(projectModels);

        return projectDtos;
    }


    @Override
    public ProjectDto addRinexFiles(ProjectDto project) {

        final ProjectModel projectModel = getProjectService().getProject(project.getName());

        final List<RinexFileMediaModel> storedRinexFiles = getRinexLocalStorage().store(project.getFiles());

        storedRinexFiles.forEach(getRinexMediaService()::insert);

        projectModel.setRinexFileMediaModels(storedRinexFiles);

        getProjectService().update(projectModel);

        final ProjectDto updatedProjectDto = getProjectConverter().convert(projectModel);

        return updatedProjectDto;
    }
}

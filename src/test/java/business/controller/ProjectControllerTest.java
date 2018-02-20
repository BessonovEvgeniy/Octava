package business.controller;

import business.model.project.Project;
import business.service.ProjectService;
import config.AppInitializer;
import config.HibernateConfiguration;
import config.MvcConfiguration;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppInitializer.class, MvcConfiguration.class, HibernateConfiguration.class})
@WebAppConfiguration
public class ProjectControllerTest {

    @Autowired
    private ProjectController controller;

    @Autowired
    private ProjectService service;

    @Test
    public void createNewEmptyProject() {
        Map<String, String> rinexFiles = new HashMap<>();
        rinexFiles.put("File Name Mock", "File Link Mock");

        Project project = new Project("Test Project");
        project.addRinexFiles(rinexFiles);

        Project readedProject =  new Project();

        BindingResult result = mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(false);

        try {
            controller.createProject(project, result);
            readedProject = service.getById(project.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assert.assertEquals(project.getCreated(), readedProject.getCreated());
        Assert.assertEquals(project.getName(), readedProject.getName());
        Assert.assertEquals(project.getRinexFiles(), readedProject.getRinexFiles());
    }

    @Test
    public void createNewProjectWithFailBindingResults() {
        BindingResult result = mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(true);

        String redirect = "";
        String etalonRedirectURL = "redirect:/create";
        try {
            redirect = controller.createProject(null, result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assert.assertEquals(redirect, etalonRedirectURL);
    }
}

package rinex.controller.rinex;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;

@Controller
@RequestMapping(value = {"/", "home"})
public class HomePageController {

    @RequestMapping
    public String getHomePage(Map<String, Object> model) {
        return "index";
    }
}

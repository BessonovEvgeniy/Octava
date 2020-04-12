package octava.controller;


import octava.dto.RinexFileMediaDto;
import octava.facade.RinexFacade;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.security.Principal;

@RestController
public class RinexController {

    @Resource
    private RinexFacade rinexFacade;

    @Resource
    private Validator rinexFileValidator;

    @InitBinder
    public void dataBinding(WebDataBinder binder) {
        binder.addValidators(rinexFileValidator);
    }

    @PostMapping(value = "/upload")
    @ResponseStatus(HttpStatus.OK)
    public RinexFileMediaDto uploadRinexFile(@RequestParam String projectName,
                                             @Valid @RequestParam(value = "file") MultipartFile file,
                                             final RedirectAttributes redirectAttributes,
                                             Principal principal) throws Exception {

        return rinexFacade.store(file);
    }
}

package octava.controller;

import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import octava.service.StorageService;

import javax.annotation.Resource;

@PropertySource({"classpath:rdbmsDev.properties", "classpath:security.properties"})
public class AbstractBusinessController extends AbstractController {

    @Resource
    private Environment env;

    protected HttpEntity<MultiValueMap<String, Object>> prepareHttpEntityForMultipartFile(MultipartFile file) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", file.getResource());

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        return requestEntity;
    }
}

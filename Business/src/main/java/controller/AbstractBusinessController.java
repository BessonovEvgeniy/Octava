package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestOperations;
import org.springframework.web.multipart.MultipartFile;

@PropertySource({"classpath:rdbmsDev.properties", "classpath:security.properties"})
public class AbstractBusinessController extends AbstractController {

    @Autowired
    private Environment env;

    protected HttpEntity<MultiValueMap<String, Object>> prepareHttpEntityForMultipartFile(MultipartFile file) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", file.getResource());

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        return requestEntity;
    }

    protected RestOperations rest(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.basicAuthentication(env.getProperty("rest.user"), env.getProperty("rest.password")).build();
    }

}

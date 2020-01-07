package octava.interceptor;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;

import static org.apache.commons.collections4.MapUtils.emptyIfNull;

public class RestTemplateApiCallInterceptor implements ClientHttpRequestInterceptor {

    private static final Logger LOG = LoggerFactory.getLogger(RestTemplateApiCallInterceptor.class);

    private static final String METHOD_MESSAGE = "Method type: {0}";
    private static final String BODY_MESSAGE = "Body: {0}";
    private static final String URL_MESSAGE = "URL: {0}";

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {

        ClientHttpResponse response = execution.execute(request, body);

        if (LOG.isDebugEnabled()) {
            final String decodedBody = ArrayUtils.isEmpty(body) ? "Empty body" : new String(body, StandardCharsets.UTF_8);

            emptyIfNull(response.getHeaders()).entrySet().stream()
                    .forEach(h -> LOG.info(h.getKey() + " : " + h.getValue().iterator().next())); //NOSONAR
            LOG.debug(MessageFormat.format(METHOD_MESSAGE, request.getMethod().name()));
            LOG.debug(MessageFormat.format(BODY_MESSAGE, decodedBody));
            LOG.debug(MessageFormat.format(URL_MESSAGE, request.getURI().toString()));
        }

        return response;
    }
}
package cl.tenpo.rest.api.client;

import cl.tenpo.rest.api.client.model.response.PercentageResponse;
import cl.tenpo.rest.api.model.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Objects;

@Component
@Slf4j
public class ExternalServiceClient {

    @Value("${external.service.url}")
    private String externalServiceUrl;

    @Value("${external.service.urn}")
    private String externalServiceUrn;

    private final RestTemplate restTemplate;
    private final RetryTemplate retryTemplate;

    public ExternalServiceClient(RestTemplate restTemplate, RetryTemplate retryTemplate) {
        this.restTemplate = restTemplate;
        this.retryTemplate = retryTemplate;
    }

    public PercentageResponse getPercentageFromWebClient() {
        var uri = UriComponentsBuilder
                .fromUriString(this.externalServiceUrl + this.externalServiceUrn)
                .buildAndExpand()
                .toUri();

        log.info("Get percentage from : " + uri.getHost() + uri.getPath());

        return retryTemplate.execute(context -> {
            PercentageResponse percentageResponse;

            try {
                percentageResponse = Objects.requireNonNull(restTemplate.getForEntity(uri, PercentageResponse.class).getBody());
            } catch (ResourceAccessException resourceAccessException) {
                var errorMessage = "No response from client service.";

                log.error(errorMessage, resourceAccessException);
                throw new ClientResourceAccessException(errorMessage);
            } catch (Exception exception) {
                var errorMessage = "The client responded with an error on the server.";

                log.error(errorMessage, exception);
                throw new ClientServerException(errorMessage);
            }
            return percentageResponse;
        });
    }
}
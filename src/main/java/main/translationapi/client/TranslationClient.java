package main.translationapi.client;

import lombok.Getter;
import main.translationapi.configuration.ApplicationConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Getter
@Configuration
public class TranslationClient {

    private final String baseUrl;
    private final String apiKey;

    public TranslationClient(ApplicationConfig applicationConfig) {

        baseUrl = applicationConfig.translatorUrl();
        apiKey = applicationConfig.translatorApiKey();
    }

    @Bean("translator")
    public RestTemplate getClient(){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate
                .getInterceptors()
                .add((request, body, execution) -> {
                    request.getHeaders().add("Authorization", "Api-Key " + apiKey);
                    return execution.execute(request, body);
                });
        return restTemplate;
    }
}

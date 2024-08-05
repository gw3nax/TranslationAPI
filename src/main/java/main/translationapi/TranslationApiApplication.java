package main.translationapi;

import main.translationapi.configuration.ApplicationConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationConfig.class)
public class TranslationApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(TranslationApiApplication.class, args);
    }

}

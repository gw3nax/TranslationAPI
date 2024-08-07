package main.translationapi;

import jakarta.servlet.http.HttpServletRequest;
import main.translationapi.dto.YandexApiRequest;
import main.translationapi.exception.LanguageNotFoundException;
import main.translationapi.mapper.YandexRequestMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;

import main.translationapi.client.TranslationClient;
import main.translationapi.service.TranslationService;
import main.translationapi.dto.TranslationRequest;
import main.translationapi.dto.TranslationResponse;

import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TranslationServiceTest {

    @Autowired
    private TranslationService translationService;

    @ParameterizedTest
    @MethodSource("provideInputForTestTranslte")
    public void testTranslate(TranslationRequest request, Object response) {


        TranslationResponse response1 = translationService.translate(request, null);

        assertNotNull(response);
        Assertions.assertEquals(response, response1.getTranslatedText());
    }
    private static Stream<Arguments> provideInputForTestTranslte() {
        return Stream.of(
                Arguments.of(new TranslationRequest("Hello, world!", "en", "ru"), new TranslationResponse("Здравствуйте, мир!")),
                Arguments.of(new TranslationRequest("Hello, world!", "", "ru"), new LanguageNotFoundException()),
                Arguments.of(new TranslationRequest("Hello, world!", "en", ""), new LanguageNotFoundException("Не найден язык, на который необходимо перевести текст."))
        );
    }
}

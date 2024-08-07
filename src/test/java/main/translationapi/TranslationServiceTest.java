package main.translationapi;

import main.translationapi.exception.LanguageNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import main.translationapi.service.TranslationService;
import main.translationapi.dto.TranslationRequest;
import main.translationapi.dto.TranslationResponse;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class TranslationServiceTest {

    @Autowired
    private TranslationService translationService;

    @ParameterizedTest
    @MethodSource("provideInputForTestTranslte")
    public void testTranslate(TranslationRequest request, TranslationResponse response, Class<? extends RuntimeException> expectedException) {

        if (expectedException != null){
            Assertions.assertThrows(expectedException, () -> translationService.translate(request, null));
            return;
        }

        TranslationResponse response1 = translationService.translate(request, null);
        assertNotNull(response);
        Assertions.assertEquals(response.getTranslatedText(), response1.getTranslatedText());
    }
    private static Stream<Arguments> provideInputForTestTranslte() {
        return Stream.of(
                Arguments.of(new TranslationRequest("Hello, world!", "en", "ru"), new TranslationResponse("Здравствуйте, мир!"), null),
                Arguments.of(new TranslationRequest("Hello, world!", "", "ru"), null, LanguageNotFoundException.class),
                Arguments.of(new TranslationRequest("Hello, world!", "en", ""),  null, LanguageNotFoundException.class)
        );
    }
}

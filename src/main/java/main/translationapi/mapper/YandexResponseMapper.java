package main.translationapi.mapper;

import main.translationapi.dto.TranslationResponse;
import main.translationapi.dto.YandexApiResponse;
import org.springframework.stereotype.Component;

@Component
public class YandexResponseMapper {

    public TranslationResponse mapToTranslationResponse(YandexApiResponse yandexApiResponse){

        TranslationResponse translationResponse = new TranslationResponse();
        translationResponse.setTranslatedText(yandexApiResponse.getTranslations()[0].getText());
        return translationResponse;
    }
}

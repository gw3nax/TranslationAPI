package main.translationapi.mapper;

import main.translationapi.dto.TranslationRequest;
import main.translationapi.dto.YandexApiRequest;
import org.springframework.stereotype.Component;

@Component
public class YandexRequestMapper {
    public YandexApiRequest mapToYandexRequest(TranslationRequest translationRequest){

        YandexApiRequest yandexApiRequest = new YandexApiRequest();
        yandexApiRequest.setTexts(new String[]{translationRequest.getTextToTranslate()});
        yandexApiRequest.setSourceLanguageCode(translationRequest.getOriginalLang());
        yandexApiRequest.setTargetLanguageCode(translationRequest.getTranslatedLang());
        return yandexApiRequest;
    }
}

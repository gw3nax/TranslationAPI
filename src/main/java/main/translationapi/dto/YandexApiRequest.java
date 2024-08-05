package main.translationapi.dto;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
public class YandexApiRequest {
    String sourceLanguageCode;
    String targetLanguageCode;
    @NonNull
    String[] texts;

}

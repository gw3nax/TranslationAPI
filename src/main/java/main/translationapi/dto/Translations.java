package main.translationapi.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Translations {
    String text;
    String detectedLanguageCode;
}

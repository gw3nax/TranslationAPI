package main.translationapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class LanguageNotFoundException extends RuntimeException {
    public LanguageNotFoundException() {
        super("Не найден язык исходного сообщения");
    }

    public LanguageNotFoundException(String message) {
        super(message);
    }
}
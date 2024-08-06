package main.translationapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TranslationResourceAccessException extends RuntimeException {
    public TranslationResourceAccessException() {
        super("Ошибка доступа к ресурсу перевода");
    }

    public TranslationResourceAccessException(String message) {
        super(message);
    }
}


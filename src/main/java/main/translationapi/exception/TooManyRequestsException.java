package main.translationapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TooManyRequestsException extends RuntimeException{

    public TooManyRequestsException() {
        super("Клиент превысил лимит запросов.\n");
    }
}

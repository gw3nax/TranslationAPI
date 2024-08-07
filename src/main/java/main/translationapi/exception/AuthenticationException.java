package main.translationapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AuthenticationException extends RuntimeException{
    public AuthenticationException(){
        super("Для выполнения операции необходима авторизация. Возможно, ваш API-ключ недействителен.");
    }

}

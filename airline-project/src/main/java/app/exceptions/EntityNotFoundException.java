package app.exceptions;

import microservice.starter.exceptions.BusinessException;
import org.springframework.http.HttpStatus;

public class EntityNotFoundException extends BusinessException {

    public EntityNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
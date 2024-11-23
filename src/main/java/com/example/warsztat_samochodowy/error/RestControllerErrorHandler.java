package com.example.warsztat_samochodowy.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class RestControllerErrorHandler {

    @ExceptionHandler(KlientAlreadyExistError.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse klientAlreadyExist(KlientAlreadyExistError exception) {
        String message = exception.getMessage();
        return new ErrorResponse(message, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(PojazdAlreadyExistError.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse pojazdAlreadyExist(PojazdAlreadyExistError exception) {
        String message = exception.getMessage();
        return new ErrorResponse(message, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MechanikAlreadyExistError.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse mechanikAlreadyExist(MechanikAlreadyExistError exception) {
        String message = exception.getMessage();
        return new ErrorResponse(message, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(KlientNotFoundError.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse klientNotFoundError(KlientNotFoundError exception) {
        String message = exception.getMessage();
        return new ErrorResponse(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MechanikNotFoundError.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse mechanikNotFoundError(MechanikNotFoundError exception) {
        String message = exception.getMessage();
        return new ErrorResponse(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PojazdNotFoundError.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse pojazdNotFoundError(PojazdNotFoundError exception) {
        String message = exception.getMessage();
        return new ErrorResponse(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NaprawaNotFoundError.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse naprawaNotFoundError(NaprawaNotFoundError exception) {
        String message = exception.getMessage();
        return new ErrorResponse(message, HttpStatus.NOT_FOUND);
    }
}

package com.example.autorepairshop.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class RestControllerErrorHandler {

    @ExceptionHandler(ClientAlreadyExistException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse klientAlreadyExist(ClientAlreadyExistException exception) {
        String message = exception.getMessage();
        return new ErrorResponse(message, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CarAlreadyExistException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse pojazdAlreadyExist(CarAlreadyExistException exception) {
        String message = exception.getMessage();
        return new ErrorResponse(message, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MechanicAlreadyExistException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse mechanikAlreadyExist(MechanicAlreadyExistException exception) {
        String message = exception.getMessage();
        return new ErrorResponse(message, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ClientNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse klientNotFoundError(ClientNotFoundException exception) {
        String message = exception.getMessage();
        return new ErrorResponse(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MechanicNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse mechanikNotFoundError(MechanicNotFoundException exception) {
        String message = exception.getMessage();
        return new ErrorResponse(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CarNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse pojazdNotFoundError(CarNotFoundException exception) {
        String message = exception.getMessage();
        return new ErrorResponse(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RepairNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse naprawaNotFoundError(RepairNotFoundException exception) {
        String message = exception.getMessage();
        return new ErrorResponse(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EndDateBeforeDateStartsException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ErrorResponse endDateBeforeDateStartsException(EndDateBeforeDateStartsException exception) {
        String message = exception.getMessage();
        return new ErrorResponse(message, HttpStatus.NOT_ACCEPTABLE);
    }
}

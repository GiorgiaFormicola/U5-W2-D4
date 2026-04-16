package GiorgiaFormicola.U5_W2_D4.exceptions;

import GiorgiaFormicola.U5_W2_D4.payloads.ErrorPayload;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.core.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ErrorsHandler {
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorPayload handleBadRequest(BadRequestException ex) {
        return new ErrorPayload(ex.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorPayload handleBadRequest(NotFoundException ex) {
        return new ErrorPayload(ex.getMessage(), LocalDateTime.now());
    }

    //Per queryParam non corrispondente a proprietà
    @ExceptionHandler(PropertyReferenceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorPayload handleBadRequest(PropertyReferenceException ex) {
        return new ErrorPayload("Not valid search param.", LocalDateTime.now());
    }

    //Per formato UUID errato
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorPayload handleBadRequest(MethodArgumentTypeMismatchException ex) {
        return new ErrorPayload("Not valid ID provided.", LocalDateTime.now());
    }

    //Per valori del Json di cui non riesce a fare il parse
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorPayload handleBadRequest(HttpMessageNotReadableException ex) {
        return new ErrorPayload("Not valid value provided.", LocalDateTime.now());
    }

    //Per violazione length varchar
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorPayload handleBadRequest(DataIntegrityViolationException ex) {
        return new ErrorPayload("Not valid value provide", LocalDateTime.now());
    }
}

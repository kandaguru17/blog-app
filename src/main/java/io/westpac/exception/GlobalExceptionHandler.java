package io.westpac.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@RestController
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({NotFoundException.class,IllegalArgumentException.class})
    public final ResponseEntity<Object> handleBadRequests(Exception e) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        return getResponseEntityException(e, httpStatus);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleGenericException(Exception e) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        return getResponseEntityException(e, httpStatus);
    }

    private ResponseEntity<Object> getResponseEntityException(Exception e, HttpStatus httpStatus) {
        AppExceptionResponse appExceptionResponse = new AppExceptionResponse(e.getMessage(), httpStatus);
        return ResponseEntity.badRequest().body(appExceptionResponse);
    }
}

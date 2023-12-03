package pl.krzychuuweb.labelapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
class GlobalExceptionsHandler {

    @ExceptionHandler(AlreadyExistsException.class)
    ResponseEntity<ResponseErrorMessage> handleAlreadyExistsException(RuntimeException ex) {
        ResponseErrorMessage message = new ResponseErrorMessage(
                ex.getMessage(),
                HttpStatus.CONFLICT
        );

        return new ResponseEntity<>(message, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NotFoundException.class)
    ResponseEntity<ResponseErrorMessage> handleNotFoundException(RuntimeException ex) {
        ResponseErrorMessage message = new ResponseErrorMessage(
                ex.getMessage(),
                HttpStatus.NOT_FOUND
        );

        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AccessDeniedException.class)
    ResponseEntity<ResponseErrorMessage> handleAccessDeniedException(AccessDeniedException ex) {
        ResponseErrorMessage message = new ResponseErrorMessage(
                ex.getMessage(),
                HttpStatus.FORBIDDEN
        );

        return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);
    }


    @ExceptionHandler(BadRequestException.class)
    ResponseEntity<ResponseErrorMessage> handleBadRequestException(RuntimeException ex) {
        ResponseErrorMessage message = new ResponseErrorMessage(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST
        );

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}

package pl.krzychuuweb.labelapp.exception;

import org.springframework.http.HttpStatus;

import java.util.Date;

public class ResponseErrorMessage {

    private final String message;
    private final Date timestamp;
    private final HttpStatus status;

    public ResponseErrorMessage(final String message, final HttpStatus status) {
        this.message = message;
        this.timestamp = new Date();
        this.status = status;
    }

    String getMessage() {
        return message;
    }

    Date getTimestamp() {
        return timestamp;
    }

    HttpStatus getStatus() {
        return status;
    }
}

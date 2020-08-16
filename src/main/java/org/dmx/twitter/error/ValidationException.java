package org.dmx.twitter.error;

public class ValidationException extends RuntimeException {

    private Errors errors;

    public ValidationException(String message, Errors errors) {
        super(message);
        this.errors = errors;
    }

    public ValidationException(String message, Errors errors, Throwable cause) {
        super(message, cause);
        this.errors = errors;
    }

    public Errors getError() {
        return errors;
    }
}

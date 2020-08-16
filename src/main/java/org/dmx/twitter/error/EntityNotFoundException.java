package org.dmx.twitter.error;

public class EntityNotFoundException extends RuntimeException {

    private Errors errors;

    public EntityNotFoundException(String message, Errors errors) {
        super(message);
        this.errors = errors;
    }

    public EntityNotFoundException(String message, Errors errors, Throwable cause) {
        super(message, cause);
        this.errors = errors;
    }

    public Errors getError() {
        return errors;
    }
}

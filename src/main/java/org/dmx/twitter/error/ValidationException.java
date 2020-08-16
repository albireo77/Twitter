package org.dmx.twitter.error;

public class ValidationException extends RuntimeException {

    private TwitterError error;

    public ValidationException(String message, TwitterError error) {
        super(message);
        this.error = error;
    }

    public ValidationException(String message, TwitterError error, Throwable cause) {
        super(message, cause);
        this.error = error;
    }

    public TwitterError getError() {
        return error;
    }
}

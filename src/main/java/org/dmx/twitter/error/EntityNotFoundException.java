package org.dmx.twitter.error;

public class EntityNotFoundException extends RuntimeException {

    private TwitterError error;

    public EntityNotFoundException(String message, TwitterError error) {
        super(message);
        this.error = error;
    }

    public EntityNotFoundException(String message, TwitterError error, Throwable cause) {
        super(message, cause);
        this.error = error;
    }

    public TwitterError getError() {
        return error;
    }
}

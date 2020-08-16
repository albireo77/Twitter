package org.dmx.twitter.error;

public enum Errors {
    USER_NOT_FOUND(1),
    TWEET_TEXT_EMPTY(2),
    TWEET_TEXT_TOO_LONG(3);

    private int code;

    Errors(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static Errors findByCode(int code) {
        for (Errors errors : values()) {
            if (errors.getCode() == code) {
                return errors;
            }
        }
        return null;
    }
}

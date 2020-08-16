package org.dmx.twitter.error;

public enum TwitterError {
    USER_NOT_FOUND(1),
    TWEET_TEXT_EMPTY(2),
    TWEET_TEXT_TOO_LONG(3);

    private int code;

    TwitterError(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static TwitterError findByCode(int code) {
        for (TwitterError error : values()) {
            if (error.getCode() == code) {
                return error;
            }
        }
        return null;
    }
}

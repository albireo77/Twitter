package org.dmx.twitter.error;

public class ApplicationError {

    String errorMessage;
    int errorCode;

    public ApplicationError() {}

    public ApplicationError(String errorMessage, int errorCode) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }
}

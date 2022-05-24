package eus.ecrop.api.exception;

public class MissingTokenException extends RuntimeException {

    public MissingTokenException(String message) {
        super(message);
    }

}

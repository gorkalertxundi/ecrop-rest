package eus.ecrop.api.exception;

/**
 * Custom exception that is thrown when a JWT token is missing
 */
public class MissingTokenException extends RuntimeException {

    public MissingTokenException(String message) {
        super(message);
    }

}

package eus.ecrop.api.exception;

public class UserNotFoundException extends NotFoundException {

    private static final long serialVersionUID = 1L;

    public UserNotFoundException(Long id) {
        super("Could not find user " + id);
    }

}

package nl.fontys.s3.bvbforum.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserUsernameAlreadyExistsException extends ResponseStatusException {
    public UserUsernameAlreadyExistsException() { super(HttpStatus.BAD_REQUEST, "USERNAME_EXISTS"); }
}

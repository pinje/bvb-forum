package nl.fontys.s3.bvbforum.business.exception.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserIdAlreadyExistsException extends ResponseStatusException {
    public UserIdAlreadyExistsException() { super(HttpStatus.BAD_REQUEST, "USER_ALREADY_EXISTS"); }
}

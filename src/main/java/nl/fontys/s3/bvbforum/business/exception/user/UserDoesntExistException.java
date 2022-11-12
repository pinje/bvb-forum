package nl.fontys.s3.bvbforum.business.exception.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserDoesntExistException extends ResponseStatusException {
    public UserDoesntExistException() {
        super(HttpStatus.BAD_REQUEST, "USER_DOESNT_EXIST");
    }
}

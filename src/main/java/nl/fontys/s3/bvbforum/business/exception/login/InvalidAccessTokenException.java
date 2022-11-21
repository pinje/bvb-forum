package nl.fontys.s3.bvbforum.business.exception.login;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidAccessTokenException extends ResponseStatusException {
    public InvalidAccessTokenException(String errorCause) {
        super(HttpStatus.UNAUTHORIZED, errorCause);
    }
}

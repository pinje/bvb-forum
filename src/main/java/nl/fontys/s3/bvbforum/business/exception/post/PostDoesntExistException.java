package nl.fontys.s3.bvbforum.business.exception.post;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PostDoesntExistException extends ResponseStatusException {
    public PostDoesntExistException() { super(HttpStatus.BAD_REQUEST, "POST_DOESNT_EXIST"); }
}
